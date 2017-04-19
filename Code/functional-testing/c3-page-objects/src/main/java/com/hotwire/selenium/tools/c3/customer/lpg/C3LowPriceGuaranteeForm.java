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
public class C3LowPriceGuaranteeForm extends ToolsAbstractPage {
    private static final double PRICE_DIFF = 5.00;

    public C3LowPriceGuaranteeForm(WebDriver webdriver) {
        super(webdriver, By.id("lowPriceForm"));
    }

    public String fill(Double totalAmount) {
        selectDate("input[name='claimSubmissionDate']", getCurrentDate());
        //Choose price must be less than Hotwire price
        String lowerPrice = new DecimalFormat("###.##").format(totalAmount - PRICE_DIFF);
        setText("input[name='lowPriceRate']", lowerPrice);
        //Any source
        setText("input[name='sourceOfLowerRate']", "Test");
        //DateFound=current date
        selectDate("input[name='dateFound']", getCurrentDate());
        //Time before now on 3 hours
        setText("input[name='timeFound']", convertToAmPm(getTimeBefore(3)));
        findOne("form[name='lowPriceForm']").click();
        freeze(1);
        String refundAmount = findOne("td.displayValue div#totalClaimAmount", EXTRA_WAIT).getText();
        submitFormByName("lowPriceForm");
        return refundAmount;
    }

    public String getConfirmationMsg() {
        return findOne("div.c3Skin", EXTRA_WAIT).getText();
    }
}
