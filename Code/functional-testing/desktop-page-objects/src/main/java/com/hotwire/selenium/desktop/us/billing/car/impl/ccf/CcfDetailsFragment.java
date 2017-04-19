/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl.ccf;

import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.hotwire.util.webdriver.ui.WebDriverWait;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarDetails;
import com.hotwire.testing.ZeroResultsTestException;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import com.hotwire.util.webdriver.po.PageObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 2/1/13
 * Time: 6:35 AM
 */
public class CcfDetailsFragment extends AbstractUSPage implements CarDetails {
    private static final int MAX_ITERATIONS = 5;
    private static final String CSS_BLOCK_IDENTIFIER = "div.spa-rightCol div#tileName-R1 div.carDetails";

    private static final String CSS_VENDOR_GRID_ELEMENT = "div#tileName-vendorGrid .carVendorGrid a.vendor";

    /**
     * CAR DETAILS
     */
    private static final String CSS_PER_DAY_PRICE = "div#carDetails .priceDetails .singleCurrency  .amount";
    private static final String CSS_TOTAL_PRICE = "div#carDetails .priceDetails .total";
    private static final String CSS_VENDOR = "div#carDetails .carImage img";
    private static final String CSS_CURRENCY = "div#carDetails .priceDetails .singleCurrency  .amount" +
            " span.singleCurrency";

    private static final String CSS_BACK_TO_DETAILS = "div#tileName-vendorGrid .changable a span.btn";

    private static final long DEFAULT_WAIT = 10;

    /**
     * Live chat & Reference number section
     */
    private static final String CSS_REF_NUMBER = "div#tileName-detailsLiveChat span#referenceNumberDetails";
    private static final String CSS_PHONE_NUM = "div#tileName-detailsLiveChat span.phone";
    private static final String CSS_LIVE_CHAT = "div#tileName-detailsLiveChat a";

    private static final String CSS_CAR_FEATURES_LIST = "div .featureList li";

    private static final String CSS_VENDOR_MAP = "div .vendorMap";
    private static final String CSS_VENDOR_ADDRESS_LINK = "div .address";
    private static final String XPATH_CLOSE_VENDOR_MAP = "//img[@alt='Close']";
    private static final String CLASS_VENDOR_GRID_AIRPORT = "seleniumHotRate";


    @FindBy(xpath = "//div[@id='tileName-vendorGrid']//a[contains(@class, 'vendor  selected')]")
    private WebElement selectedVendor;

    @FindBy(xpath = "//a[contains(@class, 'opacityCode-O')]")
    private WebElement continueBtn;

    public CcfDetailsFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(CSS_BLOCK_IDENTIFIER));
    }

    public String getReferenceNumber() {
        return getWebDriver().findElement(By.cssSelector(CSS_REF_NUMBER)).getText();
    }

    public String getPhoneNumber() {
        return getWebDriver().findElement(By.cssSelector(CSS_PHONE_NUM)).getText();
    }

    public void openLiveChat() {
        getWebDriver().findElement(By.cssSelector(CSS_LIVE_CHAT)).click();
    }

    private String[] getVendorAsArray(WebElement vendorRow) {
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
            .until(ExpectedConditions.visibilityOf(vendorRow));
        return new String[]{
                vendorRow.findElement(By.cssSelector("div.logo img")).getAttribute("alt"),
                vendorRow.findElement(By.cssSelector("div.price .perDay")).getText().replaceAll("[^0-9.]", ""),
                vendorRow.findElement(By.cssSelector("div.price .total")).getText().replaceAll("[^0-9.]", ""),
                vendorRow.findElement(By.cssSelector("div.price .perDay .amount .singleCurrency, div.price .perDay" +
                        " .amount .multiCurrency")).getText().trim(),
        };
    }

    @Override
    public List<String[]> getVendorGridEntities() {
        // Animated. Make sure web element of grid is stable in its location.
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(".carVendorGrid"), MAX_ITERATIONS));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(".carDetails .btn"), MAX_ITERATIONS));

        List<String[]> out = new ArrayList<String[]>();
        for (WebElement elm : getVendorGridElements()) {
            out.add(getVendorAsArray(elm));
        }

        return out;
    }

    @Override
    public void selectVendorEntityBy(String vendor, Float perDayPrice, Float totalPrice) {
        // Animated. Make sure web element of grid is stable in its location.
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(".carVendorGrid"), MAX_ITERATIONS));
        new WebDriverWait(getWebDriver(), DEFAULT_WAIT)
                .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(".carDetails .btn"), MAX_ITERATIONS));
        for (WebElement elm : getVendorGridElements()) {
            String[] row = getVendorAsArray(elm);
            if (row[0].equals(vendor) && Float.parseFloat(row[1]) == perDayPrice && Float.parseFloat(row[2]) ==
                    totalPrice) {
                elm.click();
                return;
            }
        }
    }

    @Override
    public String[] getSelectedVendorGridEntity() {
        return getVendorAsArray(new WebDriverWait(getWebDriver(), MAX_SEARCH_PAGE_WAIT_SECONDS)
            .until(ExpectedConditions
                .presenceOfElementLocated(
                    By.xpath("//div[@id='tileName-vendorGrid']//a[contains(@class, 'vendor  selected')]"))));
    }

    @Override
    public void continuePanel() {
        continueBtn.click();
        // Would really love to have a locator value that hints at car selected unavailable so we don't have to use
        // page source text.
        By by = By.id("carSpaRightColUpdatingLayer-panel_mask");

        waitForMaskingLayerToClose(by, by, MAX_SEARCH_PAGE_WAIT_SECONDS, false);
        if ((getWebDriver().findElements(By.cssSelector(".priceCheckMsg, .soldOut")).size() > 0 &&
            getWebDriver().findElement(By.cssSelector(".priceCheckMsg, .soldOut")).getText()
                .contains("The rental agency you selected is sold out")) ||
            (getWebDriver().findElements(By.cssSelector(".carDetails .body")).size() > 0 &&
            getWebDriver().findElement(By.cssSelector(".carDetails .body")).getText()
                .contains("The car you selected is unavailable"))) {
            throw new ZeroResultsTestException("Selected car solution is unavailable.");
        }
        new WebDriverWait(getWebDriver(), MAX_SEARCH_PAGE_WAIT_SECONDS)
                .until(PageObjectUtils.webElementVisibleTestFunction(
                        By.cssSelector("div#tileName-driverInfo"), true));
    }

    @Override
    public void priceCheckAfterContinue() {
        continueBtn.click();
        new WebDriverWait(getWebDriver(), 10)
                .until(PageObjectUtils.webElementVisibleTestFunction(By.cssSelector("div.priceCheckMsg"), true));
    }

    @Override
    public void goBack() {
        getWebDriver().findElement(By.cssSelector(CSS_BACK_TO_DETAILS)).click();
    }

    @Override
    public List<String> getCarFeatureList() {
        List<WebElement>  carFeatureWebElements = getWebDriver().findElements(By.cssSelector(CSS_CAR_FEATURES_LIST));
        List<String> resultValues = new ArrayList<String>();
        for (WebElement e:carFeatureWebElements) {
            resultValues.add(e.getText());
        }
        return resultValues;
    }


    @Override
    public boolean localSearchMapIsDisplayed() {
        return getWebDriver().findElement(By.cssSelector(CSS_VENDOR_MAP)).isDisplayed();
    }

    @Override
    public boolean airportResultVendorGridIsDisplayed() {
        return getWebDriver().findElement(By.className(CLASS_VENDOR_GRID_AIRPORT)).isDisplayed();
    }

    @Override
    public List<String> getFootNoteList() {
        throw new NoSuchElementException("Can't get car details from page. Not implemented yet.");
    }

    @Override
    public void clickOnVendorAddressLink() {
        getWebDriver().findElement(By.cssSelector(CSS_VENDOR_ADDRESS_LINK)).click();
    }


    @Override
    public void closeVendorMap() {
        getWebDriver().findElement(By.xpath(XPATH_CLOSE_VENDOR_MAP)).click();
    }

    @Override
    public int getVendorMapNum() {
        List<WebElement> vendorMaps = getWebDriver().findElements(By.cssSelector(CSS_VENDOR_MAP));
        //Check how many maps displayed on vendor grid simultaneously
        //mapNum  - is number of displayed maps
        int mapNum = 0;
        for (WebElement vendorMap:vendorMaps) {
            if (vendorMap.isDisplayed()) {
                mapNum++;
            }
        }
        return mapNum;
    }

    @Override
    public CarSolutionModel getCarOptionsSet() {
        CarSolutionModel sm = new CarSolutionModel();
        /**
         * Wait while block will be loaded
         */
        new WebDriverWait(getWebDriver(), MAX_SEARCH_PAGE_WAIT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_VENDOR)));

        sm.setPerDayPrice(getPerDayPrice());
        sm.setTotalPrice(getTotalPrice());
        sm.setCurrency(getCurrency());
        sm.setVendor(getVendor());
        return sm;
    }


    // GETTERS AND SETTERS
    private Float getPerDayPrice() {
        return Float.parseFloat(
                getWebDriver().findElement(By.cssSelector(CSS_PER_DAY_PRICE))
                        .getText().replaceAll("[^0-9.]", ""));
    }

    private Float getTotalPrice() {
        return Float.parseFloat(
                getWebDriver().findElement(By.cssSelector(CSS_TOTAL_PRICE))
                        .getText().replaceAll("[^0-9.]", ""));
    }

    private String getVendor() {
        return getWebDriver().findElement(By.cssSelector(CSS_VENDOR)).getAttribute("alt");
    }

    private String getCurrency() {
        return getWebDriver().findElement(By.cssSelector(CSS_CURRENCY)).getText().trim();
    }

    private List<WebElement> getVendorGridElements() {
        return getWebDriver().findElements(By.cssSelector(CSS_VENDOR_GRID_ELEMENT));
    }

    public boolean isLiveChatDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(CSS_LIVE_CHAT)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
