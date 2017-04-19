/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.job;

import java.util.List;

/**
 * @author abareli
 *
 */
public interface JobModel {

    void runJob(List<String> params, String dir);

    void checkJobOutput();

    void retrieveReferenceNumber();

    void waitForLoadingLayer();

    void validateTimeInDB();

    void setJobDirectory(String jobDirectory);

    void setJobParams(List<String> jobParams);

    /**
     * Just runs the job and waits for it to complete before running the next step
     * @param port
     */
    void runJob(String port, Integer timeout);

    /**
     * Runs the job and asserts the log file for success
     * @param port
     * @param success
     * @return
     */
    boolean runJob(String port, String success);

    /**
     * Runs shell script on a given env
     * @param path
     */
    void runShellScript(String path);

    /**
     * set a reference to trouble shoot in jenkins slave
     * @param ref
     */
    void setReference(String refer);
}
