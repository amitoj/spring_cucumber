/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.details;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created by IntelliJ IDEA. User: v-abudyak Date: 5/16/12 Time: 5:11 PM To
 * change this template use File | Settings | File Templates.
 */
public class AirDetailsPage extends AbstractPageObject {

    private static final String[] TILE_NAMES = new String[]{"tiles-def.air.details.opaque",
        "tiles-def.air.review-fare", "tiles-def.air.details.retail", "tiles-def.airPlusCar.review-fare",
        "tiles-def.air.billing"};

    @FindBy(id = "tileName-B5")
    private WebElement airDetail;

    @FindBy(xpath = "//a[@class='startSearch']")
    private WebElement rentalCarOption;

    @FindBy(css = ".dollars")
    private WebElement opaquePrice;

    @FindBy(xpath = "//p//span[@class='dollars']")
    private WebElement totalPrice;

    @FindBy(xpath = "//div[contains(@data-jsname,'MesoBanner')]")
    private WebElement mesoAds;

    public AirDetailsPage(WebDriver webdriver) {
        super(webdriver, TILE_NAMES);
    }

    public AirDetailsPage(WebDriver webdriver, String tileName) {
        super(webdriver, new String[] {tileName});
    }

    public AirDetailsPage(WebDriver webdriver, String[] tileNames) {
        super(webdriver, tileNames);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector("form[name='continueFormBottom']");
    }

    public void select() {
        getWebDriver().findElement(By.cssSelector("form[name='continueFormBottom']")).submit();
    }

    public boolean isRentalCarOptionLinkAvailable() {
        try {
            return rentalCarOption.isDisplayed() && rentalCarOption.isEnabled();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void addRentalCar() {
        if (isRentalCarOptionLinkAvailable()) {
            rentalCarOption.click();
            new WebDriverWait(getWebDriver(), getTimeout()).until(
                    ExpectedConditions.elementToBeClickable(By.name("selectedCarAddOnSolutionId"))).click();
        }
        else {
            throw new RuntimeException("Can't rent a car as add-on for flight.. The link didn't appear");
        }
    }

    public String getOpaquePrice() {
        return opaquePrice.getText().replaceAll("[^0-9]", "");
    }

    public String gettotalPrice() {
        return totalPrice.getText().replaceAll("[^0-9]", "");
    }

    public void addRentalCarViaPackage() {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 90);

        airDetail.findElement(By.id("confirmPaymentBtn")).click();
    }

    public boolean checkMesoAdsIsDisplayed() {
        return mesoAds.isDisplayed();
    }
}
