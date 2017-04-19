/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.elements;

import com.hotwire.selenium.desktop.row.WebElementWrapper;
import org.openqa.selenium.WebElement;

public final class Input extends WebElementWrapper {

    public Input(final WebElement webElement) {
        super(webElement);
    }

    public void click() {
        element.click();
    }

    public String getText() {
        return element.getAttribute("value");
    }

    public void setText(final String text) {
        element.clear();
        element.sendKeys(text);
    }
}
