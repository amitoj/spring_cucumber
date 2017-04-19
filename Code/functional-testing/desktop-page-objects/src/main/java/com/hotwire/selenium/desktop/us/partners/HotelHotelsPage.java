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
 * Created by v-ikomarov on 2/20/2015.
 */
public class HotelHotelsPage extends AbstractDesktopPage implements HotelPartnersPage {
    private final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.UK);

    @FindBy (id = "q-destination")
    private WebElement location;

    @FindBy (id = "q-localised-check-in")
    private WebElement checkInDate;

    @FindBy (id = "q-localised-check-out")
    private WebElement checkOutDate;

    @FindBy (xpath = ".//div[@class='summary']//button[contains(text(), 'Change search')]")
    private WebElement changeSearch;

    public HotelHotelsPage(WebDriver webdriver) {
        super(webdriver);
        WebDriverWait wait = new WebDriverWait(webdriver, 3);
        wait.until(ExpectedConditions.visibilityOf(changeSearch));
    }

    @Override
    public boolean checkSearchOptions(String loc, Date pickUp, Date dropOff) {
        if (!location.isDisplayed()) {
            changeSearch.click();
        }
        return location.getAttribute("value").toLowerCase().contains(loc.toLowerCase()) &&
                checkInDate.getAttribute("value").equals(dateFormat.format(pickUp)) &&
                checkOutDate.getAttribute("value").equals(dateFormat.format(dropOff));
    }
}
