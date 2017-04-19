/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.elements;

import com.hotwire.selenium.desktop.row.WebElementWrapper;
import org.openqa.selenium.WebElement;

public final class Button extends WebElementWrapper {

    public Button(final WebElement element) {
        super(element);
    }

    public void click() {
        element.click();
    }
}
