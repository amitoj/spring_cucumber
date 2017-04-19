/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.legal;

/**
 * User: v-ngolodiuk
 * Since: 4/4/14
 */
public class LegalParametersImpl implements LegalParameters {
    private String copy;

    @Override
    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getCopy() {
        return copy;
    }
}
