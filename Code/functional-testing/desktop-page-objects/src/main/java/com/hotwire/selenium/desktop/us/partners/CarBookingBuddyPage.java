/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.partners;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: v-vzyryanov
 * Date: 4/16/13
 * Time: 5:25 AM
 */
public class CarBookingBuddyPage extends AbstractDesktopPage implements CarPartnersPage {

    private static final DateFormat DATE_FORMATTER = new SimpleDateFormat("EEE MMM d", Locale.UK);
    private final String closeBanner = ".layer-a img.close-icon";


    @FindBy(css = ".long-locations")
    private WebElement searchParametersContainer;

    public CarBookingBuddyPage(WebDriver webdriver) {
        super(webdriver);


        new WebDriverWait(webdriver, 10)
            .until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".bookingbuddy_com")));
    }

    @Override
    public NAME getName() {
        return NAME.BBUDDY;
    }

    @Override
    public String getActualSearchQuery() {

        if (isBannerPresent()) {
            closeAllBanners();
        }
        getWebDriver().manage().window().maximize();

        return searchParametersContainer.getText().trim();
    }

    private void closeAllBanners() {
        while (isBannerPresent()) {
            getWebDriver().findElement(By.cssSelector(closeBanner)).click();
            new WebDriverWait(getWebDriver(), 15);
        }
    }

    private boolean isBannerPresent() {
        return getWebDriver().findElement(By.cssSelector(".c-traq-layer")).isDisplayed();
    }

    @Override
    public String getExpectedSearchQuery(Date pickUp, Date dropOff) {
        return QUERY_FORMAT
                .replaceAll("%PICKUPDATE%", DATE_FORMATTER.format(pickUp))
                .replaceAll("%DROPOFFDATE%", DATE_FORMATTER.format(dropOff))
                .replaceAll("( )+", " ").trim();
    }

    @Override
    public void close() {
        getWebDriver().close();
        Alert alert;
        try {
            alert = getWebDriver().switchTo().alert();
            alert.accept();
        }
        catch (NoAlertPresentException e) {
            //
        }
    }
}
