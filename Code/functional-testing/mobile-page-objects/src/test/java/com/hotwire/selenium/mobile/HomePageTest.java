/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.mobile;

import com.hotwire.selenium.mobile.account.SignInPage;
import com.hotwire.util.webdriver.WebDriverManager;
import com.hotwire.util.webdriver.predicates.NavigateTo;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Mobile POs testing
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
        {
                "classpath:mobile-po-test-context.xml"
        }
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Ignore
public class HomePageTest {

    @Autowired
    NavigateTo<MobileHotwireHomePage> navigate;

    @Autowired
    WebDriver webdriverInstance;

    @Autowired
    WebDriverManager manager;

    @Test
    public void testLoginViaHeader() {
        MobileHotwireHomePage home = navigate.apply(webdriverInstance);
        SignInPage signInPage = home.navigateToSignInOrRegister();
        signInPage.withUserName("savedCreditCard@hotwire.com").withPassword("password").signIn();
        assertThat(home.signedIn()).as("We should be on the home page and logged in").isTrue();
    }

    @Test
    public void testLoginViaHeaderWithBadPassword() {
        MobileHotwireHomePage home = navigate.apply(webdriverInstance);
        SignInPage signInPage = home.navigateToSignInOrRegister();
        signInPage.withUserName("savedCreditCard@hotwire.com").withPassword("notPassword").signIn();
        assertThat(signInPage.hasAuthenticationError())
                .as("We should have an error on password").isTrue();
    }
}
