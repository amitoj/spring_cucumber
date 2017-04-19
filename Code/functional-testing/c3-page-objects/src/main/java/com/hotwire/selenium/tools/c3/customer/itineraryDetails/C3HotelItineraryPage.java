/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.itineraryDetails;


import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * New design is applied for Hotel itineraries only
 */
public class C3HotelItineraryPage extends C3ItineraryDetailsPage {
    private static final String BOOKING_DETAILS_ROW =
            "//div[@class='travelDetailsBlockContent']" +
                    "//div[@class='hotelInfoRow']//span[@class='label'][text()='%s']/../span[@class='value']";

    private static final String PAYMENT_DETAILS_ROW =
            "//div[@class='paymentRowContent']//div[@class='colTitle'][text()='%s']/../div[@class='colValue']";

    private static final String TRANSACTION_AMOUNT_ROW =
            "//div[@class='subSubSectionContent']//div[@class='transactionRow']//div[@class='leftCol'" +
                    " and contains(text(), '%s')]/..//div[@class='rightCol']";

    private static String blockHotelLink = "//a[contains(text(), 'Block hotel')]";
    private static String unblockHotelLink = "//a[contains(text(), 'Unblock hotel')]";

    public C3HotelItineraryPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.itniInfoContainer"));
        waitTextLoaded(By.id("itinNumber"), EXTRA_WAIT);
    }

    @Override
    public String getItineraryNumber() {
        return findOne("div#itinNumber", DEFAULT_WAIT).getText().replaceFirst("^HW# ", "");
    }

    @Override
    public String getStatus() {
        return findOne("a#itinStatus", EXTRA_WAIT).getText();
    }

    @Override
    public String getPNR() {
        return findOne("div#itinGDSConfNum").getText();
    }

    @Override
    public String getDeviceType() {
        return findOne("div#itinDeviceType").getText();
    }

    @Override
    public String getApplicationType() {
        return findOne("div#itinAppType").getText();
    }

    @Override
    public String getAllowedRecoveryStatus() {
        final String status = findOne("span#recoveryStatusHolder")
                .getText().replace("Allowed recovery status", "").trim();
        if (StringUtils.isEmpty(status)) {
            return "NOT DISPLAYED";
        }
        else {
            return status;
        }
    }

    @Override
    public void doCancellation() {
        findOne("span.btnsHolder input[name='cancel']", DEFAULT_WAIT).click();
        findOne("div.cancelSection button", EXTRA_WAIT).click();
        findOne("div.cancelPopupHolder input[name='cancel']", DEFAULT_WAIT).click();
    }

    @Override
    public String getTotalCost() {
        return findOne("div.transactionAmount div.rightCol.highlighted", EXTRA_WAIT)
                .getText().replace(",", "").replaceAll("\\(.*\\)", "");
    }

    @Override
    public void serviceRecoveryClick() {
        findOne("span.btnsHolder input[name='serviceRecovery']", EXTRA_WAIT).click();
    }

    @Override
    public void clickOnDetailsUponConfirmation() {
        findOne("a[href*='-detail.jsp']").click();
    }

    @Override
    public void clickViewConfirmation() {
        findOne("a[href*='/hotel/redirectToConfirmPage']", DEFAULT_WAIT).click();
    }

    @Override
    public String getGuestName() {
        return getBookingDetails("Guest Name(s)").getText().replaceAll("^.*, ", "");
    }

    @Override
    public String getBillingName() {
        return getPaymentDetails("Billing Name").getText();
    }

    @Override
    public String getHotDollarsAmount() {
        return getTransactionAmount("HotDollars Used").getText().replaceAll("[$-,]", "");
    }

    private WebElement getBookingDetails(String rowName) {
        return findOne(By.xpath(String.format(BOOKING_DETAILS_ROW, rowName)));
    }

    private WebElement getPaymentDetails(String rowName) {
        return findOne(By.xpath(String.format(PAYMENT_DETAILS_ROW, rowName)));
    }

    private WebElement getTransactionAmount(String rowName) {
        return findOne(By.xpath(String.format(TRANSACTION_AMOUNT_ROW, rowName)), DEFAULT_WAIT);
    }

    public void clickOnManuallyBillCreditItineraryLink() {
        findOne(By.xpath("//a[text()='Manually Bill/Credit Itinerary']")).click();
    }

    public void createManualCredit(String amountDollars) {
        findOne(By.name("selectedCreditReason"), DEFAULT_WAIT).sendKeys("Other");
        findOne(By.name("creditAmountString")).sendKeys(amountDollars);
        findOne(By.name("creditButton")).click();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void createManualBilling(String amountDollars) {
        findOne(By.name("selectedDebitReason"), DEFAULT_WAIT).sendKeys("Other");
        findOne(By.name("debitAmountString")).sendKeys(amountDollars);
        findOne(By.name("debitButton")).click();
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void checkManualBilling(String amountDollars) {
        String actualText = findOne("#errorMessaging", DEFAULT_WAIT).getText().replace("\n", "").replace("\r", "");
        String expectedText = String.format("Claim was processed successfully" +
                " for the amount of $%s USD.%n%nThe manual billing was successful." +
                " The Customer's Card has been charged.", amountDollars).replace("\n", "").replace("\r", "");
        assertThat(actualText).isEqualTo(expectedText);
    }

    public void checkManualCredit(String amountDollars) {
        String actualText = findOne("#errorMessaging", DEFAULT_WAIT).getText().replace("\n", "").replace("\r", "");
        String expectedText = String.format("Claim was processed successfully" +
                " for the amount of $%s USD.%n%nThe manual credit was successful." +
                " The Customer's Card has been credited.", amountDollars).replace("\n", "").replace("\r", "");
        assertThat(actualText).isEqualTo(expectedText);
    }

    public void clickEditItineraryCaseHistory() {
        clickOnLink("Edit case history for itinerary", DEFAULT_WAIT);
    }

    public void clickEditCaseEntriesForCustomer() {
        clickOnLink("Edit case entries for customer", DEFAULT_WAIT);
    }

    public boolean isAVSDeclinedPurchase() {
        return findOne("#itinStatus", DEFAULT_WAIT).getText().contains("Avs Declined");
    }

    public boolean isCPVResubmitOptionAvailable() {
        return isElementDisplayed(By.xpath(".//input[@value='retry purchase with cpv']"), DEFAULT_WAIT);
    }

    public String getHotelReservationNumber() {
        return findOne("#itinGDSConfNum").getText();
    }

    public String getFullHotelName() {
        return findOne("#itinCompanyName").getText();
    }

    public String getNights() {
        return findMany(".travelDetailsBlock.orderBlock .value").get(3).getText();
    }

    public String getRooms() {
        return findMany(".travelDetailsBlock.orderBlock .value").get(2).getText();
    }

    public String getStarRating() {
        return findMany(".travelDetailsBlock.hotelInfo .value").get(4).getText();
    }

    public String getCheckInDate() {
        return findOne("#itinCheckIn").getText().split(" ")[0];
    }

    public String getCheckOutDate() {
        return findOne("#itinCheckOut").getText().split(" ")[0];
    }

    public List<String> getAmenities() {
        List<String> amenities = new ArrayList<>();
        String value = ".travelDetailsBlock.detailsBlock .value";
        for (int i = 0; i < findMany(value).get(1).getText().split(", ").length; i++) {
            amenities.add(findMany(value).get(1).getText().split(", ")[i]);
        }
        return amenities;
    }

    public String getTripProtectionAmount() {
        return findMany(".subSubSectionContent .colsHolder .rightCol").get(5).getText().replace(",", "");
    }

    public void clickBlockHotel() {
        findOne(By.xpath(blockHotelLink)).click();
    }

    public void clickUnblockHotel() {
        findOne(By.xpath(unblockHotelLink)).click();
    }

    @Override
    public String doAvsOverride(String securityCode) {
        findOne("input#cpvNumber", DEFAULT_WAIT).sendKeys(securityCode);
        findOne("input#verballyConfirmedByBank", DEFAULT_WAIT).click();
        findOne("input[value = 'avs override']").click();
        String errorMsg = findOne("div.msgBox.errorMsgBox", PAGE_WAIT).getText();
        return errorMsg;


    }

}
