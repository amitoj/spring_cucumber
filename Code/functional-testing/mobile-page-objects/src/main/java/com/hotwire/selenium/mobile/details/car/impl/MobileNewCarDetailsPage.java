/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.details.car.impl;

import com.hotwire.selenium.mobile.models.CarSolutionModel;
import com.hotwire.selenium.mobile.view.ViewIds;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-jolopez
 * Date: 12/11/14
 */
public class MobileNewCarDetailsPage extends MobileCarDetailsPageTemplate {

    @FindBy(css = "form button[name='carDetailsOpaqueContinue'], form button[name='carDetailsRetailContinue']")
    private WebElement continueSubmit;

    @FindBy(xpath = "//div[@class='hr']")
    private WebElement hotRate;

    @FindBy(xpath = "//div[@class='carTypeName']")
    private WebElement carTypeName;

    @FindBy(xpath = "//ul[@class='tripSummary']/li[1]/div/strong")
    private WebElement pickupLocation;

    @FindBy(xpath = "//ul[@class='tripSummary']/li[2]/div/strong")
    private WebElement pickupDate;

    @FindBy(xpath = "//ul[@class='tripSummary']/li[3]/div/strong")
    private WebElement dropOffDate;

    @FindBy(xpath = "//span[@class='persons']")
    private WebElement seats;

    @FindBy(xpath = "//span[contains(@class,'sign')]")
    private WebElement sign;

    @FindBy(xpath = "//span[contains(@class,'price')]")
    private WebElement amount;

    @FindBy(xpath = "//span[contains(@class,'cents')]")
    private WebElement cents;

    @FindBy(xpath = "//ul[@class='bookingInfo']/li[3]/span[2]")
    private WebElement totalPrice;

    public MobileNewCarDetailsPage(WebDriver driver) {
        super(driver, new String[]{ViewIds.TILE_CAR_DETAILS_OPAQUE, ViewIds.TILE_CAR_DETAILS_RETAIL});
    }


    public MobileNewCarDetailsPage(WebDriver webdriver, String[] tileNames) {
        super(webdriver, tileNames);
    }


    @Override
    public void continueSubmit() {
        continueSubmit.submit();
    }

    @Override
    public void verifyDetailsPageInfo(CarSolutionModel carModel) {
        if (carModel.isOpaque()) {
            assertThat(hotRate.isDisplayed()).as("The hot rate label is not being displayed for opaque cars").isTrue();
        }
        assertThat(carModel.getCarName())
                .as("The model of the car on the details page is not the same as the results page")
                .isEqualToIgnoringCase(carTypeName.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd");
        Date d1;
        Date d2;
        try {
            d2 = sdf.parse((dropOffDate.getText()).replace(",", ""));
            assertThat(sdf.parse(sdf.format(carModel.getEndDate())))
                    .as("The Drop Off date is not correctly displayed on the details page").isEqualTo(d2);
            d1 = sdf.parse((pickupDate.getText()).replace(",", ""));
            assertThat(sdf.parse(sdf.format(carModel.getStartDate())))
                    .as("The Pick Up date is not correctly displayed on the details page").isEqualTo(d1);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        assertThat(pickupLocation.getText()).as("The Location is different than the selected for search")
                .containsIgnoringCase(carModel.getLocation());
        assertThat(carModel.getPerDayPrice()).as("The Price per day is not correctly displayed on the details page")
                .containsIgnoringCase(sign.getText() + amount.getText() + cents.getText());
        /*
        TO-DO : compare results page total price vs "from price" from message that shows that the price has changed.
        Message needs to be implemented on new car details page in order to fix TO DO.
        assertThat(carModel.getTotalPrice()).as("The Total Price is not correctly displayed on the details page")
        .containsIgnoringCase(totalPrice.getText());
        */
    }

}

