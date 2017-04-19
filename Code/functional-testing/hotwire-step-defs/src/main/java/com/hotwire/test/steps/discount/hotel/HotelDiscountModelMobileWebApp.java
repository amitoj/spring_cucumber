/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.discount.hotel;

import com.hotwire.test.steps.discount.DiscountModelTemplate;

/**
 * Mobile hotel discount model implementation.
 */
public class HotelDiscountModelMobileWebApp extends DiscountModelTemplate {

    public void setupValidCode() {
        String code = createDiscount(SampleType.VALID);
        sessionModifingParams.addParameter("dccid=" + code);
    }
}
