/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class provides the methods to access the payment method panel on us billing page.
 *
 * @author Alok Gupta
 * @since 2012.04
 */
public class PaymentMethodFragment extends AbstractUSPage {

    @FindBy(name = "paymentForm.selectedCardType")
    private WebElement ccType;

    @FindBy(name = "btnPaymentInfo")
    private WebElement btnPaymentInfo;

    public PaymentMethodFragment(WebDriver webDriver) {
        super(webDriver, By.id("billingPanelPaymentInfoComp"));
    }

    public void clickContinueButton() {
        btnPaymentInfo.click();
    }

    private boolean isNewPaymentType(WebElement paymentTypeRadio) {
        return paymentTypeRadio.getAttribute("value").equals("0");
    }
}

