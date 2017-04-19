/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static Float parseFloat(String text) {
        Pattern pattern = Pattern.compile("(\\d+([,\\.\\s]\\d+)*)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String group = matcher.group(0);
            group = group.replaceAll("[,\\.\\s]", "");
            int length = group.length();
            group = group.substring(0, length - 2) + "." + group.substring(length - 2, length);
            return Float.parseFloat(group);
        }

        return null;
    }
}
