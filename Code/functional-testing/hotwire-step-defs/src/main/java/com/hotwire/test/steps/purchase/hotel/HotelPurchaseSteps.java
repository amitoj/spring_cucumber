/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.hotel;

import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.account.ParticipantInformation;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.AbstractSteps;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.PurchaseParameters.PaymentMethodType;
import com.hotwire.test.steps.purchase.paypal.PayPalPurchaseSteps;
import com.hotwire.testing.UnimplementedTestException;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Arrays;
import java.util.List;

/**
 * @author v-mzabuga
 * @since 2012.07
 */
public class HotelPurchaseSteps extends AbstractSteps {


    @Autowired
    @Qualifier("applicationModel")
    ApplicationModel applicationModel;

    @Autowired
    @Qualifier("hotelPurchaseModel")
    private HotelPurchaseModel model;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Autowired
    private AuthenticationParameters authenticationParameters;

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
    @Qualifier("obsoleteBillingInformationUser")
    private UserInformation obsoleteBillingInformationUser;

    @Autowired
    @Qualifier("blankBillingInformationUser")
    private UserInformation blankBillingInformationUser;

    @Autowired
    @Qualifier("specialCharactersBillingInformationUser")
    private UserInformation specialCharactersInformationUser;

    @Autowired
    @Qualifier("specialCharactersTravelerInformationUser")
    private UserInformation specialCharactersTravelerInformation;

    private String insuranceCost;

    @When("^I choose a blank last name field for hotel traveler$")
    public void blankLastName() {
        purchaseParameters.getUserInformation().setLastName(null);
        this.model.completePurchase(false);
    }

    @When("^I type a credit card number with letter")
    public void creditCardContainsLetter() {
        purchaseParameters.getUserInformation().setLastName(null);

        this.model.completePurchase(false);
    }

    @When("^I choose a blank email field for hotel traveler$")
    public void blankEmailAndConfirmEmail() {
        purchaseParameters.getUserInformation().setEmailId(null);
        this.model.completePurchase(false);
    }

    @When("^I authenticate with \"(.*)\" user and \"(.*)\" password on billing page$")
    public void authOnBillingPage(String email, String password) {
        this.model.attemptToSignIn(email, password);
    }

    @When("^I choose a blank hotel travelers section like (guest|user)$")
    public void blankTravelersSection(String auth) {
        boolean bAuth = false; // guest
        if (auth.equals("user")) {
            bAuth = true;
        }

        purchaseParameters.getUserInformation().setFirstName(null);
        purchaseParameters.getUserInformation().setLastName(null);
        purchaseParameters.getUserInformation().setEmailId(null);
        purchaseParameters.getUserInformation().setMiddleName(null);
        purchaseParameters.getUserInformation().setPrimaryPhoneNumber(null);
        this.model.completePurchase(bAuth);
    }

    @When("^I choose a hotel(.*) as (.*) a quote$")
    public void selectFirstResult(String purchaseOption, String guestOrUser) {
        if (StringUtils.isNotBlank(purchaseOption) && purchaseOption.contains("opaque")) {
            this.purchaseParameters.setOpaqueRetail("opaque");
        }
        else {
            this.purchaseParameters.setOpaqueRetail("retail");
        }
        this.purchaseParameters.setResultNumberToSelect(0);
        this.model.selectPGood();
        this.model.continueToBillingFromDetails();

        if (StringUtils.isNotBlank(purchaseOption) && purchaseOption.contains("insurance")) {
            this.purchaseParameters.setOptForInsurance(true);
        }

        if (StringUtils.isNotBlank(purchaseOption) && purchaseOption.contains("purchase")) {
            if (StringUtils.containsIgnoreCase(purchaseOption, "paypal")) {
                if (!PayPalPurchaseSteps.isPaypalSandboxAvailable()) {
                    throw new PendingException("Paypal sandbox is unavailable on Friday.");
                }
                this.purchaseParameters.setPaymentMethodType(PaymentMethodType.PayPal);
            }
            else if (StringUtils.containsIgnoreCase(purchaseOption, "v.me") ||
                     StringUtils.containsIgnoreCase(purchaseOption, "vme")) {
                this.purchaseParameters.setPaymentMethodType(PaymentMethodType.V_ME);
            }

            else if (!purchaseParameters.getPaymentMethodType().equals(PaymentMethodType.HotDollars)) {
                this.purchaseParameters.setPaymentMethodType(PaymentMethodType.CreditCard);
            }
            model.completePurchase(guestOrUser.equals("user"));
        }
    }

    @When("^I complete a hotel as (.*) a quote$")
    public void completePurchase(String guestOrUser) {
        model.completePurchase(guestOrUser.equalsIgnoreCase("user"));
    }

    @When("^I choose a (opaque|retail) hotel$")
    public void chooseFirstResult(String opacity) {
        this.purchaseParameters.setOpaqueRetail(opacity);
        this.purchaseParameters.setResultNumberToSelect(0);
        this.model.selectPGood();
        this.model.continueToBillingFromDetails();
        this.purchaseParameters.setPaymentMethodType(PaymentMethodType.CreditCard);
    }

    @When("^I choose a (opaque|retail) hotel that costs (less|more) than (.*)$")
    public void chooseResultWithCost(String opacity, String condition, String cost) {
        this.purchaseParameters.setOpaqueRetail(opacity);
        this.purchaseParameters.setResultNumberToSelect(this.model.getSolutionNumberWithPrice(cost, condition));
        this.model.selectPGood();
        this.model.continueToBillingFromDetails();
        this.purchaseParameters.setPaymentMethodType(PaymentMethodType.CreditCard);
    }

    @When("^I fill in credit card number with (.+)$")
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
        else if ("character in card number".equals(creditCardNumberType)) {
            UserInformation userInformation = SerializationUtils.clone(purchaseParameters.getUserInformation());
            userInformation.setCcNumber("411111111111111a");
            purchaseParameters.setUserInformation(userInformation);
        }
        else if ("only characters in security code".equals(creditCardNumberType)) {
            UserInformation userInformation = SerializationUtils.clone(purchaseParameters.getUserInformation());
            userInformation.setSecurityCode("AAA");
            purchaseParameters.setUserInformation(userInformation);
        }
        else if ("obsolete credit card date".equals(creditCardNumberType)) {
            purchaseParameters.setUserInformation(obsoleteBillingInformationUser);
        }
        else if ("only numbers in billing address".equals(creditCardNumberType)) {
            UserInformation userInformation = SerializationUtils.clone(purchaseParameters.getUserInformation());
            userInformation.setBillingAddress("123456");
            purchaseParameters.setUserInformation(userInformation);
        }
        else if ("only characters in zip code".equals(creditCardNumberType)) {
            UserInformation userInformation = SerializationUtils.clone(purchaseParameters.getUserInformation());
            userInformation.setZipCode("AAAA");
            purchaseParameters.setUserInformation(userInformation);
        }
        else if ("blank values".equals(creditCardNumberType)) {
            purchaseParameters.setUserInformation(blankBillingInformationUser);
        }
        else if ("dinersCreditCardNumber".equals(creditCardNumberType)) {
            UserInformation userInformation = SerializationUtils.clone(purchaseParameters.getUserInformation());
            userInformation.setCcNumber("36438936438936");
            userInformation.setSecurityCode("888");
            purchaseParameters.setUserInformation(userInformation);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @When("^I edit saved credit card information$")
    public void editSavedCreditCardAndPurchaseHotel() {
        UserInformation userInformation = SerializationUtils.clone(purchaseParameters.getUserInformation());
        Integer rnd = 10 + (int) (Math.random() * ((99 - 10) + 1));
        userInformation.setZipCode("941" + rnd.toString()); // updating ZipCode
        // for the
        // creditCard
        purchaseParameters.setUserInformation(userInformation);
        model.clickEditCreditCardLink();
        //model.fillSavedCreditCard();
        model.editCard();
    }

    @When("^I fill credit card and save billing information for (new registered|logged) user$")
    public void fillCreditCardAndSaveBillingInformation(String newOrExistedUser) {
        purchaseParameters.getUserInformation().setEmailId(null);
        UserInformation userInformation = purchaseParameters.getUserInformation();
        if (newOrExistedUser.equals("new registered")) {
            model.fillTravelerInfo(userInformation, false);
        }
        try {
            // Looking for saved credit cards
            model.selectSavedCreditCard();
        }
        catch (NoSuchElementException ignored) {
            // No saved credit cards found - filling out form
            model.fillCreditCard();
            model.saveBillingTotalInBean();
            model.saveBillingInformation(purchaseParameters.getSavedCreditCard().getNames().get(0));
        }
    }

    @Then("^I should see (blank|special characters|obsolete|numbers in name|invalid phone number|blank " +
          "country|digital name|too long name|letter in card number|" +
          "numbers in address|numbers in city|AAA in security) " +
          "(billing|traveler) information errors(?:(?:\\sfor a\\s)(guest|registered) user)?$")
    public void validateBookingInformationErrors(String bookingProfile, String bookingStep, String userType) {
        boolean isSignedInUser = "registered".equals(userType);

        if ("billing".equals(bookingStep)) {
            this.model.validateBillingErrors(bookingProfile, isSignedInUser);
        }
        else if ("traveler".equals(bookingStep)) {
            this.model.validateTravelerErrors(bookingProfile);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    @When("^I fill in the traveler information for hotel and select the activities and services$")
    public void fillTravelInfoForHotel() {
        this.model.fillTravelInfoForHotel();
    }

    @Then("^the ticket types are sorted in alphabetical_order$")
    public void checkTicketTypesSortedOrNot() {
        this.model.checkTicketTypesSortedOrNot();

    }

    @Then("^I see More Details links and they are click-able$")
    public void checkMoreDetailsLinks() {
        this.model.checkMoreDetailsLinks();

    }

    @And("^Choose activities$")
    public void selectActivities() {
        this.model.selectActivities();
    }

    @When("^I choose to add a new traveler$")
    public void chooseAddNewTravelerFromSelection() {
        model.chooseAddNewTravelerFromSelection();
    }

    @When("^I complete the booking with saved user account$")
    public void completeHotelBookingWithSavedUser() {
        model.completeHotelBookingWithSavedUser();
    }

    @Then("^Summary of charges on opaque billing page is valid$")
    public void validateSummaryOfChargesChargesOnOpaqueHotelBillingPage() {
        this.model.verifySummaryOfChargesChargesOnOpaqueHotelBillingPage();
    }

    @Then("^Summary of charges on new billing page is valid$")
    public void validateSummaryOfChargesOnNewBillingPage() {
        this.model.verifySummaryOfChargesOnNewBillingPage();
    }

    @Then("^Charges from retail confirmation page are stored in DB correctly$")
    public void validateRetailChargesAreStoredInDBCorrectly() {
        this.model.verifyRetailChargesAreStoredInDBCorrectly();
    }

    @Then("^Charges from opaque confirmation page are stored in DB correctly$")
    public void validateOpaqueChargesAreStoredInDBCorrectly() {
        this.model.verifyOpaqueChargesAreStoredInDBCorrectly();
    }

    @Then("^I receive error message$")
    public void verifyErrorMessage() {
        this.model.verifyErrorMessage();
    }

    @When("^I add a new traveler$")
    public void addNewTraveler() {
        // Create the ParticipantInformation using first name, last name, phone
        // country code and phone number
        ParticipantInformation participant = new ParticipantInformation(
            "NewTravelerFirstName",
            "NewTravelerLastName",
            "1" /* phone country code */,
            "4151231234" /* phone number */);
        // Set it in the model
        this.purchaseParameters.getUserInformation().setParticipantInformation(participant);
    }

    @When("^I book that hotel$")
    public void continueToBillingFromDetails() {
        model.continueToBillingFromDetails();
    }

    @When("^I book selected hotel$")
    public void bookSelectedHotel() {
        model.bookSelectedHotel();
    }

    @And("^mandatory hotel-imposed fees include \"([^\"]*)\"$")
    public void verifyMandatoryFeesSection(String fee) {
        model.verifyMandatoryFee(fee);
    }

    @When("^I choose a hotel result$")
    public void chooseHotelResult() {
        this.purchaseParameters.setResultNumberToSelect(0);
        this.model.selectPGood();
    }

    @When("^I choose a hotel that has \"(.*)\" in its name$")
    public void chooseHotelResultWhoseNameContains(String nameSubstring) {
        model.chooseHotelResultWhoseNameContains(nameSubstring.trim());
    }

    @When("^I choose a retail resort result$")
    public void chooseRetailResortResult() {
        model.chooseRetailResortResult();
    }

    @Then("^I (will|will not) see the resort fees on the details page$")
    public void verifyResortFeesOnDetailsPage(String state) {
        this.model.verifyResortFeesOnDetailsPage("will".equals(state.trim()));
    }

    @Then("^I (will|will not) see the resort fees on the billing page trip summary$")
    public void verifyResortFeesOnBillingTripSummary(String state) {
        model.verifyResortFeesOnBillingTripSummary("will".equals(state.trim()));
    }

    @Then("^I see the discount information on the hotel details$")
    public void verifyDiscountBannerOnHotelDetails() throws Throwable {
        model.verifyDiscountBannerOnHotelDetails();
    }

    @When("^I review the itinerary as a (guest|registered) user$")
    public void reviewItineraryAsGuestOrRegistered(String userType) {
        Boolean param = false;
        model.setADAAmenities();
        model.continueToBillingFromDetails();
        if (userType.equals("registered")) {
            param = true;
        }
        model.attemptToSignIn(authenticationParameters, param);
    }

    @Then("^I see the discount information on the (guest|registered) itinerary$")
    public void verifyDiscountBannerOnHotelItinerary(String userType) throws Throwable {
        Boolean param = false;

        if (userType.equals("registered")) {
            param = true;
        }
        model.verifyDiscountBannerOnHotelItinerary(param);
    }

    @And("^I click on price module of hotel details$")
    public void continueToBillingFromDetailsBySelectingPrice() {
        model.continueToBillingFromDetailsBySelectingPrice();
    }

    @Then("^I should navigate to signin for purchase as guest or user$")
    public void landonBillingPageToPurchase() {
        model.landonBillingPageToPurchase();
    }

    @And("^google adwords marketing beacon is present$")
    public void googleAdwordsMarketingBeaconIsPresent() {
        model.verifyMarketingBeacon();
    }

    @Then("^I see hotel-imposed taxes and fees are included in total price$")
    public void verifyTripSummary() {
        model.verifyBillingPage();
    }

    @And("^I am not charged for hotel-imposed taxes and fees$")
    public void verifyFeesAndTaxesOnConfirmation() {
        model.verifyFeesAndTaxesOnConfirmation();
    }

    @When("^I fill in (traveler info|incorrect email|empty phone number)$")
    public void fillBillingPage(String info) throws CloneNotSupportedException {
        UserInformation userInformation = SerializationUtils.clone(purchaseParameters.getUserInformation());
        if ("incorrect email".equals(info)) {
            userInformation.setEmailId("wrongEmail");
        }
        else if ("empty phone number".equals(info)) {
            userInformation.setPrimaryPhoneNumber("");
        }
        else if (!"traveler info".equals(info)) {
            throw new UnimplementedTestException("Implement me!");
        }
        model.fillTravelerInfo(userInformation, false);
    }

    @Then("^I see (error|success) validation marks? (?:on|for) (traveler info|email|phone number)$")
    public void verifyTravelerInfoValidationMark(String marks, String fields) {
        model.verifyValidationMarks(fields, marks);
    }

    @Then("^I see new agree and book section$")
    public void iSeeNewAgreeAndBookSection() {
        model.validateNewAgreeAndBookSection();
    }

    @Then("^I see (reduced|full) set of credit card fields$")
    public void validateSetOfCreditCardFields(String setOfFields) {
        model.validateSetOfCreditCardFields(setOfFields);
    }

    @And("^Saving of credit card (is|is not) available$")
    public void validateSaveCreditCardPresence(String savedCreditCardPresence) {
        model.validateSaveCreditCardPresence(savedCreditCardPresence);
    }

    @Then("^I will see the last hotel booked on the opaque details page$")
    public void verifyLastHotelBookedOnOpaqueDetailsPage() {
        model.verifyLastHotelBookedOnOpaqueDetailsPage();
    }

    @Then("^I will see (opaque|retail) details page$")
    public void verifyDetailsPage(String pageType) {
        model.verifyDetailsPage(pageType.trim());
    }

    @Then("^the emails are populated in traveler info$")
    public void verifyEmailsPopulatedInTravelerInfoBillingPage() {
        model.verifyEmailsPopulatedInTravelerInfoBillingPage();
    }

    @Given("I filtered by (.*) hood name")
    public void filteredByHoodName(String hoodName) {
        model.filteredByHoodName(hoodName);
    }

    @Given("I filter by (1|2|3|4) star rating")
    public void filteredByStarRating(String rating) {
        model.filteredByStarRating(rating);
    }

    @Given("I check hotel ADA amenity consist")
    public void checkHotelADAAmenityConsist() {
        model.checkHotelADAAmenityConsist();
    }

    @When("^I change traveler name to (.*) (.*)$")
    public void changeTravelerFirstAndLastName(String firstName, String lastName) {
        purchaseParameters.getUserInformation().setFirstName(firstName);
        purchaseParameters.getUserInformation().setLastName(lastName);
    }

    @When("^I choose by name the \"(.*)\" hood/hotel")
    public void chooseSolutionByName(String name) {
        model.chooseSolutionByName(name.trim());
    }

    @When("^I choose the retail hero hotel$")
    public void chooseRetailHeroResult() {
        model.chooseRetailHeroResult();
    }

    @When("^I go back to results from the details page$")
    public void goBackToResultsFromDetailsPage() {
        model.goBackToResultsFromDetailsPage();
    }

    @Given("^I process the details page$")
    public void process_Details_Page() {
        model.processDetailsPage();
    }

    @Given("^I process the billing page$")
    public void process_Billing_Page() {
        model.processBillingPage();
    }

    @Then("^the option to save billing info (is|is not) displayed$")
    public void verifySaveBillingInfoVisibility(String state) {
        model.verifySaveBillingInfoVisibility(state.trim().equals("is"));
    }

    @Then("^I (see|don't see) logo (.*) card$")
    public void verifyCreditCardLogoVisibility(String visibility, String cardName) {
        model.validateCreditCardLogoVisibility(visibility, cardName);
    }

    @Then("^(all|credit card) payment options will be available$")
    public void verifyPaymentOptionsEnabledState(String paytype) {
        if (paytype.trim().equals("all")) {
            model.verifyAllPaymentOptionsEnabled();
        }
        else {
            model.verifyCreditCardOnlyEnabled();
        }
    }

    @Then("^I (|don't )see trip insurance module on hotel billing page")
    public void verifyInsuranceModuleOnBillingPage(String isVisible) {
        model.verifyInsuranceModuleVisibilityOnBillingPage(isVisible.equals(""));
    }

    @Then("^the billing page trip summary (will|will not) show insurance$")
    public void verifyInsuranceUpdateOnBillingTripSummary(String state) {
        model.verifyInsuranceUpdateOnBillingTripSummary(state.trim().equals("will"));
    }

    @Then("^Trip insurance will show on confirmation$")
    public void verifyInsuranceOnConfirmation() {
        model.verifyInsuranceCostOnConfirmation();
    }

    @When("^I check that insurance cost depends on number of rooms$")
    public void checkInsuranceCost() {
        model.validateInsuranceCostRegardingRoomNumbers();
    }

    @Then("^I verify purchase details on hotel billing and confirmation pages$")
    public void verifyPurchaseDetailsOnBillingAndConfirmationPages() {
        model.verifyPurchaseDetailsOnBillingAndConfirmationPages();
    }

    @When("^I select a hotel with (condo|all-inclusive) room type$")
    public void chooseOpaqueResultWithRoomType(String roomType) {
        model.chooseOpaqueResultWithRoomType(roomType);
    }

    @Then("^I will see the details for the hotel chosen$")
    public void verifyDetailsForChosenHotel() {
        model.verifyHotelDetailsPage();
    }

    @Then("^I see hotel with (.*) secret type on details page$")
    public void verifyHotelTypeOnDetails(String resultType) {
        model.verifyHotelTypeOnDetails(resultType);
    }

    @Then("^I see hotel with (.*) secret type on billing page$")
    public void verifyHotelTypeOnBilling(String resultType) {
        model.verifyHotelTypeOnOneBilling(resultType);
    }

    @Given("^I choose (USD|CAD|CHF|EUR|NZD|AUD|GBP|DKK|NOK|SEK) currency$")
    public void chooseCurrency(String currency) {
        model.chooseCurrency(currency);
    }

    @When("^I click the hotel confirmation page car cross sell$")
    public void clickConfirmationPageCarCrossSell() {
        model.clickConfirmationPageCarCrossSell();
    }

    @Then("^I (will|will not) see the hotel confirmation car cross sell$")
    public void verifyConfirmationPageCarCrossSell(String state) {
        model.verifyConfirmationPageCarCrossSell(state.equals("will"));
    }

    @Then("^I am on the billing page$")
    public void verifyOnBillingPage() {
        model.verifyOnBillingPage();
    }

    @When("^I navigate back from the billing page$")
    public void navigateBackToDetailsPage() {
        model.navigateBackToDetailsPage();
    }

    @Then("^I receive errors for blank fields on hotel billing page$")
    public void verifyHotelBillingForBlankFields() {

        // model.continueOnBillingPageOnPaymentFragment();
        UserInformation userInfo = new UserInformation();
        purchaseParameters.setUserInformation(userInfo);
        model.processBillingPage();

        List<String> errors = Arrays.asList(
            "Credit card expiration date is incorrect.",
            "Credit card type is not accepted. Please use one of the card types displayed.", "Enter a phone number.",
            "Enter a valid billing address.", "Enter a valid city name.", "Enter a valid email address.",
            "Enter a valid security code. This is the 3 or 4-digit number on the back of your card.",
            "Enter the first name.", "Enter the last name.", "The two email addresses need to match.");

        for (String error : errors) {
            applicationModel.verifyMessageOnPage(error, ErrorMessenger.MessageType.valueOf("error"));
        }
    }

    @Then("^I receive errors for too shot (traveler|card holder) name on hotel billing page$")
    public void verifyInvalidTravelerOrCardHolderInfo(String name) {
        UserInformation userInfo = new UserInformation(validCardLengthUser);
        String errorMessage = "First name, middle initial, or last name fields cannot contain numbers or special " +
                              "characters. First name and last name must be at least 2 characters.";
        if ("traveler".equalsIgnoreCase(name)) {
            userInfo.setFirstName("a");
            userInfo.setLastName("h");
        }
        else {
            userInfo.setCcFirstName("a");
            userInfo.setCcLastName("h");
        }
        purchaseParameters.setUserInformation(userInfo);
        model.processBillingPage();
        applicationModel.verifyMessageOnPage(errorMessage, ErrorMessenger.MessageType.valueOf("error"));
    }

    @Then("^I receive errors for empty zip code on hotel billing page$")
    public void verifyEmptyZipCode() {
        UserInformation userInfo = new UserInformation(validCardLengthUser);
        String errorMessage = "Enter a valid postal code.";
        userInfo.setBillingCountry("United States");
        userInfo.setZipCode("");
        purchaseParameters.setUserInformation(userInfo);
        model.processBillingPage();
        applicationModel.verifyMessageOnPage(errorMessage, ErrorMessenger.MessageType.valueOf("error"));
    }

    @Then("^I receive errors for special characters in fields on hotel billing page$")
    public void setInvalidTravelerAndBillingInfo() {
        UserInformation userInfo = new UserInformation(validCardLengthUser);
        List<String> errors = Arrays.asList(
            "Credit card type is not accepted. Please use one of the card types displayed.",
            "Enter a valid billing address.", "Enter a valid city name.", "Enter a valid email address.",
            "Enter a valid phone number.", "Enter a valid postal code.",
            "Enter a valid security code. This is the 3 or 4-digit number on the back of your card.",
            "First name, middle initial, or last name fields cannot contain numbers or special characters. " +
            "First name and last name must be at least 2 characters.", "The two email addresses need to match.");

        userInfo.setFirstName(specialCharactersTravelerInformation.getFirstName());
        userInfo.setLastName(specialCharactersTravelerInformation.getLastName());
        userInfo.setEmailId(specialCharactersTravelerInformation.getEmailId());
        userInfo.setPhoneCountryCode(specialCharactersTravelerInformation.getPhoneCountryCode());
        userInfo.setPrimaryPhoneNumber(specialCharactersTravelerInformation.getPrimaryPhoneNumber());

        userInfo.setCcFirstName(specialCharactersInformationUser.getCcFirstName());
        userInfo.setCcLastName(specialCharactersInformationUser.getCcLastName());
        userInfo.setBillingInformationName(specialCharactersInformationUser.getBillingInformationName());
        userInfo.setBillingAddress(specialCharactersInformationUser.getBillingAddress());
        userInfo.setCity(specialCharactersInformationUser.getCity());
        userInfo.setZipCode(specialCharactersInformationUser.getZipCode());
        userInfo.setCcNumber(specialCharactersInformationUser.getCcNumber());
        userInfo.setSecurityCode(specialCharactersInformationUser.getSecurityCode());

        purchaseParameters.setUserInformation(userInfo);
        model.processBillingPage();

        for (String error : errors) {
            applicationModel.verifyMessageOnPage(error, ErrorMessenger.MessageType.valueOf("error"));
        }
    }

    @Then("^I fill in billing page with valid data and receive confirmation$")
    public void verifyErrorsForSpecialCharacters() {
        UserInformation userInfo = new UserInformation(validCardLengthUser);
        purchaseParameters.setUserInformation(userInfo);
        model.processBillingPage();
        model.receiveConfirmation();
    }

    @Then("^The country code field should be auto-filled with (\\d+)$")
    public void verifyCountryCodeFieldIsAutoFilled(int countryCode) throws Throwable {
        model.verifyAutofillCountryCode(Integer.toString(countryCode));
    }

    @When("^I fill in guest traveler info$")
    public void fillTravelerInfo() {
        model.fillTravelerInfo();
    }

    @When("^I fill in guest billing info$")
    public void fillBillingInfo() {
        model.fillBillingInfo();
    }

    @When("^I choose a (1|2|3|4) star hotel result$")
    public void selectHotelResultByStarRating(String rating) {
        model.selectHotelResultByStarRating(rating);
    }

    @Then("^I validate that insurance cost is (\\d+)% of total cost$")
    public void validateInsuranceCostIsPercentageOfTotal(int expectedPercentage) throws Throwable {
        model.validateInsuranceCostIsPercentageOfTotal(expectedPercentage);
    }

    @And("^I complete booking with an existing account$")
    public void bookHotelWithNewlyCreatedAccount() {
        model.fillInTravelerInfoWithPhoneNumberAndBook();
    }

    @Then("^I validate insurance cost for hotel is correct$")
    public void validateInsuranceCostInDb() {
        model.validateInsuranceCostInDb(purchaseParameters.getInsuranceTotal());
    }

    @When("^I get the insurance cost from hotel confirmation$")
    public void getInsuranceCostFromHotelDetails() throws Throwable {
        purchaseParameters.setInsuranceTotal(model.getHotelProtectionCostOnConfirmation());
    }

    @Then("^I validate insurance cost for hotel is (.*) on billing page$")
    public void checkInsuranceCostForHotelOnBillingPage(String insuranceCost) {
        model.checkInsuranceCostForHotelOnBillingPage(insuranceCost);
    }

    public void fillTravelerInfoForGuestUser() {
        model.fillTravelerUserInfoForGuestUser();
    }

    public void fillTravelerInfoForLoggedUser() {
        model.fillTravelerUserInfoForLoggedUser();
    }

    public void processInsuranceOnBillingPage() {
        model.processInsuranceOnBillingPage();
    }

    public void processPaymentByType(PurchaseParameters.PaymentMethodType methodType) {
        model.processPaymentByType(methodType);
    }

    public void processAgreeAndTerms() {
        model.processAgreeAndTerms();
    }

    public void saveBillingTotalInBean() {
        model.saveBillingTotalInBean();
    }
}
