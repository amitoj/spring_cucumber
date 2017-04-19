/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by v-ikomarov on 2/21/2015.
 */
public class HotelTrivagoPage extends AbstractDesktopPage implements HotelPartnersPage {
    private final DateFormat dateFormat = new SimpleDateFormat("E, MM/dd", Locale.UK);

    @FindBy(id = "js_querystring")
    private WebElement location;

    @FindBy (id = "date_from")
    private WebElement checkInDate;

    @FindBy (id = "date_to")
    private WebElement checkOutDate;

    public HotelTrivagoPage(WebDriver webdriver) {
        super(webdriver);
        WebDriverWait wait = new WebDriverWait(webdriver, 3);
        wait.until(ExpectedConditions.visibilityOf(location));
    }

    @Override
    public boolean checkSearchOptions(String loc, Date checkInDate, Date checkOutDate) {
        String checkInDateFormatted = dateFormat.format(checkInDate).substring(0, 2) +
                "," + dateFormat.format(checkInDate).substring(4);
        String checkOutDateFormatted = dateFormat.format(checkOutDate).substring(0, 2) +
                "," + dateFormat.format(checkOutDate).substring(4);

        return location.getAttribute("value").toLowerCase().contains(loc.toLowerCase()) &&
                checkInDateFormatted.equals(this.checkInDate.getText()) &&
                checkOutDateFormatted.equals(this.checkOutDate.getText());
    }
}
