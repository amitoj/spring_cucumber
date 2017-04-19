/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.tools.c3;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 9/30/13
 * Time: 4:09 AM
 */
public class C3ErrorMessagingFragment extends ToolsAbstractPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(C3ErrorMessagingFragment.class.getName());
    private static final String CSS_LOCATOR = "td#errorMessaging, div.errorMessages";

    public C3ErrorMessagingFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(CSS_LOCATOR));
    }

    public List<String> getListOfErrors() {
        List<String> out = new ArrayList<String>();
        StringBuilder sb = new StringBuilder("List of...\n");
        try {
            WebElement errorContainer = findOne("div.errorMessages");
            if (!errorContainer.getText().isEmpty()) {
                List<WebElement> errorMessages = findMany("div.errorMessages ul li");
                for (WebElement elm : errorMessages) {
                    out.add(elm.getText());
                    sb.append(elm.getText() + "\n");
                }
            }
        }
        catch (NoSuchElementException e) {
            if (findMany("#errorMessaging").size() > 0) {
                out.add(findOne("#errorMessaging").getText());
                sb.append(out.toString() + "\n");
            }
            else {
                sb.append("No error messages are present on the page!");
            }
        }
        LOGGER.info(sb.toString());
        return out;
    }
}
