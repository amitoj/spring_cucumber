//
//* Copyright 2014 Hotwire. All Rights Reserved.
//*
//* This software is the proprietary information of Hotwire.
//* Use is subject to license terms.
//
package com.hotwire.selenium.desktop.us.billing.onepage;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.billing.SecureCodeVerificationWindow;

/**
 * @author vjong
 */
public class HotelPurchaseReviewFragment extends AbstractPageObject {

    private static final String XPATH_RULES = "//input[contains(@name, 'agreement')]//..//.." +
        "//a[contains(@href, '/hotwire-travel-products-rules-and-restrictions?cc=')]";

    private Boolean agreeWithTerms = true;

    @FindBy(css = ".confirmPaymentCheckbox input[name = 'billingReviewModel\\.agreement']")
    private WebElement agreementChecked;

    @FindBy(css = "button[id = 'confirmPaymentBtn']")
    private WebElement btnPurchase;

    @FindBy(xpath = "//input[contains(@name, 'agreement')]//..//..//a[contains(@href, '/terms-use?cc=')]")
    private WebElement termsOfUse;

    @FindBy(xpath = XPATH_RULES)
    private WebElement productRulesAndRestrictions;

    public HotelPurchaseReviewFragment(WebDriver webDriver) {
        super(webDriver, By.id("tileName-termsAndConditions"));
    }

    public void openTermsOfUse() {
        termsOfUse.click();
    }

    public void openProductRulesAndRestrictions() {
        productRulesAndRestrictions.click();
    }

    public void continuePanel() {
        if (agreeWithTerms && !agreementChecked.isSelected()) {
            agreementChecked.click();
        }
        btnPurchase.click();
        new SecureCodeVerificationWindow(getWebDriver()).proceed();
    }

    public WebElement getBookButton() {
        return btnPurchase;
    }

    public HotelPurchaseReviewFragment agreeWithTerms() {
        if (agreeWithTerms && !agreementChecked.isSelected()) {
            agreementChecked.click();
        }
        return this;
    }

    public HotelPurchaseReviewFragment setAgreeWithTerms(Boolean agreeWithTerms) {
        this.agreeWithTerms = agreeWithTerms;
        return this;
    }
}

