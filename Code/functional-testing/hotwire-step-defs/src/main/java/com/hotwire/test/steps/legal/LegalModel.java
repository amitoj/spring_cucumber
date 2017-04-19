/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.legal;

/**
 * User: v-abudyak
 * Date: 12/24/13
 */
public interface LegalModel {

    void goToLegalPage();

    void verifyLegalPage();

    void goToPrivacyPolicyPage();

    void goToTermsOfUsePage();

    void goToRulesAndRestrictioinsPage();

    void goToLowPriceGuaranteePage();

    void clickBackButton();

    void verifyLegalDetailsPage(String pageName);

    void verifyText(String text, boolean textPresent);

    void verifyCopiesOnSearchResults();

    void verifyCopiesOnDetails();
}
