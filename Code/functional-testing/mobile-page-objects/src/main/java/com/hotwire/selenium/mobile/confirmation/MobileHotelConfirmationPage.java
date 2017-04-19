/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.confirmation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import com.hotwire.selenium.mobile.view.ViewIds;
import com.hotwire.util.webdriver.po.PageObjectUtils;

public class MobileHotelConfirmationPage extends MobileAbstractPage {
    private static final String DISCOUNT_AMOUNT = "span[data-bdd='hwDiscount']";
    private static final String CONFIRM_SUMMARY = ".hotel-confirm .summary";

    @FindBy(css = "span#confNumber")
    private WebElement confNumber;

    @FindBy(css = "span#hwItinerary")
    private WebElement hwItinerary;

    @FindBy(css = "span[data-bdd='total']")
    private WebElement total;

    public MobileHotelConfirmationPage(WebDriver webDriver) {
        super(webDriver, ViewIds.TILE_HOTEL_PURCHASE_CONFIRM);
        new WebDriverWait(getWebDriver(), 10)
            .until(PageObjectUtils.webElementVisibleTestFunction(
                webDriver.findElement(By.cssSelector(CONFIRM_SUMMARY)), true));
    }

    public String getConfirmationNumber() {
        return confNumber.getAttribute("data-confirm");
    }

    public String getHotwireItinerary() {
        return hwItinerary.getAttribute("data-itinerary");
    }

    public Float getDiscountAmount() {
        return new Float(
            getWebDriver().findElement(By.cssSelector(DISCOUNT_AMOUNT)).getText().replaceAll("[^\\d.,]", ""));
    }

    public Float getTotalAmount() {
        return new Float(total.getText().replaceAll("[^\\d.,]", ""));
    }
}
