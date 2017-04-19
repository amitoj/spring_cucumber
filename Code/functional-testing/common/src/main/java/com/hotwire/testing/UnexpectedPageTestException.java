/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.testing;

/**
 * Thrown by functional UI tests that expect to navigate to certain page but instead get some other page.
 *
 * @author vjong
 */
public class UnexpectedPageTestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnexpectedPageTestException(String message) {
        super(message);
    }

    public UnexpectedPageTestException(Throwable cause) {
        super(cause);
    }

    public UnexpectedPageTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
