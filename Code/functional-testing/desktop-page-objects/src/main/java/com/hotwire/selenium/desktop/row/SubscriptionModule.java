/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row;

import com.google.common.collect.ImmutableMap;
import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.PageName;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Map;

public class SubscriptionModule extends AbstractRowPage {

    public static final String MODULE_CONTAINER = "//div[contains(@class, 'SubscriptionModule')]";
    public static final String PLACEMENT_CODE = "//input[@type='hidden' and @name='placementCode']";
    public static final String SUBSCRIBE_BUTTON = "//button[contains(text(), 'Subscribe')]";

    private static final int MAX_TIME_OUT_SECONDS = 30;

    private static final Map<String, String> PLACEMENT_CODES = ImmutableMap.of(
        "5000105", "tile.hotel.results.opaque:subscriptionModule",
        "5000106", "tile.hotel.results.opaque:dbmSubscriptionModule",
        "5000107", "tile.hotel.geo.low.level.secret:SecretSubscriptionModule",
        "5000108", "tile.hotel.geo.low.level.lastMinute:LastMinuteSubscriptionModule");

    @FindBy(xpath = PLACEMENT_CODE)
    private WebElement placementCode;

    @FindBy(css = "input#email")
    private WebElement emailInput;

    @FindBy(xpath = SUBSCRIBE_BUTTON)
    private WebElement subscribeButton;

    @FindBy(css = "div.congratulation")
    private WebElement congratulation;

    public SubscriptionModule(WebDriver webdriver) {
        super(webdriver, By.xpath(MODULE_CONTAINER));
        new Wait<>(new VisibilityOf(getWebDriver().findElement(By.xpath(MODULE_CONTAINER))))
            .maxWait(MAX_TIME_OUT_SECONDS).apply(getWebDriver());
    }

    public void subscribe(String email) {
        emailInput.sendKeys(email);
        subscribeButton.click();
        // TODO: move Wait to AbstractDesktopPage
        new Wait<>(new InvisibilityOf(By.xpath(SUBSCRIBE_BUTTON))).maxWait(MAX_TIME_OUT_SECONDS).apply(getWebDriver());
    }

    public boolean verifyPlacementCode() {
        String placementCodeValue = this.placementCode.getAttribute("value");
        String pageName = new PageName().apply(getWebDriver());
        String className = getWebDriver().findElement(By.xpath(MODULE_CONTAINER)).getAttribute("class");
        String pageNameIdActual = pageName + ":" + className;
        String pageNameIdExpected = PLACEMENT_CODES.get(placementCodeValue);

//        System.err.println(placementCodeValue);
//        System.err.println(pageNameIdActual);
//        System.err.println(pageNameIdExpected);

        return pageNameIdActual.equals(pageNameIdExpected);
    }

    public Boolean isConfirmationDisplayed() {
        return congratulation.isDisplayed();
    }
}
