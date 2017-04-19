/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.row;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.row.elements.Label;
import com.hotwire.selenium.desktop.row.search.NewFareFinder;
import com.hotwire.selenium.desktop.row.search.OldFareFinder;

public class HomePage extends AbstractRowPage {

    private static final String HOME_FARE_FINDER = "homeFareFinder";
    private static final String TOP_DESTINATIONS = "topDestinations";
    private static final String ERROR_MESSAGES = "errorMessages";

    @FindBy(css = "div.callAndBook span")
    private Label callAndBook;

    @FindBy(className = TOP_DESTINATIONS)
    private WebElement topDestinations;

    @FindBy(className = ERROR_MESSAGES)
    private WebElement errorMessages;

    @FindBy(css = "div.discountCodeLayer")
    private WebElement discountBanner;

    @FindBy(css = "div.discountCodeLayer .discountRule")
    private WebElement discountCondition;

    public HomePage(WebDriver webdriver) {
        super(webdriver, new String[]{"tile.hotel.index", "tile.home", "tile.hotwire.hotel"});
    }

    public NewFareFinder getFareFinder() {
        return new NewFareFinder(getWebDriver(), By.className(HOME_FARE_FINDER));
    }

    public OldFareFinder getOldFareFinder() {
        return new OldFareFinder(getWebDriver(), By.className(HOME_FARE_FINDER));
    }

    public String getTopDestinationUrl(String topDestination) {
        return topDestinations.findElement(By.partialLinkText(topDestination)).getAttribute("href");
    }

    public String getErrorMessages() {
        return errorMessages.getText();
    }

    public String getCustomerCarePhoneNumber() {
        return callAndBook.getText();
    }

    public GlobalHeader getGlobalHeader() {
        return new GlobalHeader(getWebDriver());
    }

    public List<WebElement> getTopDestinations() {
        return getWebDriver().findElements(By.xpath(
            "//a/div[@class='description']/div[@class='dealDescription']/div[@class='location']"));
    }

    public boolean isDiscountBannerPresent() {
        try {
            discountBanner.isDisplayed();
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getDiscountBannerText() {
        return discountBanner.findElement(By.cssSelector("div.discountFooter")).getText();
    }

    public String getDiscountRule() {
        return discountBanner.findElement(By.cssSelector(".discountRule")).getText();
    }
}
