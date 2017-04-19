/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.login;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import hotwire.view.jaxb.domain.mobileapi.HWLoginRQ;
import hotwire.view.jaxb.domain.mobileapi.HWLoginRS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-ngolodiuk
 * Since: 1/5/15
 */
public class LoginService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class.getName());
    private HWLoginRS response;
    private RequestProperties requestProperties;
    private RequestPaths paths;

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void loginUser(UserInformation userInfo) {
        HWLoginRQ loginRequest;

        loginRequest = createLoginRequest(userInfo.getEmailId(), userInfo.getPassword());
        LOGGER.info("Logging user: " + userInfo.getEmailId());
        try {
            client.reset()
                .path(paths.getLoginUserPath())
                .accept(MediaType.APPLICATION_XML_TYPE);

            response = client.post(loginRequest, HWLoginRS.class);

            assertThat(response).isNotNull();
            String oAuthToken = response.getOAuthToken();
            requestProperties.setOauthToken(oAuthToken);
            long customerID = response.getCustomerID();
            requestProperties.setCustomerId(customerID);
            LOGGER.info("Setting customer oAuth token to :" + oAuthToken);
            LOGGER.info("Setting customer id to :" + customerID);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Login response returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Login responsee has failed with " +  message, e);
        }
    }
    public void tryToLoginWithCorrectEmailAndIncorrectCustomer() {
        loginUserWithCustomEmailPassword(requestProperties.getCreatedUserEmail(),
                "fake", "1016", "Login error : Incorrect password");
    }

    public void tryToLoginWithIncorrectEmailAndIncorrectCustomer() {
        loginUserWithCustomEmailPassword(requestProperties.getCreatedUserEmail() + "fake",
                "fake", "1015", "Login error : User not found");
    }

    public void loginUserWithCustomEmailPassword(String email, String password, String errorCode, String errorMessage) {
        HWLoginRQ loginRequest;

        loginRequest = createLoginRequest(email, password);
        LOGGER.info("Logging user: " + email + " " + password);
        try {
            client.reset()
                    .path(paths.getLoginUserPath())
                    .accept(MediaType.APPLICATION_XML_TYPE);

            response = client.post(loginRequest, HWLoginRS.class);
            fail("Test failed because login request with wrong login and password passed");
        }
        catch (NotAuthorizedException e) {
            e.getResponse().bufferEntity();
            LOGGER.error("Login response returned status " + e.getResponse().getStatus());
            LOGGER.error("Response body:\n" + e.getResponse().getEntity());
            String errorResponse = e.getResponse().readEntity(String.class);
            assertThat(errorResponse).contains(errorCode);
            assertThat(errorResponse).contains(errorMessage);
        }
    }

    public HWLoginRQ createLoginRequest(String userEmail, String userPassword) {
        HWLoginRQ request = new HWLoginRQ();
        request.setCustomerEmail(userEmail);
        request.setCustomerSecret(userPassword);
        return request;
    }

    public void verifyOauthTokenForCustomer() {
        assertThat(response.getOAuthToken()).isNotEmpty();
    }

    public void verifyLoginResponseIsNotPresent() {
        assertThat(response).isNull();
    }



}
