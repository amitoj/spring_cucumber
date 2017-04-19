/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.purchase.steps;

import com.hotwire.test.steps.AbstractSteps;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import com.hotwire.test.steps.purchase.CarPurchaseService;
import com.hotwire.test.steps.purchase.HotelPurchaseService;
import com.hotwire.testing.UnimplementedTestException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * User: v-ngolodiuk
 * Since: 1/8/15
 */
public class PurchaseSteps extends AbstractSteps {

    @Autowired
    HotelPurchaseService hotelPurchaseService;
    @Autowired
    CarPurchaseService carPurchaseService;

    @Autowired
    RequestProperties requestProperties;

    @Autowired
    @Qualifier("abstractUser")
    private UserInformation userInformation;
    @Autowired
    @Qualifier("validMastercardUser")
    private UserInformation masterCardUserInfo;
    @Autowired
    @Qualifier("validAmexUser")
    private UserInformation validAmexUser;
    @Autowired
    @Qualifier("invalidVisaUser")
    private UserInformation invalidVisaUser;
    @Autowired
    @Qualifier("validJCBUser")
    private UserInformation validJCBUser;
    @Autowired
    @Qualifier("validDiscoverUser")
    private UserInformation validDiscoverUser;
    @Autowired
    @Qualifier("hotDollarsUser")
    private UserInformation hotDollarsUser;


    @And("^I use (saved|new) (.*) card$")
    public void setUpSpecificCreditCardPaymentType(String isNew, String cardType) {
        UserInformation userInfo;
        switch (cardType) {
            case "Visa":
                userInfo = userInformation;
                break;
            case "MasterCard":
                userInfo = masterCardUserInfo;
                break;
            case "American Express":
                userInfo = validAmexUser;
                break;
            case "Discover":
                userInfo = validDiscoverUser;
                break;
            case "JCB":
                userInfo = validJCBUser;
                break;
            default:
                throw new UnimplementedTestException("No support for the credit card of type " + cardType);
        }
        requestProperties.setIsNewCard("new".equalsIgnoreCase(isNew));
        requestProperties.setUserInformation(userInfo);
    }

    @And("^I use invalid Visa card$")
    public void useInvalidVisaCard() {
        requestProperties.setIsNewCard(true);
        requestProperties.setUserInformation(invalidVisaUser);
    }

    @And("^I use Hot Dollars payment$")
    public void useHotDollarsPayment() {
        requestProperties.setHotDollarsPayment(true);
        requestProperties.setIsNewCard(true);
        requestProperties.setUserInformation(hotDollarsUser);
    }

    @When("^I execute the secure booking request$")
    public void executeSecureBookingRequest() throws DatatypeConfigurationException {
        if (requestProperties.getVertical().equalsIgnoreCase("hotel")) {
            hotelPurchaseService.executeSecureBookingRequest();
        }
        else {
            carPurchaseService.executeSecureBookingRequest();
        }
    }

    @When("^I execute the booking request$")
    public void executeBookingRequest() throws DatatypeConfigurationException {
        if (requestProperties.getVertical().equalsIgnoreCase("hotel")) {
            hotelPurchaseService.executeBookingRequest();
        }
        else {
            carPurchaseService.executeBookingRequest();
        }
    }

    @Then("^hot dollars info is in trip summary$")
    public void verifyHotDollarsInfo() throws Throwable {
        if (requestProperties.getVertical().equalsIgnoreCase("hotel")) {
            hotelPurchaseService.verifyHotDollarsInfo();
        }
        else {
            carPurchaseService.verifyHotDollarsInfo();
        }
    }

    @Then("^booking result is present$")
    public void checkBookingResultExist() {
        if (requestProperties.getVertical().equalsIgnoreCase("hotel")) {
            hotelPurchaseService.checkBookingResultExist();
        }
        else {
            carPurchaseService.checkBookingResultExist();
        }
    }

    @Then("^billing info is present$")
    public void checkBillingInfo() {

        if (requestProperties.getVertical().equalsIgnoreCase("hotel")) {
            hotelPurchaseService.checkBillingInfo();
        }
        else {
            carPurchaseService.checkBillingInfo();
        }
    }

    @Then("^reservation info is present$")
    public void verifyReservationInfo() {
        if (requestProperties.getVertical().equalsIgnoreCase("hotel")) {
            hotelPurchaseService.verifyReservationInfo();
        }
        else {
            carPurchaseService.verifyReservationInfo();
        }
    }


    @Then("^travel advisory is present$")
    public void checkTravelAdvisory() {
        if (requestProperties.getVertical().equalsIgnoreCase("hotel")) {
            hotelPurchaseService.checkTravelAdvisory();
        }
        else {
            carPurchaseService.checkTravelAdvisory();
        }
    }

    @Then("^(hotel|car) reservation details are present$")
    public void verifyHotelDetails(String vertical) {
        if ("hotel" .equalsIgnoreCase(vertical)) {
            hotelPurchaseService.checkHotelDetails();
        }
        else {
            carPurchaseService.checkCarDetails();
        }
    }

    @Then("^(hotel|car) details response is present$")
    public void detailsResponseIsPresent(String vertical) {
        if ("hotel" .equalsIgnoreCase(vertical)) {
            hotelPurchaseService.checkDetailsResultExist();
        }
        else {
            carPurchaseService.checkDetailsResultExist();
        }
    }


    @Given("^(hotel|car) details request has been executed$")
    public void checkDetailsResponseExists(String vertical) {
        if ("hotel" .equalsIgnoreCase(vertical)) {
            hotelPurchaseService.checkDetailsResponseExists();
        }
        else {
            carPurchaseService.checkDetailsResponseExists();
        }
    }

}
