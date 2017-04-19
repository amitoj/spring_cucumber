/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile.results.car;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 5/28/14
 * Time: 9:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class MobileCarMultipleLocationsPage extends MobileAbstractPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileCarMultipleLocationsPage.class.getSimpleName());


    @FindBy(css = ".btn.f.btn")
    private List<WebElement> suggestedLocations;

    public MobileCarMultipleLocationsPage(WebDriver webDriver) {
        super(webDriver, "tile.car.multiple.locations");
    }

    public List<String> getSuggestedLocations() {
        List <String> results = new ArrayList<String>();
        for (WebElement item: this.suggestedLocations) {
            results.add(item.getText());
        }
        return results;
    }


}
