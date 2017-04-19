/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.hotDollars;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


/**
 * Page Object
 */
public class C3MassHotDollarsReport extends ToolsAbstractPage {
    public C3MassHotDollarsReport(WebDriver webdriver) {
        super(webdriver, By.cssSelector("div#addMassHotdollarsPopup"));
    }

    public List<String> getEmails(List<WebElement> elements) {
        elements.remove(0);
        List<String> emails = new ArrayList<>();
        for (WebElement element : elements) {
            emails.add(element.getText());
        }
        return emails;

    }

    public List<String> getSuccessfulEmails() {
        return getEmails(findMany("ul.success li"));
    }

    public List<String> getNonExistEmails() {
        return getEmails(findMany("ul.notExisted li"));
    }
}
