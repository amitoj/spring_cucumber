/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.mobile.account;

import com.hotwire.selenium.mobile.MobileAbstractPage;
import org.openqa.selenium.WebDriver;

public class MobilePasswordAssistancePage extends MobileAbstractPage {

    public MobilePasswordAssistancePage(WebDriver webdriver) {
        super(webdriver, "tile.account.pwdassistance");
    }
}
