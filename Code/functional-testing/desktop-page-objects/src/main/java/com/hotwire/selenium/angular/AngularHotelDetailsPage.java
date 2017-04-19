/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.angular;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.hotwire.util.webdriver.functions.AllExpectedConditions;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPage;
import com.hotwire.util.webdriver.po.PageObjectUtils;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * @author vjong
 *
 */
public class AngularHotelDetailsPage extends AbstractPage {
    private static final String GOOGLE_MAPS_CONTENT = "a[href*='https://maps.google.com']";
    private static final String BOOK_BUTTON = "a[data-bdd='bookingUrl'] button";
    private static final String PRICE_PER_NIGHT = "div[data-bdd='displayPrice']";
    private static final String AMENITIES_LIST = "ul.amenities";
    private static final String ACCESSIBILITY_LIST = "ul.accessibilityList";

    @FindBy(css = GOOGLE_MAPS_CONTENT)
    private WebElement googleMapsContent;

    @FindBy(css = AMENITIES_LIST)
    private WebElement amenitiesList;

    @FindBy(css = ACCESSIBILITY_LIST)
    private WebElement accessibilityList;

    @FindBy(css = "div[data-bdd='olab-feedback']")
    private WebElement olabLink;

    @SuppressWarnings("unchecked")
    public AngularHotelDetailsPage(WebDriver webdriver) {
        super(webdriver, new AllExpectedConditions(new ExpectedCondition[] {
            new Wait<Boolean>(new VisibilityOf(By.cssSelector(".hotel-summary .hotel-info"))).maxWait(20),
            new Wait<Boolean>(new VisibilityOf(By.cssSelector(".hotel-summary .price-summary"))).maxWait(20)}));
    }

    public boolean isMapLoaded() {
        try {
            new WebDriverWait(getWebDriver(), 25 * 2).until(PageObjectUtils.webElementVisibleTestFunction(
                googleMapsContent, true));
            return true;
        }
        catch (TimeoutException e) {
            return false;
        }
    }

    public void bookHotel() {
        new WebDriverWait(getWebDriver(), 5)
            .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(BOOK_BUTTON), 4));
        getWebDriver().findElement(By.cssSelector(BOOK_BUTTON)).click();
    }

    public String getDetailsAmenitiesListAsString() {
        List<String> list = new ArrayList<String>();
        for (WebElement element : amenitiesList.findElements(By.cssSelector("li"))) {
            list.add(element.getText().trim());
        }
        return StringUtils.join(list, ", ").trim();
    }

    public String getDetailsAccessibilityListAsString() {
        List<String> list = new ArrayList<String>();
        for (WebElement element : accessibilityList.findElements(By.cssSelector("li"))) {
            list.add(element.getText().trim());
        }
        return StringUtils.join(list, ", ").trim();
    }

    public String getHotelName() {
        return getWebDriver().findElement(By.cssSelector("h2[data-bdd='hotelName']")).getText().trim();
    }

    public String getPricePerNight() {
        new WebDriverWait(getWebDriver(), 5)
            .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(PRICE_PER_NIGHT), 4));
        return getWebDriver().findElement(By.cssSelector(PRICE_PER_NIGHT)).getText();
    }

    public String getCustomerCarePhoneNumber() {
        return getWebDriver().findElement(By.cssSelector("span[data-bdd='phoneNumber']")).getText().trim();
    }

    public String getCustomerCareRefNumber() {
        return getWebDriver().findElement(By.cssSelector("span[data-bdd='referenceNumber']")).getText().trim();
    }

    public AngularHotelResultsPage clickBackToResultsLink() {
        getWebDriver().findElement(By.cssSelector("a.goback")).click();
        return new AngularHotelResultsPage(getWebDriver());
    }

    public void clickOlabLink() {
        olabLink.click();
    }
}
