/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.helpCenter;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * User: v-abudyak
 * Date: 8/13/13
 */
public class HCSubcategoryPage extends AbstractPageObject {

    public HCSubcategoryPage(WebDriver webdriver) {
        super(webdriver);
    }

    public void clickOnAnyArticle()  {
        new WebDriverWait(getWebDriver(), 1)
                .until(ExpectedConditions.
                        presenceOfElementLocated(By.xpath(".//*[@id='tileName-A2']/div/ol/li[1]/a"))).click();
    }
}
