/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by v-ozelenov on 4/11/2014.
 */
public class C3ShareItineraryForm extends ToolsAbstractPage {

    public C3ShareItineraryForm(WebDriver webdriver) {
        super(webdriver, By.name("shareThisItineraryForm"));
    }

    public void clickConfirmShareItinerary() {
        findOne("input[value = 'Share']").click();
    }

    public String getMessage() {
        return findOne("div.itineraryFormLayer div.errorMessages ul", DEFAULT_WAIT).getText();
    }

    public void setEmail(String email) {
        findOne("input.sendToEmail").sendKeys(email);
    }

    public void setCustomerMessage() {
        setText("textarea[name='emailMessage']", "Test");
    }

    public String getConfirmationMessage() {
        return findOne(".errorMessages.successMessages.successMessagesShareIt>ul>li", EXTRA_WAIT).getText().trim();
    }

    public void cancelShareItinerary() {
        findOne("form.shareItineraryForm input[value = 'Cancel']").click();
    }
}
