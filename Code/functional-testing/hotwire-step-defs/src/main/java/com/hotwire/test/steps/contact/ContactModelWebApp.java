/*
* Copyright 2014 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.test.steps.contact;

import com.hotwire.selenium.desktop.us.confirm.ContactInformationFragment;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;


/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 1/29/14
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContactModelWebApp extends WebdriverAwareModel implements ContactModel {

    public void verifyPhoneNumber(final String locale) throws Exception {
        throw  new UnimplementedTestException("Implement me...");
    }

    public void  verifyPhoneNumberForOutsideUSUsers() {
        ContactInformationFragment contactInformationFragment = new ContactInformationFragment(getWebdriverInstance());
        contactInformationFragment.verifyPhoneForOutsideUSUsers();
    }

}
