/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.results;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: prbhat
 * Date: Jun 19, 2012
 * Time: 3:50:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileHotelResultsMapPage extends MobileAbstractPage {

    @FindBy(name = "selectedNeighborhoodIds")
    private List<WebElement> neighborhoods;

    @FindBy(className = "submit")
    private WebElement areaMap;

    public MobileHotelResultsMapPage(WebDriver driver, String hotelResultsMapPageTileDefinition) {
        super(driver, hotelResultsMapPageTileDefinition);
    }

    public void clickNeighborhoodFilter() {
        areaMap.click();
    }

    public void filterOutTopNeighborhood() {
        for (WebElement hood : neighborhoods) {
            if (hood.isSelected()) {
                hood.click();
                break;
            }
        }
    }

    public void filterOutAllNeighborhoods() {
        for (WebElement hood : neighborhoods) {
            if (hood.isSelected()) {
                hood.click();
            }
        }
    }
}
