/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.c3.csraccount;

import com.hotwire.selenium.tools.c3.C3IndexPage;
import com.hotwire.selenium.tools.c3.csraccount.C3AdminAccManagementPage;
import com.hotwire.selenium.tools.c3.login.C3LockedAccounts;
import com.hotwire.selenium.tools.c3.login.CSRUnlockForm;
import com.hotwire.selenium.tools.c3.riskManagement.C3RiskManagementAdminPage;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.c3.C3CsrAccount;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3CSRDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-abudyak
 * Date: 8/12/13
 * Time: 7:54 AM
 */
public class C3CSRAccountModel extends ToolsAbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3CSRAccountModel.class);

    @Autowired
    private C3CsrAccount c3CsrAccount;

    public void clickEditCsrAccount() {
        new C3IndexPage(getWebdriverInstance()).clickEditCSRAcc();
    }

    public void setCSR() {
        if ("csrcroz1".equals(c3CsrAccount.getUsername())) {
            c3CsrAccount.setAgentName(new C3CSRDao(getDataBaseConnection()).getRegularCSR());
        }
        else if ("csrcroz9".equals(c3CsrAccount.getUsername())) {
            c3CsrAccount.setAgentName(new C3CSRDao(getDataBaseConnection()).getFinanceCSR());
        }
        else {
            throw new UnimplementedTestException("No such CSR admin");
        }
        LOGGER.info("CSR account for manipulation: " + c3CsrAccount.getAgentName());

    }

    public void searchCsrAccountOnTab(String status) {
        LOGGER.info("Search for " + status.toUpperCase() + " " + c3CsrAccount);
        new C3AdminAccManagementPage(getWebdriverInstance()).clickCSRStatusTab(status)
            .clickCSRLink(c3CsrAccount.getAgentName());
    }

    public void changeStatusOfSelectedCsrAccount(String status) {
        LOGGER.info("Change status of " + c3CsrAccount.getAgentName() + " to " + status.toUpperCase());
        C3AdminAccManagementPage adminPage = new C3AdminAccManagementPage(getWebdriverInstance());
        adminPage.changeStatus();
        String msg = adminPage.getConfirmationMessage();
        if (status.equals("Active")) {
            assertThat(msg).contains("is now active");
        }
        else {
            assertThat(msg).contains("is now inactive");
        }

    }

    public void verifyCsrStatus(String status) {
        LOGGER.info("Is status of " + c3CsrAccount + " equal to " + status.toUpperCase());
        boolean csrActivityStatus = new C3AdminAccManagementPage(getWebdriverInstance())
                .checkCsrAccountStatusOnTab(status, c3CsrAccount.getAgentName());
        assertThat(csrActivityStatus).as("CSR status doesn't equal " + status).isTrue();
    }

    public void verifyCSRLocked(boolean unlocked) {
        String lockedDate = String.valueOf(new C3CSRDao(getDataBaseConnection())
                .getCsrLoginFailures(c3CsrAccount.getAgentName()).get(0).get("LOCKED_DATE"));
        if (!unlocked) {
            assertThat(lockedDate).isNotNull();
            c3CsrAccount.setPasswordLockTimestamp(lockedDate);
        }
        else {
            assertThat(lockedDate).isEqualTo("null");
        }

    }

    public void setValidAgent() {
        c3CsrAccount.setAgentName(new C3CSRDao(getDataBaseConnection()).getValidCsrAccount());
    }

    public void setValidAgent11() {
        c3CsrAccount.setAgentName(new C3CSRDao(getDataBaseConnection()).getValidCsr11Account());
    }

    public void verifyNoRecordsForThisCsrInDb() {
        assertThat(new C3CSRDao(getDataBaseConnection()).checkIfAgentExists(c3CsrAccount.getAgentName())).isFalse();
    }

    public void verifyNumOfLoginFailuresInDb(Integer failuresNum) {
        List<Map<String, Object>> failuresRows = new C3CSRDao(getDataBaseConnection())
                .getCsrLoginFailures(c3CsrAccount.getAgentName());
        assertThat(failuresRows.size()).isEqualTo(1).as("Only one row must be in DataBase");
        assertThat(((BigDecimal) failuresRows.get(0).get("LOGIN_FAILURES")).intValue()).isEqualTo(failuresNum);
    }

    public void clickOnUnLockCSR() {
        new C3IndexPage(getWebdriverInstance()).clickOnUnlockAccount();
    }

    public void verifyCSRUnlockForm() {
        new C3LockedAccounts(getWebdriverInstance());
    }

    public void createCSRWithUserLevel(String level) {
        String randEmail = "test_hotwire_" + System.currentTimeMillis() + "@hotwire.com";
        String randName = "CSR" + System.currentTimeMillis();

        C3AdminAccManagementPage adminPage = new C3AdminAccManagementPage(getWebdriverInstance());

        adminPage.setFullName("TestRTC", "CSR");
        adminPage.setSupervisorEmail(randEmail);
        adminPage.setUserName(randName);
        c3CsrAccount.setAgentName(randName);
        adminPage.selectLocation("Hotwire");
        adminPage.selectUserLevel(level);
        adminPage.selectUserCategory("Other");

        adminPage.submit();
    }

    public void verifyCreateCSRMessage(String msg) {
        assertThat(new C3AdminAccManagementPage(getWebdriverInstance()).getMessage())
                .as("Error message is not presenting")
                .contains(msg);
    }

    public void unlockGivenUser(String user) {
        String password = "test" + System.currentTimeMillis() + "password";
        logSession("Unlocking users");
        new C3LockedAccounts(getWebdriverInstance()).clickUnlock();
        checkValidation();
        changePassword(password, password);
    }

    public void checkValidation() {
        changePassword("", "");
        verifyValidationMsg("CSR Login: Field validations, none of these can be blank - Password," +
                " Confirmation Password");
        changePassword("hotwire1", "hotwire2");
        verifyValidationMsg("CSR Change Password: " +
                "Validation error that New Password does not match Retype New Password");
    }

    public void verifyValidationMsg(String msg) {
        assertThat(msg).isEqualTo(new CSRUnlockForm(getWebdriverInstance()).getErrorMessage());
    }

    public void changePassword(String oldPass, String newPass) {
        CSRUnlockForm csrUnlockForm = new CSRUnlockForm(getWebdriverInstance());
        csrUnlockForm.setNewPassword(oldPass);
        csrUnlockForm.setConfirmPassword(newPass);
        csrUnlockForm.clickSubmit();
    }

    public void clickRiskManagementAdminLink() {
        new C3IndexPage(getWebdriverInstance()).clickOnRiskManagement();
    }

    public void clickFraudWatchListAdminLink() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).clickOnFraudWatchList();
    }

    public void selectTransactionsReviewVertical(String vertical) {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                clickOnTransactionsReviewVertical(vertical);
    }

    public void clickClearOldTransactionsAdminLink() {
        new C3RiskManagementAdminPage(getWebdriverInstance()).
                clickOnClearOldTransactionsLink();
    }

    public void clickMailInRebateAdminLink() {
        new C3IndexPage(getWebdriverInstance()).clickOnMailInRebate();
    }

    public void clickHotelCreditCardAdminLink() {
        new C3IndexPage(getWebdriverInstance()).clickOnHotelCreditCardAdmin();
    }

    public void createCSRWithEmail(String email) {
        String randName = "CSR" + System.currentTimeMillis();

        C3AdminAccManagementPage adminPage = new C3AdminAccManagementPage(getWebdriverInstance());

        adminPage.setFullName("TestRTC", "CSR");
        adminPage.setSupervisorEmail(email);
        adminPage.setUserName(randName);
        c3CsrAccount.setAgentName(randName);
        adminPage.selectLocation("Hotwire");
        adminPage.selectUserLevel("Hotel Credit Card (3007)");
        adminPage.selectUserCategory("Other");

        adminPage.submit();
    }

    public void resetPassword() {
        C3AdminAccManagementPage adminPage = new C3AdminAccManagementPage(getWebdriverInstance());
        adminPage.clickResetPassword();
    }

}
