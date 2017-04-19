/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet;

/**
 * Indicates any error interacting with {@link XnetAriService}.
 * @author Renat Zhilkibaev
 */
public class XnetAriServiceException extends RuntimeException {

    public static final String ERROR_CODE_UNAUTHORIZED = "HTTP401";
    public static final String ERROR_CODE_UNDEFINED = "UNDEFINED";
    public static final String ERROR_CODE_INTERNAL_ERROR = "ARI0";
    public static final String ERROR_CODE_TOTAL_INVENTORY_NOT_IN_RANGE = "ARI301";
    public static final String ERROR_CODE_CURRENCY_LENGTH = "ARI700";
    public static final String ERROR_CODE_CURRENCY_UNKNOWN = "ARI701";
    public static final String ERROR_CODE_PERDAY_RATE_NOT_IN_RANGE = "ARI702";
    public static final String ERROR_CODE_END_DATE_NOT_WITHIN_RANGE = "ARI404";
    public static final String ERROR_CODE_START_DATE_PAST = "ARI400";
    public static final String ERROR_CODE_END_DATE_PAST = "ARI401";
    public static final String ERROR_CODE_START_DATE_AFTER_END_DATE = "ARI402";
    public static final String ERROR_CODE_DATES_OVERLAP = "ARI101";
    public static final String ERROR_CODE_UNKNOWN_ROOMTYPE = "ARI502";
    public static final String ERROR_CODE_UNKNOWN_HOTEL_ID  =  "ARI200";

    private static final long serialVersionUID = XnetAriServiceException.class.getSimpleName().hashCode();
    private final String errorCode;

    /**
     * Constructs a new instance.
     * @param errorCode - the code identifying the error
     * @param message - the error message
     */
    public XnetAriServiceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new instance.
     * @param errorCode - the code identifying the error
     * @param message - the error message
     * @param cause - the cause
     */
    public XnetAriServiceException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Returns the code identifying the error.
     * @return - error code
     */
    public String getErrorCode() {
        return errorCode;
    }

}
