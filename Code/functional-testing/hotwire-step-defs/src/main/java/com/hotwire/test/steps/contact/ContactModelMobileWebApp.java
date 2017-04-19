/*
* Copyright 2014 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.test.steps.contact;

import com.hotwire.selenium.mobile.MobileHotwireHomePage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 *    ContactModelMobileWebApp
 */
public final class ContactModelMobileWebApp extends WebdriverAwareModel implements ContactModel {

    private static final Map<String, String> CUSTOMER_SERVICE_PHONE_NUMBERS = new HashMap<String, String>() {
        {
            put("us", "855-240-5213");
            put("uk", "0808 234 5903");
            put("ie", "1 800 760738");
            put("no", "80 01 72 28");
            put("se", "020 797 237");
            put("dk", "80 25 00 72");
            put("au", "1 800 306 217");
            put("nz", "0800 452 690");
            put("hk", "800 933 475");
            put("de", "0800 723 5637");
            put("mx", "001-8554643751");
            put("sg", "800 120 6272");
        }
    };

    public void verifyPhoneNumber(final String locale) throws Exception {
        MobileHotwireHomePage homePage = new MobileHotwireHomePage(getWebdriverInstance());
        assertThat(homePage.getContactNumber()).contains(CUSTOMER_SERVICE_PHONE_NUMBERS.get(locale));
    }

    @Override
    public void verifyPhoneNumberForOutsideUSUsers() {
        throw new UnimplementedTestException("Implement me...");
    }
}
