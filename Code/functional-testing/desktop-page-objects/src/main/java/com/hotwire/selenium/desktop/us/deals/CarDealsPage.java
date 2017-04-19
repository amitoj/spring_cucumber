/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.deals;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.fest.assertions.Assertions.assertThat;

/**
 * CarDealsPage
 */
public class CarDealsPage extends AbstractDealsPage {

    private static final String DISCOUNT_INTL_CARS = ".//a[contains(text(), 'Discount international cars')]";

    @FindBy (xpath = DISCOUNT_INTL_CARS)
    WebElement discountIntlCars;

    @FindBy(xpath = "//h2[@class='subheadline' and contains( text(), 'Hotwire Rental Car Deals')]")
    private WebElement carDealsHeader;

    public CarDealsPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getDiscountIntlCarsLink() {
        return discountIntlCars;
    }

    @Override
    public void verifyPage() {
        assertThat(getWebDriver().getCurrentUrl())
            .as("Car deals page loaded")
            .contains("//car-information//us//index.jsp");
        assertThat(carDealsHeader.isDisplayed()).as("Car deals header is visible ").isTrue();
    }
}
