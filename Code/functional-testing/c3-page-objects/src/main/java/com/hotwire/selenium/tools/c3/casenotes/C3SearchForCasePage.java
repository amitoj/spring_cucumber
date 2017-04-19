/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.casenotes;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-abudyak
 * Date: 6/3/13
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3SearchForCasePage extends ToolsAbstractPage {

    public C3SearchForCasePage(WebDriver webDriver) {
        super(webDriver, By.id("caseSearchFormContainer"));
    }

    public void searchCaseByID(String caseID) {
        setTextAndSubmit("input[name='caseId']", caseID);
    }

    public void searchCaseByEmail(String email) {
        setTextAndSubmit("input[name='emailAddress']", email);
    }

    public void submit() {
        findOne("form[name='csrForm']", DEFAULT_WAIT).submit();
    }

    public void searchCaseByCSR(Integer daysToSearchFrom) {
        selectValue("select[name='csrId']", "2135406051");
        selectDate("input#csrStartDate-field", getDateBefore(daysToSearchFrom));
        selectDate("input#csrEndDate-field", getCurrentDate());
        submit();
    }

    public void searchCaseByItinerary(String itineraryNumber) {
        setTextAndSubmit("input[name='confirmationNumber']", itineraryNumber);
    }
}
