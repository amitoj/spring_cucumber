/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.dmu;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 * User: ozelenov
 * Date: 9/4/14
 * Time: 6:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DMUSearchBox extends ToolsAbstractPage {
    public DMUSearchBox(WebDriver webdriver) {
        super(webdriver, By.className("headerSearchFormContainer"));
    }

    public void searchProcedure(String searchWord) {
        setText("input#headerSearchVal", searchWord);
    }
}
