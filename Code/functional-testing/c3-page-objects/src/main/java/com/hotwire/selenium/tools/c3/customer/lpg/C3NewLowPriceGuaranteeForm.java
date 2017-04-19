/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.customer.lpg;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/3/14
 * Time: 7:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3NewLowPriceGuaranteeForm extends ToolsAbstractPage {
    private static final double PRICE_DIFF = 5.00;

    public C3NewLowPriceGuaranteeForm(WebDriver webdriver) {
        super(webdriver, By.className("LowPriceGuaranteeComp"));
    }

    public Double fill(Double totalAmount) {
//        freeze(1);
        //Choose price must be less than Hotwire price
        String lowerPrice = new DecimalFormat("###.00").format(totalAmount - PRICE_DIFF);
        setText("input[name='lowPriceRate']", lowerPrice);
        //wait for amount
        findOne("div#totalClaimAmount.totalClaimAmountHolder");
        //Any source
        setText("input[name='sourceOfLowerRate']", "Test");
        //DateFound=current date
        selectDate("input[name='claimSubmissionDate']", getCurrentDate());
        selectDate("input[name='dateFound']", getCurrentDate());
        //Time before now on 3 hours
        setText("input[name='timeFound']", convertToAmPm(getTimeBefore(3)));
        findOne("input[value='Process Low Price Guarantee']").click();
        return PRICE_DIFF;
    }

    public String getConfirmationMsg() {
        //for slow environments
        freeze(1);
        return findOne("div#errMessages.successMsg", EXTRA_WAIT).getText();
    }
}
