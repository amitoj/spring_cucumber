/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.search.car;

import com.hotwire.selenium.desktop.row.models.CarDataVerificationParameters;
import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarDetails;
import com.hotwire.selenium.desktop.us.confirm.CarConfirmationPage;
import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPage;
import com.hotwire.selenium.desktop.us.results.car.CarResultsPageProvider;
import com.hotwire.selenium.desktop.us.results.car.fragments.CarSolutionFragment;
import com.hotwire.selenium.desktop.us.results.car.fragments.CarTripWatcherFragment;
import com.hotwire.selenium.desktop.us.results.car.fragments.depositFilter.CarDepositFilter;
import com.hotwire.selenium.desktop.us.results.car.fragments.fareFinder.CarFareFinder;
import com.hotwire.selenium.desktop.us.results.car.fragments.fareFinder.CarFareFinderProvider;
import com.hotwire.selenium.desktop.us.search.CarSearchFragment;
import com.hotwire.selenium.desktop.us.search.CarSupplierPageFragment;
import com.hotwire.selenium.desktop.utils.DesktopPageProviderUtils;
import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.car.entities.VendorGridEntity;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.util.db.c3.C3SearchDao;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import cucumber.api.PendingException;
import org.apache.commons.lang3.StringUtils;
import org.ehoffman.testing.fest.webdriver.WebElementAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author lryzhikov
 * @since 2012.04
 */
public class CarSearchModelWebApp extends CarSearchModelTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarSearchModelWebApp.class.getName());

    private static final String NEW_TRIP = "We'll email you when this price drops.";
    private static final String WATCHED_TRIP = "We're now watching this car rental price for you.";
    private static final String FULL_TRIP = "Trip Watcher is full.";

    private static final String DEFAULT_FAREFINDER_DATE = "mm/dd/yy";

    //private static String search_id;

    @Autowired
    @Qualifier("carDataVerificationParameters")
    private CarDataVerificationParameters carDataVerificationParameters;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("authenticationParameters")
    private AuthenticationParameters authenticationParameters;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * Logging of car search options
     *
     * @param globalSearchParameters Search parameters to log
     */
    private void logSearchRequest(SearchParameters globalSearchParameters) {
        StringBuilder sb = new StringBuilder("Searching a car..\n");
        String pickUpTime = "NOON", dropOffTime = "NOON";
        String pickUpDate = "EMPTY", dropOffDate = "EMPTY";
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        Date pickUp = globalSearchParameters.getStartDate();
        if (pickUp != null) {
            pickUpDate = formatter.format(globalSearchParameters.getStartDate());
        }
        Date dropOff = globalSearchParameters.getEndDate();
        if (dropOff != null) {
            dropOffDate = formatter.format(globalSearchParameters.getEndDate());
        }

        sb.append(String.format("LOCATION: <%s>", globalSearchParameters.getDestinationLocation()));
        if (searchParameters.getDropOffLocation() != null) {
            sb.append(String.format(" - <%s>", searchParameters.getDropOffLocation()));
        }
        sb.append("\n");
        sb.append(String.format("DATE: %s - %s\n", pickUpDate, dropOffDate));

        if (searchParameters.getPickupTime() != null) {
            pickUpTime = searchParameters.getPickupTime();
        }
        if (searchParameters.getDropoffTime() != null) {
            dropOffTime = searchParameters.getDropoffTime();
        }
        sb.append(String.format("TIME: %s - %s", pickUpTime, dropOffTime));
        LOGGER.info(sb.toString());
    }

    @Override
    public void findFare(String selectionCriteria) {
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        logSearchRequest(globalSearchParameters);
        logSession("Requesting quotes");

        CarSearchFragment searchFragment = delegateSearchPage().findCarRental();

//        String pageName = new PageName().apply(getWebdriverInstance());
//        if (RefreshPageHandler.reloadPageIfDropdownSkinNotLoaded(
//                getWebdriverInstance(),
//                By.cssSelector(".endTime span.ui-selectmenu-button a"),
//                pageName.contains("uhp.index") || pageName.contains("hotwire.home") ?
//                    By.cssSelector(MultiVerticalFareFinder.CAR_RADIO) : null)) {
//            // If we had to reload page we need the current state of the search fragment.
//            searchFragment = delegateSearchPage().findCarRental();
//        }

        searchFragment.
                endLocation(searchParameters.getDropOffLocation()).
                endTime(searchParameters.getDropoffTime()).
                endDate(globalSearchParameters.getEndDate()).
                startTime(searchParameters.getPickupTime()).
                startDate(globalSearchParameters.getStartDate()).
                startLocation(globalSearchParameters.getDestinationLocation()).
                findFare();

    }

    @Override
    public void launchSearch() {
        // List of page's tiles where new car search is accessible
        List<String> tilesList = Arrays.asList("tile.car-spa.results", "tiles-def.seo-site-map.options",
                                               "tiles-def.car.results.refresh");
        if (tilesList.contains(DesktopPageProviderUtils.getPageTileDefinition(getWebdriverInstance()))) {

            SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
            logSearchRequest(globalSearchParameters);

            CarFareFinderProvider.get(getWebdriverInstance())
                    .setPickUpLocation(globalSearchParameters.getDestinationLocation())
                    .setDropOffLocation(searchParameters.getDropOffLocation())
                    .setPickUpDate(globalSearchParameters.getStartDate())
                    .setPickUpTime(searchParameters.getPickupTime())
                    .setDropOffDate(globalSearchParameters.getEndDate())
                    .setDropOffTime(searchParameters.getDropoffTime())
                    .run();
        }
    }

    @Override
    public void navigatetoSupplierLandingPage(String suppliername) {
        //throw new PendingException("FIX ME. Going to URL just goes to a can't process request page.");
        try {
            if (StringUtils.isNotEmpty(suppliername)) {
                URL carSupplierPageUrl = new URL(applicationUrl, suppliername + "-rental-cars");
                getWebdriverInstance().navigate().to(carSupplierPageUrl);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void confirmSupplierPage(String suppliername) {
        if (StringUtils.isNotEmpty(suppliername)) {
            CarSupplierPageFragment supplierPage = new CarSupplierPageFragment(getWebdriverInstance());
            Boolean isBannerShown = supplierPage.hasSupplierBanner(StringUtils.capitalize(suppliername));
            assertThat(isBannerShown)
                    .as("Supplier Banner should show up on Landing page. But currently it is not showing up")
                    .isTrue();
        }
    }

    @Override
    public void verifyRentalDaysOnBillingPage(Integer dayCount) {
        CarSolutionModel carSolutionModel = CarBillingPageProvider.get(getWebdriverInstance()).
                getCarPurchaseReviewFragment().getCarOptionsSet();
        assertThat(carSolutionModel.getRentalDaysCount())
                .as("rental days count")
                .isEqualTo(dayCount);
    }

    @Override
    public void verifyRentalCarProtectionCostOnBillingPage(Integer protectionCost) {
        CarSolutionModel carSolutionModel = CarBillingPageProvider.get(getWebdriverInstance()).
                getCarPurchaseReviewFragment().getCarOptionsSet();

        assertThat(carSolutionModel.getDamageProtection())
                .as("Rental car damage protection")
                .isEqualTo(protectionCost.floatValue());
    }

    @Override
    public void navigateToResultsPage() {
        CarResultsPageProvider.get(getWebdriverInstance());
    }

    @Override
    public void verifyResultsPage() {
        LOGGER.info("Verifying car results page...");
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());

        boolean isResultsEmpty = carResultsPage.getResult().isResultsEmpty();
        assertThat(!isResultsEmpty).as("List of search results is empty");
    }

    @Override
    public void verifyNoResultsPage() {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        boolean isResultsEmpty = carResultsPage.getResult().isResultsEmpty();

        assertThat(isResultsEmpty).as("List of search results should be empty");
        assertThat(carResultsPage.isNoResultsMessageDisplayed()).as("No results message should be displayed");
    }

    @Override
    public boolean isDepositFilterPresent() {
        try {
            new CarDepositFilter(getWebdriverInstance());
        }
        catch (RuntimeException ex) {
            return false;
        }
        return true;
    }

    @Override
    public void openDepositFilterExplanationPopup(boolean doOpen) {
        CarDepositFilter depositFilter = new CarDepositFilter(getWebdriverInstance());
        if (doOpen) {
            depositFilter.clickMoreInformationLink();
        }
        else {
            depositFilter.closeExplanationPopup();
        }
    }

    @Override
    public void verifyDepositFilterPopupExplanation(String text) {
        String actual = new CarDepositFilter(getWebdriverInstance()).getPopupExplanation().
            replaceAll("\r?\n", " ").trim();
        String expected = text.replaceAll("\r?\n", " ").trim();
        assertThat(actual).as("Debit deposit type explanation").isEqualTo(expected);
    }

    @Override
    public void verifyDepositFilterText(String text) {
        CarDepositFilter carDepositFilter = new CarDepositFilter(getWebdriverInstance());
        assertThat(carDepositFilter.getFilterText()).contains(text);
    }

    @Override
    public void selectDepositOptionByType(CarDepositFilter.DepositTypes cardType) {
        CarDepositFilter carDepositFilter = new CarDepositFilter(getWebdriverInstance());

        switch (cardType) {
            case DEBIT:
            case CREDIT:
                carDepositFilter.selectDepositByType(cardType);
                break;
            case I_AM_LOCAL:
            case I_AM_TRAVELING:
                carDepositFilter.selectDebitOptionByTravelType(cardType);
                break;
            default:
                throw new RuntimeException("Unsupported card type to select...");
        }
        LOGGER.info("Deposit type " + cardType.toString().toUpperCase() + " was selected..");

        new WebDriverWait(getWebdriverInstance(), 20).until(
            new IsElementLocationStable(getWebdriverInstance(), By.cssSelector("#tileName-results")));
    }

    @Override
    public boolean isDebitDepositExplanationOpen() {
        return new CarDepositFilter(getWebdriverInstance()).isDebitDepositExplanationOpen();
    }

    @Override
    public void verifyDepositTypeSelection(CarDepositFilter.DepositTypes cardType) {
        CarDepositFilter carDepositFilter = new CarDepositFilter(getWebdriverInstance());

        assertThat(cardType).isIn(
            CarDepositFilter.DepositTypes.CREDIT,
            CarDepositFilter.DepositTypes.I_AM_LOCAL,
            CarDepositFilter.DepositTypes.I_AM_TRAVELING
        ).as("Unsupported deposit type [DEBIT]...Clarify travel type for debit option...");

        assertThat(cardType).as("Deposit types don't match")
                .isEqualTo(carDepositFilter.getSelectedDepositType());
    }

    @Override
    public void verifyThatCheapestSolutionIsFirst() {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        CarSolutionFragment firstSolution = carResultsPage.getResult().get(0);
        CarSolutionFragment cheapestSolution = carResultsPage.getResult()
                .cheapest()
                .get(0);
        assertThat(firstSolution.getTotalPrice())
                .as("First Solution price: " + firstSolution.getTotalPrice() + "\n " +
                        "Cheapest Solution price: " + cheapestSolution.getTotalPrice() + "\n" +
                        "First solution should be cheapest.")
                .isLessThanOrEqualTo(cheapestSolution.getTotalPrice());
    }

    @Override
    public void verifyOnlyRetailOrOpaqueResultsPage(String typeOfSolution) {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        //If we check for opaque - verify that there is no retail solutions.
        if (typeOfSolution.equals("opaque")) {
            assertThat(!carResultsPage.getResult().isResultsContainRetailSolutions())
                    .as("not all solutions on results page are " + typeOfSolution);
        }
        //if we check for retail - verify that there is no opaque solutions.
        else if (typeOfSolution.equals("retail")) {
            assertThat(!carResultsPage.getResult().isResultsContainOpaqueSolutions())
                    .as("not all solutions on results page are " + typeOfSolution);
        }
    }

    @Override
    public void verifyTripWatcherInResults(boolean condition) {
        CarTripWatcherFragment carTripWatcherFragment = new CarTripWatcherFragment(getWebdriverInstance());
        assertThat(carTripWatcherFragment.isTripWatcherDisplayed()).isNotEqualTo(condition);
    }

    @Override
    public void verifyTripWatcherEmailField(boolean state, String emailId) {
        CarTripWatcherFragment carTripWatcherFragment = new CarTripWatcherFragment(getWebdriverInstance());
        if (!state) {
            assertThat(carTripWatcherFragment.getEmailValue())
                    .isEqualToIgnoringCase(emailId);
        }
        else {
            assertThat(carTripWatcherFragment.getEmailValue())
                    .as("Email field in TripWatcher module")
                    .isEmpty();
        }
    }


    @Override
    public void verifyTripWatcherCopy(String state) {
        switch (state) {
            case "new":
                state = NEW_TRIP;
                break;
            case "watched":
                state = WATCHED_TRIP;
                break;
            default:
                state = FULL_TRIP;
                break;
        }
        assertThat(new CarTripWatcherFragment(getWebdriverInstance()).getTripWatcherCopy()).isEqualTo(state);
    }

    @Override
    public void watchTrip(boolean state, String emailId) {
        CarTripWatcherFragment carTripWatcherFragment = new CarTripWatcherFragment(getWebdriverInstance());
        if (!state) {
            carTripWatcherFragment.getEmailTextField().clear();
            carTripWatcherFragment.getEmailTextField().sendKeys(emailId);
        }
        carTripWatcherFragment.watchTrip();
        new WebDriverWait(getWebdriverInstance(), 20).until(new IsAjaxDone());
    }

    @Override
    public void verifyEmailFieldErrorValidation() {
        CarTripWatcherFragment carTripWatcherFragment = new CarTripWatcherFragment(getWebdriverInstance());
        assertThat(carTripWatcherFragment.getEmailTextFieldValidationError()).isTrue();
    }

    @Override
    public void verifyUserIsRedirectedToTheLowestPriceSolution() {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        assertThat(carResultsPage.getResult().get(0).isLowestPrice()).as("First solution should be with lowest price.");
    }

    @Override
    public void verifyFareFinderOnResults() {
        String actualUpTime;

        CarFareFinder carFareFinder = CarFareFinderProvider.get(getWebdriverInstance());
        // PICK UP LOCATION
        assertThat(carFareFinder.getPickUpLocation())
                .as("New search on results page contains invalid pickUpLocation value")
                .isEqualTo(searchParameters.getGlobalSearchParameters().getDestinationLocation());

        // DROP OFF LOCATION
        if (searchParameters.getDropOffLocation() != null) {
            assertThat(carFareFinder.getDropOffLocation())
                    .as("New search on results page contains invalid dropOffLocation value")
                    .isEqualTo(searchParameters.getDropOffLocation());

//            assertThat(carFareFinder.isOneWay())
//                    .as("One-way radio button isn't selected").isTrue();
        }

        // PICK UP DATE
        assertThat(carFareFinder.getPickUpDate())
                .as("New search on results page contains invalid start date")
                .isEqualTo(carFareFinder.getDateFormat().format(searchParameters.
                        getGlobalSearchParameters().getStartDate()));

        // DROP OFF DATE
        assertThat(carFareFinder.getDropOffDate())
                .as("New search on results page contains invalid end date")
                .isEqualTo(carFareFinder.getDateFormat().format(searchParameters.
                        getGlobalSearchParameters().getEndDate()));

        // PICK UP TIME
        actualUpTime = (searchParameters.getPickupTime() == null) ? "noon" : searchParameters.getPickupTime();
        assertThat(carFareFinder.getPickUpTime())
                .as("New search on results page contains invalid start time")
                .isEqualTo(actualUpTime);

        // DROP OFF TIME
        actualUpTime = (searchParameters.getDropoffTime() == null) ? "noon" : searchParameters.getDropoffTime();
        assertThat(carFareFinder.getDropOffTime())
                .as("New search on results page contains invalid end time")
                .isEqualTo(actualUpTime);

    }

    @Override
    public void verifyDefaultDatesInFareFinder() {
        CarFareFinder carFareFinder = CarFareFinderProvider.get(getWebdriverInstance());

        assertThat(carFareFinder.getPickUpDatePlaceholder())
                .as("Dates on UHP/LP are not equal to default values")
                .isEqualTo(DEFAULT_FAREFINDER_DATE);

        assertThat(carFareFinder.getDropOffDatePlaceholder())
                .as("New search on results page contains invalid end date")
                .isEqualTo(DEFAULT_FAREFINDER_DATE);
    }

    @Override
    public void verifyTimesInFareFinder(String typeOfTime) {
        CarFareFinder carFareFinder = CarFareFinderProvider.get(getWebdriverInstance());
        List <String> actual = carFareFinder.getListOfTimes(typeOfTime);
        assertThat(actual.containsAll(carFareFinder.getExpectedListOfTime()))
                .as("Not all the items in " + typeOfTime + " time list are equal to expected").isTrue();
    }


    @Override
    public void verifyDisambiguationPage(boolean hasErrorMessage) {
        ErrorMessenger msg = new ErrorMessenger(getWebdriverInstance());
        msg.setMessageType(ErrorMessenger.MessageType.valueOf("error"));
        if (hasErrorMessage) {
            for (String m : msg.getMessages()) {
                assertThat(m.contains("Where are you going?")).isTrue();
                assertThat(m.contains("Please choose your location from the list.")).isTrue();
                assertThat(m.contains("If your location is not listed, please check " +
                        "your spelling or make sure it is on our")).isTrue();
                assertThat(m.contains("destination cities list")).isTrue();
            }
        }
        verifyDisambiguationLayerIsVisible();
    }

    @Override
    public void verifyDisambiguationLayerIsVisible() {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());

        WebElement disambiguationLayer = carSearchFragment.getAutoComplete().findElement(By.xpath("..//../div"));
        new WebDriverWait(getWebdriverInstance(), 10).until(PageObjectUtils.webElementVisibleTestFunction(
            disambiguationLayer, true));
        assertThat("Choose a location".equals(disambiguationLayer.getText())).isTrue();
    }

    @Override
    public void verifyDisambiguationLayerIsNotVisible() {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());

        WebElementAssert.assertThat(carSearchFragment.getAutoComplete().findElement(By.xpath("..//../div")))
                .isNotDisplayed();
    }

    @Override
    public void clickOnTheDestinationFiled() {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());
        carSearchFragment.getStartTimeCar().submit();
    }

    @Override
    public void verifySuggestedLocation(String number, String expectedAutoCompleteItem, String style, String click) {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());
        carSearchFragment.isAutoCompleteDisplayed();
        LOGGER.info("Auto-complete is visible");

        ArrayList<String> autocomleteContents = carSearchFragment.getAutocomleteContents();
        ArrayList<String> autocompleteAttributes = carSearchFragment.getAttibutesForAutocomplete("class");
        List<WebElement> autocomleteElements = carSearchFragment.getAutocompleteElements();

        Integer i = autocomleteContents.indexOf(expectedAutoCompleteItem);

        if (number != null) {
            if (number.trim().equals("first")) {
                assertThat(i == 0).as("First auto-complete item is : " + expectedAutoCompleteItem).isTrue();
            }
            else if (number.trim().equals("second")) {
                assertThat(i == 1).as("Second auto-complete item is : " + expectedAutoCompleteItem).isTrue();
            }
            else if (number.trim().equals("third")) {
                assertThat(i == 2).as("Third auto-complete item is : " + expectedAutoCompleteItem).isTrue();
            }
        }
        else {
            assertThat(i != -1).isTrue();
        }

        if (style != null) {
            if (style.trim().equals("underlined and highlighted")) {
                assertThat(autocompleteAttributes.get(i).contains("yui-ac-highlight"))
                        .as("N" + i.toString() + " auto-complete item is highlighted and underlined").isTrue();
            }
        }

        if (click != null) {
            if (click.trim().equals("and I click it")) {
                autocomleteElements.get(i).click();

                try {
                    carSearchFragment.isAutoCompleteDisplayed();
                    throw new InvalidElementStateException();
                }
                catch (TimeoutException e) {
                    LOGGER.info("Auto-complete is invisible");
                }
                // This is a hack. For some reason, focus is still on this element and typing start date appends to
                // this element instead of the start date element. Attempt to send tab key to get off this element.
                carSearchFragment.getStartLocation().sendKeys(Keys.TAB);
            }
        }
    }

    @Override
    public void verifyNonEmptyRefineSearchModule() {
        assertThat(new CarDepositFilter(getWebdriverInstance()).isRefineYourSearchModuleDisplayed())
                .as("No refine search module with card radio")
                .isTrue();
    }

    @Override
    public void clickSearch() {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());
        carSearchFragment.getSearchButton().click();
    }

    @Override
    public void verifyResultsContainLocation(String location) {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());
        String actualLocation = carSearchFragment.getStartLocation().getAttribute("value");
        assertThat(actualLocation.equals(location))
                .as("Location on results page expected " + location +
                ", but actual " + actualLocation).isTrue();

    }

    @Override
    public void verifyTypeOFSearchOnCarResults(String typeOfSearch) {
        Boolean isRoundTripExpected = typeOfSearch.equals("Roundtrip");
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());

        assertThat(carSearchFragment.isRoundTripRadioChecked())
                .as("Roundtrip radio is not selected").isEqualTo(isRoundTripExpected);
        assertThat(carSearchFragment.isOneWayTripRadioChecked())
                .as("One way radio is not selected").isEqualTo(!isRoundTripExpected);
    }


    @Override
    public void typeLocation(String location) {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());
        carSearchFragment.getStartLocation().sendKeys(location);
    }

    @Override
    public void setDates(Date startDate, Date endDate) {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());
        carSearchFragment.startDate(startDate);
        carSearchFragment.endDate(endDate);
    }

    @Override
    public void clickOnTheCarStartTime() {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance());
        carSearchFragment.getStartTimeCar().click();
    }

    @Override
    public void verifyIfNearbyAirportModuleIsNotPresent() {
        CarResultsPage resultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        assertThat(resultsPage.isNearbyAirportModuleDisplayed()).isFalse();
    }

    @Override
    public void verifyIfNearbyAirportModuleIsPresent() {
        CarResultsPage resultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        assertThat(resultsPage.isNearbyAirportModuleDisplayed()).isTrue();
    }

    @Override
    public void saveSearchIdForCarResults() {
        String referenceNumber;
        String search_id;
        CarResultsPage resultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        referenceNumber = resultsPage.getResult().get(0).getReferenceNumber();
        LOGGER.info("Search referenceNumber: " + referenceNumber);
        //sql = "select search_id from search_solution where display_number='" + referenceNumber + "'";
        //sql = "select search_id from search_solution where display_number='5" + referenceNumber + "'";
        //previously there was query with '5+referenceNumber', so tests failed, RTC-997 for example @bshukaylo
        try {
            search_id = new C3SearchDao(getDataBaseConnection()).getSearchIdByReference(referenceNumber);
        }
        catch (DataAccessException e) {
            throw new PendingException("Search_id for this search where referenceNumber=" + referenceNumber +
                                       " is not found in the database.");
        }
        searchParameters.getGlobalSearchParameters().setSearchId(search_id);
        LOGGER.info("Current search id: " + search_id);
    }

    @Override
    public void verifyStatusCodeFromDB(String expectedCode) {
        String sql;

        List<Map<String, Object>> queryResult;

        String searchId = searchParameters.getGlobalSearchParameters().getSearchId();
        sql = "select status_code from search_solution where search_id ='" + searchId + "'";
        queryResult = jdbcTemplate.queryForList(sql);
        List<String> listOfCodes = new ArrayList<String>();

        for (int i = 0; i < queryResult.size(); i++) {
            listOfCodes.add(queryResult.get(i).get("STATUS_CODE").toString());
        }
        assertThat(listOfCodes.contains(expectedCode)).as("Expected code " + expectedCode + " was not found").isTrue();
    }

    @Override
    public void verifyCheapestRetailCarPriceWithDB() {
        String displayNumber = carSearchParameters.getSelectedSearchSolution().getDisplayNumber();

        List<VendorGridEntity> vendorGridEntities = new ArrayList<VendorGridEntity>();
        CarDetails carDetails = CarBillingPageProvider.get(getWebdriverInstance()).getCarDetailsPage();
        for (String[] elm : carDetails.getVendorGridEntities()) {
            VendorGridEntity entity = new VendorGridEntity(elm);
            vendorGridEntities.add(entity);
        }

        String sql = "select cheapest_pub_fare_amount " +
                "from search_solution where display_number = '" + displayNumber + "'";
        Map<String, Object> queryResult;
        queryResult = jdbcTemplate.queryForMap(sql);
        System.out.println(queryResult.get("CHEAPEST_PUB_FARE_AMOUNT"));

        BigDecimal dbValue = (BigDecimal) queryResult.get("CHEAPEST_PUB_FARE_AMOUNT");
        BigDecimal gridValue = new BigDecimal(vendorGridEntities.get(0).getPerDayPrice().toString());
        float dailyRateDiff = dbValue.subtract(gridValue).floatValue();

        assertThat(dailyRateDiff).as("Taxes and fees. Difference of " +
                dailyRateDiff + " exceeds .01").isLessThanOrEqualTo(new Float(.01));
    }

    @Override
    public  void compareSearchIdForCarResults() {
        String referenceNumber;
        String sql;
        Map<String, Object> queryResult;
        CarResultsPage resultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        referenceNumber = resultsPage.getResult().get(0).getReferenceNumber();
        sql = "select search_id from search_solution where display_number='" + referenceNumber + "'";
        queryResult = jdbcTemplate.queryForMap(sql);
        assertThat(queryResult.get("SEARCH_ID").toString())
                .as("Search_id should be different for each unique request car results.")
                .isNotEqualTo(searchParameters.getGlobalSearchParameters().getSearchId());
    }

    @Override
    public void compareTaxesAndFeesOnDetailsWithDB() {
        String sql;
        List<Map<String, Object>> queryResult;
        BigDecimal totalTax = BigDecimal.ZERO;
        CarSolutionModel carBillingOptionSet = CarBillingPageProvider.get(getWebdriverInstance())
                .getCarPurchaseReviewFragment().getCarOptionsSet();
        LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSet);

        String displayNumber = carSearchParameters.getSelectedSearchSolution().getDisplayNumber();

        sql = "select spm.amount from search s, search_solution ss, search_pgood sp, " +
                "rental_car rc, search_price_modifier spm " +
                "where s.search_id = ss.search_id and sp.search_solution_id = ss.search_solution_id and" +
                " sp.pgood_id = rc.pgood_id and rc.pgood_id = spm.pgood_id and ss.display_number = '" + displayNumber +
                "' and spm.type_code != 10015";

        queryResult = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> aQueryResult : queryResult) {
            totalTax = totalTax.add((BigDecimal) aQueryResult.get("AMOUNT"));
        }
        totalTax = totalTax.multiply(BigDecimal
                .valueOf(carBillingOptionSet.getRentalDaysCount()));
        Float totalTaxDiff = totalTax.subtract(BigDecimal
                .valueOf(carBillingOptionSet.getTaxesAndFees()))
                .abs().round(new MathContext(2)).floatValue();

        assertThat(totalTaxDiff).as("Taxes and fees. Difference of " +
                totalTaxDiff + " exceeds .01").isLessThanOrEqualTo(new Float(.01));
    }

    @Override
    public void compareDailyRateOnDetailsWithDB() {
        String sql;
        Map<String, Object> queryResult;
        CarSolutionModel carBillingOptionSet = CarBillingPageProvider.get(getWebdriverInstance())
                .getCarPurchaseReviewFragment().getCarOptionsSet();
        LOGGER.info("The next car options was presented on details(billing) page " + carBillingOptionSet);
        String displayNumber = carSearchParameters.getSelectedSearchSolution().getDisplayNumber();

        sql = "select sp.base_cost_amount from search s, search_solution ss, search_pgood sp," +
                " rental_car rc, search_price_modifier spm where s.search_id = ss.search_id and" +
                " sp.search_solution_id = ss.search_solution_id and sp.pgood_id = rc.pgood_id and" +
                " rc.pgood_id = spm.pgood_id and ss.display_number = '" + displayNumber + "'" +
                " and spm.type_code = 10015";

        queryResult = jdbcTemplate.queryForMap(sql);
        BigDecimal baseCostAmount = (BigDecimal) queryResult.get("BASE_COST_AMOUNT");

        sql = "select spm.amount from search s, search_solution ss, search_pgood sp, " +
                "rental_car rc, search_price_modifier spm where s.search_id = ss.search_id " +
                "and sp.search_solution_id = ss.search_solution_id and sp.pgood_id = rc.pgood_id and " +
                "rc.pgood_id = spm.pgood_id and ss.display_number = '" + displayNumber + "'" +
                " and spm.type_code = 10015";

        queryResult = jdbcTemplate.queryForMap(sql);
        BigDecimal amount = (BigDecimal) queryResult.get("AMOUNT");

        BigDecimal totalDBValue = amount.add(baseCostAmount);
        Float totalDailyRateDiff = totalDBValue.subtract(BigDecimal
                .valueOf(carBillingOptionSet.getPerDayPrice()))
                .abs().round(new MathContext(2)).floatValue();

        assertThat(totalDailyRateDiff).as("Per day price. Difference of " +
                totalDailyRateDiff + " exceeds .01").isLessThanOrEqualTo(new Float(.01));
    }

    @Override
    public void verifyStatusCodeCountInDB(Integer count) {
        CarConfirmationPage page = new CarConfirmationPage(getWebdriverInstance());
        purchaseParameters.setDisplayNumber(page.getItineraryNumber());
        String sql;
        Map<String, Object> queryResult;


        sql = "select count(*) from payment_receipt pr, purchase_order po\n" +
                "where pr.purchase_order_id = po.purchase_order_id " +
                "and po.display_number = '" + purchaseParameters.getDisplayNumber() + "'";

        queryResult = jdbcTemplate.queryForMap(sql);
        BigDecimal amount = (BigDecimal) queryResult.get("COUNT(*)");
        assertThat(amount.intValue())
                .as("STATUS_CODEs count is not equal to requested " + count).isEqualTo(count);
    }

    @Override
    public void verifyCarSearchDataInDB() {
        String email = authenticationParameters.getUsername();
        SearchParameters globalSearchParameters = searchParameters.getGlobalSearchParameters();
        String search_id = applicationModel.getSearchIDFromDB(email);
        String customer_id = applicationModel.getCustomerIDFromDB(email);

        DateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat formatterDate2 = new SimpleDateFormat("yyyyMMdd");
        DateFormat formatterTime = new SimpleDateFormat("hh:mma");
        DateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yyyy hh:mma");

        Date current_date = new Date();

        String start_date, pick_up_time;
        String start_date_db, pick_up_time_db;

        String end_date, drop_off_time;
        String end_date_db, drop_off_time_db;

        String sql1, sql2, sql3, sql4;

        String pcr_state = "N";

        Map<String, Object> mapResult;
        List<Map<String, Object>> listResult;


        sql1 = "select pickup_airport_code from search " +
                "where class = \'C\' and search_id = \'" + search_id + "\'";

        sql2 = "select cheapest_pub_fare_amount from search_solution" +
                " where search_id = \'" + search_id + "\'";

        sql3 = "select cheapest_multi_vendor_pub_code from search_solution" +
                " where search_id = \'" + search_id + "\'";

        sql4 = "select * from search where search_id = \'" + search_id + "\'" +
                "and customer_id = \'" + customer_id + "\' ";

        mapResult = jdbcTemplate.queryForMap(sql1);

        assertThat(globalSearchParameters.getDestinationLocation().equals(mapResult.get("pickup_airport_code")))
                .as("Pickup airport code in DB: expected: " +
                        globalSearchParameters.getDestinationLocation() + "; actual: " +
                        mapResult.get("pickup_airport_code")).isTrue();

        listResult = jdbcTemplate.queryForList(sql2);
        assertThat(listResult.size() > 0).as("cheapest_pub_fare_amount field is not empty").isTrue();

        listResult = jdbcTemplate.queryForList(sql3);
        assertThat(listResult.size() > 0).as("cheapest_multi_vendor_pub_code is not empty").isTrue();


        mapResult = jdbcTemplate.queryForMap(sql4);

        start_date_db = formatterDate.format(mapResult.get("Start_date"));
        pick_up_time_db = formatterTime.format(mapResult.get("Start_date"));
        start_date = formatterDate.format(globalSearchParameters.getStartDate());
        pick_up_time = searchParameters.getPickupTime();


        end_date_db = formatterDate.format(mapResult.get("End_date"));
        drop_off_time_db = formatterTime.format(mapResult.get("End_date"));
        end_date = formatterDate.format(globalSearchParameters.getEndDate());
        drop_off_time = searchParameters.getDropoffTime();

        assertThat(start_date.equals(start_date_db))
                .as("Start date from UHP:" + start_date + "; start date from DB: " + start_date_db).isTrue();
        assertThat(pick_up_time.equalsIgnoreCase(pick_up_time_db))
                .as("Pick up time from UHP:" + pick_up_time + "; pick up time from DB: " + pick_up_time_db).isTrue();
        assertThat(end_date.equals(end_date_db))
                .as("End date from UHP:" + end_date + "; end date from DB: " + end_date_db).isTrue();
        assertThat(drop_off_time.equalsIgnoreCase(drop_off_time_db))
                .as("Drop off time from UHP:" + drop_off_time + ";" +
                        " drop off time from DB: " + drop_off_time_db).isTrue();


        assertThat(globalSearchParameters.getDestinationLocation()
                .equals(mapResult.get("Pickup_airport_code").toString())).isTrue();

        try {
            long millesecDifference = formatterDateTime.parse(end_date + " " + drop_off_time).getTime() -
                    formatterDateTime.parse(start_date + " " + pick_up_time).getTime();
            long daysDifference = millesecDifference / (1000 * 60 * 60 * 24); //transform millisec to days
            BigDecimal quantity = (BigDecimal) mapResult.get("Quantity");

            assertThat(quantity.longValue() == daysDifference)
                    .as("(EndDate - StartDate) is: " + daysDifference + "should equals" +
                            " Quantity in DB: " + mapResult.get("Quantity")).isTrue();
        }
        catch (ParseException e) {
            System.out.println("Can't parse a string to the necessary date format");
        }


        assertThat((BigDecimal) mapResult.get("Is_long_term") == BigDecimal.ZERO).as("Is_long_term be zero").isTrue();
        assertThat(mapResult.get("Search_day").toString().equals(formatterDate2.format(current_date)))
                .as("Current search day is: " + formatterDate2.format(current_date) + "should equals" +
                        " Search_day in DB: " + mapResult.get("Search_day").toString()).isTrue();


        assertThat(mapResult.get("PCR_state").toString().equals(pcr_state))
                .as("PCR_state should be: " + pcr_state).isTrue();

        // mapResult.get("Best_price_amount").toString(); ??????????????????????
    }

    @Override
    public void verifyCarResultsNotFromCache() {
        String email = authenticationParameters.getUsername();
        String search_id = applicationModel.getSearchIDFromDB(email);

        List<Map<String, Object>> listResult;

        String sql1 = "select sp.crs_cache_status_code" +
                " from search s, search_solution ss, search_pgood sp, rental_car rc" +
                " where s.search_id = ss.search_id and sp.search_solution_id = ss.search_solution_id" +
                " and sp.pgood_id = rc.pgood_id and ss.search_id = \'" + search_id + "\'";

        listResult = jdbcTemplate.queryForList(sql1);
        assertThat(listResult.size() != 0).as("SQL returns non empty results").isTrue();
        for (int i = 0; i < listResult.size(); i++) {
            assertThat(listResult.get(i).get("CRS_CACHE_STATUS_CODE").equals("N"))
                    .as("All values for CRS_CACHE_STATUS_CODE should be equals 'N' ")
                    .isTrue();
        }
    }

    @Override
    public  void  verifyCommissionsForRetailCar() {

        List<Map<String, Object>> listResult;

        String sql1 = "select src.create_date,src.pgood_id, src.car_vendor_cd, src.pickup_airport_code, " +
                "src.car_type_cd,src.opacity_code,src.commission,r.email, " +
                "rs.status_code, rs.crs_error_code, rs.crs_error_msg " +
                "from sold_rental_car src, reservation r, reservation_status rs " +
                "where src.pgood_id = r.pgood_id " +
                "and r.reservation_id = rs.reservation_id " +
                "and src.opacity_code = \'N\' " +
                "and src.car_rate_type <> 'N' and src.car_rate_type <> \'M\' " +
                "and src.commission = \'0\' " +
                "and src.create_date > sysdate -3 " +
                "and status_code = \'50020\' " +
                "order by r.pgood_id desc, rs.status_code asc";

        listResult = jdbcTemplate.queryForList(sql1);
        assertThat(listResult.size() == 0).as("SQL returns an empty results").isTrue();

    }

    @Override
    public void verifyCarResultsPageDidntChange() {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        String firstSolutionNumber = carResultsPage.getResult().get(0).getReferenceNumber();
        CarFareFinder carFareFinder = CarFareFinderProvider.get(getWebdriverInstance());
        carFareFinder.run();
        String secondSolutionNumber = carResultsPage.getResult().get(0).getReferenceNumber();
        assertThat(firstSolutionNumber)
                .as("Display numbers of first solutions are not equal!").isEqualToIgnoringCase(secondSolutionNumber);
    }

    @Override
    public void makeCarSearchFromResultPage() {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance(), By.id("carRefineSearch"));
        carSearchFragment.startDate(carSearchParameters.getGlobalSearchParameters().getStartDate());
        carSearchFragment.endDate(carSearchParameters.getGlobalSearchParameters().getEndDate());
        carSearchFragment.startLocation(carSearchParameters.getGlobalSearchParameters().getDestinationLocation());
        carSearchFragment.getSearchButton().click();
    }

    @Override
    public void clickOnSearchButton() {
        CarSearchFragment carSearchFragment = new CarSearchFragment(getWebdriverInstance(), By.id("carRefineSearch"));
        carSearchFragment.getSearchButton().click();
    }

    @Override
    public void verifyLocationOnResultsPage(String location) {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        assertThat(carResultsPage.getStartLocation()).as("Location on results page as expected")
                .isEqualToIgnoringCase(location);
    }

    @Override
    public void verifyIntentMediaExistingElements() {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        assertThat(carResultsPage.isIntentMediaElementsExisting())
                .as("D2C module is not being replaced by Intent Media Elements")
                .isTrue();
    }

    @Override
    public void  validateMeSoBanners() {
        CarResultsPage carResultsPage = CarResultsPageProvider.get(getWebdriverInstance());
        assertThat(carResultsPage.isMeSoBannersDisplayed())
                .as("MeSo Banners are not being displayed correctly at the car results page")
                .isTrue();
    }
}
