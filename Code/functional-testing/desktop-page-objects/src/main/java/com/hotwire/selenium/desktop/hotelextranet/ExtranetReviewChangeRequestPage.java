/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.hotelextranet;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.widget.DatePicker;

/**
 * @author adeshmukh
 *
 */
public class ExtranetReviewChangeRequestPage extends AbstractUSPage {

    @FindBy(name = "startDate")
    private WebElement startDateField;

    @FindBy(name = "endDate")
    private WebElement endDateField;

    @FindBy(css = "button.btn.blueBt")
    private WebElement goButton;

    @FindBy(css = "table.roomDetails")
    private WebElement reviewChangeTable;

    public ExtranetReviewChangeRequestPage(WebDriver webDriver) {
        super(webDriver, new String[] {"tiles-def.xnet.review-change-requests"});
    }

    public void reviewChanges(Date startDate, Date endDate) {
        try {
            new DatePicker(getWebDriver(), startDateField).selectDate(startDate);
        }
        catch (NoSuchElementException e) {
            startDateField.clear();
            startDateField.sendKeys(DatePicker.getFormattedDate(startDate) + Keys.TAB);
        }
        try {
            new DatePicker(getWebDriver(), endDateField).selectDate(endDate);
        }
        catch (NoSuchElementException e) {
            endDateField.clear();
            endDateField.sendKeys(DatePicker.getFormattedDate(endDate) + Keys.TAB);
        }
        goButton.click();
    }

    public boolean validateReviewChangeTable() {
        boolean assertReviewChangesTable = true;
        try {
            // get the CL from the table
            String changeListId = getWebDriver().
                findElement(By.xpath("//table[@class='roomDetails']/tbody/tr[2]/td[3]")).getText();

            // click on the view details link of that CL
            getWebDriver().findElement(By.xpath("//table[@class='roomDetails']/tbody/tr[2]/td[5]/a")).click();

            // Validate the CL# on details
            if (!getWebDriver().findElement(By.xpath("//div[@class='reviewChangesTableIntro']/strong")).getText()
                .contains(changeListId)) {
                assertReviewChangesTable = false;
            }
        }
        catch (NoSuchElementException e) {
            e.printStackTrace();
            assertReviewChangesTable = false;
        }
        return assertReviewChangesTable;
    }

}
