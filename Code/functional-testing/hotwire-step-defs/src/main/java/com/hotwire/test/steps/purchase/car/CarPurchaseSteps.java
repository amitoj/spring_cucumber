/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car;

import com.hotwire.selenium.desktop.row.models.CarDataVerificationParameters;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.paypal.PayPalPurchaseSteps;
import com.hotwire.test.steps.search.car.CarSearchParametersImpl;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author v-mzabuga
 * @since 7/18/12
 */
public class CarPurchaseSteps extends AbstractSteps {

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel model;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("carDataVerificationParameters")
    private CarDataVerificationParameters carDataVerificationParameters;

    @Autowired
    @Qualifier("randomGuestUser")
    private UserInformation validCardLengthUser;

    @Autowired
    @Qualifier("invalidCardLengthUser")
    private UserInformation invalidCardLengthUser;

    @Autowired
    @Qualifier("invalidMasterCardUser")
    private UserInformation invalidMasterCardUser;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParametersImpl carSearchParameters;

    @When("^I choose a(.*) car and purchase as(.*) a quote$")
    public void selectFirstCarResultAndCompletePurchase(String purchaseType, String guestOrUser) {
        selectFirstCarResult(purchaseType, null);
        model.completePurchase("user".equals(guestOrUser.trim()));
    }

    @When("^I choose the first(.*) car$")
    public void selectCarResult(String opaqueRetail) {
        purchaseParameters.setOpaqueRetail(opaqueRetail);
        model.selectPGood();
    }

    @When("^I continue to billing as(.*) a quote$")
    public void continuePurchase(String guestOrUser) {
        model.continueToBillingFromDetails();
        model.continueToBilling("user".equals(guestOrUser.trim()));
    }

    @When("^I fill payment info$")
    public void fillPayment() {
        model.fillPaymentInfo();
    }

    @When("^I save my payment info$")
    public void savePaymentInfo() {
        model.savePaymentInfo();
    }

    @When("^I proceed with the payment$")
    public void proceedPayment() {
        model.proceedWPayment();
    }

    @Then("^Car details page is displayed$")
    public void details_page_displayed() {
        model.verifyCarDetailsPage();
    }

    @Then("^I verify payment method is saved$")
    public void savedPaymentMethod() {
        model.verifySavedPaymentInfo();
    }


    @When("^I write to (.*) (PayPal|payment|driver's) field (.*)$")
    public void writeToField(String fieldType, String blockType, String fieldValue) {
        if ("PayPal".equals(blockType)) {
            if (!PayPalPurchaseSteps.isPaypalSandboxAvailable()) {
                throw new PendingException("Paypal sandbox is unavailable on Friday.");
            }
            model.writeToPayPalField(fieldType, fieldValue);
        }
        else if ("payment".equals(blockType)) {
            model.writeToPaymentField(fieldType, fieldValue);
        }
        else if ("driver's".equals(blockType)) {
            model.writeToTravelerField(fieldType, fieldValue);
        }
    }

    @When("^I choose a \"([^\"]*)\" car and purchase with out insurance as (user|guest)$")
    public void selectFirstCarResultAndCompletePurchaseWithOutInsurance(String purchaseType, String guestOrUser) {
        selectFirstCarResultAndCompletePurchase(purchaseType, guestOrUser);
    }

    @When("^I confirm new search with updated (.*)$")
    public void confirmNewSearch(String parameter) {
        model.confirmNewSearch(parameter);
    }

    @Then("^I should see updated value$")
    public void verifyUpdatedParameter() {
        model.verifyUpdatedParameter();
    }

    @When("^I choose a(.*) car(\\sand hold on details)?$")       // User goes to Details page by default
    public void selectFirstCarResult(String purchaseType, String holdOnDetails) {

        purchaseParameters.setOpaqueRetail(purchaseType);
        model.selectPGood();

        if (holdOnDetails == null) {
            goFromDetailsToBilling();
        }
    }

    public void goFromDetailsToBilling() {
        model.processCarDetailsPage();
        model.continueToBillingFromDetails();
    }

    @When("^I choose an (airport|local) (.*) result$")
    public void chooseAirportOrLocalResult(String airportOrLocal, String carModels) {
        model.selectCarByCdCodeAndAirportOrLocalLocation(airportOrLocal, carModels);
    }

    @When("^I click 'Continue' button and wait for price check$")
    public void clickContinueButtonAndWaitForPriceCheck() {
        model.waitForPriceCheckAfterContinue();
    }

    @When("^I click a(.*) car(\\sand hold on details)? with (total|per day) cost (more|less) than (.*) USD$")
    public void selectCarWithCostMoreLessThan(
            String purchaseType, String holdOnDetails, String priceType, String condition, float cost) {

        purchaseParameters.setPriceTypeToSelect("total".equals(priceType) ? "total" : "per day");
        purchaseParameters.setPriceConditionToSelect("more".equals(condition) ? "more" : "less");
        purchaseParameters.setCostToSelect(cost);

        selectFirstCarResult(purchaseType, holdOnDetails);
    }

    @Given("^I want to check all data$")
    public void setVerificationParameters() {
        carDataVerificationParameters.setShouldSaveDataForVerification(true);
    }

    @When("^I choose a(.*) car with (.*) payment type$")
    public void selectPrepaidResult(String purchaseType, String paymentType) {
        purchaseParameters.setIsPrepaidSolution("prepaid".equals(paymentType));
        purchaseParameters.setResultNumberToSelect(1);
        model.selectPGood();
    }

    /**
     * Fills all required fields from details to billing pages according to payment type
     * Available for credit card and PayPal
     * But doesn't complete purchase
     */
    @When("^I fill in all ?(creditCard|PayPal|HotDollars|savedVisa|savedMasterCard|) billing information$")
    public void fillInAllBillingInformation(PurchaseParameters.PaymentMethodType methodType) {
        setPaymentMethod(methodType);
        processAllBillingInformation(true);
    }

    public void setPaymentMethod(PurchaseParameters.PaymentMethodType methodType) {
        if (methodType != null) {
            if (methodType.equals(PurchaseParameters.PaymentMethodType.PayPal) &&
                    !PayPalPurchaseSteps.isPaypalSandboxAvailable()) {
                throw new PendingException("Paypal sandbox is unavailable on Friday.");
            }
            purchaseParameters.setPaymentMethodType(methodType);
        }
    }

    public void processAllBillingInformation(Boolean isUser) {
        model.processAllBillingInformation(isUser);
    }

    /**
    *Fills billing information with CPV or AVS error credit card number
     **/
    @When("^I fill in billing information to cause a ?(CPV|AVS|Auth|) error$")
    public void fillInErrorCCNumber(String errorType) {
        model.fillInErrorCCNumber(errorType);
    }

    @When("^I fill in traveler's information$")
    public void fillInTravellerInformation() {
        UserInformation user = purchaseParameters.getUserInformation();
        model.fillTravellerInfo(user);
    }

    @When("^I verify that user logged in and traveller info is prepopulated$")
    public void verifyTravellerInfoPrepopulated() {
        model.verifyTravellerInfoPrepopulated();
    }

    @When("^I fill in insurance$")
    public void fillInsurance() {
        model.fillInsurance();
    }

    /**
     * Clicking on payment method radio button on billing page
     * Only available for PayPal
     */
//    @When("^I select (PayPal|savedVisa) payment option$")
//    public void selectPaymentMethod(PurchaseParameters.PaymentMethodType methodType) {
//        if (methodType == null) {
//            throw new PendingException("No such payment option.");
//        }
//        if (methodType.equals(PurchaseParameters.PaymentMethodType.PayPal) &&
//                !PayPalPurchaseSteps.isPaypalSandboxAvailable()) {
//            throw new PendingException("Paypal sandbox is unavailable on Friday.");
//        }
//        purchaseParameters.setPaymentMethodType(methodType);
//        model.selectPaymentMethod();
//    }

    @When("^I fill in state$")
    public void fillState() {
        purchaseParameters.setShouldSetState(true);
    }

    @When("^I fill \"([^\"]*)\" in postal code field$")
    public void fillPostalCode(String zipCode) {
        purchaseParameters.getUserInformation().setZipCode(zipCode);
    }

    @Then("^I change primary driver\'s name to (.*)$")       // billing page
    public void changeDriverNames(String driverName) {
        if ("random".equalsIgnoreCase(driverName)) {
            model.changeDriverNamesOnBillingPage(
                    RandomStringUtils.randomAlphabetic(8),
                    RandomStringUtils.randomAlphabetic(12));
        }
        else {
            model.changeDriverNamesOnBillingPage(
                    driverName.split("\\s")[0],
                    driverName.split("\\s")[1]);
        }
    }

    /**
     * Notice: The target page (billing or confirmation) should be displayed before call.
     *
     * @param pageName String
     * @param carType  String type of a car which we selected on results page.
     */
    @Then("^I compare(.*) car options between results and (details|billing|confirmation)$")
    public void confirmCarOptionsOnPage(String carType, String pageName) {
        if ("details".equals(pageName)) {
            selectFirstCarResult(carType, null);
            goFromDetailsToBilling();
            model.compareSolutionOptionsWithDetails();
        }
        else if ("confirmation".equals(pageName)) {
            model.compareSolutionOptionsWithConfirmation();
        }
        else if ("billing".equals(pageName)) {
            model.compareSolutionOptionsWithDetails();
        }
        else {
            throw new PendingException("Invalid page name. Values billing or confirmation was expected.");
        }
    }

    @Then("^I verify Guaranteed car section on details page$")
    public void verifyGuaranteedCarSection() {
        model.verifyGuaranteedCarSection();
    }

    @Then("^I verify header for car details page$")
    public void verifyHeaderForCarDetailsPage() {
        model.verifyHeaderForCarDetailsPage();
    }

    @Then("^I save car confirmation information$")
    public void saveCarConfirmationInformation() {
        model.saveCarConfirmationInformation();
    }

    /**
     * This step compares retail car options between first grid row and trip summary on details page
     * then it changes to second grid row and checks again. used in rtc 1055 @bshukaylo
     */
    @Then("^I compare car options between grid and trip summary section$")
    public void compareVendorGridWithTripSummary() {
        model.compareVendorGridWithTripSummary();
    }

    @Then("^I compare total price between Review your trip and trip summary$")
    public void compareTotalBetweenReviewModuleAndStipSummary() {
        model.compareTotalBetweenReviewModuleAndStipSummary();
    }

    /**
     * This step compares locations in grid on retail car details page with DB values;
     * used in RTC-4039 @bshukaylo
     */
    @Then("^I verify locations in retail car grid with DB$")
    public void verifyLocationsInRetailGridWithDB() {
        model.verifyLocationsInRetailGridWithDB();
    }

    @Then("^I verify locations for opaque car on details page with DB$")
    public void verifyLocationsForOpaqueCarWithDB() {
        model.verifyLocationsForOpaqueCarWithDB();
    }

    @Then("^I verify status and network codes for failed car purchase with DB$")
    public void verifyStatusAndNetworkCodesForFailedCarPurchaseWithDB() {
        model.verifyStatusAndNetworkCodesForFailedCarPurchaseWithDB();
    }

    @Then("^I verify airport locations for opaque car on details page with DB$")
    public void verifyAirportLocationsForOpaqueCarWithDB() {
        model.verifyAirportLocationsForOpaqueCarWithDB();
    }


    /**
     * Step compares car options between billing (trip summary module)
     * and confirmation pages
     */
    @Then("Trip summary is the same after booking")
    public void verifyTripSummaryAfterBooking() {
        // Get random solution from results page by carType
        selectFirstCarResult((Math.random() < 0.5) ? "opaque" : "retail", null);
        goFromDetailsToBilling();
        model.verifyTripSummaryAfterBooking();
    }

    @Then("^Trip summary before booking is$")
    public void verifyTripSummaryBeforeBooking(DataTable params) {
        model.verifyTripSummaryBeforeBooking(params);
    }

    /**
     * Hertz confirmation code
     * - Only for results from Hertz in Caribbean locations Hertz tour
     * number should be displayed on Confirmation page and email
     * - Hertz tour number should be ITHOTWCARB
     */
    @Then("^Hertz confirmation code is displayed$")
    public void verifyHertzConfirmationCode() {
        model.verifyHertzConfirmationCodeExists();
    }

    /**
     * Steps for fill not valid information for test validation
     */
    @Given("^I'm a guest user with (invalid MasterCard) credit card$")
    @When("^I fill in CC number with (.+)$")
    public void fillCreditCardNumber(String creditCardNumberType) {
        if ("invalidCreditCardNumber".equals(creditCardNumberType)) {
            purchaseParameters.setUserInformation(invalidCardLengthUser);
        }
        else if ("validCreditCardNumber".equals(creditCardNumberType)) {
            purchaseParameters.setUserInformation(validCardLengthUser);
        }
        else if ("invalid MasterCard".equals(creditCardNumberType)) {
            purchaseParameters.setUserInformation(invalidMasterCardUser);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Verify that message in Price Details Module was appear(for car intl)
     */
    @Then("^I see corresponding currency message in Price Details Module$")
    public void receiveCurrencyMessage() {
        model.verifyCurrencyMessage();
    }

    /**
     * Verify that additional features message in HotDollars Module was appear
     */
    @Then("^I see (.+) in HotDollars Module$")
    public void checkHotDollarsMessage(String message) {
        model.checkHotDollarsMessage(message);
    }

    @Then("^HotDollars payment method is (not available|available) on car billing page$")
    public void checkHotDollarsModule(String moduleIsAvailable) {
        if ("available".equalsIgnoreCase(moduleIsAvailable)) {
            model.checkHotDollarsModule(true);
        }
        else {
            model.checkHotDollarsModule(false);
        }
    }

    @When("^I'm back to results page from details page$")
    public void backToResultsFromDetails() {
        model.backToResultsFromDetailsPage();
    }

    @Then("^promotional checkbox is (hidden|displayed)( and checked| and unchecked)?$")
    public void verify_promotional_checkbox_is_displayed(String displayCondition, String checkedCondition) {
        model.verifyPromotionalCheckboxIsDisplayed("hidden".equals(displayCondition));

        if ("displayed".equals(displayCondition)) {
            model.verifyPromotionalCheckboxIsChecked(" and checked".equals(checkedCondition));
        }
    }

    @Then("^I choose (saved )?(PayPal|CreditCard) payment method$")
    public void choose_payment_method(String savedState, PurchaseParameters.PaymentMethodType methodType) {
        setPaymentMethod(methodType);
        model.choosePaymentMethod("saved ".equals(savedState));
    }

    @Then("^(PayPal|HotDollars|CreditCard|SavedCreditCard|none) method is chosen$")
    public void find_payment_method_that_is_chosen(PurchaseParameters.PaymentMethodType methodType) {
        setPaymentMethod(methodType);
        model.checkPaymentMethodThatIsChosen(methodType);
    }

    @Then("^I (will|will not) see insurance in car billing$")
    public void verifyInsuranceModuleInBilling(String state) {
        model.verifyInsuranceModuleInBilling(state.trim().equals("will") ? false : true);
    }

    @Then("^I see insurance is still selected$")
    public void verifyInsuranceIsSelected() {
        model.verifyInsuranceIsSelected();
    }

    @Then("^I validate insurance cost for car is \"([^\"]*)\" USD on billing page$")
    public void getInsuranceCost(String cost) {
        model.getInsuranceCost(cost);
    }

}



