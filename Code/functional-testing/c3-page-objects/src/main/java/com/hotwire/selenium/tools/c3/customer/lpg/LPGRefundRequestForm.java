/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.lpg;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;

/**
 * Created by v-ozelenov on 4/9/2014.
 */
public class LPGRefundRequestForm extends ToolsAbstractPage {
    private static final double PRICE_DIFF = 5.00;

    private String confirmationMessage =
        "Please note that it may take up to 3 business days for us to confirm the lower rate you submitted. " +
        "If we are able to verify the lower rate, a refund will be issued within 10 business days.";

    public LPGRefundRequestForm(WebDriver webdriver) {
        super(webdriver, By.id("LPGRefundLayerMain-body"));
    }

    public String getMsg() {
        return confirmationMessage;
    }

    public void setMSG(String msg) {
        this.confirmationMessage = msg;
    }

    public void fill(Double totalAmount, boolean isOpaque) {
        //Choose price must be less than Hotwire price
        String lowerPrice = new DecimalFormat("###.##").format(totalAmount - PRICE_DIFF);
        setText("input[name='competitorPrice']", lowerPrice);
        //Date found
        setDateFound();
        //Time before now on 3 hours
        DateTime timeFound = getTimeBefore(5);
        selectValue("select#hour", timeFound.toString("h"));
        selectValue("select#minute", timeFound.toString("mm"));
        selectValue("select#amOrPm", "AM");

        //Any source
        setText("textarea#competitorPriceSource", "Test");

        try {
            //Fill addresses
            setText("input#customerAddress1", "test");
            setText("input#customerAddress2", "test");
            setText("input#customerCity", "test");
            selectValue("select#customerState", "AA");
            setText("input#customerZipCode", "10005");
            logger.info("RETAIL PURCHASE");
        }
        catch (NoSuchElementException e) {
            logger.info("OPAQUE PURCHASE");
        }
    }

    public void setDateFound() {
        selectDate("input[name='foundDate'], input#LPGRefundHotelDateFound-field", getCurrentDate());
    }

    public void submit() {
        findOne("button img[alt='Submit']").click();
    }

    public String getHotwireTripPrice() {
        return findOne("span#itinHotwireTotalPrice", DEFAULT_WAIT).getText();
    }

    public String getConfirmationMsg() {
        waitUntilDisappear("div#LPGRefundLayerMain-body form");
        findOne("div.confirmContinue", DEFAULT_WAIT);
        return findOne("div#LPGRefundLayerMain-body").getText();
    }
}
