/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.widget;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates either native drop-down select or jQuery UI custom drop-down widget.
 *
 * @author v-ypuchkova
 * @since 2012.10
 */
public class DropDownSelector {

    private static final int TIMEOUT = 5;
    private WebElement nativeDropDown;
    private WebDriver webDriver;

    public DropDownSelector(WebDriver webDriver, By selector) {
        new WebDriverWait(webDriver, TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(selector));
        this.webDriver = webDriver;
        this.nativeDropDown = webDriver.findElement(selector);
    }

    public DropDownSelector(WebDriver webdriver, WebElement nativeDropDown) {
        this.webDriver = webdriver;
        this.nativeDropDown = nativeDropDown;
    }

    /**
     * Select item from drop-down by its visible text
     */
    public void selectByVisibleText(String itemText) {

        if (StringUtils.isEmpty(itemText)) {
            return;
        }

        if (nativeDropDown.isDisplayed()) {
            Select startTimeCarSelect = new Select(nativeDropDown);
            startTimeCarSelect.selectByVisibleText(itemText);
            return;
        }

        By href = By.cssSelector(String.format("a[id^='%s']", nativeDropDown.getAttribute("id")));

        new WebDriverWait(webDriver, TIMEOUT).until(ExpectedConditions.presenceOfAllElementsLocatedBy(href));

        WebElement customDropDown = webDriver.findElement(href);
        customDropDown.click();
        customDropDown.sendKeys(Keys.HOME);
        By elementSelector = By.xpath("//div[contains(@class,'ui-selectmenu-open')]//a[text()='" + itemText + "']");
        WebElement element = webDriver.findElement(elementSelector);
        element.click();
    }

    public List<String> getValueList() {
        Select nativeDropDownSelector = new Select(nativeDropDown);
        List<String> valueList = new ArrayList<String>();
        for (WebElement option: nativeDropDownSelector.getOptions()) {
            valueList.add(option.getAttribute("value"));
        }
        return valueList;
    }
}
