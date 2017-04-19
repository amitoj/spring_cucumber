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
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 3/6/14
 * Time: 11:48 PM
 * Page that show search results for Case Notes
 */
public class C3CaseNotesResultsPage extends ToolsAbstractPage {

    public C3CaseNotesResultsPage(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div.caseQueryResults"));
    }

    public C3CaseNotesResultsPage(WebDriver webdriver, By keyElement) {
        super(webdriver, keyElement);
    }

    public String getCaseNoteID() {
        return findOne("div.caseNoteDescriptionItemContent").getText();
    }

    public List<String> getCaseNoteContent() {
        List<String> outputContent = new ArrayList<>();
        List<WebElement> webElementsContent = findMany(".caseNoteDescriptionItem div.caseNoteDescriptionItemContent");
        for (WebElement elem: webElementsContent) {
            outputContent.add(elem.getText());
        }
        return outputContent;
    }

    public String getCaseNoteEmail() {
        return findOne("span.contentUserAccount a").getText();
    }

    public void verifySearchResults() {
        findOne("div.caseNoteDescription");
    }

    public String getCaseNotesDescription() {
        return findOne("div[id*='caseNoteDescription_']", DEFAULT_WAIT).getText();
    }
}
