/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium.desktop.us.billing;

/**
 * User: v-vzyryanov
 * Date: 5/12/14
 * Time: 2:51 AM
 */
public interface AdditionalFeatures {

    AdditionalFeatures withInsurance(boolean insurance);

    void continuePanel();

}
