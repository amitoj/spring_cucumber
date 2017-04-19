/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.global.footer;

/**
 * GlobalFooter us Model
 * @author psuryadevara
 */
public interface GlobalFooterModel {

    /**
     * Contact Us
     */

    void openContactUsPage();

    void verifySendEmailText();

    /**
     * Site Map page
     */

    void openSiteMapPage();

    void verifyHotwireProducts();

    void verifyMyAccount();

    void verify_hotwire_legal_declaimer();

    void verify_hotwire_copyright_info();
}
