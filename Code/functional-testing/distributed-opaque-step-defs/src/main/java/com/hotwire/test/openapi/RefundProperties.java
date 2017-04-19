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
 * Date: 2/18/15
 * Time: 12:10 PM
 * Container for refund properties
 */
public class RefundProperties {
    private Integer refundReason;
    private boolean rebooked;
    private String refundPath;
    private String refundType;
    private boolean shouldCancel;
    private String refundCurrencyCode;
    private String comments;

    public void setRefundPath(String refundPath) {
        this.refundPath = refundPath;
    }

    public String getRefundPath() {
        return refundPath;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundCurrencyCode(String refundCurrencyCode) {
        this.refundCurrencyCode = refundCurrencyCode;
    }

    public String getRefundCurrencyCode() {
        return refundCurrencyCode;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public boolean isShouldCancel() {
        return shouldCancel;
    }

    public void setShouldCancel(boolean shouldCancel) {
        this.shouldCancel = shouldCancel;
    }

    public void setRefundReason(Integer refundReason) {
        this.refundReason = refundReason;
    }

    public boolean isRebooked() {
        return rebooked;
    }

    public void setRebooked(boolean rebooked) {
        this.rebooked = rebooked;
    }

    public String getComments() {
        return comments;
    }

    public Integer getRefundReason() {
        return refundReason;
    }
}
