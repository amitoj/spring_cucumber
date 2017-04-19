/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.seo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vjong
 * @since Jun 29, 2012
 */
public class DestinationListPage {
    private static final String CITY_LIST = "//div[@class='city']";
    private static final String COUNTRY_SELECT = "selectedCountry.regionId";

    @FindBy(xpath = CITY_LIST)
    private WebElement cityList;

    @FindBy(id = COUNTRY_SELECT)
    private WebElement countrySelect;

    public String getCityListLocator() {
        return CITY_LIST;
    }

    public String getSelectedCountryFromDropdownList() {
        WebElement selectedCountry = null;
        for (WebElement country : countrySelect.findElements(By.tagName("option"))) {
            if (country.isSelected()) {
                selectedCountry = country;
            }
        }
        return (selectedCountry == null) ? null : selectedCountry.getText();
    }

    public ArrayList<String> getDestinationUrls() {
        ArrayList<String> hrefs = new ArrayList<>();
        List<WebElement> aTags = cityList.findElements(By.tagName("a"));
        for (WebElement aTag : aTags) {
            String href = aTag.getAttribute("href");
            hrefs.add(href);
        }
        return hrefs;
    }
}
