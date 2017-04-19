/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.hotwire.selenium.desktop.subscription.SubscriptionModuleFragment;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.util.webdriver.functions.VisibilityOf;

/**
 * This is page object of hotel landing page
 */
public class HotelIndexPage extends AbstractIndexPage {
    private static final String TRIP_ADVISOR_CONTENT = ".tripAdvisorLogoModule .title";
    private static final String DEALS_MODULE_CONTAINER =
        ".deals ul, " +                     // New happy home page.
        ".universalDealsEngineModule, " +   // Old container
        ".hotelCityDealsModule";            // New converged container
    private static final String DEALS_MODULE_DEALS =
        ".universalDealsEngineModule table.hotel td.location, " +  // Non converged module
        ".hotelCityDealsModule .dealsContainer li.deal a, " +      // Converged module
        ".deals ul .deal";                                         // New happy home page.

    @FindBy(css = DEALS_MODULE_DEALS)
    private List<WebElement> hotelDeals;

    @FindBy(css = TRIP_ADVISOR_CONTENT)
    private WebElement tripAdvisorContent;

    public HotelIndexPage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.hotel.index", "tile.hotel.index", "tile.hotwire.hotel"});
    }

    public HotelIndexPage(WebDriver webdriver, boolean waitForElement) {
        // Ignoring waitForElements. We just need a different signature.
        super(webdriver,
              new String[] {"tiles-def.hotel.index", "tile.hotel.index", "tile.hotwire.hotel"},
              new ExpectedCondition[] {new VisibilityOf(By.cssSelector(".children span.ui-selectmenu-button a"))});
    }

    public HotelSearchFragment findFare() {
        return new HotelSearchFragment(getWebDriver());
    }

    public boolean isHotelDealsModuleDisplayed() {
        try {
            return getWebDriver().findElement(By.cssSelector(DEALS_MODULE_CONTAINER)).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isHotelDealsEmpty() {
        return hotelDeals.isEmpty();
    }

    @Override
    public void selectFirstDeal() {
        hotelDeals.get(0).click();
    }

    public boolean isTripAdvisorBadgeDisplayed() {
        if (getWebDriver().findElements(By.cssSelector(TRIP_ADVISOR_CONTENT)).size() == 0) {
            return false;
        }
        return tripAdvisorContent.isDisplayed();
    }

    public SubscriptionModuleFragment getSubscriptionModuleFragment() {
        return new SubscriptionModuleFragment(getWebDriver());
    }

    public Map<String, String> selectHotelDeal() {
        Map<String, String> map = new HashMap<>();
        WebElement tr = hotelDeals.get(0);
        if (tr.getAttribute("class").contains("deal")) {
            // New happy home page deal module.
            map.put("price", tr.findElement(By.cssSelector(".description .price")).getText().trim());
            map.put("location", tr.findElement(By.cssSelector(".description .location")).getText().trim());
            map.put("rating", tr.findElement(By.cssSelector(".description .ratings")).getText().trim());
            if (currentlyOnDomesticSite() && tr.findElements(By.cssSelector(".description .savings")).size() > 0) {
                map.put("savings", tr.findElement(By.cssSelector(".description .savings")).getText().trim()
                                     .split("\\s+")[0]);
            }
            tr.findElement(By.tagName("a")).click();
            new HotelResultsPage(getWebDriver());
            return map;
        }
        for (WebElement td : tr.findElements(By.cssSelector("span"))) {
            map.put(td.getAttribute("class"), td.getText());
        }
        tr.click();
        return map;
    }
}
