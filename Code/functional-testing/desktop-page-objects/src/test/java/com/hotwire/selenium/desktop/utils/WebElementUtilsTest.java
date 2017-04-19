/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.utils;

import java.util.List;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

/**
 * Tests {@link WebElementUtils}.
 *
 * @author Renat Zhilkibaev
 */
public class WebElementUtilsTest {

    @Test
    public void getText() {

        final String expected = "abc";

        WebElement el = new NoOpWebElement() {
            @Override
            public String getText() {
                return expected;
            }
        };

        String actual = WebElementUtils.getText(el);

        Assertions.assertThat(actual).as("element text").isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getText_nullCase() {

        WebElement el = new NoOpWebElement() {
            @Override
            public String getText() {
                return null;
            }
        };

        WebElementUtils.getText(el);
    }

    @Test
    public void getDigitsOnly() {

        WebElement el = new NoOpWebElement() {
            @Override
            public String getText() {
                return "$45.12EU";
            }
        };

        long actual = WebElementUtils.getDigitsOnly(el);

        Assertions.assertThat(actual).as("element digits").isEqualTo(4512l);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDigitsOnly_noDigits() {

        WebElement el = new NoOpWebElement() {
            @Override
            public String getText() {
                return "$abcEU";
            }
        };

        WebElementUtils.getDigitsOnly(el);
    }

    /**
     * NoOp implementation. Does nothing.
     * @author Renat Zhilkibaev
     */
    private static class NoOpWebElement implements WebElement {

        @Override
        public void click() {
        }

        @Override
        public void submit() {
        }

        @Override
        public void sendKeys(CharSequence... keysToSend) {
        }

        @Override
        public void clear() {
        }

        @Override
        public String getTagName() {
            return null;
        }

        @Override
        public String getAttribute(String name) {
            return null;
        }

        @Override
        public boolean isSelected() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }

        @Override
        public String getText() {
            return null;
        }

        @Override
        public List<WebElement> findElements(By by) {
            return null;
        }

        @Override
        public WebElement findElement(By by) {
            return null;
        }

        @Override
        public boolean isDisplayed() {
            return false;
        }

        @Override
        public Point getLocation() {
            return null;
        }

        @Override
        public Dimension getSize() {
            return null;
        }

        @Override
        public String getCssValue(String propertyName) {
            return null;
        }

    }

}
