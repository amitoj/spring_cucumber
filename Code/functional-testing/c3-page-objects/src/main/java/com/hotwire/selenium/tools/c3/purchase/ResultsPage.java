/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 10/17/14
 * Time: 12:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ResultsPage {
    void selectFirstResult();
    String getReferenceNumber();
    String getTypeOfFirstResult();
}
