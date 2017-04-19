/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.search;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CarSupplierPageFragment extends AbstractUSPage {
    private static final String SUPPLIER_CONTAINER = ".//div[contains(@class, 'yui3-u-rightColumn')]";
    private static final String SUPPLIER_TEXT = "supplierH1";

    @FindBy(className = SUPPLIER_TEXT)
    private WebElement supplierText;

    public CarSupplierPageFragment(final WebDriver webdriver) {
        super(webdriver, By.xpath(SUPPLIER_CONTAINER));
    }

    public Boolean hasSupplierBanner(String suppliername) {
        try {
            return supplierText.getText().contains(suppliername);
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
