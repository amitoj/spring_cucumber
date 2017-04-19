/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.helpCenter.Salesforce;

import com.hotwire.selenium.desktop.helpCenter.HelpCenterAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * User: v-asnitko
 * Date: 7/29/13
 * Time: 6:16 AM
 */
public class SFHCIndexPage extends HelpCenterAbstractPage {

    public SFHCIndexPage(WebDriver webDriver) {
        super(webDriver, By.className("searchBox"));
    }

    public SFHCIndexPage(WebDriver webDriver, By keyElement) {
        super(webDriver, keyElement);
    }

    public void clickAnyArticle() {
        findOne("div.homeContent ul li a[href*='/knowledgebase/articles/']", HelpCenterAbstractPage.EXTRA_WAIT).click();
    }

    public void searchWithText(String text) {
        setTextAndSubmit("input#searchValue", text);
    }

    public String verifyErrorMessage() {
        return findOne("div.errorText", HelpCenterAbstractPage.EXTRA_WAIT).getText();
    }


    public String verifySearchResults() {
        return findOne("div.article", 4).getText();
    }

    public boolean verifyTravelAdvisories() {
        return findOne("div.travelAdvisores", HelpCenterAbstractPage.DEFAULT_WAIT).isDisplayed();
    }
}
