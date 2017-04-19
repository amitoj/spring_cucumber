/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.elements;

import com.hotwire.selenium.desktop.row.WebElementWrapper;
import org.openqa.selenium.WebElement;

public class Label extends WebElementWrapper {

    public Label(WebElement element) {
        super(element);
    }

    public String getText() {
        return element.getText();
    }
}
