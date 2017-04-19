/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * User: jreichenberg
 * Date: 6/28/12
 * Time: 1:36 PM
 */
public class MultiVerticalFareFinder extends AbstractPageObject {

    public static final String AIR_RADIO = "li#airList input[type='radio']";
    public static final String CAR_RADIO = "li#carList input[type='radio']";
    public static final String HOTEL_RADIO = "li#hotelList input[type='radio']";

    private static final int WAIT_TIMEOUT = 3;

    private static final By VERTICAL_SELECTOR_CONTAINER = By.xpath("//div[@id='tileName-options']//..");

    @FindBy(xpath = ".//form[contains(@class, 'airFields')]")
    private WebElement airFieldsContainer;

    @FindBy(xpath = ".//form[contains(@class, 'carFields')]")
    private WebElement carFieldsContainer;

    @FindBy(css = "li#cruiseList > input")
    private WebElement cruiseRadio;

    @FindBy(xpath = ".//form[contains(@class, 'cruiseFields')]")
    private WebElement cruiseFieldsContainer;

    @FindBy(css = ".AutoCompleteComp.homeFareFinder.groupedByType, .hotelFields")
    private WebElement hotelFieldsContainer;

    @FindBy(xpath = ".//a[contains(@href,'/activities/index')]")
    private WebElement activitiesTab;

    public MultiVerticalFareFinder(WebDriver webDriver) {
        super(webDriver, VERTICAL_SELECTOR_CONTAINER);
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector("ul li input[type='radio'][name='vertical']");
    }

    public void waitForRadioButtonLocationStable(By radioLocatorValue) {
        new WebDriverWait(getWebDriver(), 10)
            .until(new IsElementLocationStable(getWebDriver(), radioLocatorValue, 5));
    }

    public AirSearchFragment chooseAir() {
        new WebDriverWait(getWebDriver(), WAIT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(AIR_RADIO)));

        getWebDriver().findElement(By.cssSelector(AIR_RADIO)).click();

        return new AirSearchFragment(getWebDriver());
    }

    public CarSearchFragment chooseCar() {
        new WebDriverWait(getWebDriver(), WAIT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(CAR_RADIO)));

        getWebDriver().findElement(By.cssSelector(CAR_RADIO)).click();

        return new CarSearchFragment(getWebDriver());
    }

    public CruiseSearchFragment chooseCruise() {
        cruiseRadio.click();
        return new CruiseSearchFragment(getWebDriver(), cruiseFieldsContainer);
    }

    public HotelSearchFragment chooseHotel() {

        new WebDriverWait(getWebDriver(), WAIT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(HOTEL_RADIO)));

        getWebDriver().findElement(By.cssSelector(HOTEL_RADIO)).click();

        return new HotelSearchFragment(getWebDriver());
    }

    public PackagesSearchFragment chooseACHPackage() {
        return new PackagesSearchFragment(getWebDriver());
    }

    public PackagesSearchFragment chooseAHPackage() {
        return new PackagesSearchFragment(getWebDriver());
    }

    public PackagesSearchFragment chooseHCPackage() {
        return new HCPackagesSearchFragment(getWebDriver());
    }

    public PackagesSearchFragment chooseACPackage() {
        return new PackagesSearchFragment(getWebDriver(),
            getWebDriver().findElement(By.cssSelector("form[name='airIndexForm']")));
    }

    public PackagesSearchFragment getPackagesSearchFragment() {
        return new PackagesSearchFragment(getWebDriver());
    }

    public ActivitiesSearchFragment chooseActivities() {
        activitiesTab.click();
        return new ActivitiesSearchFragment(getWebDriver());
    }

    public void clickVacationPackageRadioButton(String vacationPackage) {
        new PackagesOptionsListFragment(getWebDriver()).selectPackageByType(vacationPackage);
    }

    public WebElement getHotelFieldsContainer() {
        return hotelFieldsContainer;
    }
}
