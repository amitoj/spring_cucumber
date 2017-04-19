/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.onepage;

import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 2/7/13
 * Time: 2:09 AM
 */
public class OpChangePrimaryDriverForm extends AbstractPageObject {

    @FindBy(name = "billingForm.addTravelerNameForm.firstName")
    private WebElement firstName;

    @FindBy(name = "billingForm.addTravelerNameForm.lastName")
    private WebElement lastName;

    @FindBy(css = "div.submitButton button[type='submit']")
    private WebElement submit;

    public OpChangePrimaryDriverForm(WebDriver webDriver) {
        super(webDriver, By.id("addTravelerNameForm"));
    }

    public void changePrimaryDriver(String firstName, String lastName) {
        sendKeys(this.firstName, firstName);
        sendKeys(this.lastName, lastName);
        submit.click();
        new Wait<Boolean>(new IsAjaxDone()).maxWait(2).apply(getWebDriver());
    }
}
