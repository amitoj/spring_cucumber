/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.details;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.po.AbstractPage;

/**
 * @author v-jhernandeziniguez
 *
 */
public class AboutYourFlightPage extends AbstractPage {

    @FindBy(id = "airDetailsRetailBookNow")
    private WebElement bookNowButton;

    public AboutYourFlightPage(WebDriver webdriver) {
        super(webdriver);
    }

    public WebElement getBookNowButton() {
        return bookNowButton;
    }



}
