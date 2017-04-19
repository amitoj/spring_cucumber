/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.utils;

import org.openqa.selenium.WebElement;

/**
 * Contains convenient methods to work with {@link WebElement}.
 * Encapsulates boilerplate code (<code>null</code> handling, ...) so we don't have to put it everywhere.
 *
 * @author Renat Zhilkibaev
 */
public final class WebElementUtils {

    private WebElementUtils() {
        // prohibit instantiation
    }

    /**
     * Returns text from web element. Never <code>null</code>.
     * Uses {@link WebElement#getText()} internally.
     * @param el - the element to get text from
     * @return element's text
     * @throws IllegalArgumentException in case {@link WebElement#getText()} returns <code>null</code>
     */
    public static String getText(WebElement el) {
        String text = el.getText();
        if (text == null) {
            throw new IllegalArgumentException("WebElement has no text (null)");
        }
        return text;
    }

    /**
     * Returns only digits from the element's text.
     * Uses {@link WebElementUtils#getText(WebElement)} internally.
     * @param el - the element to get digits from.
     * @return all digits from element's text
     * @throws IllegalArgumentException in case {@link WebElement#getText()} returns <code>null</code> or if there is
     * no digits in the text
     */
    public static long getDigitsOnly(WebElement el) {
        String text = WebElementUtils.getText(el);
        String allDigits = text.replaceAll("[^0-9]", "");
        if (allDigits.isEmpty()) {
            throw new IllegalArgumentException("WebElement text has no digits");
        }

        return Long.parseLong(allDigits);
    }

}
