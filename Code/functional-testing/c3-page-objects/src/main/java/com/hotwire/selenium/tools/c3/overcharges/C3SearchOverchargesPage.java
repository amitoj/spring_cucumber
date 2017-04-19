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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * User: v-abudyak
 * Date: 9/20/13
 * Time: 2:24 AM
 */
public class C3SearchOverchargesPage extends ToolsAbstractPage {

    public C3SearchOverchargesPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("form[name='ovchSearch']"));
    }

    public void selectSearchByParam(String searchBy) {
        Select select = new Select(
                new WebDriverWait(getWebDriver(), 5)
                        .until(ExpectedConditions.visibilityOfElementLocated(By.id("searchBy"))));
        select.selectByVisibleText(searchBy);
    }

    public void selectSearchPeriod(String period) {
        Select select = new Select(
                new WebDriverWait(getWebDriver(), 5)
                        .until(ExpectedConditions.visibilityOfElementLocated(By.id("lastDateModifiedPeriod"))));
        select.selectByVisibleText(period);
    }

    public void clickSearch() {
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("Submit"))).click();
    }

    public void clickSearchForUnassigned() {
        new WebDriverWait(getWebDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("searchForUnassigned"))).click();
    }

    public void selectSearchPeriodWithDatepicker(int numOfDays) {
        selectDate("input#startDate-field", getDateBefore(numOfDays));
        selectDate("input#endDate-field", getCurrentDate());
    }

    public boolean errorOnPagePresent() {
        return getWebDriver().getPageSource().contains("Your search did not match any results.");
    }

    public void typeAssigneeName(String assignee) {
        getWebDriver().findElement(By.id("hsrAssignedLogin")).sendKeys(assignee);
    }

    public void checkUnassigned() {
        findOne("input#searchForUnassigned").click();
    }

    public void fillHsr(String hsr) {
        setText("input#hsrAssignedLogin", hsr);
    }

    public void setHotel(String hotel) {
        setText("input#propertyHotelName", hotel);
    }

    public void setState(String state) {
        selectValue("select#propertyHotelUSState", state);
    }


}


