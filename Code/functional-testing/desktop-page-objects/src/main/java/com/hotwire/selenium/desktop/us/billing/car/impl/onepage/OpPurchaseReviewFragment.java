/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.onepage;

import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcPurchaseReviewFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static com.hotwire.selenium.desktop.utils.DesktopPageProviderUtils.getVersionTests;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 7:44 AM
 */
public class OpPurchaseReviewFragment extends AcPurchaseReviewFragment {


    {
        //not changed locators
        rulesAndRestrictions = "div.agreeAndBookWrapper div.agreeAndBook a[href$='/pop-up/terms-popup.jsp#tab3']";
        termOfUse = "div.agreeAndBookWrapper div.agreeAndBook a[href$='/pop-up/terms-popup.jsp#tab2']";
        agreementCbx = "billingForm.agreement";
        btnBook = "div.agreeAndBookWrapper button[type=submit]";

        List<String> activatedVersionTests = getVersionTests(getWebDriver(), "eVar1");
        if (activatedVersionTests.contains("CTS14-02")) {

            //new Trip Summary block locators. 02.10.2014
            //after version testing finished - this locators should be default, and IF statement can be removed
            perDayPrice = ".//*[@id='dailyRate']";
            totalPrice = ".//*[@id='carTotalPrice']";
            rentalDaysCount = ".//*[@id='rentalDays']";
            estimatedTaxesAndFees = ".//*[@id='taxesAndFees']";
            carName = ".//*[contains(@class, 'tripSummaryCarNode')]/h2";
        }
        else {

            //old purchase review fragment locators
            perDayPrice = "//div[@id='priceDetails']//li[contains(text(), 'Car daily rate:')]//following-sibling::li";
            totalPrice = "//div[@id='priceDetails']//ul[contains(@class, 'totalPrice')]" +
                    "//li[contains(text(), 'Trip Total')]//following-sibling::li";
            rentalDaysCount = "//div[@id='priceDetails']//li[contains(text(), 'Rental days:')]//following-sibling::li";
            estimatedTaxesAndFees = "//div[@id='priceDetails']" +
                    "//li[contains(text(), 'axes and fees:')]//following-sibling::li";
            carName = "//div[@id='confirmSection']" +
                    "//div[contains(@class, 'tripDetails')]//div[contains(@class, 'carDetails')]";

        }
    }

    @FindBy(css = "div.agreeAndBookWrapper .rules li")
    List<WebElement> bookingRulesAndRegulations;

    public OpPurchaseReviewFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector("div#confirmSection"));
    }

    @Override
    protected String getCarName() {
        String carName = getWebDriver().findElement(By.xpath(this.carName)).getText();
        String[] carNameContainer = carName.split(" - ");
        return carNameContainer[carNameContainer.length - 1];
    }

    @Override
    public WebElement getAgreementCheckbox() {
        return getWebDriver().findElement(By.name(agreementCbx));
    }

    @Override
    public boolean isPrepaidSolution() {
        return "true".equals(getWebDriver().findElement(By.cssSelector("div.reviewCarDetails"))
                .getAttribute("data-prepaid"));
    }

    @Override
    public void book() {
        super.book();
    }

    @Override
    public List<String> getRulesAndRegulations() {
        List<String> out = new ArrayList();
        for (WebElement elm : bookingRulesAndRegulations) {
            out.add(elm.getText());
        }
        return out;
    }
}
