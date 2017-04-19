/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.purchase.car;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.selenium.tools.c3.purchase.ResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Car results methods that used in Customer Care Center
 */
public class C3CarResultsPage extends ToolsAbstractPage implements ResultsPage {

    private static String seeMapLink = ".sidebarGradientLink>img";
    private static String firstResult = "//input[@class='continueBtn']";

    public C3CarResultsPage(WebDriver webdriver) {
        super(webdriver, By.id("tileName-moduleCarRentals"));
    }

    public void verifyResults() {
        findOne(By.xpath(firstResult), DEFAULT_WAIT);
    }

    public String getReferenceNumber() {
        return findOne("li.referenceNumber p").getText().replace("-", "");
    }

    public void selectFirstResult() {
        findOne(By.xpath(firstResult)).click();
    }

    public void selectSeeMapLink() {
        findOne(seeMapLink).click();
    }

    @Override
    public String getTypeOfFirstResult() {
        if (findOne(By.xpath(firstResult)).getAttribute("src").contains("gray")) {
            return "retail";
        }
        else {
            return "opaque";
        }

    }
}
