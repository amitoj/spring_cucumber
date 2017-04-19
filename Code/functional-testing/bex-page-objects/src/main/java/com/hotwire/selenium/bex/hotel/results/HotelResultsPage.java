/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.bex.hotel.results;


import com.hotwire.selenium.bex.BexAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;


/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/9/15
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class HotelResultsPage extends BexAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelResultsPage.class.getSimpleName());

    @FindBy(id = "resultsContainer")
    private WebElement results;

    @FindBy(xpath = "//*[@id='inpStartDate']")
    private WebElement startDateField;

    @FindBy(xpath = "//*[@id='inpEndDate']")
    private WebElement endDateField;


    public HotelResultsPage(WebDriver webdriver) {
        super(webdriver, By.id("resultsContainer"));
        PageFactory.initElements(getWebDriver(), this);

    }

    public void selectRandomHotel() {
        List<WebElement> resultsList = results.findElements(By.xpath("//div[@class='hotelWrapper']"));
        Random rn = new Random();
        //WebElement result=resultsList.get(rn.nextInt(resultsList.size()) + 1);
        WebElement result = resultsList.get(1);
        result.findElement(By.xpath("//span[@class='hotelName']")).click();
    }



}
