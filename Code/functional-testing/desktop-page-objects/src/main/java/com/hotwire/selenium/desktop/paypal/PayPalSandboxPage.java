/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.paypal;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import com.hotwire.util.webdriver.po.AbstractPage;

/**
 * @author v-ozelenov
 * @since 2013.05
 */
public class PayPalSandboxPage extends AbstractPage {

    @FindBy(id = "loadLogin")
    private WebElement paypalSandboxLoginBtn;

    @FindBy(id = "login_email")
    private WebElement paypalSandboxEmail;

    @FindBy(id = "login_password")
    private WebElement paypalSandboxPassword;

    @FindBy(id = "submitLogin")
    private WebElement paypalSandboxLogin;

    @FindBy(id = "continue")
    private WebElement paypalSandboxContinue;

    @FindBy(name = "cancel_return")
    private WebElement paypalSandboxCancel;


    public PayPalSandboxPage(WebDriver webdriver) {
        super(webdriver, new Wait<Boolean>(new VisibilityOf(By.id("loadLogin"))).maxWait(20));
    }

    public void loginPayPalSandbox(String pEmail, String pPass) {
        logger.info("PayPal loging with \"" + pEmail + ":" + pPass + "\"");
        try {

            paypalSandboxLoginBtn.click();

        }
        catch (NoSuchElementException e) {
            getClickHereLink().click();
            paypalSandboxLoginBtn.click();
        }

        new Wait<Boolean>(new VisibilityOf(By.id("login_email"))).maxWait(20);
        paypalSandboxEmail.clear();
        paypalSandboxEmail.sendKeys(pEmail);


        paypalSandboxPassword.clear();
        paypalSandboxPassword.sendKeys(pPass);
        String pass = paypalSandboxPassword.getAttribute("value");
        paypalSandboxLogin.click();
    }

    public void confirmPayPalSandbox() {
        paypalSandboxContinue.click();

        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            getClickHereLink().click();
            /**
             * Wait while all popup windows were loaded...
             * Doesn't work without it.
             */
        }

    }

    public void cancelPayPalSandbox() {
        paypalSandboxCancel.click();
    }

    private WebElement getClickHereLink()  {
        return getWebDriver().findElement(By.partialLinkText("click here"));
    }
}
