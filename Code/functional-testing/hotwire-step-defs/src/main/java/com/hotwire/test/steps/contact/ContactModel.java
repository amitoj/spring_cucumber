/*
* Copyright 2014 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/
package com.hotwire.test.steps.contact;

/**
 * Contact model
 */
public interface ContactModel {

    void verifyPhoneNumber(String locale) throws Exception;

    void verifyPhoneNumberForOutsideUSUsers();
}
