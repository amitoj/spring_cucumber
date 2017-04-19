/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.help;

import com.google.common.collect.ImmutableMap;
import com.hotwire.selenium.desktop.row.AbstractRowPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class HelpCenterPage extends AbstractRowPage {

    private static Map<String, String> countryToCountryCodeMap =
        ImmutableMap.<String, String>builder()
                    .put("United Kingdom", "uk")
                    .put("Australia", "au")
                    .put("Ireland", "ie")
                    .put("Sverige", "se")
                    .put("Norge", "no")
                    .put("Danmark", "dk")
                    .put("New Zealand", "nz")
                    .put("Deutschland", "de")
                    .put("Hong Kong", "hk")
                    .put("Singapore", "sg")
                    .put("México", "mx")
                    .build();

    public HelpCenterPage(WebDriver webdriver) {
        super(webdriver, "tile.help.landing");
    }

    public String getSupportInfo(String countryName) {
        return getWebDriver().findElement(
            By.xpath(
                "//div[contains(@class, 'helpContact')]//span[@class='" + countryToCountryCodeMap.get(countryName) +
                "']//following-sibling::small[2]"))
                             .getText();
    }
}
