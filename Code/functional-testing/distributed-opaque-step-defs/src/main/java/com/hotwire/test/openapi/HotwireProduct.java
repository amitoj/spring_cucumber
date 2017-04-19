/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.openapi;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 2/13/15
 * Time: 1:34 PM
 * Hotwire products enum
 */
public enum HotwireProduct {
    CAR("cars", "car"),
    HOTEL("hotels", "hotel");


    private HotwireProduct product;
    private String pluralName;
    private String simpleName;

    HotwireProduct(String pluralName, String simpleName) {
        this.pluralName = pluralName;
        this.simpleName = simpleName;
    }

    public static HotwireProduct validate(String solution) {
        for (HotwireProduct t : HotwireProduct.values()) {
            if (String.valueOf(t.pluralName).equals(solution)) {
                return t;
            }
            else if (String.valueOf(t.simpleName).equals(solution.toLowerCase())) {
                return t;
            }
        }
        throw new RuntimeException("Not expected reservation type: " + solution);
    }

    public String getSimpleName() {
        return this.simpleName;
    }

}
