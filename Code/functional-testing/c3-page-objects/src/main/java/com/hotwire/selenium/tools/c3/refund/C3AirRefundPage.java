/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.refund;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
* Air vertical refund page methods
 */
public class C3AirRefundPage extends C3RefundPage {
    private String departSection = "outbound";
    private String returnSection = "inbound";

    public C3AirRefundPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void proceedPartialRefund() {
        clickRefund();
        try {
            getWebDriver().switchTo().alert().accept();
        }
        catch (Exception e) {
            logger.info("There are no alert window");
        }
        try {
            new WebDriverWait(getWebDriver(), 5)
                    .until(ExpectedConditions.presenceOfElementLocated(By.name("newResNum"))).sendKeys("#wwwwww");
            new WebDriverWait(getWebDriver(), 5)
                    .until(ExpectedConditions.presenceOfElementLocated(By.name("continue"))).click();
        }
        catch (Exception e) {
            logger.info("just waiting expected error message");
        }
    }

    @Override
    public void fillPartialRefundValues() {
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(departSection + "0"))).click();
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(returnSection + "0"))).click();
    }

    @Override
    public void fillPartialRefundValues(Integer numberOfSegment) {
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(departSection + (numberOfSegment - 1))))
                .click();
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.presenceOfElementLocated(By.id(returnSection + (numberOfSegment - 1))))
                .click();
    }

    public void fillPartialRefundValues(String segment, String passengers) {
        String nameOfSegment = null;

        if ("depart".equals(segment)) {
            nameOfSegment = "outbound";
        }
        else if ("return".equals(segment)) {
            nameOfSegment = "inbound";
        }


        if ("first".equals(passengers)) {
            new WebDriverWait(getWebDriver(), 5)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id(nameOfSegment + "0"))).click();
        }
        else if ("second".equals(passengers)) {
            new WebDriverWait(getWebDriver(), 5)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id(nameOfSegment + "1"))).click();
        }
        else if ("both".equals(passengers)) {
            for (int i = 0; i < 2; i++) {
                new WebDriverWait(getWebDriver(), 5)
                        .until(ExpectedConditions.presenceOfElementLocated(By.id(nameOfSegment + i))).click();
            }
        }



    }
}
