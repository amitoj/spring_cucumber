/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.hotel;

import com.hotwire.testing.MethodInvocationService;

/**
 * Created by IntelliJ IDEA.
 * User: bshivaprakash
 * Date: Oct 17, 2012
 * Time: 2:53:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestDataSetupHelper {

    /**
     * Remote service that this test uses to invoke methods
     */
    private final MethodInvocationService remoteServiceInterface;

    /**
     * Constructs a new ready to use instance.
     * @param remoteServiceInterface - the invocation service to use
     */
    public TestDataSetupHelper(MethodInvocationService remoteServiceInterface) {
        this.remoteServiceInterface = remoteServiceInterface;
    }

    public void deactivateNonXnetChannelsInNeighborhoods(long... neighborHoodIds) {
        //@Todo: add the method name
        remoteServiceInterface.invokeBeanMethod("hotelJdoCacheManipulator",
                                                "deactivateOtherChannelsInNeighborhoods",
                                                'X', neighborHoodIds);
    }
}
