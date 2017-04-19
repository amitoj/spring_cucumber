/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row;

import org.openqa.selenium.WebElement;

public abstract class WebElementWrapper {

    protected final WebElement element;

    public WebElementWrapper(final WebElement element) {
        this.element = element;
    }

    public boolean isDisplayed() {
        return element.isDisplayed();
    }

    public String getAttribute(String attribute) {
        return element.getAttribute(attribute);
    }
}
