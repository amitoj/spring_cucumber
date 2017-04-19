/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.refreshUtil;

import com.hotwire.util.webdriver.po.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * User: v-vzyryanov Date: 11/19/12 Time: 1:34 AM
 */
public class RefreshUtilPage extends AbstractPage {

    public RefreshUtilPage(WebDriver webdriver) {
        super(webdriver, By.name("updatePropertyForm"));
    }

    public void setupProperty(String propName, String propValue) {
        WebElement propertyBox = getWebDriver()
            .findElement(By.xpath("//table//tr//td//input[contains(@name, 'propValue" + propName + "')]//.."));

        WebElement input = propertyBox.findElement(By.xpath("//input[contains(@name, 'propValue" + propName + "')]"));
        input.clear();
        input.sendKeys(propValue);

        propertyBox.findElement(By.xpath("//input[contains(@onclick, 'propValue" + propName + "')]")).click();
    }

    public void changePropertyToDefault(String propName) {
        String xpath = "//span[contains(text(), 'Changed props')]//following-sibling::table";
        xpath += "//tr//td[contains(@width, '500px')]//input[contains(@value,'" + propName + "')]//..//..";
        xpath += "//input[contains(@value, 'Change to default')]";
        try {
            getWebDriver().findElement(By.xpath(xpath)).click();
        }
        catch (NoSuchElementException e) {
            // No-op
        }
    }
}
