/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.account;

import com.hotwire.util.webdriver.po.AbstractPageObject;
import org.openqa.selenium.WebDriver;

/**
 * This class represent a us hotel confirmation page.
 *
 * @author Divya M
 * @since 2012.08
 */
public class AddToYourStayPage extends AbstractPageObject {

    public AddToYourStayPage(WebDriver webdriver) {
        super(webdriver, new String[]{"tiles-def.hotel.ems.search"});
    }
}
