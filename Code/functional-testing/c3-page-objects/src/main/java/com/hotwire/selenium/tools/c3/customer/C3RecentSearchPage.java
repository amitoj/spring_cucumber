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

/**
 * v-vyulun
 */
public class C3RecentSearchPage extends ToolsAbstractPage {

    public C3RecentSearchPage(WebDriver webDriver) {
        super(webDriver, By.xpath("//b[text()='View Recent Searches']"));
        waitProgressBar();
    }

    private Integer getSearchNumByID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String xpath = ".//*[contains(@id,silkId_RecSearch_SearchId)]";
        int size = findMany(By.xpath(xpath)).size();
        for (int i = 0; i < size; i++) {
            if (findOne(By.xpath(".//*[@id='silkId_RecSearch_SearchId" + i + "']")).getText().equals(searchID)) {
                return i;
            }
        }
        return null;
    }

    public boolean verifyViewAllLink() {
        return findOne(By.xpath(".//a[text()='View all']"), EXTRA_WAIT).isDisplayed();
    }

    public void clickViewAllLink() {
        clickOnLink("View all", DEFAULT_WAIT);
    }

    public boolean verifyView15Link() {
        return isElementDisplayed(By.xpath(".//a[text()='View 15']"), EXTRA_WAIT);
    }

    public String getLastSearchID() {
        return findOne("#silkId_RecSearch_SearchId0", DEFAULT_WAIT).getText();
    }

    public void selectLastSearchButton() {
        findMany("input.displayButton", DEFAULT_WAIT)
                .get(0).click();
    }

    public void selectRecentSearchByID(String searchID) {
        freeze(2);
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        findOne(By.xpath(".//span[text()='" + searchID + "']//..//..//..//..//" +
                "..//..//input[@class='displayButton']"), DEFAULT_WAIT).click();
    }

    public String getRecentSearchByCSR(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        return findOne(By.xpath(".//span[text()='" + searchID + "']//..//..//..//..//" +
                "..//..//td[@class='inactiveIteratedElement']//div"), DEFAULT_WAIT).getText();
    }

    public String getLocationBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        return findOne("#silkId_RecSearch_SearchedFor" + getSearchNumByID(searchID), DEFAULT_WAIT).getText();
    }

    public String getCheckInBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        return findOne("#silkId_RecSearch_StartDate" + getSearchNumByID(searchID), DEFAULT_WAIT).getText();
    }

    public String getCheckOutBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        return findOne("#silkId_RecSearch_EndDate" + getSearchNumByID(searchID), DEFAULT_WAIT).getText();
    }

    public String getRoomsBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String result = findOne("#silkId_RecSearch_RoomDetails" +
                getSearchNumByID(searchID), DEFAULT_WAIT).getText();
        return result.split(" ")[1].toString();
    }

    public String getNightsBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String result = findOne("#silkId_RecSearch_RoomDetails" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.split("\n")[1].toString().split(" ")[0];
    }

    public String getAdultsBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String result = findOne("#silkId_RecSearch_Travelers" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.split(" ")[1].toString();
    }

    public String getChildrenBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String result = findOne("#silkId_RecSearch_Travelers" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.split("\n")[1].toString().split(" ")[0];
    }

    public String getTicketsBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String result = findOne("#silkId_RecSearch_Tickets" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText().trim();
        return result;
    }

    public String getFromAirportBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String result = findOne("#silkId_RecSearch_SearchedFor" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.split(" to ")[0];
    }

    public String getToAirportBySearchID(String searchID) {
        searchID = (searchID.startsWith("2")) ? searchID.substring(1) : searchID;
        String result = findOne("#silkId_RecSearch_SearchedFor" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.split(" to ")[1].split("\n")[0];
    }

    public String getPickUpTimeBySearchID(String searchID) {
        String result = findOne("#silkId_RecSearch_PickUpTime" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.trim();
    }

    public String getDropOffTimeBySearchID(String searchID) {
        String result = findOne("#silkId_RecSearch_DropOffTime" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.trim();
    }

    public String getCardTypeBySearchID(String searchID) {
        String result = findOne("#silkId_RecSearch_CardType" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.trim();
    }

    public String getCsrStatusBySearchID(String searchID) {
        String result = findOne("#silkId_RecSearch_SearchedByCsr" + getSearchNumByID(searchID), DEFAULT_WAIT)
                .getText();
        return result.trim();
    }
}
