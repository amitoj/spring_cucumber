/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.tools.c3.purchase.air;

import com.hotwire.selenium.tools.c3.purchase.C3ConfirmationPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/7/14
 * Time: 6:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3AirConfirmationPage extends C3ConfirmationPage {
    private String returningFlightNum = "//div[@id='stripedSidebarRightLayout']//div[@class='travelBack']/div/h3";
    private String departingFlightNum = " //div[@id='stripedSidebarRightLayout']//div[@class='travelTo']/div/h3";
    private String mask1 = "E, MMM d, yyyy h:mm a";
    private String mask2 = "h:mm a E, MMM d, yyyy";

    public C3AirConfirmationPage(WebDriver webdriver) {
        super(webdriver, By.className("airConfirmationPage"));
    }


    private  List<String> getContent(String xpath) {
        List<String> content = new ArrayList<String>();
        List<WebElement>  elements = findMany(By.xpath(xpath));

        for (WebElement elem: elements) {
            content.add(elem.findElement(By.xpath(".//..")).getText().trim());
        }
        return content;
    }

    private Date formatDate(String sDate, String mask) {
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(mask);
        try {
            date = format.parse(sDate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private List<String> replace(List<String> inputList) {
        List<String> updatedList = new ArrayList<String>();
        for (String elem : inputList) {
            updatedList.add(elem.replace("Departs:", "").replace("Arrives:", "")
                    .replace("From:", "").replace("To:", "")
                    .replace(" on ", " ").replace(" at ", " ").replace("Total trip time:", "").trim());
        }
        return updatedList;
    }


    public String getItineraryNumber() {
        List<WebElement> confCodes;
        confCodes = findMany(".confirmCodes>div", DEFAULT_WAIT);
        for (WebElement confCode : confCodes) {
            if (confCode.getText().contains("Itinerary")) {
                return confCode.getText().split(": ")[1];
            }
        }
        return "";
    }

    public String getConfirmationCode() {
        return findOne(By.xpath("//div[@class='confirmCodes']/div[1]")).getText().split(":")[1]
                .replace("- Your flight is confirmed.", "").trim();
    }

    public List<String> getAirTicketsNumbers() {
        List<String> airTicketsNumbers = new ArrayList<String>();
        List<WebElement>  airTicketsNumbersWebElem = findMany(
                By.xpath("//div[@id='stripedSidebarRightLayout']" +
                        "//div[@class='passengerDetails']//span[contains(text(), 'silkTicketNumber')]"));

        for (WebElement elem: airTicketsNumbersWebElem) {
            airTicketsNumbers.add(elem.findElement(By.xpath(".//..")).getText().trim());
        }
        return airTicketsNumbers;
    }

    public List<String>  getNamesOfPassengers() {
        return getContent("//div[@id='stripedSidebarRightLayout']" +
                "//div[@class='passengerDetails']//span[contains(text(), 'silkPassengerName')]");
    }

    public String getNumberOfTickets() {
        return findOne(
                By.xpath("//div[@id='stripedSidebarRightLayout']" +
                        "//span[contains(text(), 'silkHotNoOfPassengers')]//.."))
                .getText().replace("Arrives:", "").replace(" on ", " ").trim();
    }

    private List<String> getDepartingStartDateAndTimeFromPage() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkAirOutboundDate')]");
        return replace(res);
    }

    private List<String> getDepartingEndDateAndTimeFromPage() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkDepartEndCityTime')]");
        return replace(res);
    }

    private  List<String>  getReturningStartDateAndTimeFromPage() {

        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkAirInboundDate')]");
        return replace(res);

    }

    private  List<String> getReturningEndDateAndTimeFromPage() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkArrivalEndCityTime')]");
        return replace(res);

    }

    public  List<String>  getDepartingFromLocation() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkDepartStartCityAirCodeTime')]");
        return replace(res);

    }
    public  List<String>  getDepartingToLocation() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkDepartEndCityAirCodeTime')]");
        return replace(res);
    }
    public  List<String> getReturningFromLocation() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkArrivalStartCityAirCode')]");
        return replace(res);
    }
    public  List<String> getReturningToLocation() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//span[contains(text(), 'silkArrivalEndCityAirCode')]");
        return replace(res);

    }

    public List<Date> getDepartingStartDateAndTime() {
        List<Date> formattedDate = new ArrayList<Date>();
        for (String date: getDepartingStartDateAndTimeFromPage()) {
            formattedDate.add(formatDate(date, mask1));
        }
        return  formattedDate;
    }

    public  List<Date> getDepartingEndDateAndTime() {
        List<Date> formattedDate = new ArrayList<Date>();
        for (String date: getDepartingEndDateAndTimeFromPage()) {
            formattedDate.add(formatDate(date, mask2));
        }
        return  formattedDate;
    }

    public  List<Date> getReturningStartDateAndTime() {
        List<Date> formattedDate = new ArrayList<Date>();
        for (String date: getReturningStartDateAndTimeFromPage()) {
            formattedDate.add(formatDate(date, mask1));
        }
        return  formattedDate;
    }

    public List<Date> getReturningEndDateAndTime() {
        List<Date> formattedDate = new ArrayList<Date>();
        for (String date : getReturningEndDateAndTimeFromPage()) {
            formattedDate.add(formatDate(date, mask2));
        }
        return formattedDate;
    }

    public List<String> getDepartingCarrier() {
        return getContent("//div[@id='stripedSidebarRightLayout']" +
                "//div[@class='travelTo']//span[contains(text(), 'silkAirCarrier')]");

    }

    public  List<String> getReturningCarrier() {
        return getContent("//div[@id='stripedSidebarRightLayout']" +
                "//div[@class='travelBack']//span[contains(text(), 'silkAirCarrier')]");
    }

    public List<String> getDepartingTotalTripTime() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//div[@class='travelTo']//*[contains(text(), 'silkAirDuration')]");
        return replace(res);

    }

    public List<String> getReturningTotalTripTime() {
        List<String> res = getContent("//div[@id='stripedSidebarRightLayout']" +
                "//div[@class='travelBack']//*[contains(text(), 'silkAirDuration')]");
        return replace(res);
    }

    public List<String> getReturningFlightNumber() {
        List<String> res = new ArrayList<String>();
        for (WebElement elem : findMany(By.xpath(returningFlightNum))) {
            res.add(elem.getText().split(",")[0].split(" ")[1]);
        }

        return res;
    }

    public List<String> getDepartingFlightNumber() {
        List<String> res = new ArrayList<String>();
        for (WebElement elem : findMany(By.xpath(departingFlightNum))) {
            res.add(elem.getText().split(",")[0].split(" ")[1]);
        }

        return res;
    }

    public String getTotalCost() {
        return findOne(".value.mb.mt20.finalTotal").getText().replace(",", "");
    }
}
