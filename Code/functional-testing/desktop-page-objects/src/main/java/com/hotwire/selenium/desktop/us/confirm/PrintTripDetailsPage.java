/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.confirm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 8/18/14
 * Time: 12:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrintTripDetailsPage extends CarConfirmationPage {

    @FindBy(xpath = "//div[@class='printVersion']//a[@class='printButton'][1]")
    private WebElement printButtonTop;

    @FindBy(xpath = "//div[@class='printVersion']//a[@class='printButton'][2]")
    private WebElement printButtonBottom;


    public PrintTripDetailsPage(WebDriver webDriver) {
        super(webDriver, "tiles-def.car.confirm-print");
    }

    public String getURL() {
        return getWebDriver().getCurrentUrl().toString();
    }

    public WebElement getPrintButtonTop() {
        return printButtonTop;
    }

    public WebElement getPrintButtonBottom() {
        return printButtonBottom;
    }
}
