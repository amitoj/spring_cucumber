/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.fragments;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 6/24/14
 * Time: 7:09 AM
 */
public class CarChangeEmailFragment extends AbstractPageObject {

    private static final By CONTAINER = By.id("loginChangePopupLayerComp-bd");

    @FindBy(name = "billingForm.loginChangeForm._NAE_newEmail")
    private WebElement email;

    @FindBy(name = "billingForm.loginChangeForm._NAE_confirmEmail")
    private WebElement confirmation;

    @FindBy(css = "button[type=submit]")
    private WebElement submit;

    public CarChangeEmailFragment(WebDriver webdriver) {
        super(webdriver, CONTAINER);
    }

    public void change(String email, String confirmation) {
        sendKeys(this.email, email);
        sendKeys(this.confirmation, confirmation);
        submit.click();
    }
}
