/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.results.car.fragments.compareWith;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: v-vzyryanov
 * Date: 4/12/13
 * Time: 5:56 AM
 *
 * This fragment describes Dare to Compare module on search results (D2C).
 * It is used for launching searches on selected partners sites in popup windows.
 * Can be removed in future as part of vt.IM
 */
public class CompareWithFragment extends AbstractUSPage {
    public  static  final  String FIRST_D2C_MODULE = "[name='dare2Compare']";
    public  static  final  String SECONDARY_D2C_MODULE = "[name='dare2Compare_secondary']";

    private static Map<String, WebElement> PARTNERS = new HashMap<String, WebElement>();

    @FindBy(name = "d2c_BookingBuddy")
    private WebElement bookingBuddy;

    @FindBy(name = "d2c_CarRentals.com")
    private WebElement crcom;

    @FindBy(name = "d2c_Expedia")
    private WebElement expedia;

    @FindBy(name =  "d2c_Kayak")
    private WebElement kayak;

    @FindBy(name = "d2c_Orbitz")
    private WebElement orbitz;

    @FindBy(css = "[type='checkbox']")
    private List<WebElement> partnerCheckboxes;

    @FindBy(css = ".d2cSelectAllLink")
    private WebElement selectAll;

    @FindBy(xpath = ".//button//span[contains(text(), 'Compare')]//..")
    private WebElement compare;



    public CompareWithFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(FIRST_D2C_MODULE), 10);
        initializePartners();
    }

    public CompareWithFragment(WebDriver webDriver, By cssSelector) {
        super(webDriver, cssSelector, 10);
        initializePartners();
    }

    private void initializePartners() {
        PARTNERS.put("Buddy", bookingBuddy);
        PARTNERS.put("CarRentals", crcom);
        PARTNERS.put("Expedia", expedia);
        PARTNERS.put("Kayak", kayak);
        PARTNERS.put("Orbitz", orbitz);
    }


    public CompareWithFragment selectPartners(List<String> partners) {
        disableAll();
        for (String partner : partners) {
            logger.info("D2C: check <" + partner + ">");
            select(findPartner(partner), true);


        }
        return this;
    }

    private WebElement findPartner(String partner) {
        for (Map.Entry<String, WebElement> entry : PARTNERS.entrySet()) {
            if (((String) entry.getKey()).toLowerCase().contains(partner.toLowerCase().trim()) ||
                    partner.toLowerCase().trim().contains(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public boolean verifyPartnersVisibility(String partner, boolean isVisible) {
        logger.info("D2C: check <" + partner + ">");
        return isPartnerDisplayed(partner) == isVisible;
    }

    public boolean isPartnerDisplayed(String partner) {
        try {
            return findPartner(partner).isDisplayed();
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void compare() {
        compare.click();
    }


    public boolean verifyAllPartnersState(boolean state) {
        for (WebElement elm : partnerCheckboxes) {
            if (state != elm.isSelected()) {
                String status = state ? "selected" : "deselected";
                logger.warn("Checkbox of <" + elm.getAttribute("name") + "> wasn't " + status);
                return false;
            }
        }
        return true;
    }

    public void selectAllCarPartners() {
        selectAll.click();
    }


    private void select(WebElement cb, boolean setChecked) {
        if (cb.isSelected()) {
            cb.click();
        }
        if (setChecked) {
            cb.click();
        }
    }

    private void disableAll() {
        for (WebElement elm : partnerCheckboxes) {
            select(elm, false);
        }
    }

}
