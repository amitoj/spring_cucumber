/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.livechat;

import com.hotwire.test.steps.common.AbstractSteps;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: v-asnitko
 * Date: 4/3/14
 * Steps for live chat verification.
 */

public class LiveChatSteps extends AbstractSteps {

    @Autowired
    private LiveChatModelWebApp model;

    @Then("^I see pre-chat form$")
    public void verifyPreChat() {
        model.switchToNewWindow();
        model.verifyPreChatForm();
    }

    @When("I click Start LiveChart button$")
    public void i_click_start_LiveChart() {
        model.startLiveChart();
    }

    @Then("^I click Start chat button$")
    public void clickStartChatButton() {
        model.startLiveChart();
    }

    @Then("^I see error messages about pre-chat required fields$")
    public void verifyErrorMessages() {
        model.verifyErrorMessagesOnPreChatForm();
    }

    @Then("^I cancel pre-chat form$")
    public void cancelPreChatForm() {
        model.closeLiveChat();
        model.switchToDefaultWindow();
    }

}
