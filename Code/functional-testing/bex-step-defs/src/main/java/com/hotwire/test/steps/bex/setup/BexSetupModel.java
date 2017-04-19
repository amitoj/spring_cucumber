/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.bex.setup;

import com.hotwire.test.steps.bex.BexAbstractModel;

/**
 * Created with IntelliJ IDEA.
 * User: v-jolopez
 * Date: 3/9/15
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class BexSetupModel extends BexAbstractModel {

    public void abacusOverride() {
        getWebdriverInstance().manage().window().maximize();
        getWebdriverInstance()
                .get("http://vacationhotwirecom.integration.sb.karmalab.net/tools/abacus/overrides?abov=6774|0|0");
    }

    public void goToHotel() {
        getWebdriverInstance().get("http://vacationhotwirecom.integration.sb.karmalab.net/Hotel-Search");
    }

}
