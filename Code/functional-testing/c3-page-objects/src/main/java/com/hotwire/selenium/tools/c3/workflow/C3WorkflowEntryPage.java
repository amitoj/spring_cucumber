/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.workflow;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;



/**
 * Created by v-vyulun on 8/11/2014.
 */
public class C3WorkflowEntryPage extends ToolsAbstractPage {

    public C3WorkflowEntryPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector(".inputOn"));
    }

    public String getConfirmationMsg() {
        return findOne("td#errorMessaging", DEFAULT_WAIT).getText().replaceAll("\\n", "");
    }

    public void createWorkflowEntry() {
        String title = "test" + System.currentTimeMillis();

        selectValue(".inputOn", "Bug Report", DEFAULT_WAIT);
        selectValue(".inputOn", "Other", DEFAULT_WAIT); // By.name("selectedFootprintTopic")
        findOne(By.name("selectedInquiry.selectedCategoryQnAs[1].answer"), DEFAULT_WAIT).sendKeys(title);
        findOne(By.name("selectedInquiry.selectedCategoryQnAs[2].answer"), DEFAULT_WAIT).sendKeys("Watch list");
        findOne(By.name("selectedInquiry.selectedCategoryQnAs[4].answer"), DEFAULT_WAIT).sendKeys("2 Bug");
        findOne(By.xpath(".//*[@id='note']"), DEFAULT_WAIT).sendKeys("Note " + title);
        //findOne(".formButton", DEFAULT_WAIT).click();
        findOne(By.xpath("//input[@value='submit']"), DEFAULT_WAIT).click();

    }


}
