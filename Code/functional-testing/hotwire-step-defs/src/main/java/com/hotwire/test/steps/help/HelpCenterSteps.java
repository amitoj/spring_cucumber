/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.help;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class HelpCenterSteps extends AbstractSteps {

    @Autowired
    @Qualifier("helpCenterModel")
    HelpCenterModel helpCenterModel;

    @When("^I access Help Center$")
    public void openHelpCenterPage() {
        helpCenterModel.openHelpCenterPage();
    }

    @Then("^Help Center is accessible$")
    public void verifyHelpCenterPage() {
        helpCenterModel.verifyHelpCenterPage();
    }

    @When("^I select Q&As \"([^\"]*)\" searching and booking$")
    public void openQASearchingAndBookingPage(String vertical) {
        helpCenterModel.openQASearchingAndBookingPage(vertical);
    }

    @Then("^\"([^\"]*)\" Q&As searching and booking is accessible$")
    public void verifyQASearchingAndBookingPage(String vertical) {
        helpCenterModel.verifyQASearchingAndBookingPage(vertical);
    }

    @When("^I access Travel Formalities via footer link$")
    public void accessTravelFormalities() {
        helpCenterModel.accessTravelFormalities();
    }

    @Then("^Travel Formalities contain a link to France Diplomatie$")
    public void verifyTravelFormalities() {
        helpCenterModel.verifyTravelFormalities();
    }

    @Then("^I see \"([^\"]*)\" support is available \"([^\"]*)\"$")
    public void supportIsAvailable(String countryName, String supportTime) {
        helpCenterModel.verifyCenterAvailabilityHours(countryName, supportTime);
    }

    @Then("^I verify the email message within Hotwire Customer Care module for country \"([^\"]*)\"$")
    public void verifyEmailMsgWithinHotwireCustomerCareModuleForCountry(String country) throws Throwable {
        helpCenterModel.verifyEmailMsgWithinHotwireCustomerCareModuleForCountry(country);
    }

    @Then("^I verify the hours of availability message within Hotwire Customer Care module for country \"([^\"]*)\"$")
    public void verifyHoursOfAvailabilityMsgWithinHotwireCustomerCareModuleForCountry(String country) throws Throwable {
        helpCenterModel.verifyHoursOfAvailabilityMsgWithinHotwireCustomerCareModuleForCountry(country);
    }

    @Then("^I verify the phone contacts within Hotwire Customer Care module for country \"([^\"]*)\"$")
    public void verifyPhoneContactsWithinHotwireCustomerCareModuleForCountry(String country) throws Throwable {
        helpCenterModel.verifyPhoneContactsWithinHotwireCustomerCareModuleForCountry(country);
    }
}
