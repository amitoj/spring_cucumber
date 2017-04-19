/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.discount;

/**
 *
 */
public class DiscountParametersImpl implements DiscountParameters {

    private String currentDiscountAmount;
    private String newDiscountAmount;
    private boolean setIsPercentDiscount;
    private ConditionType discountCondition;
    private int conditionValue;

    public int getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(int conditionValue) {
        this.conditionValue = conditionValue;
    }

    public ConditionType getDiscountCondition() {
        return discountCondition;
    }

    public void setDiscountCondition(ConditionType discountCondition) {
        this.discountCondition = discountCondition;
    }

    public boolean getIsPercentDiscount() {
        return setIsPercentDiscount;
    }

    public void setIsPercentDiscount(boolean setIsPercentDiscount) {
        this.setIsPercentDiscount = setIsPercentDiscount;
    }

    public String getCurrentDiscountAmount() {
        return currentDiscountAmount;
    }

    public void setCurrentDiscountAmount(String currentDiscountAmount) {
        this.currentDiscountAmount = currentDiscountAmount;
    }

    public String getNewDiscountAmount() {
        return newDiscountAmount;
    }

    public void setNewDiscountAmount(String newDiscountAmount) {
        this.newDiscountAmount = newDiscountAmount;
    }
}
