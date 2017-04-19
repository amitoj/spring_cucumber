/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.confirmation;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.utils.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HotelConfirmationPage extends AbstractRowPage {

    @FindBy(css = ".hotelConfirmationNumber span, .hotelAndGuestInfo .confNumber")
    private List<WebElement> confirmElements;

    @FindBy(css = "div.tripSummaryModule div.priceFgColor div.priceAlign, .tripTotalPrice span[id='totalCharge']")
    private WebElement hotwirePrice;

    @FindBy(css = "div.imposedFees div.totalImposFeesPrice div.priceAlign, .taxDetail span#taxesAndFees")
    private WebElement imposedFeesTotalFees;

    @FindBy(css = "div.imposedFees div.totalPrice:not(.totalImposFeesPrice) div.priceAlign, .tripTotalPrice " +
                  "span[id='totalCharge']")
    private WebElement imposedFeesTotalPrice;

    public HotelConfirmationPage(WebDriver webdriver) {
        super(webdriver, new String[]{"tile.hotel.purchase-confirm", "tile.hotel.booking-confirm"});
    }

    public String getItineraryNumber() {
        return confirmElements.get(1).getAttribute("data-itinerary");
    }

    public String getConfirmationNumber() {
        return confirmElements.get(0).getAttribute("data-confirm");
    }

    public Float getTaxesAndFees() {
        // TODO: add if-else like in getHotwirePrice()
        return StringUtils.parseFloat(imposedFeesTotalFees.getText());
    }

    public Float getTotalPrice() {
        // TODO: add if-else like in getHotwirePrice()
        return StringUtils.parseFloat(imposedFeesTotalPrice.getText());
    }

    public Float getHotwirePrice() {
        return StringUtils.parseFloat(hotwirePrice.getText());
    }
}
