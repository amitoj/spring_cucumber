/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3;
import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object
 */
public class C3ViewResources extends ToolsAbstractPage {
    public C3ViewResources(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div#resourcesContainer"));
    }

    public void clickOnAddHotDollarsToMultipleAcc() {
        findOne("a[href*='massAddHotdollars.jsp']", DEFAULT_WAIT).click();
    }

    public void clickOnWorkflowLink() {
        clickOnLink("Workflow", DEFAULT_WAIT);
    }
}
