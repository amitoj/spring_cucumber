/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.car;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by v-vyulun on 9/22/2014.
 */
public class C3CarAddonPage extends ToolsAbstractPage {

    private static String btnBookNow = "button[id='airOpaqueDetailsBookNow'], button[id='airDetailsRetailBookNow']";

    public C3CarAddonPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("form[action*='/air/billing.jsp']"));
    }

    public void bookNow() {
        freeze(2);
        findOne(btnBookNow, PAGE_WAIT).click();
    }

    public void addRentalCar() {
        if (isRentalCarOptionLinkAvailable()) {
            findOne(".startSearch>span", DEFAULT_WAIT).click();
            new WebDriverWait(getWebDriver(), getTimeout()).until(
                    ExpectedConditions.elementToBeClickable(By.name("selectedCarAddOnSolutionId"))).click();
        }
        else {
            throw new RuntimeException("Can't rent a car as add-on for flight.. The link didn't appear");
        }
    }

    private boolean isRentalCarOptionLinkAvailable() {
        try {
            return findOne(".startSearch>span", EXTRA_WAIT).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
