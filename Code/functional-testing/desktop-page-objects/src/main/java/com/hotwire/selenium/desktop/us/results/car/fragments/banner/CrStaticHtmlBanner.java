/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.car.fragments.banner;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Implementation of carRentals static banner with html content
 * Should be displayed for all currencies when:
 *   - vt.CRB12=3
 *   - hotwire.biz.search.car.crDynamicBannerEnabled=FALSE
 *   - location was obtained as results of query:
 *     select * from car_location_display_policy where is_car_rentals_module_enabled='Y'
 *
 * @author v-vzyryanov
 * @since 1/31/13
 */
public class CrStaticHtmlBanner extends AbstractUSPage {

    private static final String PRICE_VALUE = "$10.95";
    private static final String ID_SEARCH_UPD_LAYER = "searchUpdatingLayer";

    @FindBy(css = "span.pricePerDay")
    private WebElement pricePerDay;

    @FindBy(css = "div#tileName-results div.moduleCarRentals div.crModuleHtml")
    private WebElement bannerContainer;

    public CrStaticHtmlBanner(WebDriver webDriver) {
        super(webDriver);
        waitForMaskingLayerToClose(By.id(ID_SEARCH_UPD_LAYER), MAX_SEARCH_PAGE_WAIT_SECONDS);

        if (!bannerContainer.isDisplayed()) {
            throw new NoSuchElementException("Banner doesn't visible");
        }

        if (!PRICE_VALUE.equals(pricePerDay.getText())) {
            throw new NoSuchElementException("Price value doesn't equal to " + PRICE_VALUE);
        }
    }
}
