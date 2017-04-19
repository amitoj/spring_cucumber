/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.properties;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotwire.qa.client.hw.RefreshUtilClient;
import com.hotwire.qa.data.UEnum;
import com.hotwire.qa.systems.HwRunEnv;

/**
 * @author adeshmukh
 *
 */
public class PropModelImpl implements PropModel {

    private static Logger LOGGER = LoggerFactory.getLogger(PropModelImpl.class);
    private String runEnv_name;


    @Override
    public void setProperties(List<String> properties) {
        RefreshUtilClient utilClient = new RefreshUtilClient(getEnv().urlBase + "/test/refreshUtil.jsp");
        LOGGER.info("Setting up the properties on " + runEnv_name + ": ");
        utilClient.echo();
        for (String prop : properties) {
            String[] keyValue = prop.split("=");
            utilClient.override(keyValue[0], keyValue[1]);
        }
        utilClient.echo();
    }

    @Override
    public void resetProperties(List<String> properties) {
        LOGGER.info("Resetting the properties on " + runEnv_name + ":");
        RefreshUtilClient utilClient = new RefreshUtilClient(getEnv().urlBase + "/test/refreshUtil.jsp");
        for (String prop : properties) {
            utilClient.reset(prop);
        }
        utilClient.echo();
    }

    /**
     * @param runEnv_name
     *            the runEnv_name to set
     */
    public void setRunEnv_name(String runEnv_name) {
        this.runEnv_name = runEnv_name;
    }

    /**
     * This method allows you to set the environment. runEnv_Name is a machine where the tests run
     *
     * @return
     */
    private HwRunEnv getEnv() {
        HwRunEnv runEnv = UEnum.match(HwRunEnv.class, runEnv_name, false);
        return runEnv;
    }

}
