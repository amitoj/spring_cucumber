/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.legal;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * User: v-abudyak
 * Date: 12/24/13
 * Time: 7:31 AM
 */
public class MobileLegalIndexPage extends MobileAbstractPage {



    @FindBy(xpath = "//span[contains(text(), 'Privacy Policy')]//..")
    private WebElement lnkPrivacyPolicy;

    @FindBy(xpath = "//span[contains(text(), 'Terms of Use')]//..")
    private WebElement lnkTermOfUse;

    @FindBy(xpath = "//span[contains(text(), 'Product Rules & Restrictions')]//..")
    private WebElement lnkRulesAndRestrictions;

    @FindBy(xpath = "//span[contains(text(), 'Low-Price Guarantee')]//..")
    private WebElement lnkLowPriceGuarantee;

    @FindBy(xpath = "//img[contains(@alt, 'Back')]")
    private WebElement lnkBack;

    public MobileLegalIndexPage(WebDriver webDriver) {
        super(webDriver, "tile.legal");
    }

    public boolean verifyLegalIndexPage() {
        if (lnkPrivacyPolicy.isDisplayed() && lnkTermOfUse.isDisplayed() &&
            lnkLowPriceGuarantee.isDisplayed() && lnkPrivacyPolicy.isDisplayed()) {
            return true;
        }
        return false;
    }

    public WebElement getLnkPrivacyPolicy() {
        return lnkPrivacyPolicy;
    }

    public WebElement getLnkTermOfUse() {
        return lnkTermOfUse;
    }

    public WebElement getLnkRulesAndRestrictions() {
        return lnkRulesAndRestrictions;
    }

    public WebElement getLnkLowPriceGuarantee() {
        return lnkLowPriceGuarantee;
    }

    public WebElement getLnkBack() {
        return lnkBack;
    }
}
