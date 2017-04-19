/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.workflow;

import com.hotwire.selenium.tools.ToolsAbstractPage;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;


/**
 * Created by v-ozelenov on 4/10/2014.
 * Also known as Footprints form
 */
public class C3WorkflowForm extends ToolsAbstractPage {
    public static final double PRICE_DIFF = 5.00;
    private DateTime timeFound;

    public C3WorkflowForm(WebDriver webdriver) {
        super(webdriver, By.name("footprintsForm"));
    }

    public void submit() {
        submitFormByName("footprintsForm");
    }


    public void createHotelLPGWorkFlow(Double totalAmount, String currency) {
        setLPGClaim();
        selectHotelSource();
        setLowerRate(totalAmount, currency);
        setDateFound();
        setHotelStatus();
        setNotes();
        submit();
    }

    public void createCarLPGWorkFlow(Double totalAmount, String currency) {
        setLPGClaim();
        selectCarSource();
        setLowerRate(totalAmount, currency);
        setDateFound();
        setAddress();
        setCarStatus();
        setNotes();
        submit();
    }

    private void setAddress() {
        setText("input[name='selectedInquiry.selectedCategoryQnAs[4].answer']", "Test");
        setText("input[name='selectedInquiry.selectedCategoryQnAs[5].answer']", "Test");
    }

    private void setHotelStatus() {
        //Status='Open'
        selectValue("select[name='selectedInquiry.selectedCategoryQnAs[4].answer']", "Open");
    }

    private void setCarStatus() {
        //Status='Open'
        selectValue("select[name='selectedInquiry.selectedCategoryQnAs[9].answer']", "Open");
    }


    private void setLPGClaim() {
        selectValue("select[name='selectedFootprintTopic']", "Low Price Guarantee");
        findOne("select[name='selectedFootprintCategory']", DEFAULT_WAIT);
    }

    private void setLowerRate(Double totalAmount, String currency) {
        //Select 30 USD as lowest price
        String lowerPrice = new DecimalFormat("###.##").format(totalAmount - PRICE_DIFF);
        setText("input[name='selectedInquiry.selectedCategoryQnAs[2].moneyAnswer']", lowerPrice);
        selectValue("select[name='selectedInquiry.selectedCategoryQnAs[2].moneyAnswer']", currency);
    }

    private void setNotes() {
        setText("textarea#note", "Test note");
    }

    private void setDateFound() {
        //select current date and 12 hours before
        timeFound = getTimeBefore(12);
        //Find all DateTime selectors
        //Year
        setDateTimeTableValue(1, "M");
        setDateTimeTableValue(2, "d");
        setDateTimeTableValue(3, "YYYY");
        //Time
        setDateTimeTableValue(5, "h");
        setDateTimeTableValue(6, "mm");
    }

    private void selectHotelSource() {
        String source = "Hotel Website";
        selectValue("select[name='selectedInquiry.selectedCategoryQnAs[0].answer']", source);
    }

    private void selectCarSource() {
        String source = "Rental Car Website";
        selectValue("select[name='selectedInquiry.selectedCategoryQnAs[0].answer']", source);
    }

    private void setDateTimeTableValue(Integer orderNum, String timeIsoFormat) {
        String tmp = "table td:nth-child(%s) select[name='selectedInquiry.selectedCategoryQnAs[3].dateTimeAnswer']";
        String selector = String.format(tmp, orderNum);
        selectValue(selector, timeFound.toString(timeIsoFormat));
    }

    public String getConfirmationMsg() {
        return findOne("td#errorMessaging").getText().replaceAll("\\n", "");
    }
}
