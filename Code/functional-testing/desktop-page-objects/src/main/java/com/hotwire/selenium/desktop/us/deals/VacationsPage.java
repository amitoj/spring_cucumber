/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.deals;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hotwire.util.webdriver.functions.InvisibilityOf;
import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.functions.Wait;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 2/5/14
 * Time: 10:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class VacationsPage extends AbstractDealsPage {
    private static final String DEALS_TABLE = ".VacationDealsTable";
    private static final String  URL = "/package";

    public VacationsPage(WebDriver webDriver) {
        //super(webDriver, new String[] {"tiles-def.package.index", "tile.hotwire.package"});
        super(webDriver, new String[] {
            "tiles-def.seo-site-map.product-overview", "SM/package-information/us/index.jsp"});
        new Wait<Boolean>(new IsAjaxDone()).maxWait(20).apply(getWebDriver());
        new Wait<Boolean>(new InvisibilityOf(By.cssSelector(".VacationDealsTable .searching img")))
            .maxWait(20).apply(getWebDriver());
    }

    public void selectVacationDeal() {
        new VacationsPage(getWebDriver());
        Map<String, String> map = new HashMap<>();
        getWebDriver().findElement(By.cssSelector(DEALS_TABLE + " tr a")).click();
    }

    @Override
    public void verifyPage() {
        assertThat(getWebDriver().getCurrentUrl())
            .as("URL for vacation page should contains " + URL)
            .contains(URL);
    }
}
