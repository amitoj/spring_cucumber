/*
 * Copyright 2014 Hotwire. All Rights Reserved. *
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.billing.BillingTermsPopupWindow;
import com.hotwire.selenium.desktop.us.billing.NotificationBillingPage;
import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarAdditionalFeatures;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarBillingPage;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarDetails;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarPaymentMethod;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarPurchaseReview;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarTravelerInfo;
import com.hotwire.selenium.desktop.us.billing.car.impl.ccf.CcfDetailsFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.fragments.CarBillingHelpCenterFragment;
import com.hotwire.selenium.desktop.us.billing.car.impl.onepage.OpPaymentMethodFragment;
import com.hotwire.selenium.desktop.us.confirm.CarConfirmationPage;
import com.hotwire.selenium.desktop.us.details.CarDetailsPage;
import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPageProvider;
import com.hotwire.selenium.desktop.us.results.car.impl.CcfPage;
import com.hotwire.test.steps.account.AccountAccessModel;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.car.entities.VendorGridEntity;
import com.hotwire.test.steps.search.car.CarSearchParametersImpl;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.car.CarsDao;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.PageName;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.transformer.jchronic.ChronicConverter;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
/**
 */
public class CarPurchaseModelWebApp extends CarPurchaseModelTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarPurchaseModelWebApp.class.getName());
    private static final String CAR_OPTIONS_MESSAGE = "%OPT% doesn't equal";
    private static final String CPV_ERROR_CC_NUMBER = "4831110847500000";
    private static final String AVS_ERROR_CC_NUMBER = "4001277777777777";
    private static final String AUTH_ERROR_CC_NUMBER = "4005519255555555";
    private static final String SOLDOUT_PROPERTY = "hotwire.biz.search.car.forceSoldOutOnPriceCheck";
    private static final Pattern US_PHONE_PATTERN =
        Pattern.compile("(1-)?\\d{3}-\\d{3}-\\d{4}", Pattern.MULTILINE | Pattern.CANON_EQ);
    private static final Pattern US_REF_NUMBER =
            Pattern.compile("(\\d{4,})", Pattern.MULTILINE | Pattern.CANON_EQ);

    // Regulations which should be displayed in "Agree and book" block
    // car_service : is_debit_card_ok_for_deposit = 'N', accepts_local_debit_card = 'N'
    private static final String RULE_CREDIT_CARD_COPY = "The agency requires a credit card in the driver's name " +
            "for deposit. The amount varies and can't be used on your card until you return the car. " +
            "Agency does not accept debit/check cards for deposit.";
    // car_service : is_debit_card_ok_for_deposit = 'Y', accepts_local_debit_card = 'N'
    private static final String RULE_CREDIT_DEBIT_CARD_WITH_TICKET = "The agency requires a credit/debit card " +
            "in the driver's name for deposit. " +
            "The amount varies and can't be used on your card until you return the car. " +
            "To use debit, the agency requires proof of a round-trip travel ticket.";
    // car_service : is_debit_card_ok_for_deposit = 'Y', accepts_local_debit_card = 'Y'
    private static final String RULE_ALL_CARD_TYPES = "The agency requires a credit/debit card in the driver's name " +
             "for deposit. The amount varies and can't be used on your card until you return the car.";

    @Resource(name = "canadianZipCode")
    private String canadianZipCode;

    /** Types of car pages where script is getting a data*/
    private static enum Pages {
        CONFIRMATION, BILLING
    }

    @Resource
    private Properties applicationProperties;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParametersImpl carSearchParameters;

    @Autowired
    @Qualifier("accountAccess")
    private AccountAccessModel accountAccessModel;

    @Autowired
    @Qualifier("authenticatedUser")
    private UserInformation authenticatedUser;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier ("randomGuestUser")
    private UserInformation randomGuestUser;

    @Autowired
    @Qualifier("randomGuestUser")
    private UserInformation questUser;

    @Autowired
    @Qualifier(value = "payableUser")
    private UserInformation payableUser;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    private CarSolutionModel resultsCarSolutionModel;

    //Basic select result method. Grabs info from purchase parameter
    @Override
    public void selectPGood() {
        //if soldout property is on, itinerary not available case is expected for all opaque cars
        String soldoutPropertyValue = applicationProperties.getProperty(SOLDOUT_PROPERTY, "false");
        if (soldoutPropertyValue.equalsIgnoreCase("true")) {
            LOGGER.info(
                    "Property " + SOLDOUT_PROPERTY + " is turned on so itinerary not being available is expected");
        }

        resultsCarSolutionModel =
                CarResultsPageProvider.get(getWebdriverInstance())
                .getResult()
                .opaqueOrRetail(purchaseParameters.getOpaqueRetail())
                .select();

        purchaseParameters.setOpaqueRetail(resultsCarSolutionModel.isOpaque() ? "opaque" : "retail");
        carSearchParameters.setSelectedSearchSolution(resultsCarSolutionModel);
    }

    //Select airport or local result.
    @Override
    public void selectCarByCdCodeAndAirportOrLocalLocation(String resultType, String carCdCode) {
        if (resultType.equalsIgnoreCase("airport")) {
            resultsCarSolutionModel =
                    CarResultsPageProvider.get(getWebdriverInstance())
                    .getResult()
                    .withAirportLocation()
                    .withCarTypeCode(carCdCode)
                    .select();
        }
        else {
            resultsCarSolutionModel =
                    CarResultsPageProvider.get(getWebdriverInstance())
                    .getResult()
                    .withCarTypeCode(carCdCode)
                    .select();
        }
        carSearchParameters.setSelectedSearchSolution(resultsCarSolutionModel);
    }

    @Override
    public void selectPGood(String carCdCode) {
        resultsCarSolutionModel =
                CarResultsPageProvider.get(getWebdriverInstance())
                    .getResult()
                    .withAirportLocation()
                    .withCarTypeCode(carCdCode)
                    .select();

        purchaseParameters.setOpaqueRetail(resultsCarSolutionModel.isOpaque() ?
                "opaque" : "retail");
        carSearchParameters.setSelectedSearchSolution(resultsCarSolutionModel);

    }

    @Override
    public void selectPGood(String carType, String carCdCode) {
        resultsCarSolutionModel =
                CarResultsPageProvider.get(getWebdriverInstance())
                        .getResult()
                        .opaqueOrRetail(carType)
                        .withCarTypeCode(carCdCode)
                        .select();

        purchaseParameters.setOpaqueRetail(carType);
        carSearchParameters.setSelectedSearchSolution(resultsCarSolutionModel);

    }

    // Processing through car's details page.
    // Selecting vendor offer from grid (by default vendor with lowest price is selected)
    @Override
    public void processCarDetailsPage() {
        StringBuilder sb = new StringBuilder("PROCESSING CAR DETAILS...");
        if ("retail".equals(purchaseParameters.getOpaqueRetail())) {

            List<VendorGridEntity> entities = getAvailableVendorEntitiesFromDetails();
            if (entities.size() > 1) {
                VendorGridEntity en = entities.get(0);
                sb.append("\n" + en + " VENDOR WITH LOWEST TOTAL PRICE WAS SELECTED..");
                selectVendorEntityOnDetails(en);
            }
            LOGGER.info(sb.toString());
        }

    }

    @Override
    public void continueToBillingFromDetails() {
        // Check notify page for inventory change.
        if (new PageName().apply(getWebdriverInstance()).contains("notify")) {
            // message specific for inventory issues and get rid of verification by String value.
            List<WebElement> elements = getWebdriverInstance().findElements(By.cssSelector(" .ml15"));
            if (elements.size() > 0) {
                String msg = elements.get(0).getText();
                if (msg.contains("itinerary you selected is no longer available") || msg.contains("#1324")) {
                    // App is working in terms of UI level. To determine if this actually has occurred from an inventory
                    // being sold out or whatever needs to be done in a unit test or integration test.
                    throw new PendingException(
                            "Issue completing booking purchase confirmation. Details:\n" + msg +
                                    "\nIf this is consistently happening, notify car rental devs " +
                                    "to investigate inventory issues."
                    );
                }
            }
        }
        else if (new PageName().apply(getWebdriverInstance()).contains("refresh")) {
            String msg = getWebdriverInstance().findElement(By.cssSelector(".unavailableMessage h2")).getText();
            if (msg.contains("The car you selected is unavailable")) {
                throw new PendingException("The itinerary selected in no longer available.");
            }
        }
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        carDetails.continuePanel();
    }

    @Override
    public void waitForPriceCheckAfterContinue() {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        carDetails.priceCheckAfterContinue();
    }

    @Override
    public void returnToDetailsFromBilling() {
        LOGGER.info("CHANGING VENDOR FOR RETAIL CAR..");
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        carDetails.goBack();
    }

    @Override
    public void verifyBillingPage() {
        LOGGER.info("Returning to Billing page");
        CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment();
    }

    @Override
    public void saveCarConfirmationInformation() {
        CarSolutionModel carConfirmationInformation = getCarSolutionFromPage(Pages.CONFIRMATION);
        carSearchParameters.setSelectedSearchSolution(carConfirmationInformation);
    }

    // Method compares car options (daily price, total price, car model, car name, capacities)
    // of selected solution from results page with the same options from confirmation
    @Override
    public void compareSolutionOptionsWithConfirmation() {
        CarSolutionModel carConfirmationOptionSet = getCarSolutionFromPage(Pages.CONFIRMATION);
        LOGGER.info("The next car options was presented on confirmation page " + carConfirmationOptionSet);
        comparePrimaryCarOptions(resultsCarSolutionModel, carConfirmationOptionSet);
        assertThat(resultsCarSolutionModel.getCarModels())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "CAR_MODELS"))
                .isEqualTo(carConfirmationOptionSet.getCarModels());
        assertThat(resultsCarSolutionModel.getMileage())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "MILEAGE"))
                .isEqualTo(carConfirmationOptionSet.getMileage());
        assertThat(resultsCarSolutionModel.getPeopleCapacity())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "PEOPLE_CAPACITY"))
                .isEqualTo(carConfirmationOptionSet.getPeopleCapacity());
        assertThat(resultsCarSolutionModel.getLargePackageCapacity())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "LARGE_PACKAGE_CAPACITY"))
                .isEqualTo(carConfirmationOptionSet.getLargePackageCapacity());
        assertThat(resultsCarSolutionModel.getSmallPackageCapacity())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "SMALL_PACKAGE_CAPACITY"))
                .isEqualTo(carConfirmationOptionSet.getSmallPackageCapacity());
    }

    // Method compares car options (daily price, total price, car name, currency)
    // of current solution from results page with the same options on details page
    @Override
    public void compareSolutionOptionsWithDetails() {
        CarSolutionModel carBillingOptionSet = getCarSolutionFromPage(Pages.BILLING);
        LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSet);
        comparePrimaryCarOptions(resultsCarSolutionModel, carBillingOptionSet);
    }

    @Override
    public void verifyGuaranteedCarSection() {
        CarPurchaseReview carPurchaseReview = CarBillingPageProvider
                .get(getWebdriverInstance()).getCarPurchaseReviewFragment();
        List<String> stringList = carPurchaseReview.getGuarantees();
        String patternFirst = "Agency is revealed immediately after you book.";
        String patternSecond = "And that's why you save big with Hotwire Hot Rates.";
        assertThat(stringList.get(0)).as("First string in Guarantees block is invalid").isEqualTo(patternFirst);
        assertThat(stringList.get(1)).as("Second string in Guarantees block is invalid").isEqualTo(patternSecond);
        assertThat(carPurchaseReview.isImgWithLogosDisplayed())
                .as("Img with logos wasn't presented in Guarantees block").isTrue();
    }

    @Override
    public void verifyHeaderForCarDetailsPage() {
        GlobalHeader globalHeader = new GlobalHeader(getWebdriverInstance());
        assertThat(globalHeader.getActiveVertical())
                .as("Active vertical is not Cars!").isEqualToIgnoringCase("Cars");
        CarDetailsPage carDetailsPage = new CarDetailsPage(getWebdriverInstance());
        assertThat(carDetailsPage.isAboutCarRentalLogoDisplayed())
                .as("About your car rental img wasn't presented").isTrue();
    }

    @Override
    public void compareVendorGridWithTripSummary() {
        CarSolutionModel carBillingOptionSet = getCarSolutionFromPage(Pages.BILLING);
        LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSet);

        List<VendorGridEntity> vendorGridEntities = new ArrayList<VendorGridEntity>();
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();

        if (carDetails.getVendorGridEntities().size() > 0) {
            for (String[] elm : carDetails.getVendorGridEntities()) {
                VendorGridEntity entity = new VendorGridEntity(elm);
                vendorGridEntities.add(entity);
            }
            assertThat(carBillingOptionSet.getVendor())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "vendor"))
                .isEqualTo(((VendorGridEntity) vendorGridEntities.toArray()[0]).getVendor());
            assertThat(carBillingOptionSet.getTotalPrice())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%" , "Total price"))
                .isEqualTo(((VendorGridEntity) vendorGridEntities.toArray()[0]).getTotalPrice());
            assertThat(carBillingOptionSet.getPerDayPrice())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "per day price"))
                .isEqualTo(((VendorGridEntity) vendorGridEntities.toArray()[0]).getPerDayPrice());

            selectVendorEntityOnDetails((VendorGridEntity) vendorGridEntities.toArray()[1]);
            CarSolutionModel carBillingOptionSetSecond = getCarSolutionFromPage(Pages.BILLING);
            LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSetSecond);

            assertThat(carBillingOptionSetSecond.getVendor())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "vendor"))
                .isEqualTo(((VendorGridEntity) vendorGridEntities.toArray()[1]).getVendor());
            assertThat(carBillingOptionSetSecond.getTotalPrice())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "Total price"))
                .isEqualTo(((VendorGridEntity) vendorGridEntities.toArray()[1]).getTotalPrice());
            assertThat(carBillingOptionSetSecond.getPerDayPrice())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "per day price"))
                .isEqualTo(((VendorGridEntity) vendorGridEntities.toArray()[1]).getPerDayPrice());
            assertThat(resultsCarSolutionModel.getTotalPrice())
                .as("Calculation of total price is not equal to total price of Trip Summary")
                .isEqualTo(((VendorGridEntity) vendorGridEntities.toArray()[1]).getTotalPrice());
        }
        else {
            LOGGER.info("There is only 1 vendor being displayed");
        }
    }

    @Override
    public void compareTotalBetweenReviewModuleAndStipSummary() {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        CarSolutionModel ppp = carDetails.getCarOptionsSet();
        Float detailsPrice =  ppp.getTotalPrice();
        Float resultsPrice = carSearchParameters.getSelectedSearchSolution().getTotalPrice();
        assertThat(detailsPrice).as("Total prices on details and results pages are not the same")
                .isEqualTo(resultsPrice);
    }

    @Override
    public void verifyLocationsForOpaqueCarWithDB() {
        CarSolutionModel carBillingOptionSet = getCarSolutionFromPage(Pages.BILLING);
        LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSet);
        String displayNumber = carSearchParameters.getSelectedSearchSolution().getDisplayNumber();
        String distance = carBillingOptionSet.getDistance();
        switch (distance) {
            case "In Terminal":
                distance = "1"; break;
            case "Counter in airport; Shuttle to car":
                distance = "2";
                break;
            case "On Airport - Shuttle to Vendor":
                distance = "3";
                break;
            case "Off Airport - Shuttle to Vendor":
                distance = "4";
                break;
            case "Contact Vendor for Location":
                distance = "0";
                break;
            default:
                break;
        }
        List<Map<String, Object>> queryResult =
                new CarsDao(getDataBaseConnection()).getVendorLocationTypeDataByDisplayNumber(displayNumber);
        BigDecimal bigDecimalDistance = new BigDecimal(distance);
        BigDecimal dbDistance = new BigDecimal(queryResult.get(0).get("CAR_VENDOR_LOCATION_TYPE").toString());
        assertThat(bigDecimalDistance).as("Distance is not equal to DB value").isEqualTo(dbDistance);
    }

    @Override
    public void verifyStatusAndNetworkCodesForFailedCarPurchaseWithDB() {
        String displayNumber = purchaseParameters.getDisplayNumber();
        List<Map<String, Object>> queryResult =
                new CarsDao(getDataBaseConnection()).getStatusAndNetworkCodesForFailedPurchase(displayNumber);
        assertThat(queryResult.get(0).get("STATUS_CODE"))
                .as("STATUS CODE is not 30207").isEqualTo(new BigDecimal(30207));
        assertThat(queryResult.get(0).get("NETWORK_STATUS_CODE"))
                .as("NETWORK STATUS is not max payment methods used")
                .isEqualTo("max payment methods used");
    }

    @Override
    public void verifyAirportLocationsForOpaqueCarWithDB() {
        String distance = carSearchParameters.getSelectedSearchSolution().getDistance();
        String airport = carSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        switch (distance) {
            case "In Terminal":
                distance = "1";
                break;
            case "Counter in airport -  Shuttle to car":
                distance = "2";
                break;
            case "On Airport - Shuttle to Vendor":
                distance = "3";
                break;
            case "Off Airport - Shuttle to Vendor":
                distance = "4";
                break;
            case "Location Off Airport - Double Shuttle to Vendor":
                distance = "5";
                break;
            default:
                distance = "0";
        }

        List<Map<String, Object>> queryResult =
                new CarsDao(getDataBaseConnection()).getVendorLocationTypeData(airport);
        BigDecimal bigDecimalDistance = new BigDecimal(distance);
        BigDecimal dbDistance = new BigDecimal(queryResult.get(3).get("CAR_VENDOR_LOCATION_TYPE").toString());
        assertThat(bigDecimalDistance).as("Distance is not equal to DB value").isEqualTo(dbDistance);
    }

    @Override
    public void verifyLocationsInRetailGridWithDB() {
        CarSolutionModel carBillingOptionSet = getCarSolutionFromPage(Pages.BILLING);
        LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSet);
        Integer locationsVerified = 0;
        List<VendorGridEntity> vendorGridEntities = new ArrayList<VendorGridEntity>();

        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        for (String[] elm : carDetails.getVendorGridEntities()) {
            VendorGridEntity entity = new VendorGridEntity(elm);
            vendorGridEntities.add(entity);
        }
        //change vendorGridEntities locations according to DB values
        for (int i = 0; i < vendorGridEntities.size(); i++) {
            switch (vendorGridEntities.get(i).getLocation()) {
                case "In Terminal":
                    vendorGridEntities.get(i).setLocation("1");
                    break;
                case "Counter in airport; Shuttle to car":
                    vendorGridEntities.get(i).setLocation("2");
                    break;
                case "On Airport - Shuttle to Vendor":
                    vendorGridEntities.get(i).setLocation("3");
                    break;
                case "Off Airport - Shuttle to Vendor":
                    vendorGridEntities.get(i).setLocation("4");
                    break;
                case "Contact Vendor for Location":
                    vendorGridEntities.get(i).setLocation("0");
                    break;
                default:
                    break;
            }
        }
        List<Map<String, Object>> queryResult =
                new CarsDao(getDataBaseConnection()).getVendorsData(searchParameters.getDestinationLocation());
        //Find this values in query results
        for (int i = 0; i < vendorGridEntities.size(); i++) {
            for (int j = 0; j < queryResult.size(); j++) {
                if (vendorGridEntities.get(i).getVendor()
                        .equalsIgnoreCase(queryResult.get(j).get("VENDOR_NAME").toString())) {
                    BigDecimal detailsPageLocations = new BigDecimal(vendorGridEntities.get(i).getLocation());
                    BigDecimal dbLocation = new BigDecimal(queryResult.get(j)
                            .get("CAR_VENDOR_LOCATION_TYPE").toString());
                    assertThat(detailsPageLocations)
                            .as("Location type is not equal to DB value").isEqualTo(dbLocation);
                    locationsVerified++;
                }
            }
        }
        assertThat(locationsVerified)
                .as("Not all locations verified. Expected" + vendorGridEntities.size() + ".Actual:" + locationsVerified)
                .isEqualTo(vendorGridEntities.size());
    }

    @Override
    public void verifyTripSummaryAfterBooking() {
        processAllBillingInformation(false);
        CarSolutionModel carBillingOptionSet = getCarSolutionFromPage(Pages.BILLING);
        LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSet);
        CarBillingPageProvider.get(getWebdriverInstance()).book(true);
        /**
         * Waits while confirmation page will appear
         */
        receiveConfirmation();
        CarSolutionModel carConfirmationOptionSet = getCarSolutionFromPage(Pages.CONFIRMATION);
        LOGGER.info("The next car options was presented on confirmation page " + carConfirmationOptionSet);
        comparePrimaryCarOptions(carBillingOptionSet, carConfirmationOptionSet);
        assertThat(carBillingOptionSet.getRentalDaysCount())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "rentalDaysCount"))
                .isEqualTo(carConfirmationOptionSet.getRentalDaysCount());
        assertThat(carBillingOptionSet.getTaxesAndFees())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "TAXES_AND_FEES"))
                .isEqualTo(carConfirmationOptionSet.getTaxesAndFees());
        /**
         * Checking for insurance
         */
        if (purchaseParameters.getOptForInsurance()) {
            assertThat(carBillingOptionSet.getDamageProtection())
                    .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "RENTAL_PROTECTION"))
                    .isNotNull();
        }
        else {
            assertThat(carBillingOptionSet.getDamageProtection())
                    .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "RENTAL_PROTECTION"))
                    .isNull();
        }

        assertThat(carBillingOptionSet.getDamageProtection())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "RENTAL_PROTECTION"))
                .isEqualTo(carConfirmationOptionSet.getDamageProtection());
    }

    private String getDateFromCalendar(String value) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        ChronicConverter converter = new ChronicConverter();
        Calendar calendar = converter.transform(value);
        return formatter.format(calendar.getTime());
    }

    @Override
    public void verifyTripSummaryBeforeBooking(DataTable params) {
        List<String> options = params.raw().get(0);
        String pickUpLocation = options.get(0);
        String pickUpDateShift = options.get(1);
        String pickUpTime = options.get(2);
        String dropOffDateShift = options.get(3);
        String dropOffTime = options.get(4);

        CarBillingPage billingPage = CarBillingPageProvider.get(getWebdriverInstance());

        assertThat(billingPage.getCarPurchaseReviewFragment().getPickUpDate())
                .as("Pick up date doesn't correct")
                .isEqualTo(getDateFromCalendar(pickUpDateShift) + ", " + pickUpTime.toUpperCase());
        assertThat(billingPage.getCarPurchaseReviewFragment().getDropOffDate())
                .as("Drop off date doesn't correct")
                .isEqualTo(getDateFromCalendar(dropOffDateShift) + ", " + dropOffTime.toUpperCase());
        assertThat(billingPage.getCarPurchaseReviewFragment().getPickUpLocation())
                .as("Pick up location doesn't correct")
                .isEqualTo(pickUpLocation);
    }

    /**
     * Compares primary (per day price, total price, car name) car options between different pages
     *
     * @param actual   CarSolutionModel object
     * @param expected CarSolutionModel object
     */
    private void comparePrimaryCarOptions(CarSolutionModel actual, CarSolutionModel expected) {
        // Revert back to actual is equal to diff if intermittent BUG52512 is deemed an issue.
        Float perDayDiff = new BigDecimal(
            actual.getPerDayPrice() - expected.getPerDayPrice()).round(new MathContext(2)).floatValue();
        assertThat(perDayDiff)
                .as(
                    CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "perDayPrice") + " : difference of " + perDayDiff +
                    " exceeds .01")
                .isLessThanOrEqualTo(new Float(.01));

        // Revert back to actual is equal to diff if intermittent BUG52512 is deemed an issue.
        Float totalDiff = Math.abs(actual.getTotalPrice() - expected.getTotalPrice());
        Float maxDiff = new BigDecimal(.01 * expected.getRentalDaysCount()).round(new MathContext(2)).floatValue();
        assertThat(totalDiff)
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "totalPrice") + " : difference of " + totalDiff +
                    " exceeds " + maxDiff)
                .isLessThanOrEqualTo(maxDiff);
        assertThat(actual.getCurrency())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "CURRENCY"))
                .isEqualTo(expected.getCurrency());
        assertThat(actual.getCarName())
                .as(CAR_OPTIONS_MESSAGE.replaceAll("%OPT%", "carName"))
                .isEqualTo(expected.getCarName());
    }

    private CarSolutionModel getCarSolutionFromPage(Pages page) {
        switch (page) {
            case CONFIRMATION:
                CarConfirmationPage carConfirmationPage = new CarConfirmationPage(getWebdriverInstance());
                return carConfirmationPage.getCarOptionsSet();
            case BILLING:
                return CarBillingPageProvider.get(getWebdriverInstance())
                        .getCarPurchaseReviewFragment().getCarOptionsSet();
            default:
                return null;
        }
    }

    @Override
    public void completePurchase(Boolean isUser) {
        processAllBillingInformation(isUser);
        clickOnAgreeAndBookButton(true);

        if (!getWebdriverInstance().findElements(By.cssSelector(".priceChangeMessage")).isEmpty()) {
            LOGGER.info("Price change found: " +
                getWebdriverInstance().findElement(By.cssSelector(".priceChangeMessage")).getText() +
                "\nRedoing billing process.");
            processAllBillingInformation(isUser);
            clickOnAgreeAndBookButton(true);
        }
    }

    @Override
    public void writeToTravelerField(String fieldType, String fieldValue) {
        UserInformation userInformation = authenticatedUser;
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        carBillingPage.selectAsGuest();

        LOGGER.info("Filling " + fieldType + " traveler field");
        if ("firstName".equals(fieldType)) {
            carBillingPage.getTravelerInfoFragment().travelerInfo(fieldValue, userInformation.getLastName());
        }
        else if ("lastName".equals(fieldType)) {
            carBillingPage.getTravelerInfoFragment().travelerInfo(userInformation.getFirstName(), fieldValue);
        }
        else if ("phone".equals(fieldType)) {
            carBillingPage.getTravelerInfoFragment().travelerPhoneNumber(fieldValue);
        }
        else if ("email".equals(fieldType)) {
            carBillingPage.getTravelerInfoFragment().travelerEmail(fieldValue);
        }
        else if ("confEmail".equals(fieldType)) {
            carBillingPage.getTravelerInfoFragment().travelerEmail(userInformation.getEmailId(), fieldValue);
        }
    }

    @Override
    public void writeToPaymentField(String fieldType, String fieldValue) {
        UserInformation userInformation = authenticatedUser;
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        carBillingPage.selectAsGuest();

        LOGGER.info("Filling " + fieldType + " traveler field");
        if ("cardNum".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().creditCardNumber(fieldValue);
        }
        else if ("securityCode".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().creditCardSecurityCode(fieldValue);
        }
        else if ("savedSecurityCode".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().savedVisaSecurityCode(fieldValue);
        }
        else if ("firstName".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().cardHolder(fieldValue, userInformation.getLastName());
        }
        else if ("lastName".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().cardHolder(userInformation.getFirstName(), fieldValue);
        }
        else if ("street".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().billingAddress(fieldValue);
        }
        else if ("city".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().city(fieldValue);
        }
        else if ("zip".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().zipCode(fieldValue);
        }
    }

    @Override
    public void writeToPayPalField(String fieldType, String fieldValue) {
        UserInformation userInformation = authenticatedUser;
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        carBillingPage.selectAsGuest();

        LOGGER.info("Filling " + fieldType + " traveler field");
        if ("firstName".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment()
                    .processPayPal().payPalUser(fieldValue, userInformation.getLastName());
        }
        else if ("lastName".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment()
                    .processPayPal().payPalUser(userInformation.getFirstName(), fieldValue);
        }
        else if ("street".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().processPayPal().payPalAddress(fieldValue);
        }
        else if ("city".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().processPayPal().payPalCity(fieldValue);
        }
        else if ("zip".equals(fieldType)) {
            carBillingPage.getPaymentMethodFragment().processPayPal().payPalZipCode(fieldValue);
        }
    }

    @Override
    public void selectPaymentMethod() {
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        if (purchaseParameters.getPaymentMethodType() == PurchaseParameters.PaymentMethodType.PayPal) {
            LOGGER.info("PayPal payment option");
            carBillingPage.getPaymentMethodFragment().processPayPal();
        }
    }

    @Override
    public void processAllBillingInformation(Boolean isUser) {
        LOGGER.info("Processing billing information..");
        UserInformation userInformation = setupCurrentUser(isUser);

        processTravelerInformation(userInformation);
        fillInsurance();

        // If insurance was selected, we need to get the new state of the billing page.
        processPaymentByType(userInformation, purchaseParameters.getPaymentMethodType());
        LOGGER.info("Billing information was filled in successfully..");
    }

    private void processPaymentByType(UserInformation userInformation,
                                      PurchaseParameters.PaymentMethodType methodType) {
        LOGGER.info("Processing payment information by type: {}", methodType);
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());

        switch (methodType) {
            case PayPal:
                fillPayPalBillingInfo();
                break;
            case HotDollars:
                CarPaymentMethod paymentMethodFragment = carBillingPage.getPaymentMethodFragment();
                paymentMethodFragment.processHotDollars();
                if (purchaseParameters.getOptForInsurance() && paymentMethodFragment.isSavedPaymentPresent()) {
                    paymentMethodFragment.chooseCreditCardPaymentMethod();
                    fillCreditCard();
                }
                else if (purchaseParameters.getOptForInsurance()) {
                    fillCreditCard();
                }
                break;
            case SavedVisa:
                carBillingPage.getPaymentMethodFragment().processSavedVisa(userInformation.getSecurityCode());
                break;
            case SavedMasterCard:
                carBillingPage.getPaymentMethodFragment().processSavedMasterCard(userInformation.getSecurityCode());
                break;
            default:
                LOGGER.info("Credit card payment option");
                if (("retail".equals(purchaseParameters.getOpaqueRetail()) &&
                     "false".equalsIgnoreCase(String.valueOf(purchaseParameters.getOptForInsurance()))) ||
                    ("retail".equals(purchaseParameters.getOpaqueRetail()) &&
                      carBillingPage.getAddFeaturesFragment().isInsuranceUnavailable())) {
                    return;
                }
                fillCreditCard();
                break;
        }
    }

    private void processTravelerInformation(UserInformation userInformation) {
        LOGGER.info("Processing traveler information..");
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        carBillingPage.getTravelerInfoFragment().
                travelerInfo(userInformation.getFirstName(), userInformation.getLastName()).
                travelerPhoneNumber(userInformation.getPrimaryPhoneNumber()).
                travelerEmail(userInformation.getEmailId()).
                creditCardAcceptance(true).ageConfirmation(true).continuePanel();
    }

    @Override
    public void fillInErrorCCNumber(String errorType) {
        UserInformation userInformation = purchaseParameters.getUserInformation();

        LOGGER.info("Filling traveler info");
        CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment().
                travelerInfo(userInformation.getFirstName(), userInformation.getLastName()).
                travelerPhoneNumber(userInformation.getPrimaryPhoneNumber()).
                travelerEmail(userInformation.getEmailId()).
                creditCardAcceptance(true).ageConfirmation(true).continuePanel();

        fillInsurance();
        // If insurance was selected, we need to get the new state of the billing page.

        //save ref number
        CarBillingHelpCenterFragment carBillingHelpCenterFragment =
                new CarBillingHelpCenterFragment(getWebdriverInstance());
        String[] parts = carBillingHelpCenterFragment.getReferenceNumber().replace("-", "").split("number\n1");
        purchaseParameters.setDisplayNumber(parts[1]);



        LOGGER.info("Filling credit card info");
        String cardNumber = "";
        switch (errorType) {
            case "AVS":
                cardNumber = AVS_ERROR_CC_NUMBER;
                break;
            case "CPV":
                cardNumber = CPV_ERROR_CC_NUMBER;
                break;
            case "Auth":
                cardNumber = AUTH_ERROR_CC_NUMBER;
                break;
            default:
                LOGGER.warn("Unknown error type");
                break;
        }
        CarBillingPageProvider.get(getWebdriverInstance()).
                getPaymentMethodFragment().processCreditCard().
                creditCardNumber(cardNumber).
                expDate(userInformation.getCcExpMonth(), userInformation.getCcExpYear()).
                creditCardSecurityCode(userInformation.getSecurityCode()).
                cardHolder(userInformation.getFirstName(), userInformation.getLastName()).
                billingAddress(userInformation.getBillingAddress()).
                country(userInformation.getBillingCountry()).
                city(userInformation.getCity()).
                state(userInformation.getState()).
                zipCode(userInformation.getZipCode()).continuePanel();
        logUserInformation(userInformation);
    }

    public UserInformation setupCurrentUser(Boolean isUser) {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        // if user login but credit card and other information wasn't configured in steps before
        // setting up it to default valid card
        // May be add this to valid credentials?
        if (userInformation == null) {
            userInformation = authenticatedUser;
        }
        // user isn't already authenticated
        if (!accountAccessModel.userExistsAndIsAuthenticated()) {
            /**
             *  need to refactor user usages
             */
            if (Boolean.FALSE.equals(authenticationParameters.isUserAuthenticated()) || !isUser) {
                CarBillingPageProvider.get(getWebdriverInstance()).selectAsGuest();
            }
            else {
                CarBillingPageProvider.get(getWebdriverInstance()).selectAsUser(authenticationParameters.getUsername(),
                        authenticationParameters.getPassword());
            }
        }
        /**
         * While purchase a car, it will be used to override the default data related
         * to "state" and "country of residence".
         * It was required to cover the test scenario's for fill the billing information as
         * non USA resident, USA resident with living in WA
         */
        if (carSearchParameters.getState() != null) {
            userInformation.setState(carSearchParameters.getState());
            userInformation.setCountry(carSearchParameters.getCountryOfResidence());
        }
        return userInformation;
    }

    @Override
    public void fillTravellerInfo(UserInformation userInformation) {
        LOGGER.info("Filling traveler info");
        CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment().
                travelerInfo(userInformation.getFirstName(), userInformation.getLastName()).
                travelerPhoneNumber(userInformation.getPrimaryPhoneNumber()).
                travelerEmail(userInformation.getEmailId()).
                creditCardAcceptance(true).ageConfirmation(true).continuePanel();
    }

    @Override
    public void fillInsurance() {
        LOGGER.info("Try to select insurance..");
        if (!purchaseParameters.isSkippedOptForInsurance()) {
            CarAdditionalFeatures addFeaturesFragment =
                    CarBillingPageProvider.get(getWebdriverInstance()).getAddFeaturesFragment();
            addFeaturesFragment.requestInsurance(purchaseParameters.getOptForInsurance());
            addFeaturesFragment.continuePanel();
        }
        else {
            LOGGER.info("Filling of additional features was skipped");
        }
    }

    @Override
    public void verifyTravellerInfoPrepopulated() {
        CarTravelerInfo carTravelerInfo = CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment();

        String savedPhone = carTravelerInfo.getSavedTravelerPrimaryPhoneNumber().replaceAll("-", "");
        String savedEmail = carTravelerInfo.getSavedTravelerEmail();
        String expectedDriverName = randomGuestUser.getFirstName() + " " + randomGuestUser.getLastName();

        assertThat(savedEmail).as("User Name is invalid").isEqualToIgnoringCase(authenticationParameters.getUsername());

        // If country code on phone number
        if (getWebdriverInstance().findElement(By.cssSelector("span.countryCode input")).isDisplayed()) {
            assertThat(savedPhone).as("Saved Phone Number of random guest user is invalid")
                    .isEqualTo("1" + randomGuestUser.getPrimaryPhoneNumber());
        }
        else {
            assertThat(savedPhone).as("Saved Phone Number of random guest user is invalid")
                    .isEqualTo(randomGuestUser.getPrimaryPhoneNumber());
        }

        assertThat(carTravelerInfo.isOptionalSignInLinkDisplayed()).isFalse();
        assertThat(carTravelerInfo.isTravelerConfirmationEmailDisplayed()).isFalse();
        assertThat(carTravelerInfo.getSelectedPrimaryDriver())
                .isEqualToIgnoringCase(expectedDriverName)
                .as("Driver's name is invalid..");
    }

    private void fillPayPalBillingInfo() {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        LOGGER.info("PayPal payment option");
        CarBillingPageProvider.get(getWebdriverInstance()).getPaymentMethodFragment().processPayPal().
                payPalUser(userInformation.getFirstName(), userInformation.getLastName()).
                payPalAddress(userInformation.getBillingAddress()).
                payPalCity(userInformation.getCity()).
                payPalState(userInformation.getState()).
                payPalZipCode(userInformation.getZipCode());
    }

    @Override
    public void fillCreditCard() {
        try {
            new WebDriverWait(getWebdriverInstance(), 3)
                    .until(visibilityOfElementLocated(By.cssSelector("div[id='paymentSection']")));
            UserInformation userInformation = purchaseParameters.getUserInformation();
            String zipCode = userInformation.getBillingCountry().equalsIgnoreCase("canada") ||
                    userInformation.getBillingCountry().equalsIgnoreCase("ca") ? canadianZipCode :
                    userInformation.getZipCode();
            CarBillingPageProvider.get(getWebdriverInstance()).
                    getPaymentMethodFragment().processCreditCard().
                    creditCardNumber(userInformation.getCcNumber()).
                    expDate(userInformation.getCcExpMonth(), userInformation.getCcExpYear()).
                    creditCardSecurityCode(userInformation.getSecurityCode()).
                    cardHolder(userInformation.getCcFirstName(), userInformation.getCcLastName()).
                    billingAddress(userInformation.getBillingAddress()).
                    country(userInformation.getBillingCountry()).
                    city(userInformation.getCity()).
                    state(userInformation.getState()).
                    zipCode(zipCode).continuePanel();
            logUserInformation(userInformation);
        }
        catch (NoSuchElementException e) {
            LOGGER.info("Payment section is hidden. Attempt to continue purchase without filling payment info.");
        }
    }

    @Override
    public void verifyPaymentStatus(String state) {
        PurchaseParameters.PaymentMethodType methodType = purchaseParameters.getPaymentMethodType();
        WebElement paymentTypeBtn = null;
        CarPaymentMethod paymentFragment =
            new OpPaymentMethodFragment(getWebdriverInstance());
        if ("none".equals(methodType.toString())) {
            assertThat(paymentFragment.isBillingSectionPresent()).isFalse();
        }
        else {
            try {
                switch (methodType) {
                    case PayPal:
                        paymentTypeBtn = paymentFragment.getPayPalRadioButton();
                        break;
                    case HotDollars:
                        paymentTypeBtn = paymentFragment.getHotDollarsButton();
                        break;
                    case CreditCard:
                        if (paymentFragment.isCreditCardIsSingleAvailablePayment()) {
                            paymentTypeBtn = paymentFragment.getCreditCardField();
                        }
                        else {
                            paymentTypeBtn = paymentFragment.getPaymentOptionCreditCard();
                        }
                        break;
                    case SavedCreditCard:
                        paymentTypeBtn = paymentFragment.getSavedCreditCardButton();
                        break;
                    case SavedVisa:
                        paymentTypeBtn = paymentFragment.getSavedVisaButton();
                        break;
                    case SavedMasterCard:
                        paymentTypeBtn = paymentFragment.getSavedMasterCardButton();
                        break;
                    default:
                        throw new RuntimeException("Payment method isn't defined..");
                }
            }
            catch (NoSuchElementException ex) {
                LOGGER.info("Payment method " + methodType + " now is unavailable");
            }

            if ("available".equals(state)) {
                assertThat(paymentTypeBtn).isNotNull();
                assertThat(paymentTypeBtn.isEnabled()).as(methodType + "method type is available").isTrue();
            }
            else if ("disabled".equals(state)) {
                assertThat(paymentTypeBtn).isNotNull();
                assertThat(paymentTypeBtn.isEnabled()).as(methodType + "method type is disabled").isFalse();
            }
            else if ("unavailable".equals(state)) {
                assertThat(paymentTypeBtn).as(methodType + "method type is unavailable").isNull();
            }
        }
    }
    @Override
    public void receiveConfirmation() {
        String pageName = new PageName().apply(getWebdriverInstance());
        // Check notify page for inventory change.
        if (pageName.contains("notify")) {
            // message specific for inventory issues and get
            // rid of verification by String value.
            List<WebElement> elements = getWebdriverInstance().findElements(By.cssSelector(" .ml15"));
            if (elements.size() > 0) {
                String msg = elements.get(0).getText();
                if (msg.contains("itinerary you selected is no longer available") || msg.contains("#1324") ||
                        msg.contains("#427") || msg.contains("#428")) {
                    // App is working in terms of UI level. To determine if this actually has occurred from an inventory
                    // being sold out or whatever needs to be done in a unit test or integration test.
                    throw new PendingException(
                            "Issue completing booking purchase confirmation. Details:\n" + msg +
                                    "\nIf this is consistently happening, notify car rental devs " +
                                    "to investigate inventory issues."
                    );
                }
            }
        }
        if ((pageName.contains("billing") || pageName.contains("car.details")) &&
                getWebdriverInstance().findElements(By.cssSelector(".notAvailable")).size() > 0) {
            throw new PendingException("Supplier sold out of selected car type.");
        }
        CarConfirmationPage page = new CarConfirmationPage(getWebdriverInstance());
        LOGGER.info("Car Confirmation Number: " + page.getConfirmationCode());
        LOGGER.info("Hotwire Itinerary: " + page.getHotwireItinerary());
        purchaseParameters.setDisplayNumber(page.getItineraryNumber());
    }

    @Override
    public void checkCarInsuranceLogo(boolean isCarInsuranceLogoVisible) {
        CarConfirmationPage carConfirmationPage = new CarConfirmationPage(getWebdriverInstance());
        boolean actualCarInsuranceLogoVisibility = carConfirmationPage.verifyCarInsuranceLogoAvaliable();
        assertThat(actualCarInsuranceLogoVisibility)
                .as("Expected car insurance logo visibility to be " + isCarInsuranceLogoVisible +
                        " but it was instead " + actualCarInsuranceLogoVisibility)
                .isEqualTo(isCarInsuranceLogoVisible);
    }

    @Override
    public void verifyCarInsuranceModuleTypeOnBillingPage(String typeOfModule) {
        CarAdditionalFeatures carInsuranceFragment =
                CarBillingPageProvider.get(getWebdriverInstance()).getAddFeaturesFragment();

        LOGGER.info("Insurance type: " + carInsuranceFragment.getInsuranceType());

        if ("appear".equals(typeOfModule)) {
            assertThat(carInsuranceFragment.getInsuranceType())
                    .as("Insurance block type is invalid..")
                    .isNotEqualTo(CarAdditionalFeatures.InsuranceType.UNAVAILABLE);
        }
        else if ("unavailable".equals(typeOfModule)) {
            assertThat(carInsuranceFragment.getInsuranceType())
                    .as("Insurance block type is invalid..")
                    .isEqualTo(CarAdditionalFeatures.InsuranceType.UNAVAILABLE);
        }
        else if ("loaded dynamically".equals(typeOfModule)) {
            assertThat(carInsuranceFragment.getInsuranceType())
                    .as("Insurance block type is invalid..")
                    .isEqualTo(CarAdditionalFeatures.InsuranceType.DYNAMIC);
        }
        else if ("presented as static module".equals(typeOfModule)) {
            assertThat(carInsuranceFragment.getInsuranceType())
                    .as("Insurance block type is invalid..")
                    .isEqualTo(CarAdditionalFeatures.InsuranceType.STATIC);
        }
    }

    /**
     * Switch focus to popup window and get text content.
     */
    private String getPopupWindowActiveTabText() {
        String windowHandle = getWebdriverInstance().getWindowHandle();
        WebDriver webDriver = getWebdriverInstance().switchTo().window("TermsOfUse");
        try {
            return new BillingTermsPopupWindow(webDriver).getActiveTabText();
        }
        finally {
            webDriver.close();
            getWebdriverInstance().switchTo().window(windowHandle);
        }
    }

    /**
     * Method checks content of popup windows by links <Terms Of Usage>
     * and <Rules And Restrictions> from the <Agree and Book> block.
     */
    @Override
    public void readTermsOfUseAndProductRules() {
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        CarPurchaseReview purchaseReviewFragment = carBillingPage.getCarPurchaseReviewFragment();

        purchaseReviewFragment.readTerms();
        assertThat(getPopupWindowActiveTabText())
            .as("Terms Of Use")
            .contains("Revision Date September 12, 2013")
            .contains("AGREEMENT BETWEEN CUSTOMER AND HOTWIRE, INC.")
            .contains("Please read the Agreement carefully.")
            .contains("If you do not accept all of these terms and conditions,")
            .contains("please do not use this Website or make bookings through this Website or our call center agents");

        purchaseReviewFragment.readRules();
        assertThat(getPopupWindowActiveTabText())
            .as("Rules and Restrictions")
            .contains("Please click here to find the additional terms and conditions that specifically apply to:")
            .contains("Revision Date June 13, 2013");
    }

    @Override
    public CarTravelerInfo.DepositCardAgreementType getDepositCardAgreementType() {
        return CarBillingPageProvider.get(getWebdriverInstance())
                .getTravelerInfoFragment().getDepositCardAgreementType();
    }

    @Override
    public boolean isDepositCardAgreementConfirmed() {
        return CarBillingPageProvider.get(getWebdriverInstance())
                .getTravelerInfoFragment().isDepositAgreementCheckboxSelected();
    }

    @Override
    public void selectDepositCardAgreementCheckbox(boolean state) {
        CarBillingPageProvider.get(getWebdriverInstance())
                .getTravelerInfoFragment().switchDepositAgreementState(state);
    }

    @Override
    public void validateDepositCardAgreementBox() {
        assertThat(CarBillingPageProvider.get(getWebdriverInstance())
                .getTravelerInfoFragment().verifyDepositCardAgreementBoxValidation())
                .as("Deposit verification message \"Please confirm your age and deposit terms\" wasn't " +
                        "appear or section wasn't highlighted in red.")
                .isTrue();
    }

    @Override
    public void verifyDepositCheckboxTypeAndRulesAndRegulation(
            CarTravelerInfo.DepositCardAgreementType depositCardAgreementType) {

        assertThat(depositCardAgreementType)
                .as("Deposit checkbox state and explanation")
                .isIn(getDepositCardAgreementType());

        List<String> rulesAndRegulations = CarBillingPageProvider.get(getWebdriverInstance())
                .getCarPurchaseReviewFragment()
                .getRulesAndRegulations();
        /**
         * Verify that copies from "Agree and book" block correspond
         * to vendor's type (label near checkbox at traveler section)
         */
        switch (depositCardAgreementType) {
            case CREDIT_CARD_ONLY:
                assertThat(RULE_CREDIT_CARD_COPY)
                        .as("Vendor accepts credit cards only")
                        .isIn(rulesAndRegulations);
                break;
            case CREDIT_DEBIT_CARD_WITH_TICKET:
                assertThat(RULE_CREDIT_DEBIT_CARD_WITH_TICKET)
                        .as("Vendor accepts credit and debit cards with ticket")
                        .isIn(rulesAndRegulations);
                break;
            case NONE:
                assertThat(RULE_ALL_CARD_TYPES)
                        .as("Vendor accepts all cards without restrictions")
                        .isIn(rulesAndRegulations);
                break;
            default:
                throw new RuntimeException("Unexpected deposit card agreement type...");
        }
    }

    @Override
    public void verifyDepositCheckboxPresence(boolean state) {
        boolean isDepositAgreementCheckboxPresent = CarBillingPageProvider.get(getWebdriverInstance())
            .getTravelerInfoFragment().isDepositAgreementCheckboxDisplayed();
        if (state) {
            assertThat(isDepositAgreementCheckboxPresent).
                as("Deposit checkbox is not present.").isTrue();
        }
        else {
            assertThat(isDepositAgreementCheckboxPresent).
                as("Deposit checkbox is present while should be absent.").isFalse();
        }
    }

    /**
     * Checks booking failure message at billing-dispatch page
     *
     * @param message String
     */
    @Override
    public void verifyBookingFailureMessage(String message) {
        assertThat((new NotificationBillingPage(getWebdriverInstance())).getNotificationMessage())
                .as("Booking failure message is invalid")
                .isEqualTo(message);
    }

    @Override
    public void clickOnAgreeAndBookButton(Boolean agreeWithTerms) {
        CarBillingPage billingPage = CarBillingPageProvider.get(getWebdriverInstance());
        PurchaseParameters.PaymentMethodType methodType = purchaseParameters.getPaymentMethodType();

        switch (methodType) {
            case PayPal:
                billingPage.bookPayPal(agreeWithTerms);
                break;
            default:
                billingPage.book(agreeWithTerms);
                LOGGER.info("Default payment method was chosen");
                break;
        }
    }

    @Override
    public void checkHotDollarsMessage(String message) {
        CarBillingPage billingPage = CarBillingPageProvider.get(getWebdriverInstance());
        String hotDollarsText = billingPage.getPaymentMethodFragment().getHotDollarsMessage();
        assertThat(hotDollarsText).contains(message)
                .as("HotDollars info message should appear in HotDollars Module");
    }

    @Override
    public void checkHotDollarsModule(boolean isVisibleModule) {
        CarBillingPage billingPage = CarBillingPageProvider.get(getWebdriverInstance());
        if (isVisibleModule) {
            assertThat(billingPage.getPaymentMethodFragment().isHotDollarsModuleAvailable())
                    .as("HotDollars Module is not displayed!")
                    .isTrue();
        }
        else {
            assertThat(billingPage.getPaymentMethodFragment().isHotDollarsModuleAvailable())
                    .as("HotDollars Module is displayed!")
                    .isFalse();
        }
    }

    @Override
    public void changeDriverNamesOnBillingPage(String firstName, String lastName) {
        LOGGER.info("Primary driver was changed to <" + firstName + " " + lastName + ">");

        authenticatedUser.setFirstName(firstName);
        authenticatedUser.setLastName(lastName);

        CarTravelerInfo cti = CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment();
        if (cti.isSavedDriverNameExists()) {
            cti.changeDriverInformation(firstName, lastName);
            assertThat(cti.getSelectedPrimaryDriver())
                    .as("Primary driver was changed but not selected")
                    .isEqualTo(firstName + " " + lastName);
        }
        else {
            cti.travelerInfo(firstName, lastName);
        }
    }

    /**
     * ////////////// VENDOR GRID ///////////////////
     * Returns list of vendor's offers from details page
     */
    @Override
    public List<VendorGridEntity> getAvailableVendorEntitiesFromDetails() {
        List<VendorGridEntity> vendorGridEntities = new ArrayList<VendorGridEntity>();

        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();

        for (String[] elm : carDetails.getVendorGridEntities()) {
            VendorGridEntity entity = new VendorGridEntity(elm);
            vendorGridEntities.add(entity);
        }
        LOGGER.info("Total vendor's offers: " + vendorGridEntities.size());
        return vendorGridEntities;
    }

    @Override
    public boolean isResultsContainLocationDescription(String location) {
        return CarResultsPageProvider.get(getWebdriverInstance())
                .getResult().isResultsContainLocationDescription(location);
    }

    @Override
    public void selectVendorEntityOnDetails(VendorGridEntity entity) {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        carDetails.selectVendorEntityBy(entity.getVendor(), entity.getPerDayPrice(), entity.getTotalPrice());
    }

    @Override
    public void checkMapAvailability(boolean availability) {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        assertThat(carDetails.localSearchMapIsDisplayed()).as("Check if Map is displayed").isEqualTo(availability);
    }

    @Override
    public void clickOnVendorAddressLink() {
        CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage().clickOnVendorAddressLink();
    }

    @Override
    public void closeVendorMap() {
        CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage().closeVendorMap();
    }

    @Override
    public void verifyVendorMapNum() {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        assertThat(carDetails.getVendorMapNum()).as("The only one Vendor map should be displayed").isEqualTo(1);
    }

    @Override
    public void verifyAirportResultsInVendorGrid() {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        assertThat(carDetails.airportResultVendorGridIsDisplayed())
                .as("Check if airport results is in vendor grid").isEqualTo(true);
    }

    @Override
    public VendorGridEntity getCheckedVendorEntityOnDetails() {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        VendorGridEntity entity = new VendorGridEntity(carDetails.getSelectedVendorGridEntity());
        LOGGER.info("CHECKED VENDOR: " + entity);
        return entity;
    }

    /**
     * Select one vendor from grid on details and checks that
     * car information (vendor logo, daily and total price) was
     * updated in block above.
     */
    @Override
    public void verifyVendorChangingOnDetails() {
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();

        for (VendorGridEntity en : getAvailableVendorEntitiesFromDetails()) {
            LOGGER.info(en + " WAS SELECTED..");
            carDetails.selectVendorEntityBy(en.getVendor(), en.getPerDayPrice(), en.getTotalPrice());

            CarSolutionModel sm = CarBillingPageProvider.get(getWebdriverInstance())
                    .getCarDetailsPage().getCarOptionsSet();

            LOGGER.info("CAR ON DETAILS: " + sm);
            assertThat(en.getPerDayPrice()).as("Daily price").isEqualTo(sm.getPerDayPrice());
            assertThat(en.getTotalPrice()).as("Total price").isEqualTo(sm.getTotalPrice());
            assertThat(en.getVendor()).as("Vendor").isEqualTo(sm.getVendor());
            assertThat(en.getCurrency()).as("Currency").isEqualTo(sm.getCurrency());
        }
    }

    @Override
    public void verifyCustomerCareModules() {
        CarBillingHelpCenterFragment helpCenterFragment = new CarBillingHelpCenterFragment(getWebdriverInstance());

        Matcher phoneMatcher = US_PHONE_PATTERN.matcher(helpCenterFragment.getCustomerCarePhoneNumber());
        Matcher referenceNumberMatcher = US_REF_NUMBER.matcher(helpCenterFragment.getReferenceNumber());
        assertThat(phoneMatcher.find())
            .as("Phone number not found in top customer care module.").isTrue();
        assertThat(referenceNumberMatcher.find())
            .as("Reference number not found in top customer care module.").isTrue();
    }

    @Override
    public void selectLiveChat(String moduleLocation) {
        if ("car details".equals(moduleLocation)) {
            CcfDetailsFragment ccfDetailsFragment = new CcfDetailsFragment(getWebdriverInstance());
            ccfDetailsFragment.openLiveChat();
        }
        else if ("result set".equals(moduleLocation)) {
            CcfPage ccfPage = new CcfPage(getWebdriverInstance());
            ccfPage.openLiveChat();
        }
    }

    @Override
    public void assertLiveChatDisplayState(boolean displayState, String moduleLocation) {
        if (moduleLocation.equals("car details")) {
            boolean isDisplayed = new CcfDetailsFragment(getWebdriverInstance()).isLiveChatDisplayed();
            assertThat(isDisplayed)
                    .as("Expected display of live chat to be " + displayState + " but got " + isDisplayed + " instead")
                    .isEqualTo(displayState);
        }
        if (moduleLocation.equals("result set")) {
            boolean isDisplayed = new CcfPage(getWebdriverInstance()).isLiveChatDisplayed();
            assertThat(isDisplayed)
                    .as("Expected display of live chat to be " + displayState + " but got " + isDisplayed + " instead")
                    .isEqualTo(displayState);
        }
    }

    @Override
    public void verifyLivePreChatDisplayed(boolean state) {
        freeze(5);   /*Wait while all popup windows were loaded... Doesn't work without it.*/
        String rootWindowHandler = getWebdriverInstance().getWindowHandle();
        Integer partnersWndCount = 0;

        if (state) {
            for (String h : getWebdriverInstance().getWindowHandles()) {
                if (!h.equals(rootWindowHandler) && !h.isEmpty()) {
                    WebDriver liveChatPopUpWebdriver = getWebdriverInstance().switchTo().window(h);
                    if (liveChatPopUpWebdriver.getTitle().equals("Hotwire Live Chat")) {
                        partnersWndCount++;
                    }
                    liveChatPopUpWebdriver.close();
                }
            }

            getWebdriverInstance().switchTo().window(rootWindowHandler);
            assertThat(partnersWndCount)
                    .as("Expected live prechat to be displayed, but it wasn't.")
                    .isGreaterThan(0);
        }
    }

    @Override
    public void choosePaymentMethod(boolean savedState) {
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        CarPaymentMethod paymentFragment = carBillingPage.getPaymentMethodFragment();
        String paymentMethod = purchaseParameters.getPaymentMethodType().toString();

        if (savedState) {
            throw new UnimplementedTestException("Implement me.");
        }
        else {
            if (paymentMethod.equals("PayPal")) {
                throw new UnimplementedTestException("Implement me.");
            }
            else if (paymentMethod.equals("CreditCard")) {
                paymentFragment.chooseCreditCardPaymentMethod();
            }
        }
    }

    @Override
    public void verifyHertzConfirmationCodeExists() {
        CarConfirmationPage confirmationPage = new CarConfirmationPage(getWebdriverInstance());
        String hertzTourNumber = confirmationPage.getHertzTourNumber();
        assertThat(hertzTourNumber)
                .as("Hertz tour number didn't appear on confirmation page")
                .isEqualTo("Hertz Tour No. : ITHOTWCARB");
    }

    @Override
    public void selectInsuranceAddonOption(boolean choice) {
        CarBillingPageProvider.get(getWebdriverInstance()).getAddFeaturesFragment().requestInsurance(choice);
        new WebDriverWait(getWebdriverInstance(), 10).until(new IsAjaxDone());
    }

    @Override
    public void checkPaymentMethodThatIsChosen(PurchaseParameters.PaymentMethodType methodType) {
        CarPaymentMethod carPaymentMethod = CarBillingPageProvider.get(getWebdriverInstance()).
            getPaymentMethodFragment();
        String chosenPaymentMethod = carPaymentMethod.getNameOfChosenPaymentMethod();
        assertThat(PurchaseParameters.PaymentMethodType.fromString(chosenPaymentMethod)).isEqualTo(methodType);
    }

    @Override
    public void doSaveUsersInformation(String state) {
        CarPaymentMethod carPaymentMethod = CarBillingPageProvider.get(getWebdriverInstance()).
                getPaymentMethodFragment();
        if (!carPaymentMethod.isSaveMyInfoExpanded()) {
            carPaymentMethod.saveMyInformation();
        }
        if ("don't".equals(state)) {
            return;
        }
        carPaymentMethod.getPasswordField().sendKeys(questUser.getVmePassword());
        carPaymentMethod.getConfirmPasswordField().sendKeys(questUser.getVmePassword());
    }

    @Override
    public void doSaveUsersPaymentInformation(String state) {
        CarPaymentMethod carPaymentMethod = CarBillingPageProvider.get(getWebdriverInstance()).
                getPaymentMethodFragment();
        carPaymentMethod.savePaymentInformation();
    }

    @Override
    public void verifyThatTravelerInformationIsPopulated() {
        CarTravelerInfo carTravelerInfo = CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment();
        UserInformation user = purchaseParameters.getUserInformation();

        String selectedPrimaryDriverName = carTravelerInfo.getSelectedPrimaryDriver();
        String expectedDriverName = user.getFirstName() + " " + user.getLastName();

        assertThat(selectedPrimaryDriverName).
            as("Primary driver name should be populated as " + expectedDriverName + " but wasn't").
            isEqualTo(expectedDriverName);

        String emailId = carTravelerInfo.getSavedTravelerEmail();
        if (emailId.equals("")) {
            emailId = carTravelerInfo.getSavedTravelerEmail();
        }
        assertThat(emailId).as("Traveler email should be populated as " + authenticationParameters.getUsername() +
                               " but wasn't").isEqualToIgnoringCase(authenticationParameters.getUsername());
        String savedPhoneNumber = carTravelerInfo.getSavedTravelerPrimaryPhoneNumber().
            replaceAll("\\D+", "");
        //DISABLE phone number verification temp while there's some mess in app's behavior regarding travelers phones
        /*assertThat(savedPhoneNumber).as("Traveler primary phone " + "number should be populated as " +
                                        user.getPrimaryPhoneNumber() + "but wasn't").
            isEqualTo(user.getPrimaryPhoneNumber());*/
        LOGGER.info("Traveler information that is populated after log in:");
        LOGGER.info("Primary driver name: " + selectedPrimaryDriverName);
        LOGGER.info("Traveler email: " + carTravelerInfo.getSavedTravelerEmail());
        LOGGER.info("Traveler primary phone number: " + savedPhoneNumber);
    }

    @Override
    public void setNewPrimaryDriverName(String firstName, String lastName) {
        CarTravelerInfo carTravelerInfo = CarBillingPageProvider.get(getWebdriverInstance())
            .getTravelerInfoFragment();
        carTravelerInfo.changeDriverInformation(firstName, lastName);
        purchaseParameters.getUserInformation().setFirstName(firstName);
        purchaseParameters.getUserInformation().setLastName(lastName);
        LOGGER.info("Setting new primary driver name to " + firstName + " " + lastName);
    }

    @Override
    public void enterNameCreditCard() {
        CarPaymentMethod carPaymentMethod = CarBillingPageProvider.get(getWebdriverInstance()).
                getPaymentMethodFragment();
        carPaymentMethod.typeCreditCardNameField(purchaseParameters.getUserInformation().getCcNumber());
    }

    @Override
    public void verifyCarDetailsPage() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void checkDriverNameOnConfirmationPage(String driverNameState) {
        CarConfirmationPage confirmPage = new CarConfirmationPage(getWebdriverInstance());
        assertThat(confirmPage.getDriverName()).isEqualTo(purchaseParameters.getUserInformation().getFirstName() +
                " " + purchaseParameters.getUserInformation().getLastName());
        LOGGER.info("Primary driver name on confirmation page is: " + confirmPage.getDriverName());
    }

    @Override
    public void chooseDriverFromExisting() {
        CarTravelerInfo carTravelerInfo = CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment();
        String driverName = carTravelerInfo.getSelectedPrimaryDriver();
        String[] names = driverName.split("\\s");
        purchaseParameters.getUserInformation().setFirstName(names[0]);
        purchaseParameters.getUserInformation().setLastName(names[1]);
    }

    @Override
    public void verifySubscriberCheckBox(String state) {
        CarTravelerInfo carTravelerInfo = CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment();
        if (state.equals("checked")) {
            assertThat(carTravelerInfo.isSubscriberCheckBoxSelected()).as("Verify subscriber checkbox selected")
                    .isTrue();
        }
        else if (state.equals("unchecked")) {
            assertThat(carTravelerInfo.isSubscriberCheckBoxSelected())
                    .as("Verify subscriber checkbox is not selected")
                    .isFalse();
        }
        else if (state.equals("unavailable")) {
            assertThat(carTravelerInfo.isSubscriberCheckBoxDisplayed())
                    .as("Verify subscriber checkbox is unavailable").isFalse();
        }
    }

    @Override
    public void verifyPage(String page) {
        switch (page) {
            case "result":
                CarResultsPageProvider.get(getWebdriverInstance());
                return;
            case "details":
                new CarDetailsPage(getWebdriverInstance());
                return;
            case "billing":
                throw new RuntimeException("Implement me!");
            default:
                return;
        }
    }

    @Override
    public void verifyInsuranceModuleInBilling(boolean isInsuranceUnavailable) {
        boolean actual =
            CarBillingPageProvider.get(getWebdriverInstance()).getAddFeaturesFragment().isInsuranceUnavailable();
        assertThat(actual)
            .as("Expected insurance unavailable to be " + isInsuranceUnavailable + " but got " + actual + " instead.")
            .isEqualTo(isInsuranceUnavailable);
    }

    @Override
    public void verifyInsuranceIsSelected() {
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        assertThat(carBillingPage.getAddFeaturesFragment().isInsuranceSelected()).isTrue();
    }

    @Override
    public void getInsuranceCost(String cost) {
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        assertThat(carBillingPage.getAddFeaturesFragment().getInsuranceCost().getText().contains("This is " + cost))
            .as("Insurance cost mismatch")
            .isTrue();
    }

}





