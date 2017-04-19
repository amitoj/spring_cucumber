/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotel.oversells;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 7/1/13
 * Time: 2:31 PM
 * This page object contains methods for operations with Hotel oversell pop-up.
 */
public class C3HotelOversellFragment extends ToolsAbstractPage {

    public C3HotelOversellFragment(WebDriver webDriver) {
        super(webDriver, By.className("HotelDetailsSearch"));
    }

    public void searchOversells(Date from, Date to) {
        selectDate("input[name='fromDate']", from);
        selectDate("input[name='toDate']", to);
        submit();
    }

    public void submit() {
        findOne("form[name='searchReservationForm']").submit();
    }

    public String getConfirmationText() {
        return findOne("div.errorMessages.c3Skin ul li", PAGE_WAIT).getText();
    }

}

