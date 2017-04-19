/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car;

import com.hotwire.selenium.desktop.row.billing.CarDetailsPage;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarTravelerInfo;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.purchase.PurchaseModelTemplate;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.car.entities.VendorGridEntity;
import com.hotwire.testing.UnimplementedTestException;

import cucumber.api.DataTable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author v-ypuchkova
 * @since 2013.04
 */
public class CarPurchaseModelTemplate extends PurchaseModelTemplate implements CarPurchaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarPurchaseModelTemplate.class.getName());

    @Override
    public void verifyDepositCheckboxPresence(boolean state) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectPGood(String carType, String carCdCode) {
        throw new UnimplementedTestException("Implement me!");
    }

    /**
     * select airport or local result.
     *
     * @param resultType
     * @param carModels
     */
    @Override
    public void selectCarByCdCodeAndAirportOrLocalLocation(String resultType, String carModels) {
        throw new UnimplementedTestException("Implement me!");
    }

    /**
     * Selecting a car from result set with chosen type
     *
     * @param carCdCode - ICAR, ECAR, CCAR, SXAR etc.
     */
    @Override
    public void selectPGood(String carCdCode) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void processCarDetailsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyUpdatedParameter() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareSolutionOptionsWithConfirmation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareSolutionOptionsWithDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyGuaranteedCarSection() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHeaderForCarDetailsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareVendorGridWithTripSummary() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void compareTotalBetweenReviewModuleAndStipSummary() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyLocationsInRetailGridWithDB() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAirportLocationsForOpaqueCarWithDB() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyLocationsForOpaqueCarWithDB() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyStatusAndNetworkCodesForFailedCarPurchaseWithDB() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkCarInsuranceLogo(boolean isLogoVisible) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCarInsuranceModuleTypeOnBillingPage(String typeOfModule) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTripSummaryAfterBooking() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTripSummaryBeforeBooking(DataTable params) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void processAllBillingInformation(Boolean isUser) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillInErrorCCNumber(String errorType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillTravellerInfo(UserInformation userInformation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillInsurance() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyTravellerInfoPrepopulated() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectPaymentMethod() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void readTermsOfUseAndProductRules() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyDepositCheckboxTypeAndRulesAndRegulation(
            CarTravelerInfo.DepositCardAgreementType depositCardAgreementType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public CarTravelerInfo.DepositCardAgreementType getDepositCardAgreementType() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public boolean isDepositCardAgreementConfirmed() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectDepositCardAgreementCheckbox(boolean state) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void validateDepositCardAgreementBox() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmNewSearch(String parameter) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void changeDriverNamesOnBillingPage(String firstName, String lastName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCurrencyMessage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkHotDollarsMessage(String message) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkHotDollarsModule(boolean isVisibleModule) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void backToResultsFromDetailsPage() {
        CarDetailsPage carDetailsPage = new CarDetailsPage(getWebdriverInstance());
        carDetailsPage.clickBackToResultsLink();
    }

    protected void logUserInformation(UserInformation info) {
        StringBuilder sb = new StringBuilder("USER INFORMATION: ");
        sb.append(info.getFirstName() + " ");
        if (info.getMiddleName() != null) {
            sb.append(info.getMiddleName() + " ");
        }
        sb.append(info.getLastName());
        sb.append(" Email: " + info.getEmailId());
        sb.append(" Phone: " + info.getPrimaryPhoneNumber() + "\n");
        sb.append("Card: " + info.getCcType() + " - " + info.getCcNumber() + " [" + info.getSecurityCode() + "]");
        sb.append(" Exp.: " + info.getCcExpMonth() + "/" + info.getCcExpYear() + "\n");
        sb.append("Address: " + info.getCountry() + " " + info.getState());
        sb.append(" " + info.getCity() + " " + info.getZipCode() + " " + info.getBillingAddress());
        LOGGER.info(sb.toString());
    }

    @Override
    public void writeToTravelerField(String fieldType, String fieldValue) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void writeToPayPalField(String fieldType, String fieldValue) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void writeToPaymentField(String fieldType, String fieldValue) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public List<VendorGridEntity> getAvailableVendorEntitiesFromDetails() {
        throw new UnimplementedTestException("Implement me!");
    }


    @Override
    public boolean isResultsContainLocationDescription(String location) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectVendorEntityOnDetails(VendorGridEntity entity) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkMapAvailability(boolean availability) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void clickOnVendorAddressLink() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void closeVendorMap() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyVendorMapNum() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAirportResultsInVendorGrid() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public VendorGridEntity getCheckedVendorEntityOnDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyVendorChangingOnDetails() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCustomerCareModules() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void selectLiveChat(String moduleLocation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void assertLiveChatDisplayState(boolean displayState, String moduleLocation) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyLivePreChatDisplayed(boolean state) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void continueToBilling(Boolean isUserSignedIn) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void fillPaymentInfo() {
        throw new UnimplementedTestException("Implement me!");
    }


    @Override
    public void verifySavedPaymentInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPaymentStatus(String condition) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPromotionalCheckboxIsDisplayed(boolean equals) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPromotionalCheckboxIsChecked(boolean equals) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void choosePaymentMethod(boolean savedState) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyHertzConfirmationCodeExists() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkPaymentMethodThatIsChosen(PurchaseParameters.PaymentMethodType methodType) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void waitForPriceCheckAfterContinue() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void doSaveUsersInformation(String state) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void doSaveUsersPaymentInformation(String state) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyThatTravelerInformationIsPopulated() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void checkDriverNameOnConfirmationPage(String driverNameState) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void chooseDriverFromExisting() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySubscriberCheckBox(String state) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyInsuranceModuleInBilling(boolean isDisplayed) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void saveCarConfirmationInformation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyInsuranceIsSelected() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setNewPrimaryDriverName(String firstName, String lastName) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void enterNameCreditCard() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyCarDetailsPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void savePaymentInfo() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void proceedWPayment() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void getInsuranceCost(String cost) {
        throw new UnimplementedTestException("Implement me!");
    }
}



