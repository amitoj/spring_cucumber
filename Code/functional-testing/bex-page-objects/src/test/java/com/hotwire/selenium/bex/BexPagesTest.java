/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.bex;

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
import java.net.MalformedURLException;


/**
 * Cucumber/webdriver integration test.
 * Static HTML page used.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bex-po-test-context.xml" })
@DirtiesContext
@Ignore
public class BexPagesTest {

    @Autowired
    File testBexAbacusOverridePage;

    @Autowired
    File testBexHotelPage;

    @Autowired
    private WebDriver webDriver;


    @Before
    public void setUp() throws Exception {
        webDriver.navigate().to(testBexAbacusOverridePage.toURI().toURL());
        webDriver.navigate().to(testBexHotelPage.toURI().toURL());
    }

    @Test
    public void testBexHotel() throws MalformedURLException {
        webDriver.navigate().to(testBexHotelPage.toURI().toURL());
    }

}







