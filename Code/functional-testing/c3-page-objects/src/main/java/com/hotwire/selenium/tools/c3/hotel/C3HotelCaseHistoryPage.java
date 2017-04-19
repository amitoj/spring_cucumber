/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.hotel;

import com.hotwire.selenium.tools.c3.customer.C3CaseHistoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ngolodiuk
 * Date: 10/18/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3HotelCaseHistoryPage extends C3CaseHistoryPage {

    public C3HotelCaseHistoryPage(WebDriver webdriver) {
        super(webdriver, By.className("caseHistory"));
    }

    public void addCaseNote(String textNote) {
        findMany(By.xpath("//a[text()='Add note']")).get(0).click();
        findOne("#newNote").sendKeys(textNote);
        findOne("#saveBtn").click();
    }

    public String getLastCaseNote() {
        return findMany(".caseNoteDescriptionItem").get(0).getText().split("\n")[0];
    }

    public String getCaseNotesDescription() {
        return findOne("div[id*='caseNoteDescription_']", DEFAULT_WAIT).getText();
    }
}
