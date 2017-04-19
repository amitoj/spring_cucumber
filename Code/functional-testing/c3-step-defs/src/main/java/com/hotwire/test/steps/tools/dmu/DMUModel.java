/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.dmu;

import com.hotwire.selenium.tools.dmu.DMUIndexPage;
import com.hotwire.selenium.tools.dmu.DMULoginPage;
import com.hotwire.selenium.tools.dmu.DMUProcedureConfirmationPage;
import com.hotwire.selenium.tools.dmu.DMUProcedurePage;
import com.hotwire.selenium.tools.dmu.DMUProcedurePopUp;
import com.hotwire.selenium.tools.dmu.DMUProceduresList;
import com.hotwire.selenium.tools.dmu.DMUSearchBox;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.AppMetaData;
import com.hotwire.test.steps.tools.bean.dmu.DMUAccountInfo;
import com.hotwire.test.steps.tools.bean.dmu.DMUProcedureInfo;
import com.hotwire.util.db.dmu.DmuDao;
import org.fest.assertions.Assertions;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Data Management Utility model
 */
public class DMUModel extends ToolsAbstractModel {
    @Resource
    Map<String, DMUAccountInfo> dmuProfiles;

    @Autowired
    private AppMetaData appMetaData;

    @Autowired
    private DMUProcedureInfo dmuProcedureInfo;

    public DMUAccountInfo getDMUInfo() {
        return dmuProfiles.get(appMetaData.getEnvironment());
    }

    public void loginIntoDMU() {
        new DMULoginPage(getWebdriverInstance()).login(getDMUInfo().getUsername(), getDMUInfo().getPassword());
    }

    public void loginIntoDMUWithFakeUser() {
        getDMUInfo().setUsername("blablablabla");
        getDMUInfo().setPassword("superPassword");
        new DMULoginPage(getWebdriverInstance()).login(getDMUInfo().getUsername(), getDMUInfo().getPassword());
    }

    public void submitDMULogin() {
        new DMULoginPage(getWebdriverInstance()).clickSubmit();
    }

    public void verifyDMUErrorMsg() {
        Assertions.assertThat(new DMULoginPage(getWebdriverInstance()).getErrorMsg()).isEqualTo("Validation Error");
        Assertions.assertThat(new DMULoginPage(getWebdriverInstance()).getErrorMsgItems())
                .contains("Username is required")
                .contains("Password is required");
    }

    public void verifyDMUWrongUserMSG() {
        Assertions.assertThat(new DMULoginPage(getWebdriverInstance()).getErrorMsg()).isEqualTo("Validation Error");
        Assertions.assertThat(new DMULoginPage(getWebdriverInstance()).getErrorMsgItems())
                .contains("Invalid username and/or password, please try again");
    }

    public void verifyDMUIndexPage() {
        Assertions.assertThat(new DMUIndexPage(getWebdriverInstance()).getInvitationMsg()).contains("Welcome");
    }


    public void searchInDMU(String searchWord) {
        new DMUSearchBox(getWebdriverInstance()).searchProcedure(searchWord);
    }

    public void verifyProcedurePopUp() {
        List<WebElement> links = new DMUProcedurePopUp(getWebdriverInstance()).getProceduresLinks();
        Assertions.assertThat(links).isNotEmpty();
        //Need to verify this later when keyword search framework will be fixed
//        for (WebElement link : links) {
//            String linkText = link.getText();
//            Assertions.assertThat(linkText).containsIgnoringCase("hotel");
//        }
    }

    public void chooseFirstProcedure() {
        String procedure = new DMUProcedurePopUp(getWebdriverInstance()).clickOnFirstProcedure();
        dmuProcedureInfo.setProcedureName(procedure);
    }

    public void verifyProcedurePage() {
        Assertions.assertThat(new DMUProcedurePage(getWebdriverInstance()).getTitle())
                .isEqualToIgnoringCase(dmuProcedureInfo.getProcedureName());
    }

    public void changePartnerVendor() {
        DMUProcedurePage dmuProcedurePage = new DMUProcedurePage(getWebdriverInstance());
        dmuProcedurePage.chooseCarVendor(dmuProcedureInfo.getCarVendor());
        dmuProcedureInfo.setCarVendorPartnerStatus(getPartnerStatus());
        dmuProcedurePage.choseCarVendorPartnerStatus(dmuProcedureInfo.getNewPartnerStatus());
        dmuProcedurePage.setDMUScriptName("BDD");
    }

    private String getPartnerStatus() {
        return new DmuDao(getDataBaseConnection()).getCarVendorPartnerStatus(dmuProcedureInfo.getCarVendor());
    }

    public void chooseDmuMenu(String menuItem) {
        new DMUIndexPage(getWebdriverInstance()).selectMenu(menuItem);
    }

    public void chooseProcedureFromList(String procedureName) {
        new DMUProceduresList(getWebdriverInstance()).clickOnProcedureLink(procedureName);
        dmuProcedureInfo.setProcedureName(procedureName);

    }

    public void runProcedureOnNonProd() {
        new DMUProcedurePage(getWebdriverInstance()).submit();
    }

    public void verifyProcedureRunSuccessful() {
        new DMUProcedureConfirmationPage(getWebdriverInstance());
    }

    public void commitProcedure() {
        new DMUProcedureConfirmationPage(getWebdriverInstance()).submit();
    }

    public void verifyProcedureInDb() {
        Assertions.assertThat(getPartnerStatus()).isEqualTo(dmuProcedureInfo.getNewPartnerStatus());
    }
}
