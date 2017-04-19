/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-edjafarov
 * Date: Nov 10, 2011
 * Time: 6:26:47 AM
 */
public class AccountTopNavBar extends AbstractPageObject {

    @FindBy(xpath = ".//a[contains(@href,'/overview')]")
    protected WebElement overviewLink;

    @FindBy(xpath = ".//a[contains(@href,'/trips')]")
    protected WebElement tripsLink;

    @FindBy(xpath = ".//a[contains(@href,'/account-info')]")
    protected WebElement accInfoLink;

    @FindBy(xpath = ".//a[contains(@href,'/payment-info')]")
    protected WebElement pmtInfoLink;

    @FindBy(xpath = ".//a[contains(@href,'/traveler-names')]")
    protected WebElement travNamesLink;

    @FindBy(xpath = ".//a[contains(@href,'/emailsubscriptions')]")
    protected WebElement emlSbscrLink;

    @FindBy(xpath = ".//a[contains(@href,'/preferences')]")
    protected WebElement prefLink;

    public AccountTopNavBar(WebDriver driver) {
        super(driver, By.cssSelector("#tileName-navTab .accountNavClass"));
    }

    public WebElement getTopNavLink(String textContent) {
        String xpath = "//a[contains(.,'%s')]";
        return getWebDriver().findElement(By.xpath(String.format(xpath, textContent)));
    }

    @Deprecated
    public WebElement getOverviewLink() {
        return overviewLink;
    }

    @Deprecated
    public WebElement getTripsLink() {
        return tripsLink;
    }

    @Deprecated
    public WebElement getAccInfoLink() {
        return accInfoLink;
    }

    @Deprecated
    public WebElement getPmtInfoLink() {
        return pmtInfoLink;
    }

    @Deprecated
    public WebElement getTravNamesLink() {
        return travNamesLink;
    }

    @Deprecated
    public WebElement getEmlSbscrLink() {
        return emlSbscrLink;
    }

    @Deprecated
    public WebElement getPrefLink() {
        return prefLink;
    }
}
