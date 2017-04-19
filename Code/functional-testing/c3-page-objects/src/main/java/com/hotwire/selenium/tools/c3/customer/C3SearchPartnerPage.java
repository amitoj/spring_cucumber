/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/17/14
 * Time: 6:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3SearchPartnerPage extends ToolsAbstractPage {
    public C3SearchPartnerPage(WebDriver webdriver) {
        super(webdriver, By.className("customerSearchQueryForm"));
    }

    public void searchForItinerary(String itin) {
        WebElement itineraryInput = findOne("input[name='partnerConfirmationNumber']", EXTRA_WAIT);
        itineraryInput.sendKeys(itin);
        findOne("img[name='confirmationNumberGif']", DEFAULT_WAIT);
        itineraryInput.sendKeys(Keys.ENTER);
    }
}
