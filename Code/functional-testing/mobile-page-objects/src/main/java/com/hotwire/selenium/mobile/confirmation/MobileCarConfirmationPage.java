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

public class MobileCarConfirmationPage extends MobileAbstractPage {

    private static final String CONFIRM_SUMMARY = ".car-confirm .summary";

    @FindBy(css = CONFIRM_SUMMARY)
    private WebElement confirmSummary;

    public MobileCarConfirmationPage(WebDriver webDriver) {
        super(webDriver, ViewIds.TILE_CAR_PURCHASE_CONFIRM);
        new WebDriverWait(getWebDriver(), 10)
            .until(PageObjectUtils.webElementVisibleTestFunction(
                webDriver.findElement(By.cssSelector(CONFIRM_SUMMARY)), true));
    }

    public String getConfirmationNumber() {
        String codeText = confirmSummary.getText();
        String subString = codeText.substring(codeText.indexOf("confirmation:"));
        return subString.substring(subString.indexOf(":") + 1, subString.indexOf("Hotwire") - 1).trim();
    }

    public String getHotwireItinerary() {
        String codeText = confirmSummary.getText();
        String subString = codeText.substring(codeText.indexOf("itinerary:"));
        return subString.substring(subString.indexOf(":") + 1).trim();
    }
}
