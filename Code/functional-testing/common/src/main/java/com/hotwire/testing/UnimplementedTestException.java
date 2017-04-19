/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.testing;

/**
 * @author vjong
 */
public class UnimplementedTestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnimplementedTestException(String message) {
        super(message);
    }

    public UnimplementedTestException(Throwable cause) {
        super(cause);
    }

    public UnimplementedTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
