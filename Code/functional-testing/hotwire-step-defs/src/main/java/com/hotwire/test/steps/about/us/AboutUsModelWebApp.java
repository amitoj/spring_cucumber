/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.about.us;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hotwire.selenium.desktop.us.GlobalFooter;
import com.hotwire.selenium.desktop.us.global.footer.AboutUsPage;

/**
 * About us steps implementation for Domestic.
 */
public class AboutUsModelWebApp extends AboutUsModelTemplate {
    private static final long DEFAULT_WAIT = 15;

    @Override
    public void openAboutUsPage() {
        new GlobalFooter(getWebdriverInstance()).navigateToAboutUsPage();
    }

    /**
     * Verify the Text on About us page
     */
    @Override
    public void verifyTexts() {
        AboutUsPage aboutUs = new AboutUsPage(getWebdriverInstance());
        aboutUs.getaffiliatesText().isDisplayed();
        aboutUs.getcompanyOverviewText().isDisplayed();
        aboutUs.getcompanyText().isDisplayed();
        aboutUs.gethotelPartners().isDisplayed();
        aboutUs.getpressRoomText().isDisplayed();
        aboutUs.getlegalText().isDisplayed();
    }

    /**
     * Verify Company links
     */
    @Override
    public void verifyCompanyLinks() {
        AboutUsPage aboutUs = new AboutUsPage(getWebdriverInstance());
        // verify company mission link
        aboutUs.getcompanyMissionLink().click();
        WebDriverWait wait = new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT);

        wait.until(ExpectedConditions.visibilityOf(aboutUs.getcompanyOverviewText()));

        // verify leadership team link
        aboutUs.getawardsLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getawardsText()));

        // verify contact us link
        aboutUs.getcontactUsLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getcontactUsText()));
    }

    /**
     * Verify Legal links
     */
    @Override
    public void verifyLegalLinks() {

        WebDriverWait wait = new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT);
        AboutUsPage aboutUs = new AboutUsPage(getWebdriverInstance());
        // verify Terms of use
        aboutUs.gettermsOfUseLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.gettermsOfUseText()));

        // verify privacy policy
        aboutUs.getprivacyPolicyLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getprivacyPolicyText()));

        // verify cookie policy
        aboutUs.getcookiePolicyLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getcookiePolicyText()));

        // verify rules and restrictions
        aboutUs.getruleRestrictionsLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getruleRestrictionsText()));

        // verify low price guarantee
        aboutUs.getlowPriceLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getlowPriceText()));

    }

    /**
     * Verify Hotel Partner Overview Link
     */
    @Override
    public void verifyHotelPartnerOverviewLink() {
        WebDriverWait wait = new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT);
        AboutUsPage aboutUs = new AboutUsPage(getWebdriverInstance());

        // verify hotel partner link
        aboutUs.gethotelPartnerOverviewLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.gethotelPartnerOverviewLinkText()));

    }

    /**
     * Verify Press Releases Link
     */
    @Override
    public void verifyPressReleaseLink() {
        WebDriverWait wait = new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT);
        AboutUsPage aboutUs = new AboutUsPage(getWebdriverInstance());

        aboutUs.getpressRoomOverviewLinks().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getpressRoomOverviewTexts()));

        aboutUs.getpressReleasesLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getpressReleasesText()));

        aboutUs.getnewsAlertsLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getnewsAlertsText()));

    }

    /**
     * Verify Affiliates Link
     */
    @Override
    public void verifyAffiliatesLink() {
        WebDriverWait wait = new WebDriverWait(getWebdriverInstance(), DEFAULT_WAIT);
        AboutUsPage aboutUs = new AboutUsPage(getWebdriverInstance());

        aboutUs.getaffiliatesOverviewLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getaffiliatesOverviewText()));

        aboutUs.getfAQLink().click();
        wait.until(ExpectedConditions.visibilityOf(aboutUs.getfAQText()));

    }

}
