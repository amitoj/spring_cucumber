/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row;

import com.hotwire.selenium.desktop.row.elements.Link;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class GlobalFooter extends AbstractRowPage {

    @FindBy(css = "a.footerLink[href*='about/terms#formalites']")
    public Link formalites;

    public GlobalFooter(WebDriver webDriver) {
        super(webDriver, By.cssSelector("div.pageFooter"));
    }

    public void clickTravelFormalitiesLink() {
        formalites.click();
    }
}
