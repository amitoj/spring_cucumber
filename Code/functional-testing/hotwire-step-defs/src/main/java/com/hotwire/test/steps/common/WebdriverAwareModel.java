/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.common;

import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;


/**
 * @author v-mzabuga
 * @since 7/17/12
 */
public abstract class WebdriverAwareModel {

    @Autowired(required = false)
    private SimpleJdbcDaoSupport databaseSupport;

    private WebDriver webdriverInstance;
    private String windowHandler;

    public WebDriver getWebdriverInstance() {
        return webdriverInstance;
    }

    public void setWebdriverInstance(WebDriver webdriverInstance) {
        this.webdriverInstance = webdriverInstance;
    }

    /**
     * Logging session ID for searching logs on View Server
     * Use grep -i YOUR_JSESSION_ID tcbiz.log
     * Path to logs such as  /p4/phoenix/main/deploy/dev
     * For QA environment use Splunk
     */
    public void logSession(String msg) {
        try {
            String session = getWebdriverInstance().manage().getCookieNamed("JSESSIONID").getValue();
            String host = getWebdriverInstance().manage().getCookieNamed("JSESSIONID").getDomain();
            StackTraceElement stack = Thread.currentThread().getStackTrace()[2];
            String className = stack.getClassName();
            LoggerFactory.getLogger(Class.forName(className))
                         .info("(JSESSIONID: {}, HOST: {}) {}",
                               session,
                               host,
                               msg);
        }
        catch (ClassNotFoundException | RuntimeException e) {
            System.out.println("Session logging failed");
        }
    }

    /**
     * This connection should be passed to DAO classes of DatabaseSupport module
     * More details here:  https://redspace.jiveon.com/docs/DOC-11616
     */
    public SimpleJdbcDaoSupport getDataBaseConnection() {
        return databaseSupport;
    }

    public void switchToNewWindow() {
        windowHandler = getWebdriverInstance().getWindowHandle();
        for (String winHandle : getWebdriverInstance().getWindowHandles()) {
            if (!winHandle.equals(windowHandler)) {
                getWebdriverInstance().switchTo().window(winHandle);
            }
        }
    }

    public void switchToDefaultWindow() {
        getWebdriverInstance().switchTo().window(windowHandler);
    }

}
