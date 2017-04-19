/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * C3 Application HomePage page object, aggregates all common links
 */
public class C3IndexPage extends ToolsAbstractPage {

    public C3IndexPage(WebDriver webDriver) {
        super(webDriver, By.className("homePageContainer"));
        waitElementStopMoving("ul.searchLinks", EXTRA_WAIT);
    }

    public void clickOnCustomerLink() {
        findOne("a#silkId_search_customer", DEFAULT_WAIT).click();
    }

    public void clickOnCreateNewInboundCallCaseLink() {
        findOne("a.newCaseLink", DEFAULT_WAIT).click();
    }

    public void clickCreateCustomer() {
        clickOnLink("Create customer", DEFAULT_WAIT);
    }

    public void clickBlackList() {
        clickOnLink("Edit black list", DEFAULT_WAIT);
    }

    public void openTravelAdvisoryUpdates() {
        clickOnLink("Travel Advisory Updates", DEFAULT_WAIT);
    }

    public void clickSearchForAHotel() {
        findOne("a#silkId_search_hotel", DEFAULT_WAIT).click();
    }

    public void clickEditCSRAcc() {
        clickOnLink("Edit CSR accounts", DEFAULT_WAIT);
    }

    public void clickHotelOvercharges() {
        clickOnLink("Hotel Overcharges Admin", DEFAULT_WAIT);
    }

    public void searchForCaseNotes() {
        findOne("a#silkId_search_case", DEFAULT_WAIT).click();
    }

    public void clickSearchForPartnerLink() {
        findOne("a#silkId_search_partner_customer", DEFAULT_WAIT).click();
    }

    public void clickOnViewResources() {
        findOne("a.menuItem[href*='resources.jsp']", DEFAULT_WAIT).click();
    }

    public void clickOnUnlockAccount() {
        findOne("a#silkId_unlock_csr", DEFAULT_WAIT).click();
    }

    public void clickOnRiskManagement() {
        findOne("a#silkId_finance_fraud_search", DEFAULT_WAIT).click();
    }

    public void clickOnMailInRebate() {
        clickOnLink("Mail-In Rebate Admin", DEFAULT_WAIT);
    }

    public void clickOnHotelCreditCardAdmin() {
        clickOnLink("Hotel Credit Card Admin", DEFAULT_WAIT);
    }
}
