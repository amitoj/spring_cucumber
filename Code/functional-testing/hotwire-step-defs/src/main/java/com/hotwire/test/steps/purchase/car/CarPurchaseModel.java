/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.car;

import com.hotwire.selenium.desktop.us.billing.car.impl.CarTravelerInfo;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.purchase.PurchaseModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.car.entities.VendorGridEntity;
import cucumber.api.DataTable;

import java.util.List;

/**
 * @author v-ypuchkova
 * @since 2013.04
 */
public interface CarPurchaseModel extends PurchaseModel {

    /**
     * Selecting a car from result set with chosen type and CdCode
     * @param carType - Opaque, Retails.
     * @param carCdCode - ICAR, ECAR, CCAR, SXAR etc.
     */
    void selectPGood(String carType, String carCdCode);

    /**
     * select airport or local result.
     * @param resultType Strings - "airport" or "local"
     * @param carModels Strings like "Standart" "Luxury" "Mini (Manual, no A\C)"
     */
    void selectCarByCdCodeAndAirportOrLocalLocation(String resultType, String carModels);

    /**
     * Selecting a car from result set with chosen type
     *
     * @param carCdCode - ICAR, ECAR, CCAR, SXAR etc.
     */
    void selectPGood(String carCdCode);

    /**
     * Processing through car's details page.
     * Selecting vendor offer from grid for retail car (by default vendor with lowest price is selected)
     */
    void processCarDetailsPage();

    void verifyUpdatedParameter();

    /**
     * Verifies purchase options (per day price, total price, locations and
     * etc). For ex., this method should verify that options on results page are
     * equal purchase options from confirmation page.
     */
    void compareSolutionOptionsWithConfirmation();

    void compareSolutionOptionsWithDetails();

    void verifyGuaranteedCarSection();

    void verifyHeaderForCarDetailsPage();

    void compareVendorGridWithTripSummary();

    void compareTotalBetweenReviewModuleAndStipSummary();

    void verifyLocationsInRetailGridWithDB();

    void verifyLocationsForOpaqueCarWithDB();

    void verifyStatusAndNetworkCodesForFailedCarPurchaseWithDB();

    void verifyAirportLocationsForOpaqueCarWithDB();

    void checkCarInsuranceLogo(boolean isLogoVisible);

    /**
     * Checks type of insurance module on CCF billing page.
     *
     * @param typeOfModule String {unavailable|loaded dynamically|presented as static module}
     */
    void verifyCarInsuranceModuleTypeOnBillingPage(String typeOfModule);

    void verifyTripSummaryAfterBooking();

    /**
     * Checks search parameters (location, start and end dates)
     * at Review Trip Summary module on Billing page
     *
     * @param params DataTable (class from cucumber.api)
     */
    void verifyTripSummaryBeforeBooking(DataTable params);

    /**
     * Fills all required fields from details to billing pages according to payment type
     * But doesn't complete purchase
     */
    void processAllBillingInformation(Boolean isUser);

    void fillInErrorCCNumber(String errorType);

    void fillTravellerInfo(UserInformation userInformation);

    void fillInsurance();

    void verifyTravellerInfoPrepopulated();

    /**
     * Check content of TermsOfUse and ProductRulesAndRestrictions popup windows
     */
    void readTermsOfUseAndProductRules();

    /**
     * Verify that deposit checkbox is hidden or displayed in depends on selected
     * airport (is_debit_card_ok_for_deposit = ? OR accepts_local_debit_card = ?)
     * and that text near the checkbox is correct
     * <p/>
     * NONE: Checkbox isn't displayed
     * <p/>
     * CREDIT_CARD_ONLY:
     * Checkbox is displayed.
     * Explanation:
     * "Yes. I understand rental agency only accepts credit card deposits.
     * Debit/check card deposits are not accepted."
     * <p/>
     * CREDIT_DEBIT_CARD_WITH_TICKET:
     * Checkbox is displayed.
     * Explanation:
     * "Yes. I understand the rental agency will require proof of a
     * round-trip travel ticket for debit/check card deposits.
     * Credit card deposits are also accepted."
     */
    void verifyDepositCheckboxTypeAndRulesAndRegulation(CarTravelerInfo.DepositCardAgreementType
                                                                depositCardAgreementType);

    /**
     * @return type of deposit card agreement
     *         NONE: The rental agency will require a credit card or debit/check card (debit-friendly type)
     *         CREDIT_DEBIT_CARD_WITH_TICKET:
     *         Debit/check card for deposit, the agency will require
     *         proof of a round-trip travel ticket
     *         CREDIT_CARD_ONLY: The rental agency will require a credit card only
     */
    CarTravelerInfo.DepositCardAgreementType getDepositCardAgreementType();

    /**
     * @return true if agreement's checkbox is checked and false
     *         in other way.
     */
    boolean isDepositCardAgreementConfirmed();

    /**
     * select/deselect deposit checkbox in Driver's panel
     */
    void selectDepositCardAgreementCheckbox(boolean state);

    /**
     * Verify that deposit agreement box is highlighted in red
     * and message "Please confirm your age and deposit terms" appears above.
     */
    void validateDepositCardAgreementBox();

    /**
     * Click on <Change primary driver> link at Billing page. Then verify that
     * popup dialog will appear and then new driver will be selected.
     */
    void changeDriverNamesOnBillingPage(String firstName, String lastName);

    /**
     * When we change driver's age or country on details page. "Search again" button is enabled.
     * In this step we click on it
     */
    void confirmNewSearch(String parameter);

    void verifyCurrencyMessage();

    /**
     * Checking info text in HotDollars Module
     *
     * @param message
     */
    void checkHotDollarsMessage(String message);

    void checkHotDollarsModule(boolean isVisibleModule);

    /**
     * Click "Back to results" link on details page
     */
    void backToResultsFromDetailsPage();

    /**
     * Write to specific driver info field
     */
    void writeToTravelerField(String fieldType, String fieldValue);

    /**
     * Write to specific PayPal field
     */
    void writeToPayPalField(String fieldType, String fieldValue);

    /**
     * Write to specific field of payment block
     */
    void writeToPaymentField(String fieldType, String fieldValue);

    List<VendorGridEntity> getAvailableVendorEntitiesFromDetails();

    boolean isResultsContainLocationDescription(String location);

    void selectVendorEntityOnDetails(VendorGridEntity entity);

    VendorGridEntity getCheckedVendorEntityOnDetails();

    void verifyVendorChangingOnDetails();

    void verifyCustomerCareModules();

    void selectLiveChat(String moduleLocation);

    void assertLiveChatDisplayState(boolean displayState, String moduleLocation);

    void verifyLivePreChatDisplayed(boolean state);

    void verifyPromotionalCheckboxIsDisplayed(boolean equals);

    void verifyPromotionalCheckboxIsChecked(boolean equals);

    void choosePaymentMethod(boolean savedState);

    void verifyHertzConfirmationCodeExists();

    void checkPaymentMethodThatIsChosen(PurchaseParameters.PaymentMethodType methodType);

    void waitForPriceCheckAfterContinue();

    void doSaveUsersInformation(String state);

    void doSaveUsersPaymentInformation(String state);

    void verifyThatTravelerInformationIsPopulated();

    void checkMapAvailability(boolean availability);

    void clickOnVendorAddressLink();

    void closeVendorMap();

    void verifyVendorMapNum();

    void verifyAirportResultsInVendorGrid();

    void checkDriverNameOnConfirmationPage(String driverNameState);

    void chooseDriverFromExisting();

    void verifySubscriberCheckBox(String state);

    void verifyInsuranceModuleInBilling(boolean isInsuranceUnavailable);

    void verifyDepositCheckboxPresence(boolean state);

    void saveCarConfirmationInformation();

    void verifyInsuranceIsSelected();

    void setNewPrimaryDriverName(String firstName, String lastName);

    void enterNameCreditCard();

    void verifyCarDetailsPage();

    void savePaymentInfo();

    void proceedWPayment();

    void continueToBilling(Boolean isUserSignedIn);

    void fillPaymentInfo();

    void verifySavedPaymentInfo();

    void getInsuranceCost(String  cost);
}












