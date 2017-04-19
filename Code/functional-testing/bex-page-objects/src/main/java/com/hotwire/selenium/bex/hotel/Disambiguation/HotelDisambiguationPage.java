/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.bex.hotel.Disambiguation;

import com.hotwire.selenium.bex.BexAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/10/15
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class HotelDisambiguationPage extends BexAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelDisambiguationPage.class.getSimpleName());

    @FindBy(css = "td.tableData a")
    private WebElement firstLink;


    public HotelDisambiguationPage(WebDriver webdriver) {
        super(webdriver, By.className("pageHeadingDisambigB"));
        PageFactory.initElements(getWebDriver(), this);
    }

    public void selectFirstOption() {
        firstLink.click();
    }
}
