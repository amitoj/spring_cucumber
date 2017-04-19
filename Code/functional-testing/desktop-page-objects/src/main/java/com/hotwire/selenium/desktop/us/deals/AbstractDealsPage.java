/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.deals;

import org.openqa.selenium.WebDriver;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  DealsPage
 */
public abstract class AbstractDealsPage extends AbstractUSPage {

    protected static final String DISCOUNT_INTL_ALL_LINKS = ".//div[contains(@class, 'discountLinks')]//ul/li/a";

    @FindBy(xpath = DISCOUNT_INTL_ALL_LINKS)
    List<WebElement> discountIntlAllLinks;

    public AbstractDealsPage(WebDriver driver) {
        super(driver, new String[]{"tiles-def.seo-site-map.destination-group", "tiles-def.seo-site-map." +
                "product-overview"});
    }

    public AbstractDealsPage(WebDriver webDriver, String tile) {
        super(webDriver, tile);
    }

    public AbstractDealsPage(WebDriver webDriver, String[] tiles) {
        super(webDriver, tiles);
    }

    public abstract void verifyPage();

    public boolean isDiscountLinksSortedAlphabetically() {
        Integer size = discountIntlAllLinks.size();

        ArrayList originalList = new ArrayList(size);
        ArrayList sortedList = new ArrayList(size);

        for (int i = 0; i < size; i++) {
            originalList.add(discountIntlAllLinks.get(i).getText());
            sortedList.add(discountIntlAllLinks.get(i).getText());
        }

        Collections.sort(sortedList);

        if (originalList.toString().equals(sortedList.toString())) {
            return true;
        }

        return false;
    }




}
