/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.dmu;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ozelenov
 * Date: 9/4/14
 * Time: 6:11 PM
 * Search results pop-up that appear after typing in DMU search box
 */
public class DMUProcedurePopUp extends ToolsAbstractPage {

    private static final String PROCEDURE_LINK = "div#headerSearchResult a";

    public DMUProcedurePopUp(WebDriver webdriver) {
        super(webdriver, By.id("headerSearchResult"));
    }

    public List<WebElement> getProceduresLinks() {
        return findMany(PROCEDURE_LINK);
    }

    public String clickOnFirstProcedure() {
        WebElement firstProcedure = findOne(PROCEDURE_LINK);
        String text = firstProcedure.getText();
        firstProcedure.click();
        return text;
    }
}
