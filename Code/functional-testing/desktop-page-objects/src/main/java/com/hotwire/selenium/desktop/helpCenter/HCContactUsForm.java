/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.helpCenter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * User: v-abudyak
 * Date: 7/23/13
 */
public class HCContactUsForm extends HelpCenterAbstractPage {

    public HCContactUsForm(WebDriver webDriver) {
        super(webDriver, By.className("emailUs"));
    }

    public WebElement getFormElementByName(String name) {
        return  new WebDriverWait(getWebDriver(), 1).until(ExpectedConditions.
                presenceOfElementLocated(By.name(name)));
    }

    public String getFormElementValueByName(String name) {
        return  new WebDriverWait(getWebDriver(), 1).until(ExpectedConditions.
                presenceOfElementLocated(By.name(name))).getAttribute("value");
    }

    public boolean isHighlightedFormElement(String name) {
        String cssClass = new WebDriverWait(getWebDriver(), 1).until(ExpectedConditions.
                presenceOfElementLocated(By.xpath("//*[@name='" + name + "']//.."))).getAttribute("class");
        if (cssClass.equals("formRow")) {
            return false;
        }
        return true;
    }

    public boolean isErrorMessagePresent() {
        return findOne(".errorMessages", HelpCenterAbstractPage.DEFAULT_WAIT).isDisplayed();
    }

    public void clickSendButton() {
        submitFormByName("contact");
    }
}
