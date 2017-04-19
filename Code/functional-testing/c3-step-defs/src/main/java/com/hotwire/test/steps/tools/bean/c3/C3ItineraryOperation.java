/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;
import com.hotwire.util.db.c3.C3AbstractDao;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 10/13/14
 * Time: 7:25 AM
 * Store C3 operation status
 */
public class C3ItineraryOperation extends ToolsAbstractBean {
    /**
     * Common CSR operations in C3
     */
    public enum C3ItineraryOperations {
        SEARCH, CANCEL, REFUND, PARTIAL_REFUND, LPG_CLAIM
    }

    @Resource
    Map<String, C3AbstractDao> c3ItineraryOperations;

    /**
     * Operation that C3 Agent will perform
     */
    private C3ItineraryOperations purchaseOperation;
    private C3AbstractDao c3Dao;



    public C3ItineraryOperations getPurchaseOperation() {
        return purchaseOperation;
    }

    public String getC3OperationName() {
        return String.valueOf(purchaseOperation);
    }

    public void setPurchaseOperation(String purchaseOperation) {
        this.purchaseOperation = C3ItineraryOperations.valueOf(purchaseOperation.toUpperCase().replace(" ", "_"));
        setC3Dao();
    }

    public C3AbstractDao getC3Dao() {
        return c3Dao;
    }

    public void setC3Dao() {
        this.c3Dao = c3ItineraryOperations.get(getC3OperationName());
    }

    public void setC3Dao(C3AbstractDao obj) {
        this.c3Dao = obj;
    }

}
