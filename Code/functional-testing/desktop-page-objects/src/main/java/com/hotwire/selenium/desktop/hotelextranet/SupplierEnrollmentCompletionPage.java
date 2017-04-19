/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.hotelextranet;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Supplier enrollment completion page.
 */
public class SupplierEnrollmentCompletionPage {

    @FindBy(className = "WordSection1")
    private WebElement bodyText;

    //private WebDriver webdriver;

    public SupplierEnrollmentCompletionPage(WebDriver webdriver) {
        // you should not be mangling a webdriver instance in this page object.
        // If you want implicit waits extend the webdriverfactory and context to support it for your tests
        // (as long as they are not part of hotwire consumer)
        // and set it in your spring context.
        // this will mangle shared instances of webdriver, you should never do this.
        throw new RuntimeException(
            "You need a better way to manage your webdriver instance's configuration than this.");
        //this.webdriver = webdriver;
        //webdriver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
    }

    public String getBodyText() {
        return bodyText.getText();
    }
}
