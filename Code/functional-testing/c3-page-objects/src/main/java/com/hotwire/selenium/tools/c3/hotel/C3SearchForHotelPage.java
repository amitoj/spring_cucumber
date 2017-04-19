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
import org.openqa.selenium.support.ui.Select;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 7/1/13
 * Time: 8:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3SearchForHotelPage extends ToolsAbstractPage {

    public C3SearchForHotelPage(WebDriver webDriver) {
        super(webDriver, By.cssSelector("#searchForHotelContainer"));
    }

    public void searchForHotelID(String id) {
        setTextAndSubmit("input[name='hotelId']", id);
    }

    public void searchByPhoneNumber(String phoneNum) {
        setTextAndSubmit("input[name='phoneNumber']", phoneNum);
    }

    public String getErrorMessage() {
        return findOne(".errorMessages.c3Skin>ul>li").getText();
    }

    public void enterHotelName(String hotelName) {
        setText("div.formRow.sectionRowHotelName input", hotelName);
    }

    public void enterAddress(String hotelAddress) {
        setText("input[name='address']", hotelAddress);
    }

    public void enterZipCode(String zipCode) {
        setText("input[name='zipCode']", zipCode);
    }

    public void selectHotelState(String state) {
        new Select(findOne("select[name='state']")).selectByVisibleText(state);
    }

    public void selectCountry(String country) {
        new Select(findOne("select[name='country']")).selectByVisibleText(country);
    }

    public void clickSearchBtn() {
        findOne("div.btnRow input[value='Search']").click();
    }


}




