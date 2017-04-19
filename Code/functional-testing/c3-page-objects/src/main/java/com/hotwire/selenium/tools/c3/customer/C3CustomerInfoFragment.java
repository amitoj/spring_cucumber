/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;

/**
 * Autor: v-abudyak
 */
public class C3CustomerInfoFragment extends ToolsAbstractPage {

    private static final String ROW_TMP = "//div[@id='customerInfoHolder']//ul//li" +
            "//span[@class='caption'][text()='%s']/../span[@class='captionValue']";
    private BigDecimal accountCreated;

    public C3CustomerInfoFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#customerInfContainer"));
        waitProgressBar();
    }

    private String getValueByRowName(String rowName) {
        return findOne(By.xpath(String.format(ROW_TMP, rowName)), DEFAULT_WAIT).getText();
    }

    public String getPhoneNumber() {
        return getValueByRowName("Primary Phone No.");
    }

    public String getName() {
        waitProgressBar();
        return getValueByRowName("Account Name").replaceFirst("\\s+", " ");
    }

    public String getEmail() {
        return getValueByRowName("Email");
    }

    public String getZip() {
        return getValueByRowName("Zip/Postal Code");
    }

    public String getAccountType() {
        return getValueByRowName("Account-type");
    }

    public String getAltPhoneNumber() {
        return getValueByRowName("Alternate Phone No.");
    }

    public String getCountry() {
        return getValueByRowName("Country");
    }

    public String getWatermark() {
        return findOne("div#customerInfoHolder").getAttribute("class");
    }

    public String getExpressProgramStatus() {
        return getValueByRowName("Hotwire Express Program");
    }

    public String getAccountCreated() {
        return getValueByRowName("Account Created");
    }

    public String getHotDollarAmount() {
        return getValueByRowName("Hotdollar Amount");
    }



}
