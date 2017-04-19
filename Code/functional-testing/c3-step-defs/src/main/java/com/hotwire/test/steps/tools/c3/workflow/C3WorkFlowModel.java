/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.workflow;

import com.hotwire.selenium.tools.c3.C3ViewResources;
import com.hotwire.selenium.tools.c3.customer.C3CustomerMainPage;
import com.hotwire.selenium.tools.c3.workflow.C3WorkflowEntryPage;
import com.hotwire.selenium.tools.c3.workflow.C3WorkflowForm;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3CaseNotesDao;
import com.hotwire.util.db.c3.C3ItineraryInfoDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;


/**
 * Created by v-ozelenov on 4/10/2014.
 */
public class C3WorkFlowModel extends ToolsAbstractModel {
    private static  final String CONF_LPG_MSG_TMP = "Claim was accepted for the amount of .*. " +
            "Retail claims are paid after the trip is complete and verification that the itinerary was used." +
            "Follow-up entry .* was successfully submitted.";

    private static  final String FAILED_LPG_MSG = "This claim cannot be processed as a claim has " +
            "already been made against this reservation";

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private HotwireProduct hotwireProduct;
    public void clickOnCreateWorkflow() {
        c3ItineraryInfo.setTotalCost(hotwireProduct.getItineraryPage(getWebdriverInstance()).getTotalCost());
        c3ItineraryInfo.setCurrencyCode(new C3ItineraryInfoDao(getDataBaseConnection())
                .getItineraryCurrency(c3ItineraryInfo.getItineraryNumber()));
        new C3CustomerMainPage(getWebdriverInstance()).clickOnCreateWorkFlow();
    }

    public void createLPGWorkFlow() {
        C3WorkflowForm form = new C3WorkflowForm(getWebdriverInstance());
        switch (hotwireProduct.getProductVertical()) {
            case CAR:
                form.createCarLPGWorkFlow(c3ItineraryInfo.getTotalCost(), c3ItineraryInfo.getCurrencyCode());
                break;
            case HOTEL:
                form.createHotelLPGWorkFlow(c3ItineraryInfo.getTotalCost(), c3ItineraryInfo.getCurrencyCode());
                break;
            default:
                throw new UnimplementedTestException("Test not implemented");
        }
    }

    public void verifyLPGWorkFlow() {
        assertThat(new C3WorkflowForm(getWebdriverInstance()).getConfirmationMsg())
                .matches(CONF_LPG_MSG_TMP);
    }

    public void verifyLPGWorkFlowAlreadyCreated() {
        assertThat(new C3WorkflowForm(getWebdriverInstance()).getConfirmationMsg())
                .isEqualTo(FAILED_LPG_MSG);
    }

    public void clickOnWorkflowLink() {
        new C3ViewResources(getWebdriverInstance()).clickOnWorkflowLink();
    }

    public void createWorkflowEntry() {
        C3WorkflowEntryPage workflowEntryPage = new C3WorkflowEntryPage(getWebdriverInstance());
        workflowEntryPage.createWorkflowEntry();
        c3ItineraryInfo.setWorkflowEntry(workflowEntryPage.getConfirmationMsg().split(" ")[2]);
    }

    public void checkWorkflowEntry() {

        String actualText = new C3WorkflowEntryPage(getWebdriverInstance())
                .getConfirmationMsg();
        String expected = "Follow-up entry \\d{6,8} was successfully submitted.";
        assertThat(Pattern.matches(expected, actualText))
                .as("Actual - " + actualText + ", but expected - " + expected).isTrue();
    }

    public void checkWorkflowEntryInDB() {
        String entry = c3ItineraryInfo.getWorkflowEntry();
        boolean isActualDB = new C3CaseNotesDao(getDataBaseConnection()).
                isWorkflowEntryInDB(entry);
        assertThat(isActualDB).as("Entry number- " + entry + ", not contains in DB").isTrue();
    }
}
