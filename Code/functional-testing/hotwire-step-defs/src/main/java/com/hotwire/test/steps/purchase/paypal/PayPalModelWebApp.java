/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.purchase.paypal;

import com.hotwire.selenium.desktop.paypal.PayPalConfirmationPage;
import com.hotwire.selenium.desktop.paypal.PayPalSandboxPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.util.webdriver.functions.PageName;
import cucumber.api.PendingException;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author v-ozelenov
 * @since 2013.05
 */
public class PayPalModelWebApp extends WebdriverAwareModel implements PayPalModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayPalModelWebApp.class.getName());
    private String payPalDeveloperUrl;
    private String adminEmail;
    private String sandboxEmail;
    private String password;
    private String username;


//    /** DEPRECATED
//    * Signing in PayPal developer
//    * Handling PayPal pop-up login window
//    * Back to main window when pop-up disappears
//    */
//    @Override
//    public void loginPayPalAdmin() {
//        WebDriver webDriver = getWebdriverInstance();
//        webDriver.navigate().to(getPayPalDeveloperUrl());
//
//        PayPalDeveloperPage ppDev = new PayPalDeveloperPage(webDriver);
//        ppDev.clickOnPayPalLogIn();
//        String currentHandler = webDriver.getWindowHandle();
//        for (String handle : webDriver.getWindowHandles()) {
//            webDriver.switchTo().window(handle);
//            LOGGER.info("Window title: "+webDriver.getTitle());
//            if (webDriver.getCurrentUrl().contains("https://www.paypal.com/webapps/auth/loginauth")) {
//                LOGGER.info("Handling LogIn pop-up window");
//                PayPalDeveloperLoginPage ppLogIn = new PayPalDeveloperLoginPage(webDriver);
//                ppLogIn.logIn(getAdminEmail(), getPassword());
//                try {
//                    Thread.sleep(5000);
//                    LOGGER.info("Waiting...");
//                } catch (InterruptedException e) {
//                    /**
//                     * Wait while all popup windows were loaded...
//                     * Doesn't work without it.
//                     */
//                }
//
//                webDriver.switchTo().window(currentHandler);
//                LOGGER.info("Switch back to main window");
//            }
//        }
//
//    }

    // login on PayPal sandbox website
    @Override
    public void loginPayPalSandbox() {
        LOGGER.info("Login to PayPal sandbox");
        PayPalSandboxPage payPalSandbox = new PayPalSandboxPage(getWebdriverInstance());
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            /**
             * Wait while all popup windows were loaded...
             * Doesn't work without it.
             */
        }
        LOGGER.info("Waiting...");
        payPalSandbox.loginPayPalSandbox(sandboxEmail, password);
        payPalSandbox.confirmPayPalSandbox();

        try {
            if (new PageName().apply(getWebdriverInstance()).contains(".search.results") &&
                    getWebdriverInstance().findElements(By.cssSelector(".errorMessage")).size() > 0) {
                throw new PendingException("Inventory check redirected to results page due to sold out.");
            }
        }
        catch (RuntimeException e) {
            // Do nothing. Paypal sandbox confirmation page won't have a page name.
        }
    }

    // return to Hotwire website
    @Override
    public void returnToHotwireFromPaypal() {
        LOGGER.info("Returning to Hotwire...");
        new PayPalConfirmationPage(getWebdriverInstance()).returnToStore();
    }

    //    cancelPayPalSandbox
    @Override
    public void cancelPayPalSandbox() {
        LOGGER.info("Cancel PayPal sandbox");
        new PayPalSandboxPage(getWebdriverInstance()).cancelPayPalSandbox();
    }

    public String getPayPalDeveloperUrl() {
        return payPalDeveloperUrl;
    }

    public void setPayPalDeveloperUrl(String payPalDeveloperUrl) {
        this.payPalDeveloperUrl = payPalDeveloperUrl;
    }

    public String getSandboxEmail() {
        return sandboxEmail;
    }

    public void setSandboxEmail(String sandboxEmail) {
        this.sandboxEmail = sandboxEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


