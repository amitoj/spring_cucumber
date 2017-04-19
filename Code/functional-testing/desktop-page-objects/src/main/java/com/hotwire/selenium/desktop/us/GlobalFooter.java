/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.selenium.desktop.planningtools.PlanningToolsPage;
import com.hotwire.selenium.desktop.us.deals.CarDealsPage;
import com.hotwire.selenium.desktop.us.deals.DealsPage;
import com.hotwire.selenium.desktop.us.deals.FlightsDealsPage;
import com.hotwire.selenium.desktop.us.deals.HotelDealsPage;
import com.hotwire.selenium.desktop.us.deals.VacationsPage;
import com.hotwire.selenium.desktop.us.global.footer.AboutUsPage;
import com.hotwire.selenium.desktop.us.global.footer.ContactUsPage;
import com.hotwire.selenium.desktop.us.helpcenter.HelpCenterPage;

/**
 * @author unknow
 * @since 2013.04 Grobal footel module
 */

public class GlobalFooter extends AbstractUSPage {

    @FindBy(xpath = "//a[contains(@href, '/helpcenter/index.jsp')][2]")
    private WebElement helpCenterLink;

    @FindBy(xpath = "//DIV[@id='hwGlobalFooter']//A[contains(@href,'/hotel-information/us/index')]")
    private WebElement hotelsLink;

    @FindBy(xpath = "//DIV[@id='hwGlobalFooter']//A[contains(@href,'/car-information/us/index')]")
    private WebElement carsLink;

    @FindBy(xpath = "//DIV[@id='hwGlobalFooter']//A[contains(@href,'/flight-information/us/index')]")
    private WebElement flightsLink;

    @FindBy(xpath = "//DIV[@id='hwGlobalFooter']//A[contains(@href,'/package')]")
    private WebElement vacationsLink;

    @FindBy(xpath = "//DIV[@id='hwGlobalFooter']//A[contains(@href,'/deals/index')]")
    private WebElement dealsLink;

    @FindBy(xpath = "//DIV[@id='hwGlobalFooter']//A[contains(@href,'/planning/index')]")
    private WebElement planningToolsLink;

    // about us link on home page - footer
    @FindBy(xpath = "//a[contains(@href,'content/mission')]")
    private WebElement aboutUsLink;

    // contact us link on home page - footer
    @FindBy(xpath = "//a[contains(@href, 'helpcenter/contact')]")
    private WebElement contactUsLink;

    // site-map link on home page footer
    @FindBy(xpath = "//a[contains(@href, 'site-map')]")
    private WebElement siteMapLink;

    @FindBy(css = ".hwFooter .section_D>p")
    private WebElement copyrightText;

    @FindBy(css = ".disclaimerHome .disclaimer")
    private WebElement legalDeclaimer;

    public GlobalFooter(WebDriver driver) {
        super(driver);
    }

    public HelpCenterPage navigateToHelpCenterPage() {
        helpCenterLink.click();
        return new HelpCenterPage(getWebDriver());
    }

    public HotelDealsPage navigateToHotelDealsPage() {
        hotelsLink.click();
        return new HotelDealsPage(getWebDriver());
    }

    public CarDealsPage navigateToCarDealsPage() {
        carsLink.click();
        return new CarDealsPage(getWebDriver());
    }

    public FlightsDealsPage navigateToFlightDealsPage() {
        flightsLink.click();
        return new FlightsDealsPage(getWebDriver());
    }

    public VacationsPage navigateToVacationsPage() {
        vacationsLink.click();
        return new VacationsPage(getWebDriver());
    }

    public DealsPage navigateToDealsPage() {
        dealsLink.click();
        return new DealsPage(getWebDriver());
    }

    /**
     * Navigate to About us Page
     */
    public AboutUsPage navigateToAboutUsPage() {
        aboutUsLink.click();
        return new AboutUsPage(getWebDriver());
    }

    /**
     * Navigate to About us Page
     */
    public ContactUsPage navigateToContactUsPage() {
        contactUsLink.click();
        return new ContactUsPage(getWebDriver());
    }

    /**
     * Navigate to Site map Page
     */
    public SiteMapPage navigateToSiteMapPage() {
        siteMapLink.click();
        return new SiteMapPage(getWebDriver());
    }

    public PlanningToolsPage navigateToPlanningToolsPage() {
        planningToolsLink.click();
        return new PlanningToolsPage(getWebDriver());
    }

    public String getCopyrightText() {
        return copyrightText.getText();
    }

    public String getDeclaimerText() {
        return legalDeclaimer.getText();
    }
}
