/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.partners;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.AbstractDesktopPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * Created with IntelliJ IDEA.
 * User: v-vyulun
 * Date: 04/10/13
 * Time: 5:07 AM
 */
public class HotelExpediaPage extends AbstractDesktopPage implements HotelPartnersPage {

    private static final String LOCATION = "div.playback-summary-content";
    private static final int MAX_WAIT = 20;

    @FindBy(css = LOCATION)
    private WebElement location;

    @FindBy(css = "input#date1")
    private WebElement pickUpDate;

    @FindBy(css = "input#date2")
    private WebElement dropOffDate;


    public HotelExpediaPage(WebDriver webdriver) {
        super(webdriver);
        new WebDriverWait(webdriver, MAX_WAIT)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector(LOCATION), true));
    }

    @Override
    public boolean checkSearchOptions(String loc, Date pickUp, Date dropOff) {
        String verifyMessage, citySplitFromLocation, startDate, endDate;

        verifyMessage = location.getText();
        citySplitFromLocation = loc.split(",")[0];
        startDate = new SimpleDateFormat("MMM d").format(pickUp);
        endDate = new SimpleDateFormat("MMM d").format(dropOff);

        logger.info("LOCATION TEXT: " + location.getText());
        logger.info("INPUTS: " + loc + " : " + citySplitFromLocation + " : " + startDate + " : " + endDate);
        logger.info("VERIFY MSG: " + verifyMessage);
        return (verifyMessage.contains(loc) || verifyMessage.contains(citySplitFromLocation)) &&
                verifyMessage.contains(startDate) && verifyMessage.contains(endDate);
    }
}
