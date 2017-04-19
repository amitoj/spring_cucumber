/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

import cucumber.deps.com.thoughtworks.xstream.converters.SingleValueConverter;

public class NotConverter implements SingleValueConverter {

    public boolean canConvert(Class type) {
        return type.equals(Not.class);
    }

    @Override
    public Object fromString(String arg0) {
        if (" not".equals(arg0)) {
            return new Not(true);
        }
        return new Not(false);
    }

    @Override
    public String toString(Object arg0) {
        Not notted = (Not) arg0;
        if (notted.isNegated()) {
            return " not";
        }
        else {
            return "";
        }
    }
}
