/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.myAccount;
import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created with IntelliJ IDEA.
 * User: v-asnitko
 * Date: 11/14/13
 * Time: 8:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChoseLocalSiteDropdown extends AbstractPage {

    public ChoseLocalSiteDropdown(WebDriver webDriver) {
       super(webDriver);
    }

    public void chooseLocale(String locale) {
        clickOnCountrySelector();
        selectCountry(locale);
    }

    public void clickOnCountrySelector() {
        getWebDriver().findElement(By.cssSelector(".countryDropdown a")).click();
    }

    public void selectCountry(String locale) {
        new WebDriverWait(getWebDriver(), 1)
                .until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locale))).click();
    }

}
