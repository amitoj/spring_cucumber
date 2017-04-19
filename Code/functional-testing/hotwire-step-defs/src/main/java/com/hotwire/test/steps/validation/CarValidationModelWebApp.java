/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.validation;

import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarBillingPage;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarTravelerInfo;
import com.hotwire.selenium.desktop.widget.ErrorMessenger;
import com.hotwire.test.steps.account.UserInformation;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.purchase.PurchaseParameters;
import com.hotwire.test.steps.purchase.car.CarPurchaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-ngolodiuk
 * Since: 8/20/14
 */

public class CarValidationModelWebApp extends ValidationModelTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarValidationModelWebApp.class.getName());

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    @Autowired
    @Qualifier("purchaseParameters")
    private PurchaseParameters purchaseParameters;

    @Autowired
    @Qualifier("carPurchaseModel")
    private CarPurchaseModel purchaseModel;

    @Override
    public void primaryDriverFieldTesting() {
        String err1 = "Enter missing information in the blank fields indicated below.";
        String err2 = "Names cannot contain any special characters" +
                " or numbers, and last name must be at least 2 characters";

        CarTravelerInfo travelerInfo = CarBillingPageProvider.get(getWebdriverInstance()).getTravelerInfoFragment();
        travelerInfo.changeDriverInformation("", "");
        applicationModel.verifyMessageOnPage(err1, ErrorMessenger.MessageType.valueOf("error"));

        travelerInfo.changeDriverInformation("?", "Driver");
        applicationModel.verifyMessageOnPage("", ErrorMessenger.MessageType.valueOf("error"));

        travelerInfo.changeDriverInformation("New1", "Driver");
        applicationModel.verifyMessageOnPage(err2, ErrorMessenger.MessageType.valueOf("error"));

        travelerInfo.changeDriverInformation("New", "Driver1");
        applicationModel.verifyMessageOnPage(err2, ErrorMessenger.MessageType.valueOf("error"));

        travelerInfo.changeDriverInformation("validFirstName", "validLastName");
        assertThat(travelerInfo.getSelectedPrimaryDriver()).as("Primary driver was changed and selected")
                .isEqualTo("validFirstName validLastName");
        travelerInfo.changeDriverInformation("validNewFirstName", "validNewLastName");
        assertThat(travelerInfo.getSelectedPrimaryDriver()).as("Primary driver was changed and selected")
                .isEqualTo("validNewFirstName validNewLastName");
    }

    @Override
    public void primaryDriverAgeValidation() {
        String err1 = "Primary driver must be at least 25 years old" +
                " at time of pick up. Driver age will be verified by the rental agency.";
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        UserInformation userInformation = purchaseParameters.getUserInformation();
        LOGGER.info("Filling traveler info");
        carBillingPage.getTravelerInfoFragment().
                travelerInfo(userInformation.getFirstName(), userInformation.getLastName()).
                travelerPhoneNumber(userInformation.getPrimaryPhoneNumber()).
                travelerEmail(userInformation.getEmailId()).
                creditCardAcceptance(true).ageConfirmation(false).continuePanel();
        purchaseModel.fillInsurance();

        LOGGER.info("Filling credit card info");
        purchaseModel.fillCreditCard();
        purchaseModel.clickOnAgreeAndBookButton(true);
        applicationModel.verifyMessageOnPage(err1, ErrorMessenger.MessageType.valueOf("error"));
    }

    @Override
    public void primaryPhoneNumberTesting() {
        String err1 = "Enter a phone number.";
        String err2 = "Please enter a 10-digit phone number beginning with area code. " +
                "Do not include any letters or special characters.";
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        UserInformation userInformation = purchaseParameters.getUserInformation();

        LOGGER.info("Filling traveler info");
        carBillingPage.getTravelerInfoFragment().
                travelerInfo(userInformation.getFirstName(), userInformation.getLastName()).
                travelerPhoneNumber("").travelerEmail(userInformation.getEmailId()).
                creditCardAcceptance(true).ageConfirmation(true).continuePanel();
        purchaseModel.fillInsurance();
        carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());

        LOGGER.info("Filling credit card info");
        purchaseModel.fillCreditCard();
        purchaseModel.clickOnAgreeAndBookButton(true);
        applicationModel.verifyMessageOnPage(err1, ErrorMessenger.MessageType.valueOf("error"));
        //more when 10 digit phone number
        carBillingPage.getTravelerInfoFragment().travelerPhoneNumber("555 555-555555");
        purchaseModel.fillCreditCard();
        purchaseModel.clickOnAgreeAndBookButton(true);
        applicationModel.verifyMessageOnPage(err2, ErrorMessenger.MessageType.valueOf("error"));
        //less when 10 digit phone number
        carBillingPage.getTravelerInfoFragment().travelerPhoneNumber("555 555-555");
        purchaseModel.fillCreditCard();
        purchaseModel.clickOnAgreeAndBookButton(true);
        applicationModel.verifyMessageOnPage(err2, ErrorMessenger.MessageType.valueOf("error"));
        //phone number contains a letter
        carBillingPage.getTravelerInfoFragment().travelerPhoneNumber("555 555a-555");
        purchaseModel.fillCreditCard();
        purchaseModel.clickOnAgreeAndBookButton(true);
        applicationModel.verifyMessageOnPage(err2, ErrorMessenger.MessageType.valueOf("error"));
    }

    @Override
    public void emailAddressFieldTesting() {
        String err1 = "Enter an email address., Confirm email address field is blank.";
        String err2 = "Email address is not valid.";
        String err3 = "The two email addresses need to match.";
        String err4 = "Enter a valid email address." +
                " Please make sure there are no spaces, and that the '@' is included.";
        CarBillingPage carBillingPage = CarBillingPageProvider.get(getWebdriverInstance());
        carBillingPage.getTravelerInfoFragment().travelerEmailForLoggedUser("", "");  //empty email
        applicationModel.verifyMessageOnPage(err1, ErrorMessenger.MessageType.valueOf("error"));
        carBillingPage.getTravelerInfoFragment()
                .travelerEmailForLoggedUser("qa_regression@hotwire.com", "qa_regression@hotwire.com"); //existed email
        applicationModel.verifyMessageOnPage(err2, ErrorMessenger.MessageType.valueOf("error"));
        carBillingPage.getTravelerInfoFragment()     //differ emails
                .travelerEmailForLoggedUser("first@hotwire.com", "second@hotwire.com");
        applicationModel.verifyMessageOnPage(err3, ErrorMessenger.MessageType.valueOf("error"));
        carBillingPage.getTravelerInfoFragment()    //invalid format of email
                .travelerEmailForLoggedUser("newEmail@hotwire.qw", "newEmail@hotwire.qw");
        applicationModel.verifyMessageOnPage(err4, ErrorMessenger.MessageType.valueOf("error"));
    }

}
