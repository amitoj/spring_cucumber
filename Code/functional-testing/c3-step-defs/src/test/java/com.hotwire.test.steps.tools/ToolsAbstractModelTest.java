/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools;

import com.hotwire.util.db.c3.C3SearchDao;
import org.fest.assertions.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit tests for methods in ToolsAbstractModel class
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:tools-abstract-model-test-context.xml",
        "classpath*:webdriver-disposable-factory-context.xml",
         "classpath*:tools-steps-cucumber.xml" }
)
@DirtiesContext
@Ignore
public class ToolsAbstractModelTest {

    @Autowired
    private ToolsAbstractModelStub modelStub;

    private WebDriver getWebDriver() {
        return modelStub.getWebdriverInstance();
    }

    @Before
    public void setUp() throws Exception {
        getWebDriver().get(ToolsAbstractModelTest.class.getResource("/html/test-c3-with-frames-page.html").toString());
    }

    @Test
    public void testSwitchToC3Frame() throws Exception {
        modelStub.switchToFrame("c3Frame");
        Assertions.assertThat(getWebDriver().findElement(By.cssSelector("div#mainMenuContainer")).isDisplayed())
                .isTrue();
    }

    @Test
    public void testSwitchToNotesFrame() throws Exception {
        modelStub.switchToFrame("notesFrame");
        Assertions.assertThat(getWebDriver().findElement(By.cssSelector("div#caseEntryFrameContent")).isDisplayed())
                .isTrue();
    }

    @Test
    public void testSwitchToDefaultContent() throws Exception {
        modelStub.switchToDefaultContent();
        Assertions.assertThat(getWebDriver().findElement(By.cssSelector("frameset#allFrames")).isDisplayed())
                .isTrue();
        modelStub.switchToFrame("c3Frame");
        Assertions.assertThat(getWebDriver().findElement(By.cssSelector("div#mainMenuContainer")).isDisplayed())
                .isTrue();
        modelStub.switchToDefaultContent();
        Assertions.assertThat(getWebDriver().findElement(By.cssSelector("frameset#allFrames")).isDisplayed())
                .isTrue();
    }

    @Test
    public void testDatabaseIntegration() throws Exception {
        String hotelItinerary = new C3SearchDao(modelStub.getDataBaseConnection()).getHotelItinerary();
        Assertions.assertThat(hotelItinerary).isNotEmpty().isNotEqualTo("");
    }

    @After
    public void rollBackChanges() {
        getWebDriver().switchTo().defaultContent();
    }
}
