/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

//@XStreamConverter(value=NotConverter.class, priority=1, types={Not.class})
public class Not {
    boolean not = false;

    public Not(boolean not) {
        this.not = not;
    }

    public boolean isNegated() {
        return not;
    }
}
