/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.user;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import hotwire.view.jaxb.domain.mobileapi.ClientInfoType;
import hotwire.view.jaxb.domain.mobileapi.MobileAPIVersionTestRQ;
import hotwire.view.jaxb.domain.mobileapi.MobileAPIVersionTestRS;
import hotwire.view.jaxb.domain.mobileapi.VersionScenario;
import hotwire.view.jaxb.domain.mobileapi.VersionScenarioList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-dsobko
 * Since: 01/21/15
 */
public class VersionTestsService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionTestsService.class.getName());

    private MobileAPIVersionTestRS versionTestResponse;

    private RequestProperties requestProperties;
    private RequestPaths paths;

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    private MobileAPIVersionTestRQ setUpVersionTestsInRequest(List<String> versionTests) {
        MobileAPIVersionTestRQ mobileAPIVersionTestRQ = new MobileAPIVersionTestRQ();
        mobileAPIVersionTestRQ.setVersionScenarioList(new VersionScenarioList());
        mobileAPIVersionTestRQ.getVersionScenarioList().setVersionScenarioID(versionTests);

        return mobileAPIVersionTestRQ;
    }

    public void setClientIdForVersionTestsRequest(long clientId) {
        requestProperties.setClientId(clientId);
    }

    public void executeVersionTestRequest(List<String> versionTests) {

        MobileAPIVersionTestRQ mobileAPIVersionTestRQ = setUpVersionTestsInRequest(versionTests);
        mobileAPIVersionTestRQ.setClientInfo(new ClientInfoType());
        mobileAPIVersionTestRQ.getClientInfo().setClientID(requestProperties.getClientId());

        try {
            client.reset()
                    .path(paths.getVersionTestsPath())
                    .accept(MediaType.APPLICATION_XML_TYPE);
            versionTestResponse = client.post(mobileAPIVersionTestRQ, MobileAPIVersionTestRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.info("Request returned status " + e.getResponse().getStatus());
            LOGGER.info("Response body:\n" + e.getResponse().readEntity(String.class));
            LOGGER.info("Request has failed");
        }
    }

    public void verifyVersionScenarioValues() {
        List<VersionScenario> versionScenarios = getVersionScenarioListFromResponse();
        assertThat(versionScenarios.get(0).getId()).isNotEmpty();

        for (VersionScenario versionScenario : versionScenarios) {
            assertThat(versionScenario.getValue()).isIn(new String[]{"1", "2", "3"});
        }
    }

    private List<VersionScenario> getVersionScenarioListFromResponse() {
        return versionTestResponse.getVersionScenarios().getVersionScenario();
    }

    public void verifyScenarioVersionsWhenRequestingMultipleTimes(boolean oneAndTheSameVersion) {
        List<String> scenarioValues = new ArrayList<>();
        Random rand = new Random(10);

        for (int i = 0; i <= 10; i++) {
            int randomClientId = rand.nextInt();
            setClientIdForVersionTestsRequest(randomClientId);
            executeVersionTestRequest(scenarioValues);
            scenarioValues.add(i, getVersionScenarioListFromResponse().get(0).getValue());
        }

        String firstValue = scenarioValues.get(0);
        if (oneAndTheSameVersion) {
            for (String scenarioValue : scenarioValues) {
                assertThat(scenarioValue.equals(firstValue));
            }
        }
        else {
            boolean isAtLeastOneVersionDifferent = false;
            for (String scenarioValue : scenarioValues) {
                if (!scenarioValue.equalsIgnoreCase(firstValue)) {
                    isAtLeastOneVersionDifferent = true;
                }
            }
            assertThat(isAtLeastOneVersionDifferent).
                    as("Scenario versions distribution does not work.").isTrue();
        }
    }

    public void verifyReceivingSpecificVTValue(String expectedValue) {
        assertThat(expectedValue).isEqualTo(getVersionScenarioListFromResponse().get(0).getValue());
    }

}
