/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.partners.PartnerIMLFragment;
import com.hotwire.selenium.desktop.us.results.HotelResultsPage;
import com.hotwire.selenium.desktop.us.search.ActivitiesSearchFragment;
import com.hotwire.selenium.desktop.us.search.AirMFILayer;
import com.hotwire.selenium.desktop.us.search.AirSearchFragment;
import com.hotwire.selenium.desktop.us.search.CarSearchFragment;
import com.hotwire.selenium.desktop.us.search.CruiseSearchFragment;
import com.hotwire.selenium.desktop.us.search.HotelMFILayer;
import com.hotwire.selenium.desktop.us.search.HotelSearchFragment;
import com.hotwire.selenium.desktop.us.search.MultiVerticalFareFinder;
import com.hotwire.selenium.desktop.us.search.PackagesSearchFragment;
import com.hotwire.selenium.desktop.us.search.SearchPage;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class HomePage extends AbstractPageObject implements SearchPage {

    private static final String PARTNER_CHECKBOX_CSS = ".IM_input";

    @FindBy(css = ".IM_do_compare")
    private WebElement searchPartner;

    @FindBy(className = "HotelPlannerPopupComp")
    private WebElement hotelPlannerLayer;

    @FindBy(xpath = "//div[contains(@class,'mesoBanner')]")
    private WebElement mesoAds;

    @FindBy(css = "div.errorMessages ul li")
    private WebElement errorMessage;

    public HomePage(WebDriver webdriver) {
        super(webdriver, new String[] {"tiles-def.uhp.index", "tile.hotwire.home"});
    }

    @Override
    protected By getLocatorOfElementToWaitFor() {
        return By.cssSelector("#tileName-A1 .content, #tileName-farefinder");
    }

    public HotelSearchFragment findHotelFare() {
        return getMultiVerticalFarefinder().chooseHotel();
    }

    public AirSearchFragment findAirFare() {
        return getMultiVerticalFarefinder().chooseAir();
    }

    @Override
    public CarSearchFragment findCarRental() {
        return getMultiVerticalFarefinder().chooseCar();
    }

    public CruiseSearchFragment findCruise() {
        return getMultiVerticalFarefinder().chooseCruise();
    }

    public PackagesSearchFragment findPackage(String vacationPackage) {
        if (vacationPackage.contentEquals("AHC")) {
            return getMultiVerticalFarefinder().chooseACHPackage();
        }
        else if (vacationPackage.contentEquals("AH")) {
            return getMultiVerticalFarefinder().chooseAHPackage();
        }
        else if (vacationPackage.contentEquals("HC")) {
            return getMultiVerticalFarefinder().chooseHCPackage();
        }
        else if (vacationPackage.contentEquals("AC")) {
            return getMultiVerticalFarefinder().chooseACPackage();
        }
        return null;
    }

    public ActivitiesSearchFragment findActivities() {
        return new MultiVerticalFareFinder(getWebDriver()).chooseActivities();
    }

    public MultiVerticalFareFinder getMultiVerticalFarefinder() {
        return new MultiVerticalFareFinder(getWebDriver());
    }

    public boolean isHotelPlannerLayerDisplayed() {
        return hotelPlannerLayer.isDisplayed();
    }

    public AirMFILayer getMFILayer() {
        return new AirMFILayer(getWebDriver());
    }

    public HotelMFILayer getHotelMFILayer() {
        return new HotelMFILayer(getWebDriver());
    }

    public boolean errorHandling(String error) {
        return getErrorMessages().contains(error);
    }

    public PartnerIMLFragment getPartnerIMLFragment() {
        return new PartnerIMLFragment(getWebDriver(), By.className("IM_lightbox_container"));
    }

    public Map<String, String> selectHotelDeal() {
        Map<String, String> map = new HashMap<>();
        if (getWebDriver().findElements(By.cssSelector(".deals ul .deal")).size() > 0) {
            // New home page deals.
            WebElement deal = getWebDriver().findElement(By.cssSelector(".deals ul .deal"));
            map.put("price", deal.findElement(By.cssSelector(".description .price")).getText().trim());
            map.put("location", deal.findElement(By.cssSelector(".description .location")).getText().trim());
            map.put("rating", deal.findElement(By.cssSelector(".description .ratings")).getText().trim());
            if (new GlobalHeader(getWebDriver()).hasVerticalNavigationTabs() &&
                    deal.findElements(By.cssSelector(".description .savings")).size() > 0) {
                map.put("savings", deal.findElement(By.cssSelector(".description .savings")).getText().trim()
                                       .split("\\s+")[0]);
            }
            deal.findElement(By.tagName("a")).click();
            new HotelResultsPage(getWebDriver());
            return map;
        }
        WebElement tr = getWebDriver().findElement(By.cssSelector("table.hotel.dealsTable tbody tr"));
        for (WebElement td : tr.findElements(By.cssSelector("td"))) {
            map.put(td.getAttribute("class"), td.getText());
        }
        tr.click();
        return map;
    }

    public boolean checkMesoAdsIsDisplayed() {
        return mesoAds.isDisplayed();
    }

    public WebElement getHotelInfoPage() {
        return getWebDriver().findElement(By.xpath("//a[contains(@href,'hotel-information')]"));
    }

    public String getErrorMessages() {
        return errorMessage.getText();
    }
}
