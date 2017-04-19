/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.help;

import com.hotwire.selenium.mobile.MobileHelpCenterPage;

public class HelpCenterModelMobileWebApp extends HelpCenterModelTemplate {

    @Override
    public void verifyHelpCenterPage() {
        new MobileHelpCenterPage(getWebdriverInstance());
    }
}
