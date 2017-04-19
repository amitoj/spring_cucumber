/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.elements;

import com.hotwire.selenium.desktop.row.WebElementWrapper;
import org.openqa.selenium.WebElement;

public final class Checkbox extends WebElementWrapper {

    public Checkbox(final WebElement element) {
        super(element);
    }

    public void check() {
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void uncheck() {
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isChecked() {
        return element.isSelected();
    }
}
