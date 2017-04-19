/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object
 */
public class C3HotelActivationForm extends ToolsAbstractPage {
    public C3HotelActivationForm(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div#hotelActivationPopupLayer-bd"));
    }

    public void selectReason() {
        selectValue("select[name='reason']", "1");
    }

    public void setRequestedBy(String text) {
        setText("input[name='requestedBy']", text);
    }

    public void setTitle(String text) {
        setText("input[name='title']", text);
    }

    public void setPhoneNumber(String text) {
        setText("input[name='phoneNumber']", text);
    }

    public void clickSubmit() {
        findOne("div.hotelActivationLeftCol  input[value='Submit']").click();
    }

    public void clickCancel() {
        findOne("div.hotelActivationLeftCol  input[value='Cancel']").click();
    }

    public String getValidationMsg() {
        return findOne("div.errorMessages.c3Skin ul li").getText();
    }
}
