/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.helpcenter;

import com.hotwire.test.steps.common.WebdriverAwareModel;

/**
 * User: v-abudyak
 * Date: 7/22/13
 */
public abstract class HelpCenterModel extends WebdriverAwareModel {

    abstract void clickSendUsEmailLink();

    abstract void fillContactUs();

    abstract void clickSendButton();

    abstract void setupHC();

    abstract void clickAnyArticle();

    abstract void checkSendUsEmailAction(String customerType);

    abstract void clickLearnMoreLink();

    abstract void checkErrorMessage();

    abstract void fillPhoneNumber(String s);

    abstract void checkSuccessSendContactUsFormMessage();

    abstract void checkElementPreFilled(String elementName);

    public abstract void checkElementHighlighted(String elementName);

}
