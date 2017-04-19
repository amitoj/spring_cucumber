/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl.ccf;

import com.hotwire.selenium.desktop.us.billing.SecureCodeVerificationWindow;
import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcPurchaseReviewFragment;
import com.hotwire.selenium.desktop.us.models.CarSolutionModel;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-vzyryanov
 * Date: 1/29/13
 * Time: 7:44 AM
 */
public class CcfPurchaseReviewFragment extends AcPurchaseReviewFragment {

    private static final String CSS_AGREE_AND_BOOK_IDENTIFIER = "div#tileName-agreeAndBook";
    private static final String SEARCH_DETAILS_CONTAINER = "//div[@id='tileName-tripSummary']//" +
            "div[contains(@class, 'destDetails')]";
    private static final String CONFIRM_CREDIT_CARD_BTN = "billingConfirm";
    private static final String CONFIRM_PAYPAL_BTN = "goToPayPal";

    /**
     * Redefine xpath for Trip Summary block
     */

   // protected String PER_DAY_PRICE = "//div[contains(@class, 'tripSummary')]//" +
     //       "div[contains(text(), 'Car daily rate:')]//following-sibling::div[contains(@class, 'itemValue')]";
//    protected String TOTAL_PRICE = "//div[contains(@class, 'tripSummary')]//div[contains(text(), 'Subtotal:')]" +
//            "//following-sibling::div[contains(@class, 'itemValue')]";
//    protected String RENTAL_DAYS_COUNT = "//div[contains(@class, 'tripSummary')]" +
//            "//div[contains(text(), 'Rental days:')]//following-sibling::div[contains(@class, 'itemValue')]";
    protected String DAMAGE_PROTECTION = "//div[contains(@class, 'tripSummary')]" +
            "//div[contains(text(), 'Rental Car Damage Protection')]//following-sibling::" +
            "div[contains(@class, 'itemValue')]";
//    protected String ESTIMATED_TAXES_AND_FEES = "//div[contains(@class, 'tripSummary')]" +
//            "//div[contains(text(), 'Taxes and fees:')]//following-sibling::div[contains(@class, 'itemValue')]";
//    protected String CAR_NAME;
//    protected String refNumber = "//div[@id = 'tileName-detailsLiveChat']//span[@id='referenceNumberDetails']";

    @FindBy(id = CONFIRM_CREDIT_CARD_BTN)
    private WebElement billingBtnConfirm;

    @FindBy(id = CONFIRM_PAYPAL_BTN)
    private WebElement billingBtnPayPal;

    {
        agreementCbx = "seleniumDepositTypeTermsAccepted";
        cssRulesAndRegulations = "div#tileName-agreeAndBook div.agreeAndBook div.info ul li";

        pickUpLocation = SEARCH_DETAILS_CONTAINER + "//div[contains(@class, 'sectionTitle')]" +
                "[contains(text(), 'Location')]/following-sibling::ul[contains(@class, 'sectionText')]//li[1]";
        dropOffLocation = "";
        pickUpDate = SEARCH_DETAILS_CONTAINER + "//div[contains(@class, 'sectionTitle')]" +
                "[contains(text(), 'Pick up')]/following-sibling::div[contains(@class, 'sectionText')]";
        dropOffDate = SEARCH_DETAILS_CONTAINER + "//div[contains(@class, 'sectionTitle')]" +
                "[contains(text(), 'Drop off')]/following-sibling::div[contains(@class, 'sectionText')]";
    }


    public CcfPurchaseReviewFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(CSS_AGREE_AND_BOOK_IDENTIFIER));
    }
    /*
     the method getCarOptionsSet in AcPurchaseReviewFragment.java doesn't work for car CCF design because xpaths differ.
     so i will Override it in CcfPurchaseReviewFragment.java step by step,  bshukaylo
     */
    @Override
    public CarSolutionModel getCarOptionsSet() {
        CarSolutionModel carSolutionModel = new CarSolutionModel();

        carSolutionModel.setDamageProtection(getDamageProtection());

        return carSolutionModel;
    }

    @Override
    public Float getDamageProtection() {
        try {
            return Float.parseFloat(
                    getWebDriver().findElement(By.xpath(DAMAGE_PROTECTION))
                            .getText().replaceAll("[^0-9.]", ""));
        }
        catch (NoSuchElementException e) {
            // no action
        }
        return null;
    }

    @Override
    public WebElement getAgreementCheckbox() {
        // It is need for scrolling page
        getWebDriver().findElement(By.cssSelector(CSS_AGREE_AND_BOOK_IDENTIFIER)).click();
        return getWebDriver().findElement(By.className(agreementCbx));
    }

    @Override
    public void book() {
        billingBtnConfirm.click();
        new SecureCodeVerificationWindow(getWebDriver()).proceed();
    }

    @Override
    public void bookPayPal() {
        billingBtnPayPal.click();
    }


}



