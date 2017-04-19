/*
        * Copyright 2012 Hotwire. All Rights Reserved.
        *
        * This software is the proprietary information of Hotwire.
        * Use is subject to license terms.
        */

package com.hotwire.selenium.desktop.us.billing.car.impl.accordion;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarDetails;
import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * User: v-vzyryanov
 * Date: 2/1/13
 * Time: 6:34 AM
 */
public class AcDetailsFragment extends AbstractPageObject implements CarDetails {

    protected static final String VENDOR_GRID_ELEMENT = "//div[contains(@class, 'rentalAgencyGrid')]" +
            "//div[contains(@class, 'agencyInfo')][contains(@class, 'opacityRetail')]";
    private static final String CSS_BLOCK_IDENTIFIER = "div#tileName-B4 form[name=continueForm], " +
            "div.commonDetailsPage div.layoutColumnRight div#tileName-B3";
    private static final String CSS_VENDOR_GRID_ONE_WAY_ELEMENT = "div.rentalAgencyGrid tr.selectableRow";
    private static final String CSS_VENDOR_GRID_CONTAINER = "div.rentalAgencyGrid";
    private static final String CLASS_VENDOR_GRID_AIRPORT = "hotwireLimitedRate";
    private static final String CSS_REVIEW_CAR_CONTAINER = ".reviewCarDetails .details";
    private static final String CSS_REVIEW_CAR_PRICE_PER_DAY = CSS_REVIEW_CAR_CONTAINER + " .pricePerDay";
    private static final String CSS_REVIEW_CAR_TOTAL_PRICE = CSS_REVIEW_CAR_CONTAINER + " .priceTotal";
    private static final String CSS_REVIEW_CAR_VENDOR = ".reviewCarDetails .vendor img";
    private static final String CSS_VENDOR_MAP = "#carMap-map";
    private static final String CSS_MAP_OPENER = ".hoursAndMap label.opener a";


    @FindBy(css = "div[id^=tileName-B] form[name^=continueForm] button[type=submit]")
    private WebElement continueBtn;

    public AcDetailsFragment(WebDriver webDriver) {
        this(webDriver, By.cssSelector(CSS_BLOCK_IDENTIFIER));
    }

    protected AcDetailsFragment(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator, new String[]{CarBillingPageProvider.TILES});
    }

    @Override
    public List<String[]> getVendorGridEntities() {

        List<String[]> out = new ArrayList<String[]>();
        try {
            getWebDriver().findElement(By.cssSelector(CSS_VENDOR_GRID_CONTAINER)).isDisplayed();

            for (WebElement elm : getVendorGridElements()) {
                out.add(getVendorAsArray(elm));
            }
        }
        catch (NoSuchElementException ex) {
           //Only 1 vendor for retail.
        }
        return out;
    }

    /**
     * Resolving type of vendor grid
     */
    private boolean isOneWayVendorGrid() {
        return getWebDriver().findElement(By.cssSelector(CSS_VENDOR_GRID_CONTAINER))
                .getAttribute("class").contains("oneWay");
    }

    private List<WebElement> getVendorGridElements() {
        return isOneWayVendorGrid() ? getWebDriver().findElements(By.cssSelector(CSS_VENDOR_GRID_ONE_WAY_ELEMENT)) :
                getWebDriver().findElements(By.xpath(VENDOR_GRID_ELEMENT));
    }

    private String[] getVendorAsArray(WebElement vendorRow) {
        return new String[]{
                vendorRow.findElement(By.cssSelector("div.agencyIco img, td.agncCol img")).getAttribute("alt"),
                vendorRow.findElement(By.cssSelector("div.dailyRate strong, td strong span strong")).getText().
                        replaceAll("[^0-9.]", ""),
                vendorRow.findElement(By.cssSelector("div.totalPrice strong, td span.price")).getText().
                        replaceAll("[^0-9.]", ""),
                vendorRow.findElement(By.cssSelector("div.dailyRate strong, td span.price")).getText().
                        replaceAll("[0-9.,]", "").trim(),
                vendorRow.findElement(By.cssSelector("div.location span, .bdd-location")).getText().
                        replaceAll("[0-9.,]", "").trim()
        };
    }

    @Override
    public void selectVendorEntityBy(String vendor, Float perDayPrice, Float totalPrice) {

        for (WebElement elm : getVendorGridElements()) {
            String[] row = getVendorAsArray(elm);
            if (row[0].equals(vendor) && Float.parseFloat(row[1]) == perDayPrice && Float.parseFloat(row[2]) ==
                    totalPrice) {
                elm.findElement(By.cssSelector("input[type='radio']")).click();
                new Wait<Boolean>(new IsAjaxDone()).maxWait(40).apply(getWebDriver());
                return;
            }
        }
    }

    @Override
    public String[] getSelectedVendorGridEntity() {
        return getVendorAsArray(new WebDriverWait(getWebDriver(), 5).until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("#rentalAgencyGrid .selected"))));
    }

    @Override
    public void continuePanel() {
        continueBtn.click();
    }

    @Override
    public void goBack() {
        throw new NoSuchElementException("Can't go back to details page. Not implemented yet.");
    }

    @Override
    public CarSolutionModel getCarOptionsSet() {
        CarSolutionModel sm = new CarSolutionModel();
        sm.setPerDayPrice(getPerDayPrice());
        sm.setTotalPrice(getTotalPrice());
        sm.setCurrency(getCurrency());
        sm.setVendor(getVendor());
        return sm;
    }

    private Float getPerDayPrice() {
        String text = getWebDriver().findElement(By.cssSelector(CSS_REVIEW_CAR_PRICE_PER_DAY)).getText();
        String priceText;
        if (text.contains("†")) {
            String[] split = text.split("†");
            priceText = split[split.length - 1].trim();
        }
        else {
            priceText = text;
        }
        return Float.parseFloat(priceText.replaceAll("[^0-9.]", ""));
    }

    private Float getTotalPrice() {
        return Float.parseFloat(getWebDriver().findElement(By.cssSelector(CSS_REVIEW_CAR_TOTAL_PRICE))
                .getText().replaceAll("[^0-9.]", ""));
    }

    private String getVendor() {
        return getWebDriver().findElement(By.cssSelector(CSS_REVIEW_CAR_VENDOR)).getAttribute("alt");
    }

    private String getCurrency() {
        return String.valueOf(getWebDriver().findElement(By.cssSelector(CSS_REVIEW_CAR_PRICE_PER_DAY))
                                  .getText().trim().charAt(0));
    }

    @Override
    public List<String> getCarFeatureList() {
        throw new NoSuchElementException("Can't get car details from page. Not implemented yet.");
    }

    @Override
    public boolean localSearchMapIsDisplayed() {
        getWebDriver().findElement(By.cssSelector(CSS_MAP_OPENER)).click();
        return getWebDriver().findElement(By.cssSelector(CSS_VENDOR_MAP)).isDisplayed();
    }

    @Override
    public void clickOnVendorAddressLink() {
        throw new NoSuchElementException("Can't get car details from page. Not implemented yet.");
    }


    @Override
    public void closeVendorMap() {
        throw new NoSuchElementException("Can't get car details from page. Not implemented yet.");
    }

    @Override
    public int getVendorMapNum() {
        throw new NoSuchElementException("Can't get car details from page. Not implemented yet.");
    }

    @Override
    public boolean airportResultVendorGridIsDisplayed() {
        return getWebDriver().findElement(By.className(CLASS_VENDOR_GRID_AIRPORT)).isDisplayed();
    }

    @Override
    public void priceCheckAfterContinue() {
        throw new NoSuchElementException("Can't do price check. Not implemented yet.");
    }

    @Override
    public List<String> getFootNoteList() {
        throw new NoSuchElementException("Can't do price check. Not implemented yet.");
    }
}



