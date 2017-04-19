/**
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.bean;

import com.hotwire.testing.UnimplementedTestException;

import java.io.Serializable;


/**
* This enum contains codes of Hotwire verticals.
 * Aka air, hotel, car, etc.
 */
public enum ProductVertical implements Serializable {
    HOTEL('H', "hotel"),
    AIR('A', "air"),
    CAR('C', "car");
//    R('R', "Cruise", "Cruises"),
//    P('P', "Package", "Packages"),
//    F('F', "FlexibleAir", "FlexibleAir");


    private ProductVertical productVertical;
    private char productCode;
    private String productName;

    ProductVertical(char productChar, String productName) {
        this.productCode = productChar;
        this.productName = productName;
    }

    public ProductVertical getProductVertical() {
        return productVertical;
    }

    public void setProductVertical(ProductVertical productVertical) {
        this.productVertical = productVertical;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCode() {
        return String.valueOf(productCode);
    }



    public static ProductVertical validate(String vertical) {
        for (ProductVertical t : ProductVertical.values()) {
            if (String.valueOf(t.productCode).equals(vertical)) {
                return t;
            }
            else if (String.valueOf(t.productName).equals(vertical.toLowerCase())) {
                return t;
            }
        }
        throw new UnimplementedTestException("Not expected reservation type: " + vertical);
    }

}
