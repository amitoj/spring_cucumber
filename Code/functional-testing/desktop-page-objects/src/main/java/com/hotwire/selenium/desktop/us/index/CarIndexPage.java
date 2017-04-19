/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.index;

import com.hotwire.selenium.desktop.us.search.CarSearchFragment;
import com.hotwire.selenium.desktop.us.search.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author v-vzyryanov
 * @since 2012.07
 */
public class CarIndexPage extends AbstractIndexPage implements SearchPage {

    @FindBy(css = ".universalDealsEngineModule .dealsTable tr td.location")
    private List<WebElement> carDeals;

    public CarIndexPage(WebDriver webDriver) {
        super(webDriver, new String[] {"tiles-def.car.index", "tile.hotwire.car"});
    }

    @Override
    public CarSearchFragment findCarRental() {
        return new CarSearchFragment(getWebDriver());
    }

    public boolean isCarDealsEmpty() {
        return carDeals.isEmpty();
    }

    @Override
    public void selectFirstDeal() {
        carDeals.get(0).click();
    }
}
