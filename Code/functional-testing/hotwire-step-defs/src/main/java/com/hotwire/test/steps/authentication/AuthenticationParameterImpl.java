/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.authentication;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * AuthenticationParameterImpl
 *
 */
public class AuthenticationParameterImpl implements AuthenticationParameters {

    private static final String RANDOM_USER_PASSWORD = "hotwire";

    private String username;
    private String password;
    private boolean isRandomUser;
    private boolean isUserAuthenticated;

    private AuthenticationParameters knownGoodParametersStore;
    private AuthenticationParameters hotDollarsAuthenticationParameters;
    private AuthenticationParameters payableUserAuthenticationParameters;
    private AuthenticationParameters loginParameters;

    public void setUsername(String username) {
        throwCredialExceptionIfSet();
        this.username = username;
    }

    public void setKnownGoodParametersStore(AuthenticationParameters knownGoodParametersStore) {
        this.knownGoodParametersStore = knownGoodParametersStore;
    }

    public void setLoginParameters(AuthenticationParameters loginParameters) {
        this.loginParameters = loginParameters;
    }

    @Override
    public AuthenticationParameters getKnownGoodParametersStore() {
        return knownGoodParametersStore;
    }

    @Override
    public AuthenticationParameters getloginParameters() {
        return loginParameters;
    }

    @Override
    public void setloginParameters() {
        throwCredentialExceptionIfKnownGoodCredentialsNotSet();
        setCredentials(loginParameters.getUsername(), loginParameters.getPassword());
    }

    @Override
    public AuthenticationParameters getHotDollarsAuthenticationParameters() {
        return hotDollarsAuthenticationParameters;
    }

    @Override
    public AuthenticationParameters getPayableUserAuthenticationParameters() {
        return payableUserAuthenticationParameters;
    }

    public void setHotDollarsAuthenticationParameters(AuthenticationParameters hotDollarsAuthenticationParameters) {
        this.hotDollarsAuthenticationParameters = hotDollarsAuthenticationParameters;
    }

    public void setPayableUserAuthenticationParameters(AuthenticationParameters payableUserAuthenticationParameters) {
        this.payableUserAuthenticationParameters = payableUserAuthenticationParameters;
    }

    @Override
    public String getUsername() {
        throwCredialExceptionIfNotSet();
        return username;
    }

    public String getPassword() {
        throwCredialExceptionIfNotSet();
        return password;
    }

    public boolean isRandomUser() {
        return isRandomUser;
    }

    @Override
    public void regenerateRandomUsername() {
        Random random = new Random();
        if (isRandomUser) {
            this.username = "G2loadtest" + (random.nextInt(1999) + 1) + "@hotwire.com";
        }
    }

    public void setPassword(String password) {
        throwCredialExceptionIfSet();
        this.password = password;
    }

    @Override
    public void setCredentials(String username, String password) {
        throwCredialExceptionIfSet();
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean areCredentialsSet() {
        return username != null && password != null;
    }

    public void throwCredialExceptionIfSet() {
        if (areCredentialsSet()) {
            throw new RuntimeException("Credentials have already been set by a step.  Please correct your test");
        }
    }

    public void throwCredialExceptionIfNotSet() {
        if (!areCredentialsSet()) {
            throw new RuntimeException(
                "Credentials have not been set by a step, use must use and authentication step to set" +
                        " credentials before logging in.  Please correct your test");
        }
    }

    @Override
    public void logout() {
        throwCredialExceptionIfNotSet();
        this.username = null;
        this.password = null;
        userLogOut();
    }

    private void throwCredentialExceptionIfKnownGoodCredentialsNotSet() {
        if (knownGoodParametersStore == null) {
            throw new RuntimeException(
                "Please configure the knownGoodParameters field on the AuthenticationParameter bean" +
                        " in your context (should already have been done for you)");
        }
    }

    @Override
    public void setKnownGoodParameters() {
        throwCredentialExceptionIfKnownGoodCredentialsNotSet();
        setCredentials(knownGoodParametersStore.getUsername(), knownGoodParametersStore.getPassword());
    }

    @Override
    public void setRandomUsernameAndPassword() {
        Random random = new Random();
        this.username = "G2loadtest" + (random.nextInt(1999) + 1) + "@hotwire.com";
        this.password = RANDOM_USER_PASSWORD;
        this.isRandomUser = true;
    }

    @Override
    public void setKnownGoodUsernameAndBadPassword() {
        throwCredentialExceptionIfKnownGoodCredentialsNotSet();
        setCredentials(this.knownGoodParametersStore.getUsername(),
            "NotMy" + this.knownGoodParametersStore.getPassword());
    }

    /**
     * Setting credentials with positive HotDollars balance
     */
    @Override
    public void setHotDollarsUser() {
        throwCredentialExceptionIfKnownGoodCredentialsNotSet();
        setCredentials(getHotDollarsAuthenticationParameters().getUsername(),
                       getHotDollarsAuthenticationParameters().getPassword());
    }

    @Override
    public void setPayableUser() {
        setCredentials(getPayableUserAuthenticationParameters().getUsername(),
                getPayableUserAuthenticationParameters().getPassword());
    }

    @Override
    public void setKnownBadParameters() {
        setCredentials("notavalid@emailaddress.com", "meh_irrelevant");
    }

    public boolean isUserAuthenticated() {
        return isUserAuthenticated;
    }

    public void userLogIn() {
        isUserAuthenticated = true;
    }

    public void userLogOut() {
        isUserAuthenticated = false;
    }

    @Override
    public void setKnownBadUsernameAndGoodPassword() {
        setCredentials("notavalid@emailaddress.com", "hotwire333");
    }

    public void setRandomPassword() {
        this.password = RandomStringUtils.randomAlphabetic(8);
    }
}
