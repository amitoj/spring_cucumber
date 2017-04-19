/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.results;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.AbstractDesktopPage;

/**
 * @author v-jhernandeziniguez
 *
 */
public class ExternalAHPage extends AbstractDesktopPage {

    private String departureDate;
    private String returnDate;
    private final String expectedText = "Which airport do you prefer for flying to Vail - " +
        "Beaver Creek (ski area), Colorado, United States of America";
    @FindBy(xpath = "//a[contains(text(),'Vail, CO, United States (EGE-All Airports)')]")
    private WebElement vailLink;
    @FindBy(css = "h1")
    private WebElement airportSelectionText;
    public ExternalAHPage(WebDriver webdriver) {
        super(webdriver);
    }
    /**
     * @return the eXPECTED_TEXT
     */
    public String getEXPECTED_TEXT() {
        return expectedText;
    }

    /**
     * @return the vailLink
     */
    public WebElement getVailLink() {
        return vailLink;
    }

    public String getAirportSelectionText() {
        return airportSelectionText.getText();
    }

    public boolean isVailLinkDisplayed() {
        try {
            getVailLink().isDisplayed();
            return true;
        }
        catch (Exception e) {
            return false;
        }

    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * @return the departureDate
     */
    public String getDepartureDate() {
        return departureDate;
    }

    /**
     * @param departureDate
     *            the departureDate to set
     */
    public void setDepartureDate(Date today, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        this.departureDate = sdf.format(addDays(today, days));
    }

    /**
     * @return the returnDate
     */
    public String getReturnDate() {
        return returnDate;
    }

    /**
     * @param returnDate
     *            the returnDate to set
     */
    public void setReturnDate(Date today, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        this.returnDate = sdf.format(addDays(today, days));
    }

}
