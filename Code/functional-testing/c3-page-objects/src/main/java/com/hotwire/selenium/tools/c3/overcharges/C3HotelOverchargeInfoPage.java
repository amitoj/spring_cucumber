/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.overcharges;


import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-jneiman
 * Date: 12/16/13
 * Time: 6:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3HotelOverchargeInfoPage extends ToolsAbstractPage {

    public C3HotelOverchargeInfoPage(WebDriver webDriver) {
        super(webDriver, By.id("ovchHotelInfoForm"));
    }

    public void clickBackToResultsLink() {
        clickOnLink("Back to Results", DEFAULT_WAIT);
    }

    public Integer getOverchargeResultsNum() {
        return findMany("form#ovchHotelInfoForm div.hotelDetails").size();
    }

    public void clickOnOverchargeAmount() {
        findOne("div[id*='ovchPriceHolder'] a").click();
    }

    public String getOverchargeAmount() {
        return findOne("div[id*='ovchPriceHolder'] a").getText();
    }

    public String changeFirstOverchargeStatus() {
        selectValue("select#contactHsrAssigned_0", "2");
        String status = findOne("select#contactHsrAssigned_0 option[value='2']").getText();
        logger.info("Overcharge status:", status);
        return status;
    }

    public void submit() {
        submitFormByName("ovchHotelInfoForm");
    }

    public String getItineraryOfFirstRow() {
        String itinerary = findOne("div.overchargesHolder div.hotelDetails a").getText();
        logger.info("Overcharge itinerary:", itinerary);
        return itinerary;
    }

    public boolean isOverchargeDisplayed(String itineraryNumber) {
        String path = String.format("//div[@class='overchargesHolder']//a[contains(text(),'%s')]", itineraryNumber);
        return isElementDisplayed(By.xpath(path));
    }

    public void clickOnShowHistory() {
        findOne("a[href*='overchargesActiveOnly']").click();
    }

    public String getNotes() {
        return findOne(By.xpath("//div[@class='ovchNotes'][br]")).getText();
    }

    public void setTitle(String text) {
        setText("input#hotelOverchargeTitle", text);
    }

    public void setSupplierEmail(String text) {
        setText("input#hotelOverchargeEmail", text);
    }

    public void setSupplierPhone(String text) {
        setText("input#hotelOverchargePhone", text);
    }

    public void setSupplierFax(String text) {
        setText("input#hotelOverchargeFax", text);
    }

    public String getFullHotelName() {
        return findMany("form#ovchHotelInfoForm>div.infoBlock>div.leftCell.fLft", DEFAULT_WAIT)
                .get(0).getText().replace("Hotel Name: ", "");
    }

    public void updateFirstItineraryStatus() {
        findOne("#selectItem_0", DEFAULT_WAIT).click();
        findOne("#contactHsrAssigned_0", DEFAULT_WAIT).sendKeys("Write Off - Exchange Rate");
        findMany(".formButton.fRt", DEFAULT_WAIT).get(5).click();
    }
}
