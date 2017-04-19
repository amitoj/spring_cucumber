/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 9/25/14
 * Time: 12:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3AreaMapPage extends ToolsAbstractPage {
    private static String areaMap = "#radiiMapPopupLayer-panel";
    private static String closeBtn =  areaMap + " .container-close";
    private static String yourSearch = areaMap + " .crossHair.fLft";


    public C3AreaMapPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector(areaMap));
    }

    public void clickCloseBtn() {
        findOne(By.cssSelector(closeBtn)).click();
    }

    public String getYourSearch() {
        return findOne(By.cssSelector(yourSearch)).getText();
    }
}
