/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.purchase;

import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import com.hotwire.test.steps.tools.bean.BillingInfo;
import com.hotwire.test.steps.tools.bean.CustomerInfo;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.TripInfo;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * User: v-abudyak
 * Date: 10/01/13
 * Time: 9:27 AM
 */

public class C3PurchaseSteps extends ToolsAbstractSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3PurchaseSteps.class);

    @Autowired
    private TripInfo tripInfo;

    @Autowired
    private HotwireProduct hotwireProduct;

    @Autowired
    private CustomerInfo customerInfo;

    @Autowired
    private BillingInfo billingInfo;

    private C3PurchaseModel getModel() {
        return hotwireProduct.getPurchaseModel();
    }

    @Given("^I'm looking for a (.*)$")
    public void setPGood(String verticalName) {
        hotwireProduct.setProductVertical(verticalName);
    }

    @Given("^I switch to (retail|opaque) hotels$")
    public void switchToRetail(String productType) {
        hotwireProduct.setProductVertical("hotel");
        if ("retail".equalsIgnoreCase(productType)) {
            hotwireProduct.setOpacity("retail");
            getModel().switchToRetail();
        }
        else if ("opaque".equalsIgnoreCase(productType)) {
            hotwireProduct.setOpacity("opaque");
            getModel().switchToOpaque();
        }

    }

    @Given("^I'm searching for a hotel in \"([^\"]*)\"$")
    public void setHotelDestinationLocation(String destinationLocation) {
        tripInfo.setFromLocation(destinationLocation);
        hotwireProduct.setProductVertical("hotel");
    }

    @Given("^I'm searching for a flight from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void setAirDestinationLocation(String fromLocation, String destinationLocation) {
        tripInfo.setFromLocation(fromLocation);
        tripInfo.setDestinationLocation(destinationLocation);
        hotwireProduct.setProductVertical("air");
    }

    @Given("^I will be flying with (\\d+) passengers$")
    public void setPassengerNumber(Integer passengerNumber) {
        tripInfo.setPassengers(passengerNumber);
    }

    @Given("^I want to travel between (.*) and (.*)$")
    public void setSearchDates(Integer start, Integer end) {
        tripInfo.setShiftStartDate(start);
        tripInfo.setShiftEndDate(end);
    }

    @Given("^I want (\\d+) room\\(s\\)$")
    public void setNumberOfHotelRooms(Integer numberOfHotelRooms) {
        tripInfo.setNumberOfHotelRooms(numberOfHotelRooms);
    }

    @Given("^I will be traveling with (\\d+) adults$")
    public void setNumberOfAdults(Integer numberOfAdults) {
        tripInfo.setNumberOfAdults(numberOfAdults);
    }

    @Given("^I will be traveling with (\\d+) children$")
    public void setNumberOfChildren(Integer numberOfChildren) {
        tripInfo.setNumberOfChildren(numberOfChildren);
    }

    @Given("^I launch search$")
    public void launchSearch()  {
        getModel().fillSearchParametersAndSubmit();
        getModel().processAlert();
//        getModel().verifyCustomerSalesPage();
        getModel().processSearchFrame();
        getModel().waitForResults();
    }

    @Given("^I search for (air|car|hotel)$")
    public void launchSearch(String verticalName) {
        hotwireProduct.setProductVertical(verticalName);
        getModel().fillSearchParametersAndSubmit();
        getModel().processAlert();
//        getModel().verifyCustomerSalesPage();
        getModel().processSearchFrame();
        getModel().waitForResults();
    }

    @Given("^I want to pick up at (\\S+) and drop off at (\\S+)$")
    public void setSearchTimes(String startTime, String endTime) {
        hotwireProduct.setProductVertical("car");
        hotwireProduct.getCarPurchaseModel().setPickupTime(startTime);
        hotwireProduct.getCarPurchaseModel().setDropoffTime(endTime);
    }

    @Given("^I see hotwire home page in C3 frame$")
    public void verifyFareFinderInC3() {
        hotwireProduct.setProductVertical("hotel");
        getModel().verifyFareFinder();
    }

    @Given("^I(\\sdon't)? see SignIn module$")
    public void verifySignInModule(String option) {
        getModel().verifySignInModule(option);
    }

    @Given("^I rebook (car|hotel)$")
    public void reBook(String verticalName) {
        hotwireProduct.setProductVertical(verticalName);
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
//        getModel().verifyCustomerSalesPage();
        getModel().processSearchFrame();
        getModel().fillSearchParametersAndSubmit();
        getModel().waitForResults();
        getModel().selectFirstResult();
        getModel().proceedDetailsPage();
        getModel().verifyPrefillingOnBillingPage();
        getModel().processBillingPage();
    }

    @Given("^I'm searching for a car in \"([^\"]*)\"$")
    public void setRoundTripLocations(String pickUpLoc) {
        // round trip, 'from'='to'
        tripInfo.setDestinationLocation(pickUpLoc);
        tripInfo.setFromLocation(pickUpLoc);
        hotwireProduct.setProductVertical("car");
    }

    @When("^I choose a .* and purchase as (guest|existing customer)$")
    public void completePurchase(String customerType)  {
        if (customerType.contains("guest")) {
            customerInfo.setCustomerType(CustomerInfo.CustomerTypes.GUEST);
        }
        else {
            customerInfo.setCustomerType(CustomerInfo.CustomerTypes.EXPRESS);
        }
        getModel().selectFirstResult();
        getModel().proceedDetailsPage();
        try {
            getModel().proceedInterstitialPage();
        }
        catch (Exception e) {
            LOGGER.info("Interstitial page doesn't present");
        }
        getModel().fillAllBillingInfo();
        getModel().clickBookButton();
    }

    @When("^I verify area map on car result page$")
    public void verifyAreaMapOnCarResult() {
        getModel().verifyAreaMapOnResults();
    }

    @Then("^I receive immediate confirmation$")
    public void receiveConfirmation() {
        getModel().receiveConfirmation();
    }

    @Then("^I verify confirmation page for hotdollars purchase$")
    public void verifyConfirmationPageForHotDollarsPurchase() {
        getModel().verifyConfirmationPageForHotDollarsPurchase();
    }

    @Then("^I verify destination location on car confirmation page contains \"(.*)\"$")
    public void verifyLocationOnConfirmationPage(String location) {
        getModel().verifyLocationOnConfirmationPage(location);
    }

    @Then("^The car was added successfully on confirmation$")
    public void checkCarAddonOnConfirmation() throws Exception {
        getModel().checkCarAddonOnConfirmation();
    }

    @Then("^I see refund/rebook message on confirmation page")
    public void verifyRefundRebookMsg() {
        getModel().verifyRefundRebookMsg();
    }

    @Then("^I see (.*) phone number on Air Results Global Contact module$")
    public void i_see_phone_number_on_Air_Results_Global_Contact_module(String phone)  {
        getModel().checkResultContactPhone(phone);
    }

    @Then("^I see (.*) phone number on Air Details Global Contact module$")
    public void i_see_phone_number_on_Air_Details_Global_Contact_module(String phone) {
        getModel().checkDetailsContactPhone(phone);

    }

    @Then("^I process the results page$")
    public void i_proceed_to_the_detail() {
        hotwireProduct.setOpacity(getModel().getTypeOfFirstResult());
        getModel().selectFirstResult();
    }

    @Then("^I make purchase and save info for non express customer$")
    public void makePurchaseAndSaveInfoForNonExpressCustomer() {
        getModel().selectFirstResult();
        getModel().killPopups();
        getModel().proceedDetailsPage();
        getModel().killPopups();
        getModel().processBillingPage();
        getModel().receiveConfirmation();
    }

    @Then("^I save confirmation page in the bean$")
    public void saveInformationInTheBeanFromConfirmationPage() {
        getModel().saveConfirmationPageInBean();
    }

    @Then("^I add rental car and complete purchase$")
    public void add_rental_car_on_detail() {
        getModel().addRentalCarOnDetail();
        getModel().processBillingPageWithCarAddOn();
    }

    @Then("^I see results page in frame")
    public void verifyResultsPageInC3()  {
        getModel().processSearchFrame();
        getModel().verifyResultsPage();
    }

    @Then("^I see results page$")
    public void verifyResultsPage() {
        getModel().verifyResultsPage();
    }

    @Then("^I see details page$")
    public void verifyDetailsPage() {
        getModel().verifyDetailsPage();
    }

    @Then("^I process the details page$")
    public void i_proceed_to_the_interstitial() {
        getModel().proceedDetailsPage();
    }

    @Then("^I process the billing page$")
    public void i_proceed_to_the_confirmation() {
        getModel().processBillingPage();
    }

    @Then("^I process the billing page without insurance$")
    public void processBillingPageWithoutInsurance() {
        billingInfo.setInsurance(false);
        getModel().processBillingPage();
    }


    @Then("^I process the billing page with HotDollars$")
    public void processBillingPageWithHotDollars() {
        billingInfo.setHotDollars(true);
        billingInfo.setInsurance(true);
        getModel().processBillingPage();
    }

    @Then("^I see prefilled values on billing page$")
    public void verifyPrefillingOnBillingPage()  {
        getModel().verifyPrefillingOnBillingPage();
    }

    @Then("^I fill user information$")
    public void iFillUserInformation() {
        getModel().fillAllBillingInfo();
    }

    @Then("^I have invalid credit card$")
    public void iTypeInvalidCreditCardNumber() {
        billingInfo.setCcNumber("4444441111111110");
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.GUEST);
    }

    @Then("^I have bad AVS credit card$")
    public void iHaveBadAVSCard() {
        billingInfo.setCcNumber("4001277777777777");
        billingInfo.setChoosePayWithNewCreditCard(true);
    }

    @Then("^I have bad CPV credit card$")
    public void iHaveBadCPVCard() {
        billingInfo.setCcNumber("4831110847500000");
        billingInfo.setChoosePayWithNewCreditCard(true);
    }

    @Then("^I have valid 54444 credit card$")
    public void iType54444CreditCardNumber() {
        billingInfo.setCcNumber("5555555555554444");
    }

    @Then("^I have valid 4...0028 credit card$")
    public void iType0028CreditCardNumber() {
        billingInfo.setCcNumber("4000000000000028");
    }

    @Then("^I have valid VPAS 4..0002 credit card$")
    public void iType0002CreditCardNumber() {
        billingInfo.setFlagChangedCard(true);
        billingInfo.setCcNumber("4000000000000002");
    }

    @Then("^I\\s+?(don't )?see static insurance module$")
    public void checkStaticInsurance(String negation) {
        getModel().checkStaticInsurance(StringUtils.isEmpty(negation));
    }

    @Then("^I verify that static insurance works correctly$")
    public void verifyStaticInsurance() {
        getModel().verifyStaticInsurance();
    }

    @Then("^insurance information saved correctly to database$")
    public void verifyInsuranceInDatabase() {
        getModel().verifyInsuranceInDatabase();
    }

    @Then("^I confirm booking$")
    public void iConfirmBooking() {
        getModel().clickBookButton();
    }

    @Then("^I see billing validation message (.*)$")
    public void verifyValidationMsgOnBilling(String msg)  {
        getModel().verifyValidationMsgOnBilling(msg);
    }

    @Then("^I see (.*) phone number on Air Billing Global Contact module$")
    public void i_see_phone_number_on_Air_Billing_Global_Contact_module(String phone) {
        getModel().checkBillingContactPhone(phone);
    }

    @Then("^I see (.*) and (.*) phone numbers on Confirmation page$")
    public void i_see_HOTWIRE_and_phone_numbers_on_Confirmation_page(String phone1, String phone2)  {
        getModel().checkConfirmationContactPhones(phone1, phone2);
    }

    @Then("^I see (.*) phone number on Car Results Global Contact module$")
    public void i_see_phone_number_on_Car_Results_Global_Contact_module(String phone) {
        getModel().checkResultContactPhone(phone);
    }

    @Then("^I see (.*) phone number on Car Billing Details Global Contact module$")
    public void i_see_phone_number_on_Car_Billing_Details_Global_Contact_module(String phone) {
        getModel().checkDetailsContactPhone(phone);
    }

    @Then("^I see (.*) phone number on Hotel Results Global Contact module$")
    public void i_see_phone_number_on_Hotel_Results_Global_Contact_module(String phone) {
        getModel().checkResultContactPhone(phone);
    }

    @Then("^I see (.*) phone number on Hotel Details Global Contact module$")
    public void i_see_phone_number_on_Hotel_Details_Global_Contact_module(String phone) {
        getModel().checkDetailsContactPhone(phone);
    }

    @Then("^I see (.*) phone number on Hotel Billing Global Contact module$")
    public void i_see_phone_number_on_Hotel_Billing_Global_Contact_module(String phone) {
        getModel().checkBillingContactPhone(phone);
    }

    @Given("^I save reference number from details page$")
    public void saveReferenceNumber() {
        getModel().saveReferenceNumberFromDetailsPage();
    }

    @Given("^I save reference number from results page$")
    public void saveReferenceNumberFromResultsPage() {
        getModel().saveReferenceNumberFromResultsPage();
    }

    @Then("^I see results page with the same search parameters$")
    public void verifySearchParametersOnResultsPage() {
        getModel().verifySearchParametersOnResultsPage();
    }

    @Then("^I see details page with the same search parameters$")
    public void verifySearchParametersOnDetailsPage() {
        getModel().verifySearchParametersOnDetailsPage();
    }

    @Then("^I see search result on interstitial page$")
    public void i_see_search_result_on_interstitial() {
        getModel().checkSearchResultOnInterstitial();
    }

    @Given("^I search for customer with (.*) \"([^\"]*)\" on interstitial page$")
    public void i_search_for_customer_with(String criteria, String value) {
        customerInfo.setCustomerType(CustomerInfo.CustomerTypes.NON_EXPRESS);
        customerInfo.setPhoneNumber(value);
        getModel().searchCustomerWithCriteria(criteria, value);
    }

    @Then("^I process the interstitial page$")
    public void i_proceed_to_the_billing() {
        getModel().proceedInterstitialPage();
    }

    @Then("^I try to proceed the interstitial page$")
    public void iTryToProceedInterstitialPage() {
        try {
            getModel().proceedInterstitialPage();
        }
        catch (Exception e) {
            LOGGER.info("Interstitial page doesn't present");
        }
    }
}

