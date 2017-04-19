/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.legal;

import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.selenium.mobile.legal.MobileLegalIndexPage;
import com.hotwire.selenium.mobile.legal.MobileLowPriceGuaranteePage;
import com.hotwire.selenium.mobile.legal.MobilePrivacyPolicyPage;
import com.hotwire.selenium.mobile.legal.MobileRulesAndRestrictionsPage;
import com.hotwire.selenium.mobile.legal.MobileTermsOfUsePage;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-abudyak
 * Date: 12/24/13
 */
public class LegalModelMobileWebApp extends LegalModelTemplate {

    @Override
    public void goToLegalPage() {
        new MobileHotwireHomePage(getWebdriverInstance()).clickLegalLink();
    }

    @Override
    public void verifyLegalPage() {
        assertThat(new MobileLegalIndexPage(getWebdriverInstance()).verifyLegalIndexPage())
            .as("Some elements on legal index page doesn't present")
            .isTrue();
    }

    @Override
    public void goToPrivacyPolicyPage() {
        new MobileLegalIndexPage(getWebdriverInstance()).getLnkPrivacyPolicy().click();
    }

    @Override
    public void goToTermsOfUsePage() {
        new MobileLegalIndexPage(getWebdriverInstance()).getLnkTermOfUse().click();
    }

    @Override
    public void goToRulesAndRestrictioinsPage() {
        new MobileLegalIndexPage(getWebdriverInstance()).getLnkRulesAndRestrictions().click();
    }

    @Override
    public void goToLowPriceGuaranteePage() {
        new MobileLegalIndexPage(getWebdriverInstance()).getLnkLowPriceGuarantee().click();
    }

    @Override
    public void clickBackButton() {
        new MobileLegalIndexPage(getWebdriverInstance()).getLnkBack().click();
    }

    @Override
    public void verifyLegalDetailsPage(String pageName) {
        if (pageName.equals("Privacy Policy")) {
            new MobilePrivacyPolicyPage(getWebdriverInstance()).getLnkBack().click();
        }
        if (pageName.equals("Terms of Use")) {
            new MobileTermsOfUsePage(getWebdriverInstance()).getLnkBack().click();
        }
        if (pageName.equals("Rules and Restrictions")) {
            new MobileRulesAndRestrictionsPage(getWebdriverInstance()).getLnkBack().click();
        }
        if (pageName.equals("Low Price Guarantee")) {
            new MobileLowPriceGuaranteePage(getWebdriverInstance()).getLnkBack().click();
        }
    }

    @Override
    public void verifyText(String text, boolean textPresent) {
        super.verifyText(text, textPresent);
    }
}
