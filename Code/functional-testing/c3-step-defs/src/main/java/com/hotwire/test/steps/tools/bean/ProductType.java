/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.bean;

import com.hotwire.testing.UnimplementedTestException;

import java.io.Serializable;


/**
* This enum contains codes of Hotwire product types.
 * Aka retail, opaque
 */
public enum ProductType implements Serializable {
    RETAIL('N', "retail", "standard rate", "Carrier"),
    OPAQUE('Y', "opaque", "hot rate", "Hotwire");

    private ProductType opacity;
    private char opacityCode;
    private String productType;
    private String rateType;
    private String billedBy;

    ProductType(char opacityCode, String productType, String rateType, String billedBy) {
        this.opacityCode = opacityCode;
        this.productType = productType;
        this.rateType = rateType;
        this.billedBy = billedBy;
    }

    public static ProductType validate(String solution) {
        for (ProductType t : ProductType.values()) {
            if (String.valueOf(t.opacityCode).equals(solution)) {
                return t;
            }
            else if (String.valueOf(t.productType).equals(solution.toLowerCase())) {
                return t;
            }
            else if (String.valueOf(t.rateType).equals(solution.toLowerCase())) {
                return t;
            }
            else if (String.valueOf(t.billedBy).equals(solution.toLowerCase())) {
                return t;
            }
        }
        throw new UnimplementedTestException("Not expected reservation type: " + solution);
    }

    public ProductType getOpacity() {
        return opacity;
    }

    public void setOpacity(ProductType productType) {
        this.opacity = productType;
    }

    public String getOpacityCode() {
        return String.valueOf(opacityCode);
    }

    public String getProductType() {
        return productType;
    }


    public String getRateType() {
        return rateType;
    }

    public String getBilledBy() {
        return billedBy;
    }
}
