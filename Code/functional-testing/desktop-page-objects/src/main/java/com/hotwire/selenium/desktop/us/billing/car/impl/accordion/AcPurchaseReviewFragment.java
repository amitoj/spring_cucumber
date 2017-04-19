/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl.accordion;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.billing.SecureCodeVerificationWindow;
import com.hotwire.selenium.desktop.us.billing.car.impl.CarPurchaseReview;
import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import com.hotwire.util.webdriver.functions.IsElementLocationStable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 7:43 AM
 */
public class AcPurchaseReviewFragment extends AbstractUSPage implements CarPurchaseReview {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcPurchaseReviewFragment.class);

    protected String rulesAndRestrictions = "div#termsOfUse a[href$='hotwire.com/pop-up/terms-popup.jsp#tab3']";
    protected String termOfUse = "div#termsOfUse a[href$='hotwire.com/pop-up/terms-popup.jsp#tab2']";
    protected String agreementCbx = "div#termsOfUse input#agreementChecked";
    protected String btnBook = "div#termsOfUse input.continueBtn";
    protected String cssRulesAndRegulations = "";

    /**
     * Review your car details block
     */
    protected String perDayPrice = "//div[contains(@class, 'totalCostModule')]" +
            "//label[contains(text(), 'Car daily rate:')]//following-sibling::div[contains(@class, 'value')]";
    protected String totalPrice = "//span[contains(text(), 'silkCarTotalPrice:')]//..";
    protected String rentalDaysCount = "//div[contains(@class, 'totalCostModule')]" +
            "//label[contains(text(), 'Rental days:')]//following-sibling::div[contains(@class, 'value')]";
    protected String damageProtection =
        "//div[@id='priceDetails']//li[contains(text(), 'Protection:') or contains(text(), 'Protection :')]" +
        "//following-sibling::li";
    protected String estimatedTaxesAndFees = "//div[contains(@class, 'totalCostModule')]" +
            "//label[contains(text(),'axes and fees:')]//following-sibling::div[contains(@class, 'value')]";
    protected String carName = "//div[contains(@class, 'grayBoxBody')]" +
            "//div[contains(@class, 'details')]//strong[contains(text(), 'car')]";
    protected String vendorName = "//div[contains(@class,'selectableRow agencyInfo " +
            "selected hasOpacityCode opacityRetail')]//div[contains(@class,'agencyName')]//span";
    protected String distance = "//div[@class='detailsInfoPlates']//div[@class='infoPlateWrapper']" +
            "//span[@class='infoPlateContent']";

    /**
     * "Guaranteed car from" section
     * (available for opaque cars only)
     */
    protected String imgWithLogos = "//div[@class='agencyLogos']" +
            "//img[contains(@src,'LimitedHotwireRateComp/vendor_logos')]";
    protected String guarantees = "//div[@class='agencyLogos']//UL/LI";


    /**
     * Search options
     */
    protected String pickUpLocation = "(//div[@class='mb20'])[1]";
    protected String dropOffLocation = "(//div[@class='mb20'])[2]";
    protected String pickUpDate = "(//div[@class='mb20'])[1]";
    protected String dropOffDate = "(//div[@class='mb20'])[2]";

    private WebElement payPalBtn;

    public AcPurchaseReviewFragment(WebDriver webDriver) {
        super(webDriver, By.id("billingPanelReviewAndPurchaseComp-contentElementRefreshable"));
    }

    protected AcPurchaseReviewFragment(WebDriver webDriver, By containerLocator) {
        super(webDriver, containerLocator);
    }

    @Override
    public CarSolutionModel getCarOptionsSet() {
        CarSolutionModel carSolutionModel = new CarSolutionModel();

        carSolutionModel.setPerDayPrice(getPerDayPrice());
        carSolutionModel.setTotalPrice(getTotalPrice());
        carSolutionModel.setCurrency(getCurrency());
        carSolutionModel.setCarName(getCarName());
        carSolutionModel.setTaxesAndFees(getTaxesAndFees());
        carSolutionModel.setRentalDaysCount(getRentalDaysCount());
        carSolutionModel.setDamageProtection(getDamageProtection());
        carSolutionModel.setVendor(getVendorName());
        carSolutionModel.setDistance(getDistance());

        return carSolutionModel;
    }

    protected String getVendorName() {
        try {
            return getWebDriver().findElement(By.xpath(vendorName)).getText();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    protected String getDistance() {
        try {
            return getWebDriver().findElement(By.xpath(distance)).getText();
        }
        catch (NoSuchElementException e) {
            return null;
        }
    }

    protected float getPerDayPrice() {
        return Float.parseFloat(
                getWebDriver().findElement(By.xpath(perDayPrice))
                        .getText().replaceAll("[^0-9.]", ""));
    }

    protected float getTotalPrice() {
        return Float.parseFloat(
                getWebDriver().findElement(By.xpath(totalPrice))
                        .getText().replaceAll("[^0-9.]", ""));
    }

    protected String getCurrency() {
        String perDayCurrency = getWebDriver().findElement(By.xpath(perDayPrice)).getText()
                .replaceAll("[0-9.,]", "").replaceAll("^[a-zA-Z]+:", "");
        String totalCurrency = getWebDriver().findElement(By.xpath(totalPrice)).getText()
                .replaceAll("[0-9.,]", "").replaceAll("^[a-zA-Z]+:", "");
        LOGGER.info("TOTAL CURRENCY: " + totalCurrency + "     DAY CURRENCY: " + perDayCurrency);
        if (perDayCurrency.equals(totalCurrency) && !perDayCurrency.isEmpty()) {
            return perDayCurrency;
        }
        return null;
    }

    protected int getRentalDaysCount() {
        return Integer.parseInt(
                getWebDriver().findElement(By.xpath(rentalDaysCount)).getText());
    }

    protected float getTaxesAndFees() {
        return Float.parseFloat(
                getWebDriver().findElement(By.xpath(estimatedTaxesAndFees))
                        .getText().replaceAll("[^0-9.]", ""));
    }

    protected Float getDamageProtection() {
        try {
            String damageProtectionText = getWebDriver().findElement(By.xpath(damageProtection)).getText();
            LOGGER.info("BILLING RENTAL CAR DAMAGE TEXT: " + damageProtectionText);
            return new Float(damageProtectionText.replaceAll("[^\\d.]", ""));
        }
        catch (NoSuchElementException e) {
            LOGGER.info("NoSuchElementException occurred parsing damage protection text.");
        }
        return null;
    }

    protected String getCarName() {
        return getWebDriver().findElement(By.xpath(carName)).getText();
    }

    public WebElement getTermsOfUse() {
        return getWebDriver().findElement(By.cssSelector(termOfUse));
    }

    public WebElement getRulesAndRestrictions() {
        return getWebDriver().findElement(By.cssSelector(rulesAndRestrictions));
    }

    public boolean isImgWithLogosDisplayed() {
        try {
            return getWebDriver().findElement(By.xpath(imgWithLogos)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<String> getGuarantees() {
        List<WebElement> webElementList = getWebDriver().findElements(By.xpath(guarantees));
        List<String> out = new ArrayList();
        for (WebElement elm : webElementList) {
            out.add(elm.getText());
        }
        return out;
    }

    @Override
    public List<String> getRulesAndRegulations() {
        List<WebElement> rules = getWebDriver().findElements(By.cssSelector(cssRulesAndRegulations));
        List<String> out = new ArrayList();
        for (WebElement elm : rules) {
            out.add(elm.getText());
        }
        return out;
    }

    public WebElement getAgreementCheckbox() {
        return getWebDriver().findElement(By.cssSelector(agreementCbx));
    }

    public WebElement getBookBtn() {
        return getWebDriver().findElement(By.cssSelector(btnBook));
    }

    @Override
    public void readTerms() {
        new WebDriverWait(getWebDriver(), 10)
            .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(termOfUse), 5));
        getTermsOfUse().click();
    }

    @Override
    public void readRules() {
        new WebDriverWait(getWebDriver(), 10)
            .until(new IsElementLocationStable(getWebDriver(), By.cssSelector(rulesAndRestrictions), 5));
        getRulesAndRestrictions().click();
    }

    @Override
    public void confirmBookingAgreements(boolean confirm) {
        if (confirm != getAgreementCheckbox().isSelected()) {
            getAgreementCheckbox().click();
        }
    }

    @Override
    public boolean isPrepaidSolution() {
        return false;
    }

    @Override
    public void book() {
        getBookBtn().click();
        new SecureCodeVerificationWindow(getWebDriver()).proceed();
    }


    @Override
    public void bookPayPal() {
        getPayPalBtn().click();
    }

    @Override
    public String getPickUpLocation() {
        return getWebDriver().findElement(By.xpath(pickUpLocation)).getText().split("\n")[1];
    }

    @Override
    public String getDropOffLocation() {
        return getWebDriver().findElement(By.xpath(dropOffLocation)).getText().split("\n")[1];
    }

    @Override
    public String getPickUpDate() {
        return getWebDriver().findElement(By.xpath(pickUpDate)).getText().split("\n")[0];
    }

    @Override
    public String getDropOffDate() {
        return getWebDriver().findElement(By.xpath(dropOffDate)).getText().split("\n")[0];
    }

    public WebElement getPayPalBtn() {
        return getWebDriver().findElement(By.className("btnP"));
    }

}


