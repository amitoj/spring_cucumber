/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3.csraccount;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Page for manipulation of CSR accounts
 */
public class C3AdminAccManagementPage extends ToolsAbstractPage {

    public C3AdminAccManagementPage(WebDriver webDriver) {
        super(webDriver, By.id("csrAccountForm"));
    }

    public void clickCSRLink(String csrLogin) {
        selectTableRow("ul.accountRow li.column4", csrLogin);
    }

    public C3AdminAccManagementPage clickCSRStatusTab(String status) {
        if (status.equals("Active")) {
            switchToTab("1");
        }
        else {
            switchToTab("2");
        }
        return this;
    }

    /**
     *
     * @param tabNumber - first or second tab ("1" or "2")
     */
    private void switchToTab(String tabNumber) {
        findOne("a[href='#tabs-" + tabNumber + "']").click();
        waitProgressBar();
    }

    public void changeStatus() {
        //wait for JS page scrolling up
        freeze(2);
        findOne("button#deactivateAccountBtn", EXTRA_WAIT).click();
    }

    public String getConfirmationMessage() {
        return findOne("div.successMessage", EXTRA_WAIT).getText();
    }

    public boolean checkCsrAccountStatusOnTab(String status, String csrLogin) {
        clickCSRStatusTab(status);
        if (status.equals("Inactive")) {
            return isElementDisplayed(By.xpath("//div[@id='tabs-2']//li[text() = '" + csrLogin + "']"), DEFAULT_WAIT);
        }
        else {
            return isElementDisplayed(By.xpath("//div[@id='tabs-1']//li[text() = '" + csrLogin + "']"), DEFAULT_WAIT);
        }
    }

    public void setFullName(String fn, String ln) {
        setText("input[name='firstName']", fn);
        setText("input[name='lastName']", ln);
    }

    public void setSupervisorEmail(String email) {
        setText("input[name='supervisorEmailAddress']", email);
    }

    public void setUserName(String username) {
        setText("input[name='userName']", username);
    }

    public void selectUserCategory(String category) {
        findOne("#silkId_userCategoryCode .arrow").click();
        findOne("select[name='userCategoryCode']").sendKeys(category);
    }

    public void selectLocation(String location) {
        findOne("#silkId_callCenterCode .arrow").click();
        findOne("select[name='callCenterCode']").sendKeys(location);
    }

    public void selectUserLevel(String level) {
        findOne("#silkId_csrAccessLevelCode .arrow").click();
        findOne("select[name='csrAccessLevelCode']").sendKeys(level);
    }

    public  void  submit() {
        findOne("div.lastFormRow button").click();
    }

    public String getMessage() {
        String msg = findOne("#msgBox").getText() + findOne("#additionalMsgBox").getText();
        return msg.trim();
    }

    public void clickResetPassword() {
        findOne(By.xpath(".//button[contains(text(), 'Reset password')]")).click();
    }
}
