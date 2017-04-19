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
 * Created with IntelliJ IDEA.
 * User: v-vyulun
 * Date: 7/24/14
 * Time: 3:05 PM
 */

public class C3HotelWorkflowPage extends ToolsAbstractPage {

    private static final String SUCCESSFUL_MSG = "#errorMessaging";

    public C3HotelWorkflowPage(WebDriver webdriver) {
        super(webdriver, By.name("selectedFootprintTopic"));
    }


    public void createWorkflow(String route, String type, String severity,
                               String title, String name, String phone) {
        selectValue(".formField.workflowRoute>select", route);
        findOne("#workflowType", DEFAULT_WAIT);
        selectValue("#workflowType>select", type);
        findOne(By.name("selectedInquiry.selectedCategoryQnAs[0].answer"), DEFAULT_WAIT).sendKeys(severity);
        findOne(By.name("selectedInquiry.selectedCategoryQnAs[2].answer"), DEFAULT_WAIT).sendKeys(title);
        findOne(By.name("selectedInquiry.selectedCategoryQnAs[3].answer"), DEFAULT_WAIT).sendKeys(name);
        findOne(By.name("selectedInquiry.selectedCategoryQnAs[4].answer"), DEFAULT_WAIT).sendKeys(phone);
        findOne(By.name("selectedInquiry.note")).sendKeys("this is the test");
        findOne(".formButton").click();
    }

    public void createWorkflowBugReport(String type, String category, String severity, String title) {
        selectValue("select[name='selectedFootprintTopic']", type);
        freeze(1);
        selectValue("select[name='selectedFootprintCategory']", category);
        selectValue("select[name='selectedInquiry.selectedCategoryQnAs[2].answer']", severity);
        findOne("input[name='selectedInquiry.selectedCategoryQnAs[0].answer']", DEFAULT_WAIT).sendKeys(title);
        findOne("#note").sendKeys("this is the test");
        findOne("input[value='submit'] ").click();
    }

    public String getSuccessfulMsg() {
        return findOne(SUCCESSFUL_MSG, DEFAULT_WAIT).getText();
    }
}

