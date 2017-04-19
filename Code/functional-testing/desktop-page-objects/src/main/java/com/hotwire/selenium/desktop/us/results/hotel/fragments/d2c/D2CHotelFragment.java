/*
* Copyright 2015 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.selenium.desktop.us.results.hotel.fragments.d2c;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.results.D2CFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.*;

/**
 * Created by v-ikomarov on 2/19/2015.
 */
public class D2CHotelFragment extends AbstractUSPage implements D2CFragment {
    public static final String FIRST_D2C_MODULE = "#IntentMediaIntercard1";
    public static final String SECOND_D2C_MODULE = "#IntentMediaRail";
    private static final String PARTNERS_CHECK_BOX = ".IM_input";
    private static final String PARTNERS_NAME = ".IM_advertiser_name span";

    @FindBy (css = ".IM_cell_content")
    private List<WebElement> allPartners;

    @FindBy (css = ".IM_advertiser_name span")
    private List<String> partnersName;

    @FindBy (css = ".IM_select_all>input")
    private WebElement selectAll;

    @FindBy (css = ".IM_do_compare")
    private WebElement btnCompare;


    public D2CHotelFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(FIRST_D2C_MODULE), 10);
    }

    public D2CHotelFragment(WebDriver webDriver, By cssLocator) {
        super(webDriver, cssLocator, 10);
    }

    public void selectAllPartners() {
        selectAll.click();
    }

    public void comparePartners() {
        btnCompare.click();
    }

    public void selectPartner(String partnerName) {
        for (WebElement partner : getAllVisiblePartners()) {
            if (getName(partner).toLowerCase().trim().contains(partnerName.toLowerCase().trim()) ||
                    partnerName.toLowerCase().trim().contains(getName(partner).toLowerCase().trim())) {

                if (!getPartner(partner).isSelected()) {
                    getPartner(partner).click();
                    return;
                }
            }
        }
        throw new RuntimeException("The partner with name " + partnerName + " is not found");
    }

    public void deselectAllPartners() {
        for (WebElement partner : allPartners) {
            if (getPartner(partner).isSelected()) {
                getPartner(partner).click();
            }
        }
    }

    private WebElement getPartner(WebElement partner) {
        return partner.findElement(By.cssSelector(PARTNERS_CHECK_BOX));
    }

    private String getName(WebElement partner) {
        return partner.findElement(By.cssSelector(PARTNERS_NAME)).getText();
    }

    private List<WebElement> getAllVisiblePartners() {
        return allPartners;
    }
}
