/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.myAccount;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/22/14
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class Last5DigitsSignInFragment extends ToolsAbstractPage {
    public Last5DigitsSignInFragment(WebDriver webdriver) {
        super(webdriver, By.className("tripFinderModule"));
    }

    public void signIn(String email, String fiveDigits) {
        setText("div.tripFinderModule input#email", email);
        setText("input#lastDigitsOfCC", fiveDigits);
        findOne("div.tripFinderModule button").click();
    }
}
