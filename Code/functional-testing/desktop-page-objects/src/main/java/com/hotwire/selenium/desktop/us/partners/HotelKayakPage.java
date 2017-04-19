/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: v-vzyryanov
 * Date: 12/7/12
 * Time: 5:06 AM
 */
public class HotelKayakPage extends AbstractDesktopPage implements HotelPartnersPage {

    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.UK);

    //@FindBy(css="div#fareFinder input#location")
    @FindBy(css = "div#inlinesearchblock input#inlinewherebox")
    private WebElement location;

    @FindBy(css = "div#inlinesearchblock input#inline_checkin_date")
    private WebElement pickUpDate;

    @FindBy(css = "div#inlinesearchblock input#inline_checkout_date")
    private WebElement dropOffDate;


    public HotelKayakPage(WebDriver webdriver) {
        super(webdriver);
        WebDriverWait wait = new WebDriverWait(webdriver, 3);
        wait.until(ExpectedConditions.visibilityOf(location));
    }

    @Override
    public boolean checkSearchOptions(String loc, Date pickUp, Date dropOff) {

        return loc.equals(location.getAttribute("value")) && pickUpDate.getAttribute("value").equals(
                dateFormat
                        .format(pickUp)) && dropOffDate.getAttribute("value").equals(dateFormat.format(dropOff));
    }
}
