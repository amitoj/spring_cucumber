/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.index;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.Wait;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created with IntelliJ IDEA. User: akrivin Date: 10/17/12 Time: 1:28 PM To change this template use File | Settings |
 * File Templates.
 */
public class PackagesIndexPage extends AbstractUSPage {
    private static final long DEFAULT_WAIT = 20;

    @FindBy(id = "isPartialHotelStay")
    private WebElement partialHotelStay;

    @FindBy(className = "moreDest")
    private WebElement addMoreDestinations;

    public PackagesIndexPage(WebDriver webdriver) {
        super(webdriver, new String[]{"tiles-def.uhp.index", "tile.hotwire.home"});
    }

    public void clickonPartialHotelStay() {
        new Wait<Boolean>(new IsAjaxDone()).maxWait((int) DEFAULT_WAIT).apply(
            getWebDriver());
        try {
            if (!partialHotelStay.isSelected()) {
                partialHotelStay.click();
            }
        }
        catch (NoSuchElementException e) {

        }
    }

    public void clickonAddMoreDestinations() {
        new Wait<>(new IsAjaxDone()).maxWait((int) DEFAULT_WAIT).apply(getWebDriver());
        try {
            addMoreDestinations.click();
        }
        catch (NoSuchElementException e) {

        }
    }
}
