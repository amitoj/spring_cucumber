/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.util;

import com.hotwire.testing.UnexpectedPageTestException;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.testing.ZeroResultsTestException;
import cucumber.api.PendingException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class ExceptionConversionAdvice implements MethodInterceptor {

    public ExceptionConversionAdvice() {
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        }
        catch (UnimplementedTestException | ZeroResultsTestException | UnexpectedPageTestException e) {
            throw createNewPendingException(e);
        }
    }

    private PendingException createNewPendingException(Exception e) {
        return new PendingException(
            e.getMessage() + " at " +
            e.getStackTrace()[0] +
            "<-- Click That  (if in an ide), ignore the rest of this stack trace, it won't help :)");
    }

}
