/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.us.billing.car.impl.onepage;

import com.hotwire.selenium.desktop.us.billing.car.impl.accordion.AcDetailsFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * User: v-vzyryanov
 * Date: 2/1/13
 * Time: 6:35 AM
 */
public class OpDetailsFragment extends AcDetailsFragment {

    private static final String CSS_BLOCK_IDENTIFIER = "div.layoutColumnRight div#tileName-B4 div.reviewCarDetails";
    private static final String CSS_FOOT_NOTE = "div.rentalAgencyGrid div.footNote";


    public OpDetailsFragment(WebDriver webDriver) {
        super(webDriver, By.cssSelector(CSS_BLOCK_IDENTIFIER));
    }


    @Override
    public List<String> getFootNoteList() {
        List<WebElement> footNoteList = getWebDriver().findElements(By.cssSelector(CSS_FOOT_NOTE));

        ArrayList<String> footNotes = new ArrayList<>(footNoteList.size());
        for (WebElement note : footNoteList) {
            footNotes.add(note.getText());
        }
        return footNotes;
    }

    @Override
    public void continuePanel() {
        // No-op
    }
}
