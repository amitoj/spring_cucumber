/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools;

import com.hotwire.selenium.tools.c3.login.C3LoginPage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;


/**
 * Cucumber/webdriver integration test.
 * Static HTML page used.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:c3-po-test-context.xml" })
@DirtiesContext
@Ignore
public class C3PagesTest {

    @Autowired
    File testC3LoginPage;

    @Autowired
    private WebDriver webDriver;

    @Before
    public void setUp() throws Exception {
        webDriver.navigate().to(testC3LoginPage.toURI().toURL());
    }

    @Test
    public void testC3Login() {
        C3LoginPage loginPage = new C3LoginPage(webDriver);
        loginPage.login("csrcroz1", "Admin1234!");
    }

}
