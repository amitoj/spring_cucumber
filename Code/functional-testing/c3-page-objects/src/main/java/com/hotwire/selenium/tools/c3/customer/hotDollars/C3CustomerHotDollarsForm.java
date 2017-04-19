/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.hotDollars;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/30/14
 * Time: 6:38 AM
 * HotDollars Form in C3
 */
public class C3CustomerHotDollarsForm extends ToolsAbstractPage {
    public static final String HOT_DOLLARS_AMOUNT = "div.editHotdollarsSection div.editSummary span";
    private int lastTransaction;
    private String lastTransTemp = "div.availableHotDollarsSection div.items ul.row:nth-child(%s)";
    private String cssActivity = ".section.usedHotDollarsSection .items .row";

    public C3CustomerHotDollarsForm(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.editHotdollarsSection"));
        waitLoading();
    }

    public String getHotDollarsAmount() {
        return findOne(HOT_DOLLARS_AMOUNT, EXTRA_WAIT).getText().replaceAll("[$,]", "");
    }

    public void setHotDollarsAmount(String amount) {
        setText("input#hotDollarsAmount", amount);
    }


    public void setHotDollarsReason(String reason) {
        selectValue("div.editHotdollarsSection select#editReason", reason);
    }

    public String getHotDollarsReason() {
        return findOne("div.editHotdollarsSection select#editReason").getText();
    }

    public void submit() {
        findOne("div.editHotdollarsSection input[name='save']").click();
    }

    public WebElement getSubmitBtn() {
        return findOne("div.editHotdollarsSection input[name='save']");
    }

    public void override() {
        findOne("div.editHotdollarsSection input[name='showHDOverrideLayer']", DEFAULT_WAIT).click();
    }

    public void setOverrideDescription(String text) {
        setText("textarea#hdOverrideReasonDescription", text, DEFAULT_WAIT);
    }

    public void clickSubmitHDOverride() {
        findOne("input#overrideWithCSR").click();
    }

    public void cancel() {
        findOne("div.editHotdollarsSection input[name='cancel']").click();
    }

    public String getConfirmationMsg() {
        return findOne("div.editHotdollarsSection div#messageBox", EXTRA_WAIT)
                .getText().replace(",", "");
    }

    public String getErrorMsg() {
        freeze(1);
        return findOne("div.editHotdollarsSection div#messageBox.errorMessage", DEFAULT_WAIT).getText();
    }

    public String getHotDollarsSummary() {
        freeze(1);
        return findOne("div.availableHotDollarsSection div.summary").getText().replace(",", "");
    }

    public void clickOnLastTransaction() {
        lastTransaction = findMany("div.availableHotDollarsSection div.items ul.row").size();
        String css = String.format(lastTransTemp + " a", lastTransaction);
        findOne(css).click();
    }

    public String getLastTransactionPopUp() {
        String css = String.format(lastTransTemp + " div.description", lastTransaction);
        return findOne(css).getText();
    }

    public void viewCaseNotes() {
        final List<WebElement> links = findMany(By.linkText("Click to view"));
        links.get(links.size() - 1).click();
    }

    public String getCaseNoteDescription() {
        return findOne("div.description", DEFAULT_WAIT).getText();
    }

    public void waitLoading() {
        waitUntilDisappear("div#editHotdollarsIndicator", PAGE_WAIT);
    }

    private int getActivityIndexForItinerary(String  itineraryNumber) {
        List<WebElement> activities = findMany(cssActivity + " .col7", DEFAULT_WAIT);
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getText().contains(itineraryNumber)) {
                return i;
            }
        }
        return -1;
    }

    public String getActivityBookingDateForItinerary(String itineraryNumber) {
        return findMany(cssActivity + " .col6").
                get(getActivityIndexForItinerary(itineraryNumber)).getText();
    }

    public String getActivityBookingForItinerary(String itineraryNumber) {
        return findMany(cssActivity + " .col7").
                get(getActivityIndexForItinerary(itineraryNumber)).getText();
    }

    public String getActivityAmountForItinerary(String itineraryNumber) {
        return findMany(cssActivity + " .col8").
                get(getActivityIndexForItinerary(itineraryNumber)).getText();
    }

    public boolean isVisible() {
        try {
            return getWebDriver().findElement(By.cssSelector("div.editHotdollarsSection")).isDisplayed();
        }
        catch (Exception e) {
            return false;
        }
    }

}
