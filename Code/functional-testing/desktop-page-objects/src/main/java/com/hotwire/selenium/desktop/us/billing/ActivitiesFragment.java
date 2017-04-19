/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing;

import java.util.ArrayList;
import java.util.List;

import com.hotwire.util.webdriver.functions.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * Created by IntelliJ IDEA. User: jreichenberg Date: 7/17/12 Time: 3:03 PM To
 * change this template use File | Settings | File Templates.
 */
public class ActivitiesFragment extends AbstractUSPage {

    @FindBy(name = "btnDsActivity")
    private WebElement btnDsActivity;

    @FindBy(xpath = ".//div[@class='formRow']/span")
    private List<WebElement> ticketTypeTexts;

    @FindBy(css = " .activity.gtHeader")
    private WebElement selectQuantity;

    @FindBy(css = " .activity")
    private WebElement selectGT;

    @FindBy(css = " .activity.gtHeader .button.expand")
    private WebElement selectActivities;

    @FindBy(xpath = ".//a[contains(@id, 'details-opener')]")
    private List<WebElement> moreDetails;

    @FindBy(xpath = ".//div[@class='yui-panel-container shadow']/div/div")
    private WebElement dsPopUp;

    public ActivitiesFragment(WebDriver webDriver) {
        super(webDriver, By.id("billingPanelActivitiesComp"));
    }

    public List<String> getListOfActivitiesTicketTypes() {
        List<String> ticketsTypeList = new ArrayList<String>();

        for (WebElement ticketTypeText : ticketTypeTexts) {
            if (ticketTypeText.getText().indexOf("Adult") > 0) {
                ticketsTypeList.add("Adult");
            }
            else if (ticketTypeText.getText().indexOf("Child") > 0) {
                ticketsTypeList.add("Child");
            }
            else if (ticketTypeText.getText().indexOf("Senior") > 0) {
                ticketsTypeList.add("Senior");
            }
            else if (ticketTypeText.getText().indexOf("Youth") > 0) {
                ticketsTypeList.add("Youth");
            }
            else if (ticketTypeText.getText().indexOf("Traveler") > 0) {
                ticketsTypeList.add("Traveler");
            }
        }
        return ticketsTypeList;
    }

    public Boolean verifyActiviesGT() {
        try {
            if (selectGT.isDisplayed()) {
                return true;
            }
        }
        catch (NoSuchElementException e) {
            // Nothing to do if not found.
        }
        return false;
    }

    public Boolean verifyGT() {
        try {
            if (!(selectGT.isDisplayed())) {
                return true;
            }
        }
        catch (NoSuchElementException e) {
            // Nothing to do if not found.
        }
        return false;

    }

    // This function checks for more details links, clicks on it and return
    // false if ds-popup is not invoked.
    public boolean verifyMoreDetailsLinks() {

        // Iterate through all more details links and click them to see if ds
        // popup is invoked.
        for (WebElement element : moreDetails) {
            try {
                if (element.isDisplayed()) {
                    element.click();
                    if (!dsPopUp.isDisplayed()) {
                        return false;
                    }
                    else {
                        //if ds popup is opened, close it.
                        new Wait<>(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[@class='yui-panel-container shadow']/div/a")))
                                .maxWait(3).apply(getWebDriver()).click();
                    }
                }
                else {
                    return false;
                }
            }
            catch (NoSuchElementException e) {
                logger.info("Element - More details links and/or DS pop-up, don't appear.");
            }
        }

        return true;
    }

    public void selectActivities() {
        try {
            if (selectActivities.isDisplayed()) {
                selectActivities.click();
            }
        }
        catch (NoSuchElementException e) {
            // Nothing to do if not found.
        }
    }

    public void continuePanel() {
        btnDsActivity.click();
    }
}
