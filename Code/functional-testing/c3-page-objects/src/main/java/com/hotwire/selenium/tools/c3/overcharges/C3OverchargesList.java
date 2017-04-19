/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.overcharges;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 9/20/13
 * Time: 6:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3OverchargesList extends ToolsAbstractPage {
    private String hotelOvercharge = "div.hotelDetails a";

    public C3OverchargesList(WebDriver webDriver) {
        super(webDriver, By.id("hsr"));
    }

    public List<WebElement> getOverchargesList() {
        return findMany(hotelOvercharge);
    }

    public List<WebElement> getColumn(String field) {
        String column = findOne(By.xpath("//a[contains(text(),'" + field + "')]"))
                .getAttribute("class")
                .replace("linkCol ", "");
        return getWebDriver().findElements(By.cssSelector("div." + column));
    }

    public List<WebElement> getHotelNameColumn() {
        String column = findOne(By.xpath("//div[a[contains(text(),'Hotel Name')]]"))
                .getAttribute("class")
                .replace("linkCol ", "");
        return getWebDriver().findElements(By.cssSelector(String.format("div.%s a[b]", column)));
    }

    public List<WebElement> getTotalOverchargesColumn() {
        String column = findOne(By.xpath("//a[contains(text(),'Total Overcharges')]"))
                .getAttribute("class")
                .replace("linkCol ", "");
        return getWebDriver().findElements(By.cssSelector(String.format("div.%s b", column)));
    }

    public String getFullHotelName() {
        return findOne(hotelOvercharge).getText().trim();
    }

    public String getAssignee() {
        return findOne("div.hotelDetails select option:nth-child(2)").getText();
    }

    public void setSortOrder(String field) {
        clickOnLink(field, DEFAULT_WAIT);
    }

    public void chooseHSR() {
        //select csrcroz9 by customer id
        selectValue("div.hotelDetails  select", "2135406060");
    }

    public void clickUpdate() {
        findOne("div.hotelDetails input.formButton").click();
    }

    public void navigateToAnyOverchargeDetails() {
        freeze(1);
        Random random = new Random();
        List<WebElement> webElements = findMany("div.hotelDetails a", DEFAULT_WAIT);
        webElements.get(random.nextInt(webElements.size())).click();
    }

    public void selectAll() {
        findOne("input.selectAll").click();
    }

    public void unAsssignAll() {
        selectAll();
        selectValue("select.generalAction", "0");
        clickUpdate();
    }

    public void sortByOverchargesAmount() {
        findOne("a[href*='sortType=OverchargesCount']").click();
    }

    public void sortByOverchargesTotal() {
        findOne("a[href*='sortType=TotalOvercharge']").click();
    }

    public void openHotelWithMinimumOvercharges() {
        sortByOverchargesAmount();
        navigateToAnyOverchargeDetails();
    }

    public void clickOnSearchForOverchargesInfo() {
        findOne("a[href*='ovchInfoSearch.jsp']", DEFAULT_WAIT).click();
    }

    public Map getOverchargesRow(int num) {
        Map<String, String> list = new HashMap<>();
        List<WebElement> lstHotelName = findMany(".row1", DEFAULT_WAIT);
        List<WebElement> lstAmount = findMany(".col2", DEFAULT_WAIT);
        List<WebElement> lstDateModified = findMany(".col3", DEFAULT_WAIT);
        List<WebElement> lstLastDate = findMany(".col4", DEFAULT_WAIT);
        List<WebElement> lstTotal = findMany(".row1", DEFAULT_WAIT);

        list.put("HOTEL_NAME", lstHotelName.get(num + 1).getText().replace("\n$", "!").split("!")[0]);
        list.put("OVERCHARGE_TOTAL", lstTotal.get(num + 1).getText().replace("\n$", "!").split("!")[1]);
        list.put("OVERCHARGE_COUNT", lstAmount.get(num + 1).getText());
        list.put("OVERCHARGE_DATE_MODIFIED", lstDateModified.get(num + 1).getText());
        list.put("LAST_OVERCHARGE_DATE", lstLastDate.get(num + 1).getText());

        return list;
    }

    public void clickNextPage() {
        clickOnLink("Next", DEFAULT_WAIT);
    }
}
