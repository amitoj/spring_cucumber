/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.legal;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 12/25/13
 * Time: 6:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class MobileLowPriceGuaranteePage extends MobileAbstractPage {

    @FindBy(xpath = "//img[contains(@alt, 'Back')]")
    private WebElement lnkBack;

    public MobileLowPriceGuaranteePage(WebDriver webdriver) {
        super(webdriver, "tile.legal.lowPrice");
    }

    public WebElement getLnkBack() {
        return lnkBack;
    }
}
