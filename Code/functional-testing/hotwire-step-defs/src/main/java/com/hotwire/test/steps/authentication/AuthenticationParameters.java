/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

import java.io.Serializable;

/**
 * interface AuthenticationParameters
 */
public interface AuthenticationParameters extends Serializable {

    AuthenticationParameters getKnownGoodParametersStore();
    AuthenticationParameters getHotDollarsAuthenticationParameters();
    AuthenticationParameters getPayableUserAuthenticationParameters();
    AuthenticationParameters getloginParameters();

    String getUsername();

    String getPassword();

    void setRandomUsernameAndPassword();

    /**
     * Change username to other allowable value
     * if user has random credentials
     */
    void regenerateRandomUsername();

    boolean isRandomUser();

    void setCredentials(String username, String password);

    boolean areCredentialsSet();

    void logout();

    void setloginParameters();

    void setKnownGoodParameters();

    void setKnownGoodUsernameAndBadPassword();

    void setKnownBadParameters();

    void setHotDollarsUser();

    void setPayableUser();

    boolean isUserAuthenticated();

    void userLogIn();

    void userLogOut();

    void setKnownBadUsernameAndGoodPassword();

    void setRandomPassword();
}
