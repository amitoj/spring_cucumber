/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.global.footer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

/**
 * About Us page
 */
public class AboutUsPage extends AbstractUSPage {

    @FindBy(css = "#block-menu-menu-company>h2")
    private WebElement companyText;

    @FindBy(xpath = "//a[contains(@href, '/helpcenter/index.jsp')][2]")
    private WebElement helpCenterLink;

    @FindBy(css = "#block-menu-menu-press-room>h2")
    private WebElement pressRoomText;

    @FindBy(css = "#block-menu-menu-suppliers>h2")
    private WebElement hotelPartners;

    @FindBy(css = "#block-menu-menu-affiliates>h2")
    private WebElement affiliatesText;

    @FindBy(css = "#block-menu-menu-legal>h2")
    private WebElement legalText;

    // company overview text
    @FindBy(css = "#block-menu-menu-company div ul li:nth-child(1)")
    private WebElement companyOverviewText;

    // company mission selector
    @FindBy(css = "#block-menu-menu-company div ul li:nth-child(1)")
    private WebElement companyMissionLink;

    // leadership team selector
    @FindBy(css = "#block-menu-menu-company div ul li:nth-child(2)")
    private WebElement leadershipTeamLink;

    // awards selector
    @FindBy(xpath = "//div[@id='block-menu-menu-company']/div/ul/li[2]/a")
    private WebElement awardsLink;

    // awards text
    @FindBy(xpath = "//h2[text()='Awards']")
    private WebElement awardsText;

    // contact us link
    @FindBy(xpath = "//div[@id='block-menu-menu-company']/div/ul/li[3]/a")
    private WebElement contactUsLink;

    // contact us text
    @FindBy(xpath = "//h2[text()='Contact us']")
    private WebElement contactUsText;

    /**
     * Legal links
     */
    // terms of use link
    @FindBy(xpath = "//div[@id='block-menu-menu-legal']/div/ul/li[1]/a")
    private WebElement termsOfUseLink;

    @FindBy(xpath = "//strong[text()='Hotwire Website Terms of Use']")
    private WebElement termsOfUseText;

    @FindBy(xpath = "//div[@id='block-menu-menu-legal']/div/ul/li[2]/a")
    private WebElement privacyPolicyLink;

    @FindBy(xpath = "//h2[text()='Privacy Policy']")
    private WebElement privacyPolicyText;
    @FindBy(xpath = "//div[@id='block-menu-menu-legal']/div/ul/li[3]/a")
    private WebElement cookiePolicyLink;

    @FindBy(xpath = "//h2[text()='Cookie Policy']")
    private WebElement cookiePolicyText;

    @FindBy(xpath = "//div[@id='block-menu-menu-legal']/div/ul/li[4]/a")
    private WebElement ruleRestrictionsLink;

    @FindBy(xpath = "//h2[text()='Hotwire Travel Products Rules and Restrictions']")
    private WebElement ruleRestrictionsText;

    @FindBy(xpath = "//div[@id='block-menu-menu-legal']/div/ul/li[5]/a")
    private WebElement lowPriceLink;

    @FindBy(xpath = "//h2[text()='Low price guarantee']")
    private WebElement lowPriceText;

    @FindBy(xpath = "//div[@id='block-menu-menu-suppliers']/div/ul/li/a")
    private WebElement hotelPartnerOverviewLink;

    @FindBy(xpath = "//h3[text()='Become a Hotwire Hotel Partner']")
    private WebElement hotelPartnerOverviewText;

    @FindBy(xpath = "//div[@id='block-menu-menu-press-room']/div/ul/li[1]/a")
    private WebElement pressRoomOverviewLinks;

    @FindBy(xpath = "//h2[text()='Welcome to the Hotwire Press Room']")
    private WebElement pressRoomOverviewTexts;

    @FindBy(xpath = "//div[@id='block-menu-menu-press-room']/div/ul/li[2]/a")
    private WebElement pressReleasesLink;

    @FindBy(xpath = "//h2[text()='News Releases']")
    private WebElement pressReleasesText;

    @FindBy(xpath = "//div[@id='block-menu-menu-press-room']/div/ul/li[3]/a")
    private WebElement newsAlertsLink;

    @FindBy(xpath = "//h2[text()='News Alerts']")
    private WebElement newsAlertsText;

    @FindBy(xpath = "//div[@id='block-menu-menu-affiliates']/div/ul/li[1]/a")
    private WebElement affiliatesOverviewLink;

    @FindBy(xpath = "//h1[text()='About the Hotwire affiliate programme']")
    private WebElement affiliatesOverviewText;

    @FindBy(xpath = "//div[@id='block-menu-menu-affiliates']/div/ul/li[2]/a")
    private WebElement fAQLink;

    @FindBy(xpath = "//h2[text()='Affiliate program FAQs']")
    private WebElement fAQText;

    public AboutUsPage(WebDriver webdriver) {
        super(webdriver);
    }

    public WebElement getcompanyText() {
        return companyText;
    }

    public WebElement getpressRoomText() {
        return pressRoomText;
    }

    public WebElement gethotelPartners() {
        return hotelPartners;
    }

    public WebElement getaffiliatesText() {
        return affiliatesText;
    }

    public WebElement getlegalText() {
        return legalText;
    }

    public WebElement getcompanyMissionLink() {
        return companyMissionLink;
    }

    public WebElement getcompanyOverviewText() {
        return companyOverviewText;
    }

    public WebElement getleadershipTeamLink() {
        return leadershipTeamLink;
    }

    public WebElement getawardsLink() {
        return awardsLink;
    }

    public WebElement getawardsText() {
        return awardsText;
    }

    public WebElement getcontactUsLink() {
        return contactUsLink;
    }

    public WebElement getcontactUsText() {
        return contactUsText;
    }

    public WebElement gettermsOfUseLink() {
        return termsOfUseLink;
    }

    // terms of use text

    public WebElement gettermsOfUseText() {
        return termsOfUseText;
    }

    public WebElement getprivacyPolicyLink() {
        return privacyPolicyLink;
    }

    public WebElement getprivacyPolicyText() {
        return privacyPolicyText;
    }

    public WebElement getcookiePolicyLink() {
        return cookiePolicyLink;
    }

    public WebElement getcookiePolicyText() {
        return cookiePolicyText;
    }

    public WebElement getruleRestrictionsLink() {
        return ruleRestrictionsLink;
    }

    public WebElement getruleRestrictionsText() {
        return ruleRestrictionsText;
    }

    public WebElement getlowPriceLink() {
        return lowPriceLink;
    }

    public WebElement getlowPriceText() {
        return lowPriceText;
    }

    /**
     * Hotel Partner Link
     */

    public WebElement gethotelPartnerOverviewLink() {
        return hotelPartnerOverviewLink;
    }

    public WebElement gethotelPartnerOverviewLinkText() {
        return hotelPartnerOverviewText;
    }

    /**
     * Press room releases
     */

    public WebElement getpressRoomOverviewLinks() {
        return pressRoomOverviewLinks;
    }

    public WebElement getpressRoomOverviewTexts() {
        return pressRoomOverviewTexts;
    }

    public WebElement getpressReleasesLink() {
        return pressReleasesLink;
    }

    public WebElement getpressReleasesText() {
        return pressReleasesText;
    }

    public WebElement getnewsAlertsLink() {
        return newsAlertsLink;
    }

    public WebElement getnewsAlertsText() {
        return newsAlertsText;
    }

    /**
     * Affiliates Links
     */

    public WebElement getaffiliatesOverviewLink() {
        return affiliatesOverviewLink;
    }

    public WebElement getaffiliatesOverviewText() {
        return affiliatesOverviewText;
    }

    public WebElement getfAQLink() {
        return fAQLink;
    }

    public WebElement getfAQText() {
        return fAQLink;
    }

}
