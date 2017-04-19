/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer.itineraryDetails;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.testing.UnimplementedTestException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Page object class of C3 itinerary details page.
 */
public class C3ItineraryDetailsPage extends ToolsAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(C3ItineraryDetailsPage.class.getName());
    private static final String STAR_RATING_LINK = "a[href*='hotelBenchmarkPopup.jsp']";

    public C3ItineraryDetailsPage(WebDriver webDriver) {
       super(webDriver, By.cssSelector("img[src*='smallItineraryIcon']"));
    }

    public C3ItineraryDetailsPage(WebDriver webdriver, By by) {
        super(webdriver, by);
    }

    public String getItineraryNumber() {
        return findOne("td#silkId_ItInf_ItineraryNum.infobarValue", EXTRA_WAIT).getText();
    }

    public void serviceRecoveryClick() {
        findOne("input.displayButton[name='serviceRecovery']", EXTRA_WAIT).click();
    }

    public String getTotalCost() {
        return findOne(".subSubSection.transactionAmount div.rightCol.highlighted," +
                " #silkId_TrAm_TotCost, #silkId_TrAm_TotCostToCustomer, #silkId_TrAm_TotalCost", EXTRA_WAIT)
                .getText().replace(",", "").replaceAll("\\(.*\\)", "");
    }

    public void doCancellation() {
        findOne("input.displayButton[name='cancel']", EXTRA_WAIT).click();
        findOne("td#cancelCell button", EXTRA_WAIT).click();
        try {
            findOne("div#titleLayerDiv input", DEFAULT_WAIT).click();
        }
        catch (TimeoutException e) {
            LOGGER.info("No double confirmation popup");
        }
    }

    public void doResendBookingConfirmation() {
        findOne("input[value*='Resend']", DEFAULT_WAIT).click();
    }

    public String getMessage() {
        return findOne("div.msgBox", EXTRA_WAIT).getText().trim();
    }

    public void clickViewConfirmation() {
        findOne("a[href*='purchase-details.jsp']", DEFAULT_WAIT).click();
    }

    public String getAllowedRecoveryStatus() {
        try {
            return findOne("span#recoveryStatusHolder").getText().replace("Allowed recovery status", "").trim();
        }
        catch (NoSuchElementException ex) {
            return "NOT DISPLAYED";
        }
    }

    public void clickOnShareItinerary() {
        findOne("input[value = 'Share itinerary']", DEFAULT_WAIT).click();
    }

    public boolean isShareItineraryClosed() {
        return findOne("div#resendBookingConfirmationPopup-panel_c") .getAttribute("class")
                .contains("yui-overlay-hidden");
    }

    public String getStatus() {
        return findOne("td#silkId_ItInf_Status b", DEFAULT_WAIT).getText();
    }

    public String getHotelAddress() {
        return  findMany(".travelDetailsBlock.hotelInfo .value").get(1).getText().replace("\n", " ");
    }

    public String getPNR() {
        return findOne("td#silkId_ItInf_GDSConfNum, TD#silkId_ItInf_RecordLocator, DIV#itinRecLocPNR").getText();
    }

    public String getGuestName() {
        return findOne(By.id("silkId_TrDet_ParticipantNames")).getText().replaceAll("^.*, ", "");
    }

    public String getBillingName() {
        return findOne("#silkId_PaymDet_Full_Authorization_BillingName", DEFAULT_WAIT).getText();
    }

    public String getHotDollarsAmount() {
        return getWebDriver().findElement(By.id("silkId_TrAm_HotDollarsUsed")).getText();
    }

    public void openBillingPage() {
        clickOnLink("Display Billing Page for Itinerary", DEFAULT_WAIT);
    }

    public void clickOnLPGClaimLink() {
        clickOnLink("Low Price Guarantee Claim", DEFAULT_WAIT);
    }

    public boolean isLPGLinkDisplayed() {
        return isElementDisplayed(By.linkText("Low Price Guarantee Claim"));
    }

    public String getDeviceType() {
        return findOne("td#TESTID_deviceType, div#itinDeviceType").getText();
    }

    public String getApplicationType() {
        return findOne("td#TESTID_appType, div#itinAppType").getText();
    }

    public void addHotDollarsToItinerary() {
        clickOnLink("Add HotDollars for Itinerary", DEFAULT_WAIT);
    }

    public boolean isStarRatingLinkDisplayed() {
        return isElementDisplayed(STAR_RATING_LINK);
    }

    public void clickOnHotelStarRatingLink() {
        findOne(STAR_RATING_LINK).click();
    }

    public void clickOnDisplayBillingPageForItinerary() {
        clickOnLink("Display Billing Page for Itinerary", DEFAULT_WAIT);
    }

    public void clickOnDetailsUponConfirmation() {
        throw new UnimplementedTestException("Test implemented only for hotels");
    }

    public String getRefundedByValue() {
        return findOne("div#refundedByCol div#itinRefBy", EXTRA_WAIT).getText();
    }

    public String doAvsOverride(String securityCode) {
        throw new UnimplementedTestException("Test implemented only for hotels");
    }

    public String doCpvOverride() {
        findOne(By.xpath(".//*[contains(text(), 'To override CPV go here')]")).click();
        findOne("#verballyConfirmedByBank", EXTRA_WAIT).click();
        findOne(By.xpath(".//input[contains(@value, 'cpv override') ]")).click();
        String errorMsg = findOne("div.msgBox.errorMsgBox", PAGE_WAIT).getText();
        return errorMsg;
    }

}
