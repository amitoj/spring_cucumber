/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.common.billing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.selenium.desktop.globalheader.GlobalHeader;

/**
 * Trip Summary module for Hotel One Page Billing Page.
 * @author vjong
 */
public class HotelTripSummaryFragment extends AbstractDesktopPage {
    private static final String TRIP_INSURANCE = ".tripInsurance";
    private static final String DISCOUNT = ".hotelTripSummary div[id='hotelTripPriceDetail'] .discount";

    @FindBy(id = "subTotal")
    private WebElement subTotal;

    @FindBy(css = "div.taxDetail.nodeIndent div.yui3-u-1-3, div.hotelImposedFeesTaxes div.yui3-u-1-3")
    private WebElement hotelImposedFeesTaxes;

    @FindBy(css = "div.tripTotalPrice div.yui3-u-1-3")
    private WebElement tripTotalPrice;

    @FindBy(css = "div.hotwireNow div.yui3-u-1-3")
    private WebElement hotwireNow;

    @FindBy(css = ".resortFeeDetail")
    private WebElement resortFeeDetails;

    @FindBy(css = TRIP_INSURANCE)
    private WebElement tripInsurance;

    @FindBy(xpath = "//div[contains(text(),'Nights')]/following-sibling::div[1]")
    private WebElement nights;

    @FindBy(xpath = "//div[contains(text(),'Rooms')]/following-sibling::div[1]")
    private WebElement rooms;

    @FindBy(xpath = "//div[contains(@class,'travelDateAndRoom')]//div[@class='info'][1]")
    private WebElement checkInDate;

    @FindBy(xpath = "//div[contains(@class,'travelDateAndRoom')]//div[@class='info'][2]")
    private WebElement checkOutDate;

    @FindBy(xpath = "//div[contains(@class, 'priceDetail')]//div[1]//div[2]")
    private WebElement pricePerNight;

    @FindBy(xpath = ".//*[@id='insurance']")
    private WebElement insuranceCost;

    @FindBy(css = ".hotelName")
    private WebElement hotelName;

    public HotelTripSummaryFragment(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".tripSummaryModule, .hotelTripSummary"));
    }

    private Float parseFloat(String text) {
        Pattern pattern = Pattern.compile("(\\d+([,\\.]\\d+)*)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return Float.parseFloat(matcher.group(0));
        }

        return null;
    }

    /**
     * Remove the dollar seperator from the given string. Does not affect the formatting of currency in the string.
     */
    private String removeDollarSeparater(String text) {
        GlobalHeader header = new GlobalHeader(getWebDriver());
        // Start assumption dollar seperator is ",".
        String separater = ",";
        if (header.getSelectedCountry().equals("Sverige") || header.getSelectedCountry().equals("Norge")) {
            // Special case because spaces can be used after the currency text for some POS and we only want to alter
            // the dollar characters.
            if (header.getSelectedCurrency().startsWith("USD") || header.getSelectedCurrency().startsWith("GBP") ||
                header.getSelectedCurrency().startsWith("EUR")) {
                // There is no space for these currency symbols.
                separater = " ";
            }
            else {
                String[] texts = text.split(" ");
                return texts[0] + " " + texts[1].replaceAll(" ", "");
            }
        }
        else if (header.getSelectedCountry().equals("Danmark") || header.getSelectedCountry().equals("Deutschland")) {
            separater = ".";
        }
        return text.replaceAll(separater, "");
    }

    public Float getTaxesAndFees() {
        return parseFloat(removeDollarSeparater(hotelImposedFeesTaxes.getText()));
    }

    public Float getTotalPrice() {
        return parseFloat(removeDollarSeparater(tripTotalPrice.getText()));
    }

    public Float getSubTotalPrice() {
        return parseFloat(removeDollarSeparater(subTotal.getText()));
    }

    public String getRawTotalPrice() {
        String total = getWebDriver().findElement(By.cssSelector("div.tripTotalPrice div.yui3-u-1-3")).getText().trim();
        if (!StringUtils.isEmpty(total)) {
            return total;
        }
        else {
            // Frigging timing issue or something. Use backup.
            logger.info("Initial total is empty from trip summary. Use back up total.");
            String[] split = getWebDriver().findElement(
                By.cssSelector(".confirmPayment-text")).getText().split("\\s+\\(")[0].trim().split("\\s+");
            String possibleCurrency = split[split.length - 2].trim();
            total = split[split.length - 1].trim();
            return possibleCurrency.equals(new GlobalHeader(getWebDriver()).getSelectedCurrencyCode()) ?
                possibleCurrency + " " + total : total;
        }
    }

    public Float getHotwirePrice() {
        return parseFloat(removeDollarSeparater(hotwireNow.getText()));
    }

    public Float getRoomNumbers() {
        return parseFloat(rooms.getText());
    }

    public Float getNightNumbers() {
        return parseFloat(nights.getText());
    }

    public WebElement getLowPriceGuaranteeModule() {
       //Low price guarantee module is optional, so it cannot be tagged with @FindBy
        try {
            return getWebDriver().findElement(By.cssSelector(".reasonsToBelieve"));
        }
        catch (NoSuchElementException lowPriceGuaranteeModuleNotPresent) {
            return null;
        }
    }

    public boolean isResortFeesDetailsDisplayed() {
        try {
            return resortFeeDetails.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isTripInsuranceDisplayed() {
        try {
            return tripInsurance.isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getTripInsuranceCost() {
        return removeDollarSeparater(tripInsurance.getText()).replaceAll("[^0-9.,]", "");
    }

    public String getCheckInDate() {
        return checkInDate.getText().replace("Check-in:", "").trim();
    }

    public String getCheckOutDate() {
        return checkOutDate.getText().replace("Check-out:", "").trim();
    }

    public String getPricePerNight() {
        return removeDollarSeparater(pricePerNight.getText().replace("/ night", "")).replaceAll("[^0-9.,]", "").trim();
    }

    public float getInsuranceCost() {
        return  Float.parseFloat(insuranceCost.getText().replace('$', ' ').replaceAll(" ", ""));
    }

    public String getHotelOrNeighborhoodName() {
        return hotelName.getText().trim();
    }

    public String getStarRating() {
        return getWebDriver().findElement(By.cssSelector(".goldStarRating span")).getAttribute("title");
    }

    public String getNumberOfRooms() {
        return getWebDriver().findElement(By.cssSelector(".priceDetail span[id='numRooms']")).getText();
    }

    public String getPriceDetailText() {
        return getWebDriver().findElement(By.cssSelector(".priceDetail")).getText();
    }

    public boolean isDiscountDisplayed() {
        try {
            return getWebDriver().findElement(
                By.cssSelector(DISCOUNT)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public Float getDiscountAmount() {
        return new Float(
            getWebDriver().findElement(By.cssSelector(DISCOUNT + " span[id='discount']")).getText()
                .replaceAll("[^\\d.,]", ""));
    }
}
