/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.legal;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * User: v-abudyak
 * Date: 12/24/13
 * Time: 7:53 AM
 */
public class LegalSteps extends AbstractSteps {

    @Autowired
    @Qualifier("legalModel")
    LegalModel model;

    @Autowired
    @Qualifier("legalParameters")
    private LegalParameters legalParameters;

    @Given("^I go to legal page$")
    public void openLegalIndexPage() {
        model.goToLegalPage();
    }

    @Then("^Legal index page is accessible$")
    public void verifyLegalPage() {
        model.verifyLegalPage();
    }

    @Given("^I go to Privacy Policy page$")
    public void openPrivacyPolicyPage() {
        model.goToPrivacyPolicyPage();
    }

    @Given("^I go to Terms of Use page$")
    public void openTermsOfUsePage() {
        model.goToTermsOfUsePage();
    }

    @Given("^I go to Rules and Restrictions page$")
    public void openRulesAndRestrictionsPage() {
        model.goToRulesAndRestrictioinsPage();
    }

    @Given("^I go to Low Price Guarantee page$")
    public void openLowPriceGuaranteePage() {
        model.goToLowPriceGuaranteePage();
    }

    @Given("^I click on Back button$")
    public void clickBackButton() {
        model.clickBackButton();
    }

    @Given("^Page (.*) is accesible$")
    public void verifyLegalDetailsPage(String pageName) {
        model.verifyLegalDetailsPage(pageName);
    }

    @Then("^I (?:(don't))? see the copy$")
    public void verifyTextPresence(String negation) {
        boolean isTextPresent = negation == null;
        model.verifyText(legalParameters.getCopy(), isTextPresent);
    }

    @Given("^the following copy$")
    public void setUpCopy(String copy) {
        legalParameters.setCopy(copy);
    }

    @Then("^I see copies appropriate to the search results$")
    public void verifyCopiesOnSearchResults() {
        model.verifyCopiesOnSearchResults();
    }

    @Then("^I see appropriate copies on details$")
    public void verifyCopiesOnDetails() {
        model.verifyCopiesOnDetails();
    }
}
