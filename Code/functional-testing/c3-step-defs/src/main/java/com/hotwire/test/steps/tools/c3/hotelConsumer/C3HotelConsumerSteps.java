/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.hotelConsumer;


import com.hotwire.test.steps.tools.ToolsAbstractSteps;
import cucumber.api.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 5/5/14
 * Time: 1:32 PM
 * Contains all steps for Hotel Supply functionality
 */

public class C3HotelConsumerSteps extends ToolsAbstractSteps {

    @Autowired
    private C3HotelConsumerModel model;

    private String testTitle = "Test_title_" + System.currentTimeMillis();

    @Then("The (Air|Hotel) Deal Maintenance tab available")
    public void verifyDealMaintenanceTab(String vertical) {
        model.verifyDealMaintenanceTab(vertical);
    }

    @Then("I click (Air|Hotel) Deal Maintenance tab")
    public void clickDealMaintenanceTab(String vertical) {
        model.clickDealMaintenanceTab(vertical);
    }

    @Then("I click \"Preview\" button")
    public void clickPreviewButton() {
        model.clickPreviewButton();
    }

    @Then("Execute any query from the dropdown menu")
    public void executeAnyQuery() {
        model.executeAnyQuery();
    }

    @Then("I see XID is (not )?displayed at the top of the page")
    public void checkXIDisDisplayed(String param) {
        model.checkXIDisDisplayed(param);
    }

    @Then("Deals appear at the bottom of the page")
    public void checkDealsAreDisplayed() {
        model.checkDealsAreDisplayed();
    }
}
