/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.primer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.po.AbstractPage;

/**
 *
 * @author jhernandez
 *
 */

public class RomePage extends AbstractPage {

    @FindBy(css = "h1.landingPageTitle")
    private WebElement header;

    public RomePage(WebDriver webdriver) {
        super(webdriver);
    }

    public WebElement getHeader() {
        return header;
    }

}
