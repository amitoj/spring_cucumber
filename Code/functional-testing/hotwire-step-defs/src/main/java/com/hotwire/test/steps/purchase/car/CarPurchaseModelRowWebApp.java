/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.car;

import com.hotwire.selenium.desktop.row.billing.CarConfirmationPage;
import com.hotwire.selenium.desktop.row.billing.CarDetailsPage;
import com.hotwire.selenium.desktop.row.models.CarDataVerificationParameters;
import com.hotwire.selenium.desktop.row.models.CarIntlSolutionModel;
import com.hotwire.selenium.desktop.row.results.CarResultsPage;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.search.car.CarSearchParametersImpl;
import com.hotwire.util.webdriver.SessionModifingParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static com.thoughtworks.selenium.SeleneseTestBase.assertTrue;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author v-ypuchkova
 * @since 2012.10
 */
public class CarPurchaseModelRowWebApp extends CarPurchaseModelTemplate {
    public static final String DATE_FORMAT_PATTERN = "EEEE, d MMM yyyy, HH:mm";

    @Autowired
    @Qualifier("sessionModifingParams")
    private SessionModifingParams sessionModifingParams;
    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParametersImpl carSearchParameters;
    @Autowired
    @Qualifier("carDataVerificationParameters")
    private CarDataVerificationParameters carDataVerificationParameters;

    @Override
    public void selectPGood() {
        CarResultsPage carResultsPage = new CarResultsPage(getWebdriverInstance());
        carResultsPage.selectCarResult(purchaseParameters.getResultNumberToSelect(),
                                       purchaseParameters.isPrepaidSolution());
        if (carDataVerificationParameters.isShouldSaveDataForVerification()) {
            verificationDataOnResult(carResultsPage);
        }
        carResultsPage.clickContinue();
    }

    private void verificationDataOnResult(CarResultsPage carResultsPage) {
        CarIntlSolutionModel optionSetOnResult = carResultsPage.getSolutionOptionsSet();
        CarIntlSolutionModel optionSetForSearch = carDataVerificationParameters.getOptionSetForSearch();
        assertThat(optionSetForSearch.match(optionSetOnResult));
        //save date from search for verification with date on details and confirmation
        optionSetOnResult.setPickUpDate(optionSetForSearch.getPickUpDate());
        optionSetOnResult.setDropOffDate(optionSetForSearch.getDropOffDate());
        carDataVerificationParameters.setOptionSetOnResult(optionSetOnResult);
    }

    public void confirmNewSearch(String parameter) {
        CarDetailsPage carDetailsPage = new CarDetailsPage(getWebdriverInstance());
        if (parameter.equals("country")) {
            carDetailsPage.clickSearchAgainButtonForCountry();
        }
        else if (parameter.equals("driversAge")) {
            carDetailsPage.clickSearchAgainButtonForDriversAge();
        }
    }

    public void verifyUpdatedParameter() {
        CarDetailsPage carDetailsPage = new CarDetailsPage(getWebdriverInstance());
        CarIntlSolutionModel optionSetAfterUpdate = new CarIntlSolutionModel();
        optionSetAfterUpdate.setDriversAge(carDetailsPage.getDriversAge());
        optionSetAfterUpdate.setCountry(carDetailsPage.getCountry());
        assertThat(optionSetAfterUpdate.match(carDataVerificationParameters.getOptionSetOnDetails()));
    }

    @Override
    public void completePurchase(Boolean isUser) {
        UserInformation userInformation = purchaseParameters.getUserInformation();
        CarDetailsPage carDetailsPage = new CarDetailsPage(getWebdriverInstance());

        if (purchaseParameters.isShouldSetState()) {
            userInformation.setState(carSearchParameters.getState());
        }

        carDetailsPage.driversAge(carSearchParameters.getDriversAge()).
            travelFirstName(userInformation.getFirstName()).
                          travelerLastName(userInformation.getLastName()).
                          phoneNumber(userInformation.getPrimaryPhoneNumber()).
                          emailAddress(userInformation.getEmailId()).
                          confirmEmailAddress(userInformation.getEmailId());

        if (purchaseParameters.isPrepaidSolution() != null && purchaseParameters.isPrepaidSolution()) {
            carDetailsPage.billingAddress(userInformation.getBillingAddress()).
                city(userInformation.getCity()).
                              cardName(userInformation.getCcType()).
                              cardNumber(userInformation.getCcNumber()).
                              cardExpMonth(userInformation.getCcExpMonth()).
                              cardExpYear(userInformation.getCcExpYear()).
                              cardCvvNumber(userInformation.getSecurityCode()).
                              state(userInformation.getState()).
                              postalCode(userInformation.getZipCode());
        }
        else {
            carDetailsPage.billingAddress(userInformation.getBillingAddress()).
                city(userInformation.getCity()).
                              postalCode(userInformation.getZipCode()).
                              state(userInformation.getState());
        }

        if (carDataVerificationParameters.isShouldSaveDataForVerification()) {
            CarIntlSolutionModel solutionSetOnDetails = carDetailsPage.getSolutionOptionsSet();
            try {
                setSolutionOptionsSetParameters(carDetailsPage, solutionSetOnDetails);
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
            carDataVerificationParameters.setOptionSetOnDetails(solutionSetOnDetails);
            assertThat(solutionSetOnDetails.match(carDataVerificationParameters.getOptionSetOnResult()));
        }
        carDetailsPage.book();
    }

    private void setSolutionOptionsSetParameters(CarDetailsPage carDetailsPage,
                                                 CarIntlSolutionModel solutionSetOnDetails) throws ParseException {
        if (carDetailsPage.getTotalPrice().equals(carDetailsPage.getTotalPriceOnPriceDetails())) {
            solutionSetOnDetails.setTotalPrice(carDetailsPage.getTotalPrice());
        }
        if (carDetailsPage.getCurrency().equals(carDetailsPage.getCurrencyPriceDetails())) {
            solutionSetOnDetails.setCurrency(carDetailsPage.getCurrency());
        }
        if (carDetailsPage.getCarModel().equals(carDetailsPage.getCarModelPriceDetails())) {
            solutionSetOnDetails.setCarModel(carDetailsPage.getCarModel());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        solutionSetOnDetails.setPickUpDate(dateFormat.parse(carDetailsPage.getPickUpDate()));
        solutionSetOnDetails.setDropOffDate(dateFormat.parse(carDetailsPage.getDropOffDate()));
    }

    @Override
    public void receiveConfirmation() {
        CarConfirmationPage carConfirmationPage = new CarConfirmationPage(getWebdriverInstance());
        if (carDataVerificationParameters.isShouldSaveDataForVerification()) {
            try {
                verificationDataOnConfirmationPage(carConfirmationPage);
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void verificationDataOnConfirmationPage(CarConfirmationPage carConfirmationPage) throws ParseException {
        CarIntlSolutionModel solutionSetOnConfirmation = carConfirmationPage.getSolutionOptionsSet();
        setParametersForConfirmationPage(carConfirmationPage, solutionSetOnConfirmation);
        carDataVerificationParameters.setOptionSetOnConfirmation(solutionSetOnConfirmation);
        assertThat(solutionSetOnConfirmation.match(carDataVerificationParameters.getOptionSetOnDetails()));
    }

    private void setParametersForConfirmationPage(CarConfirmationPage carConfirmationPage,
                                                  CarIntlSolutionModel solutionSet) throws ParseException {
        if (carConfirmationPage.getCardHolderName() != null &&
            carConfirmationPage.getCardHolderName().equals(carConfirmationPage.getDriverName())) {
            solutionSet.setDriverName(carConfirmationPage.getDriverName());
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        solutionSet.setPickUpDate(dateFormat.parse(carConfirmationPage.getPickUpDate()));
        solutionSet.setDropOffDate(dateFormat.parse(carConfirmationPage.getDropOffDate()));
    }

    public void verifyCurrencyMessage() {
        CarDetailsPage carDetailsPage = new CarDetailsPage(getWebdriverInstance());
        assertTrue(carDetailsPage.isCurrencyMessageEnabled());
    }

    @Override
    public void backToResultsFromDetailsPage() {
        CarDetailsPage carDetailsPage = new CarDetailsPage(getWebdriverInstance());
        carDetailsPage.clickBackToResultsLink();
    }

    @Override
    public void processCarDetailsPage() {
        // no op.
    }
}
