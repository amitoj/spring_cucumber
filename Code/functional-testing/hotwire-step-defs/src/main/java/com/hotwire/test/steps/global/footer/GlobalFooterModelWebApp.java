/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.global.footer;

import com.hotwire.selenium.desktop.us.GlobalFooter;
import com.hotwire.selenium.desktop.us.SiteMapPage;
import com.hotwire.selenium.desktop.us.global.footer.ContactUsPage;

import java.util.Calendar;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author psuryadevara
 */
public class GlobalFooterModelWebApp extends GlobalFooterModelTemplate {
    private static final long DEFAULT_WAIT = 15;

    /**
     * Contact Us page
     */

    @Override
    public void openContactUsPage() {
        new GlobalFooter(getWebdriverInstance()).navigateToContactUsPage();
    }

    @Override
    public void verifySendEmailText() {
        ContactUsPage contact = new ContactUsPage(getWebdriverInstance());
        contact.getsendUSEmailText().isDisplayed();
    }

    /**
     * Site Map page
     */

    @Override
    public void openSiteMapPage() {
        new GlobalFooter(getWebdriverInstance()).navigateToSiteMapPage();
    }

    @Override
    public void verifyHotwireProducts() {
        SiteMapPage sitemap = new SiteMapPage(getWebdriverInstance());

        // Hotels Links
        sitemap.getHotwireProductsText().isDisplayed();
        sitemap.gethotelRatings().isDisplayed();
        sitemap.gethotelRatings().isDisplayed();
        sitemap.getsearchForHotel().isDisplayed();

        // Car Rentals
        sitemap.gettypeOfRentalCars().isDisplayed();
        sitemap.getsearchCar().isDisplayed();

        // Flight link
        sitemap.getsearchFlight().isDisplayed();

    }

    /**
     * My account
     */
    @Override
    public void verifyMyAccount() {
        SiteMapPage sitemap = new SiteMapPage(getWebdriverInstance());
        sitemap.getmyAccountText().isDisplayed();
        sitemap.getregistrationBenefits().isDisplayed();
        sitemap.getloginToHotwire().isDisplayed();
        sitemap.getsignUpForLowPrices().isDisplayed();
        sitemap.getcreateHotwireAccount().isDisplayed();
    }

    @Override
    public void verify_hotwire_legal_declaimer() {
        String expectedDeclaimer =
                //"**Hotel savings are based on actual Hotwire Hot" +
                //" Rate bookings made in the previous 12 months as" +
                //" compared with the lowest published rates found on" +
                //" leading retail travel sites. Prices are dynamic and" +
                //" vary based on booking date and length of stay. There is" +
                //" no guarantee these savings or rates will be in effect at the" +
                //" time of your search. Availability is limited. Hot Rates are different" +
                //" from retail rates. With Hot Rates you enter the date, location and star rating." +
                //" Hotel revealed only after booking. All bookings final." +
                "*Savings based on lowest published rate we've found on leading retail travel sites" +
                " in the last 24-48 hours for the same neighborhood, star rating, hotel type" +
                ", and stay dates. Availability is limited and rates are subject to change." +
                " Prices are dynamic and vary based on date of booking, length of stay" +
                " and hotel class. There is no guarantee that these prices will be in effect at" +
                " the time of your search. Prices do not include taxes and fees. Hotwire is different" +
                " from retail travel sites. With Hotwire you enter the date and location." +
                " Hotel revealed only after booking.";
        GlobalFooter footer = new GlobalFooter(getWebdriverInstance());
        String actualDeclaimer = footer.getDeclaimerText();
        assertThat(actualDeclaimer.replace("\n", "")).contains(expectedDeclaimer.replace("\n", ""));
    }

    @Override
    public void verify_hotwire_copyright_info() {
        String expectedCopyright = "© 2000-XXXX Hotwire, Inc. All rights reserved. Hotwire, Inc." +
                " is not responsible for content on external sites. Hotwire, the Hotwire logo," +
                " Hot Fare, Hot Rate, \"4-star hotels. 2-star prices.\" and" +
                " TripStarter are either registered trademarks or trademarks of Hotwire," +
                " Inc. in the US and/or other countries. Other logos or product and company" +
                " names mentioned herein may be the property of their respective owners." +
                " CST 2053390-40; NST 20003-0209.";

        GlobalFooter footer = new GlobalFooter(getWebdriverInstance());
        String actualCopyright = footer.getCopyrightText();
        assertThat(expectedCopyright.replace("XXXX", String.valueOf(Calendar.getInstance().get(Calendar.YEAR))))
                .as("Copyright text is incorrect")
                .isEqualTo(actualCopyright);
    }
}
