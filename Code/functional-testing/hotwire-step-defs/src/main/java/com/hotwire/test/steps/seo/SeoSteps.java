/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.seo;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by IntelliJ IDEA. User: vjong Date: Jun 29, 2012 Time: 1:35:12 PM To change this template use File | Settings
 * | File Templates.
 */
public class SeoSteps extends AbstractSteps {
    @Autowired
    @Qualifier("seoModel")
    private SeoModel model;

    @Given("^I go to the hotels information page$")
    public void goToHotelsInformation() {
        model.goToHotelsInformation();
    }

    @When("^I get (top 100|all)(\\sthe\\s.*)* destination links$")
    public void getDestinationLinks(String set, String country) {
        if (country != null) {
            // Verify trim of country isn't length 0. If so set to null.
            country = (country.trim().length() != 0) ? country.replaceAll("\\sthe\\s", "").trim() : null;
        }
        model.getDestinationLinks(set, country);
    }

    @Then("^I see the (destination|top 100) links are SEO friendly$")
    public void verifyDestinationLinks(String source) {
        model.verifyDestinationLinks(source);
    }

    @Then("^the default region is (.*)$")
    public void verifyDefaultSelectedRegion(String region) {
        model.verifyDefaultSelectedRegion(region);
    }
}
