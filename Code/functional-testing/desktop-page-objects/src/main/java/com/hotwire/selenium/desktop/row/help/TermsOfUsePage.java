/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row.help;

import com.hotwire.selenium.desktop.row.AbstractRowPage;
import com.hotwire.selenium.desktop.row.elements.Link;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class TermsOfUsePage extends AbstractRowPage {

    @FindBy(css = "a[href='http://www.diplomatie.gouv.fr/fr/conseils-aux-voyageurs_909/index.html']")
    public Link diplomatie;

    public TermsOfUsePage(WebDriver webDriver) {
        super(webDriver, "tile.about.terms");
    }

    public void clickFranceDiplomatieLink() {
        diplomatie.click();
    }
}
