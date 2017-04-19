/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.discount;


/**
 * @author Unknown
 * @since 2012.07
 *        Modified: LevR 06/05/12 This class should only provide shared parameters.
 *        All vertical specific parameters should be provided from the corresponding classes
 */
public interface DiscountParameters {

    /**
     * Types of discount conditions
     */
    enum ConditionType { MIN_TOTAL, MIN_RATING, MAX_DTA }

    String getCurrentDiscountAmount();

    void setCurrentDiscountAmount(String amount);

    String getNewDiscountAmount();

    void setNewDiscountAmount(String amount);

    void setIsPercentDiscount(boolean isPercent);

    boolean getIsPercentDiscount();

    ConditionType getDiscountCondition();

    void setDiscountCondition(ConditionType condtion);

    void setConditionValue(int conditionValue);

    int getConditionValue();

}
