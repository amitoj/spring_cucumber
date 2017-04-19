/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.index;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import com.hotwire.selenium.desktop.us.AbstractUSPage;

public abstract class AbstractIndexPage extends AbstractUSPage {

    protected AbstractIndexPage(WebDriver webDriver, String pageName) {
        this(webDriver, new String[] {pageName});
    }

    protected AbstractIndexPage(WebDriver webDriver, String[] pageNames) {
        super(webDriver, pageNames);
    }

    protected AbstractIndexPage(WebDriver webDriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions) {
        super(webDriver, pageNames, expectedConditions);
    }

    protected AbstractIndexPage(WebDriver webDriver, String[] pageNames, ExpectedCondition<?>[] expectedConditions,
                                int maxExpectedConditionsWait) {
        super(webDriver, pageNames, expectedConditions, maxExpectedConditionsWait);
    }

    public abstract void selectFirstDeal();
}
