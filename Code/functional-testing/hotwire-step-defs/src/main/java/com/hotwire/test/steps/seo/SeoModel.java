/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.seo;

/**
 * Created by IntelliJ IDEA.
 * User: vjong
 * Date: Jun 29, 2012
 * Time: 1:34:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SeoModel {
    void goToHotelsInformation();
    void getDestinationLinks(String set, String country);
    void verifyDestinationLinks(String source);
    void verifyDefaultSelectedRegion(String region);
}
