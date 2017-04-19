/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing.car.impl.accordion;

import com.hotwire.selenium.desktop.us.billing.car.CarBillingPageProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hotwire.util.webdriver.functions.IsAjaxDone;
import com.hotwire.util.webdriver.po.AbstractPageObject;
import com.hotwire.util.webdriver.ui.WebDriverWait;

/**
 * User: v-vzyryanov
 * Date: 6/18/14
 * Time: 7:36 AM
 */
public class HotDollarPaymentFragment extends AbstractPageObject {

    private static final By CONTAINER = By.xpath("//div[@id='paymentSection']//div[@class='hotDollars']");
    private static final String USE_IT = "hotDollarsCheckbox";
    private static final String MESSAGE = ".hotDollars";

    public HotDollarPaymentFragment(WebDriver webdriver) {
        super(webdriver, CONTAINER, new String[]{CarBillingPageProvider.TILES});
    }

    public void useIt() {
        WebElement useIt = getHotDollarsButton();
        if (!useIt.isSelected()) {
            useIt.click();
            new WebDriverWait(getWebDriver(), getTimeout())
                    .until(new IsAjaxDone());
        }
    }

    public WebElement getHotDollarsButton() {
        return getWebDriver().findElement(By.id(USE_IT));
    }

    public String getMessage() {
        return getWebDriver().findElement(By.cssSelector(MESSAGE)).getText();
    }
}
