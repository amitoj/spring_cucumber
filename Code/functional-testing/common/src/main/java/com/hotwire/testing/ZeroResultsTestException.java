/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.testing;

/**
 * Thrown when test expects at least one result to be present on page/in result list, but there is none.
 *
 * @author vjong
 */
public class ZeroResultsTestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ZeroResultsTestException(String message) {
        super(message);
    }

    public ZeroResultsTestException(Throwable cause) {
        super(cause);
    }

    public ZeroResultsTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
