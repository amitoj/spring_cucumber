/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.confirm;

import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.util.webdriver.functions.VisibilityOf;
import com.hotwire.util.webdriver.functions.Wait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created on 4/10/2014.
 */
public class PrintVersionConfirmationPage extends AbstractUSPage {

    public PrintVersionConfirmationPage(WebDriver webdriver) {
        super(webdriver,
                "tiles-def.car.confirm-receipt-print",
                new Wait<Boolean>(new VisibilityOf(By.cssSelector("div.printVersion "))).maxWait(5));
    }

    public String getURL() {
        // should be end with confirm-receipt-print.jsp
        return getWebDriver().getCurrentUrl().toString();
    }
}
