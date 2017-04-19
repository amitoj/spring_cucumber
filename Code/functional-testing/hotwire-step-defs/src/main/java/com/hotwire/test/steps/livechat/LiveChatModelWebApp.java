/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.livechat;

import com.hotwire.selenium.desktop.helpCenter.lifeChat.PreChatForm;
import com.hotwire.selenium.desktop.helpCenter.lifeChat.PreChatOutsideOperationHoursForm;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;
import org.joda.time.DateTime;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-abudyak
 * Date: 9/4/13
 */
public class LiveChatModelWebApp extends WebdriverAwareModel {

    public void verifyPreChatForm() {
        if (isChatOperationHours()) {
            assertThat(new PreChatForm(getWebdriverInstance()).verifyPreChatForm()).isTrue();
        }
        else {
            assertThat(new PreChatOutsideOperationHoursForm(getWebdriverInstance()).getPreChatFomText())
                    .contains("Chat is outside operation hours");
        }
    }

    public void closeLiveChat() {
        if (isChatOperationHours()) {
            new PreChatForm(getWebdriverInstance()).clickCancel();
        }
        else {
            new PreChatOutsideOperationHoursForm(getWebdriverInstance()).clickClose();
        }
    }

    public void startLiveChart() {
        if (isChatOperationHours()) {
            new PreChatForm(getWebdriverInstance()).clickStartChat();
        }
    }

    public void verifyErrorMessagesOnPreChatForm() {
        throw new UnimplementedTestException("Implement this using HotwireStepDefs step");
      /*  if (isChatOperationHours()) {
            List<String> errorsList = new C3ErrorMessagingFragment(getWebdriverInstance()).getListOfErrors();
            assertThat(errorsList.get(0)).contains("Please enter your first name.");
            assertThat(errorsList.get(1)).contains("Email address should be in the correct format: xxxx@xxxx.xxx");
            assertThat(errorsList.get(2)).contains("A topic must be selected.");
            assertThat(errorsList.get(3)).contains("The question field must not be empty.");
        }*/
    }

    //Chat on Prod environment is available form 5:00 AM to 22:00 PM
    public boolean isChatOperationHours() {
        DateTime currentDateTime = new DateTime();
        if ((currentDateTime.getHourOfDay() >= 5) && (currentDateTime.getHourOfDay() <= 22)) {
            return true;
        }
        return false;
    }

}


