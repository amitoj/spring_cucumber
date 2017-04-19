/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.index;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author akrivin
 * @since 10/17/12
 */
public class CruiseIndexPage extends AbstractUSPage {

    @FindBy(xpath = "//a[contains(@onclick, 'submitCruiseLastMinuteDeal')]")
    private List<WebElement> lastMinuteCruises;

    @FindBy(xpath = "//tr[contains(@onclick, 'submitForm')]")
    private List<WebElement> topCruiseDeals;

    @FindBy(xpath = "//a//strong//span")
    private WebElement opnionLink;

    public CruiseIndexPage(WebDriver webdriver) {
        super(webdriver, "tiles-def.cruise.index");
    }

    public void clickLastMinuteDeal() {
        if (!isLastMinuteDealEmpty()) {
            lastMinuteCruises.get(0).click();
        }
    }

    public void clickTopCruiseDeal() {
        if (!isTopCruiseDealsEmpty()) {
            topCruiseDeals.get(0).click();
        }
    }

    public boolean isTopCruiseDealsEmpty() {
        return topCruiseDeals.isEmpty();
    }

    public boolean isLastMinuteDealEmpty() {
        return lastMinuteCruises.isEmpty();
    }

    public boolean verifyOpinionLink() {
        opnionLink.click();
        return opnionLink.isDisplayed();
    }
}
