/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.customer;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * v-vyulun
 */
public class C3PastBookingPage extends ToolsAbstractPage {

    private static final String CSS_TAB_CAR = "div#tabs a#ui-id-3";
    private static final String CSS_TAB_HOTELS = "div#tabs a#ui-id-2";
    private static final String CSS_TAB_AIR = "div#tabs a#ui-id-1";
    private static final String CSS_ACTIVE_TAB = "div#tabs li[aria-selected='true']";

    private static final String CSS_CONFIRMATION_MSG = ".actionsMsgBox.successMessage";

    public C3PastBookingPage(WebDriver webDriver) {
        super(webDriver, By.id("tabs"));
        waitProgressBar();
    }

    private String getTabXpathPart() {
        String div = ".";
        switch (getActiveTabName().toLowerCase()) {
            case "hotel":
                div = ".//div[@id='tabs-2']";
                break;
            case "car":
                div = ".//div[@id='tabs-3']";
                break;
            case "air":
                div = ".//div[@id='tabs-1']";
                break;
            default:
                div = ".";
        }
        return div;
    }

    private String getTabCssPart() {
        String div = ".";
        switch (getActiveTabName().toLowerCase()) {
            case "hotel":
                div = "div#tabs-2 ";
                break;
            case "car":
                div = "div#tabs-3 ";
                break;
            case "air":
                div = "div#tabs-1 ";
                break;
            default:
                div = ".";
        }
        return div;
    }

    public void chooseHotels() {
        waitProgressBar();
        findOne(CSS_TAB_HOTELS).click();
    }

    public void chooseAir() {
        waitProgressBar();
        findOne(CSS_TAB_AIR).click();
    }

    public void chooseCar() {
        waitProgressBar();
        findOne(CSS_TAB_CAR).click();
    }

    public String getActiveTabName() {
        return findOne(CSS_ACTIVE_TAB).getText();
    }

    public void resendEmailsForCheckesClick() {
        findOne(getTabCssPart() + " input[name='resend']").click();
    }

    public String getLastItinerary() {
        return findOne(getTabCssPart() + "#silkId_itineraryNumber_0", DEFAULT_WAIT).getText();
    }

    public String getLastButOneItinerary() {
        return findOne(getTabCssPart() + "#silkId_itineraryNumber_1", DEFAULT_WAIT).getText();
    }

    public void clickLastItineraryCheckBox() {
        findOne(By.xpath(getTabXpathPart() +
                "//div[@class='purchaseWrapper'][1]//input[@type='checkbox']"), DEFAULT_WAIT).click();
    }

    public List<Integer> getNumbersOfConfirmedPurchase() {
        List<WebElement> purchasesContainer  = findMany(getTabCssPart()  + ".purchaseWrapper");
        List<Integer> numbersOfConfirmedPurchase = new ArrayList<>();
        Integer i = 0;
        for (WebElement purchase: purchasesContainer) {
            if (purchase.findElement(By.cssSelector(".confirm")).getText().toLowerCase().contains("confirmed")) {
                numbersOfConfirmedPurchase.add(i);
            }
            i++;
        }
        return numbersOfConfirmedPurchase;
    }

    public void clickLastButOneItineraryCheckBox() {
        findOne(By.xpath(getTabXpathPart() +
                "//div[@class='purchaseWrapper'][2]//input[@type='checkbox']"), DEFAULT_WAIT).click();
    }

    public String getItineraryByNum(Integer num) {
        return findOne(getTabCssPart() + "#silkId_itineraryNumber_" + num, DEFAULT_WAIT).getText();
    }

    public void clickCheckBoxByNum(Integer num) {
        num = num + 1;
        findOne(By.xpath(getTabXpathPart() +
                "//div[@class='purchaseWrapper'][" + num + "]//input[@type='checkbox']"), DEFAULT_WAIT).click();
    }

    public void choosePurchaseByItinerary(String itinerary) {
        findOne(By.xpath("//span[contains(@id, 'silkId_itineraryNumber_') and contains(text(), '" + itinerary + "')]" +
                "//..//a[contains(@id, 'silkId_purchaseDetails')]"), DEFAULT_WAIT).click();
    }

    public void choosePNRLinkForItinerary(String itineraryNumber) {
        findOne(By.xpath(".//span[contains(text(), '" + itineraryNumber + "')]" +
                "/../../..//a[contains(@id, 'silkId_crsReservationNumber')]"), DEFAULT_WAIT).click();
    }

    public String getPNRByItinerary(String itineraryNumber) {
        return findOne(By.xpath(".//span[contains(text(), '" + itineraryNumber + "')]" +
                "/../../..//a[contains(@id, 'silkId_crsReservationNumber')]"), DEFAULT_WAIT).getText();
    }

    public void clickFirstCarConfirmationLink() {
        findOne("#silkId_supplierReservationNumber_0", DEFAULT_WAIT).click();
    }

    public boolean isViewLinkExistsByItinerary(String itinerary) {
        return isElementDisplayed(By.xpath("//*[contains(@id, 'silkId_itineraryNumber_') " +
                        "and contains(text(), '" + itinerary + "')]" +
                        "/../../..//a[contains(@id, 'silkId_attachedCaseLink_')]"),
                EXTRA_WAIT);
    }

    public String getPurchaseDoneByCsr() {
        String xpath = "";
        for (int i = 0; i < 10; i++) {
            xpath = getTabXpathPart() +  "//*[@id='silkId_isCSRPurchased_" + i + "']";
            if (findOne(By.xpath(xpath)).getText().equals("Y")) {
                return getItineraryByNum(i);
            }
        }
        return "none";
    }

    public void clickViewCaseAttachedLink(String itineraryNumber) {
        findOne(By.xpath("//*[contains(@id, 'silkId_itineraryNumber_') " +
                "and contains(text(), '" + itineraryNumber + "')]" +
                "/../../..//a[contains(@id, 'silkId_attachedCaseLink_')]"), DEFAULT_WAIT)
                .click();
    }

    public String getIntlSiteBookingFlag(String itineraryNumber) {
        return findOne(By.xpath(getTabXpathPart() + "//*[contains(@id, 'silkId_itineraryNumber_') " +
                "and contains(text(), '" + itineraryNumber + "')]" +
                "/../../..//*[contains(@id, 'silkId_intlSiteBooking_')]"), DEFAULT_WAIT)
                .getText();
    }

    public String getPurchaseDoneByDomestic() {
        String xpath = "";
        for (int i = 0; i < 10; i++) {
            xpath = getTabXpathPart() +  "//span[@id='silkId_isCSRPurchased_" + i + "']";
            if (findOne(By.xpath(xpath)).getText().equals("N")) {
                return getItineraryByNum(i);
            }
        }
        return "none";
    }

    public Integer getPastPurchaseCount() {
        int i = 0;
        String css = "";
        try {
            while (true) {
                css = getTabCssPart() + "#silkId_isCSRPurchased_" + i;
                findOne(css).isDisplayed();
                i++;
            }
        }
        catch (Exception e) {
            return  i;
        }
    }

    public String getConfirmationMessage() throws Exception {
        new WebDriverWait(getWebDriver(), 20)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(CSS_CONFIRMATION_MSG), 10));
        List<WebElement> msg = findMany(CSS_CONFIRMATION_MSG);
        for (WebElement elem : msg) {
            if (elem.isDisplayed()) {
                return elem.getText();
            }
        }
        throw new Exception("Locator for the confirmation message was not found");
    }

    public void clickOnLastBooking() {
        findOne("div.purchaseWrapper a[href*='/purchaseDetail/']").click();
    }

    public Integer getPurchasesNum() {
        return findMany("a[id*='silkId_purchaseDetailsH']").size();
    }

    public List<WebElement> getReservations() {
        return findMany("div[id^='silkId_reservationSummary_'] a");
    }

    public String getStatusOfLastPurchase() {
        return findOne("div[id*='silkId_reservationSummary'] a", DEFAULT_WAIT).getText();
    }

    public String wasLastPurchaseBookedByCSR() {
        return findOne(By.xpath(getTabXpathPart() + "//span[@id='silkId_isCSRPurchased_0']"), DEFAULT_WAIT).getText();
    }

    public void choosePastBookingTab(String tab) {
        int index = 0;
        index = tab.toLowerCase().equals("hotel") ? 2 : index;
        index = tab.toLowerCase().equals("car") ? 3 : index;
        index = tab.toLowerCase().equals("air") ? 1 : index;
        String css = "a#ui-id-" + index;
        findOne(css, DEFAULT_WAIT).click();
    }

    public List<WebElement> getAllVisiblePurchase() {
        return findMany(".purchaseWrapperInside");
    }

    public List<String> getAllVisibleItineraries() {
        ArrayList <String> itinsS = new ArrayList<String>();
        List <WebElement> itinsWE = findMany("span[id^='silkId_itineraryNumber_']");
        for (WebElement itinWE : itinsWE) {
            itinsS.add(itinWE.getText().trim());
        }
        return itinsS;
    }

    public Integer getNumberOfPurchase(String itinerary) {
        List<WebElement> allPurchase = getAllVisiblePurchase();
        Integer i = 0;
        for (WebElement purchase : allPurchase) {
            if (purchase.getText().contains(itinerary)) {
                return i;
            }
            i++;
        }
        throw new RuntimeException("Purchase with itinerary " + itinerary + " was not found on the page");
    }

    public String getStatusOfPurchase(String itinerary) {
        return findOne("#silkId_reservationSummary_" + getNumberOfPurchase(itinerary)).getText();
    }

    public String getRefundedValueOfPurchase(String itinerary) {
        return findOne("#silkId_amountRefunded_" + getNumberOfPurchase(itinerary)).getText();
    }
    public String clickOnFirstConfirmedBooking(String productCode) {
        WebElement purchaseLink =
                findOne("a#silkId_purchaseDetails" + productCode + "_" + getFirstConfirmedBookingIndex(), DEFAULT_WAIT);
        String itineraryNumber = getParent(purchaseLink).getText().replaceAll("[^\\d]", "");
        purchaseLink.click();
        return itineraryNumber;
    }

    private Integer getFirstConfirmedBookingIndex() {
        int i = 0;
        for (WebElement elm : getReservations()) {
            if ("Purchase Confirmed".contains(elm.getText())) {
                return i;
            }
            i++;
        }
        throw new NoSuchElementException("Element with status Purchase confirmed was not found");
    }
}
