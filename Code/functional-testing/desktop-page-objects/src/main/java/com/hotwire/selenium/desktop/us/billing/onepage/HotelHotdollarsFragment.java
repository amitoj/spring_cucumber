/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.onepage;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by v-sshubey on 1/28/2015.
 */
public class HotelHotdollarsFragment extends AbstractUSPage {

    @FindBy(id = "isHotCreditChecked")
    private WebElement hotdollarsCheckBox;

    public HotelHotdollarsFragment(WebDriver webDriver) {
        super(webDriver, By.id("hotDollarsSection"));
    }

    public boolean getHotdollarsCheckBoxState() {
        return hotdollarsCheckBox.isSelected();
    }

    public void setHotdollarsCheckBox(boolean state) {
        if (state) {
            if (!getHotdollarsCheckBoxState()) {
                hotdollarsCheckBox.click();
            }
        }
        else {
            if (getHotdollarsCheckBoxState()) {
                hotdollarsCheckBox.click();
            }
        }
    }
}
