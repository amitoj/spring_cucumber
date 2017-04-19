/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.paypal;

import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author v-jolopez
 * @since 2014.12
 */
public class PayPalConfirmationPage extends AbstractPage {

    @FindBy(name = "merchant_return_link")
    private WebElement returnToStore;


    public PayPalConfirmationPage(WebDriver webdriver) {
        super(webdriver, new Wait<Boolean>(new VisibilityOf(By.name("merchant_return_link"))).maxWait(20));


    }

    public void returnToStore() {
        returnToStore.click();
    }

}
