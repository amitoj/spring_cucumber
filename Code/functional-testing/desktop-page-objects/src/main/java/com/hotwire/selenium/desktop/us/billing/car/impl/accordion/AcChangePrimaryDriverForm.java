/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.accordion;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * User: v-vzyryanov
 * Date: 2/7/13
 * Time: 2:10 AM
 */
public class AcChangePrimaryDriverForm extends AbstractPageObject {

    @FindBy(name = "addTravelerNameForm.firstName")
    private WebElement firstName;

    @FindBy(name = "addTravelerNameForm.lastName")
    private WebElement lastName;

    @FindBy(css = "input.continueBtn")
    private WebElement submit;

    public AcChangePrimaryDriverForm(WebDriver webDriver) {
        super(webDriver, By.id("addTravelerForm"));
    }

    public void changePrimaryDriver(String firstName, String lastName) {
        sendKeys(this.firstName, firstName);
        sendKeys(this.lastName, lastName);
        this.submit.click();
        new WebDriverWait(getWebDriver(), 10).until(ExpectedConditions.stalenessOf(this.submit));
    }
}
