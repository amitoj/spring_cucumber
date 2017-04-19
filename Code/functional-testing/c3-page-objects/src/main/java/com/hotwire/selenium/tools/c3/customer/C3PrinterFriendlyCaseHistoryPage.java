/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 10/21/14
 * Time: 3:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3PrinterFriendlyCaseHistoryPage extends ToolsAbstractPage {
    private static final String CASE_HEADER = "caseNoteHeader";
    private static final String CASE_TIME = ".caseNoteDescriptionItem .contentCreationTime";
    private static final String CASE_DESCRIPTION = "caseNoteDescriptionItem";

    public C3PrinterFriendlyCaseHistoryPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".caseHistory.caseHistoryPrint"));
    }

    public List<String> getDescriptionContents() {
        List<WebElement> descriptionsPrinterPageWebElem = findMany(By.className(CASE_DESCRIPTION));
        List<String> descriptionsPrinterPage = new ArrayList<String>();

        for (WebElement elem: descriptionsPrinterPageWebElem) {
            descriptionsPrinterPage.add(elem.getText());
        }
        return descriptionsPrinterPage;
    }

    public List<String> getTimeContents() {
        List<WebElement> timePrinterPageWebElem = findMany(By.cssSelector(CASE_TIME));
        List<String> timePrinterPage = new ArrayList<String>();

        for (WebElement elem: timePrinterPageWebElem) {
            timePrinterPage.add(elem.getText());
        }
        return timePrinterPage;
    }


}
