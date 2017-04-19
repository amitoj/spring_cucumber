/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.global.footer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * Contact Us page
 */
public class ContactUsPage extends AbstractUSPage {

    @FindBy(xpath = "//div[@id='tileName-T1']/h1/img")
    private WebElement sendUSEmailText;

    public ContactUsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getsendUSEmailText() {
        return sendUSEmailText;
    }

}
