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
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 9/18/14
 * Time: 3:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3CaseHistoryPage extends ToolsAbstractPage {

    private static final String CASE_HEADER = "caseNoteHeader";
    private static final String CASE_TIME = ".caseNoteDescriptionItem .contentCreationTime";
    private static final String CASE_DESCRIPTION = "caseNoteDescriptionItem";
    private static final String CASE_FOR_ITINERARY = "//div[contains(@id, 'caseNoteDescription_')]" +
                    "//div[@class='caseNoteDescriptionItem']" +
                    "//div[@class='caseNoteDescriptionItemContent' and contains(text(), 'itinerary')]";
    private static final String PRINTER_FRIENDLY_BTN = "printLink";

    public C3CaseHistoryPage(WebDriver webdriver) {
        super(webdriver, By.className("caseHistory"));
    }

    public C3CaseHistoryPage(WebDriver webdriver, By keyPageElement) {
        super(webdriver, keyPageElement);
    }

    public WebElement getPrinterFriendlyBtn() {
        return findOne(By.className(PRINTER_FRIENDLY_BTN), DEFAULT_WAIT);
    }

    public List<String> getHeadersOfCases() {
        List<String> headersText = new ArrayList<>();
        List<WebElement> headers =  findMany(By.className(CASE_HEADER));
        for (WebElement header: headers) {
            headersText.add(header.getText());
        }
        return headersText;
    }

    public List<String> getDescriptionOfCases() {
        List<String> descriptionText = new ArrayList<>();
        List<WebElement> descriptions =  findMany(By.className(CASE_DESCRIPTION));
        for (WebElement description : descriptions) {
            descriptionText.add(description.getText());
        }
        return descriptionText;
    }

    public List<String> getTimeOfCases() {
        List<String> timeText = new ArrayList<>();
        List<WebElement> times =  findMany(By.cssSelector(CASE_TIME));
        for (WebElement time : times) {
            timeText.add(time.getText());
        }
        return timeText;
    }

    public boolean isCaseForItineraryExist(String itinerary) {
        try {
            return findOne(By.xpath(CASE_FOR_ITINERARY.replace("itinerary", itinerary))).isDisplayed();
        }
        catch (Exception e) {
            return false;
        }
    }
}
