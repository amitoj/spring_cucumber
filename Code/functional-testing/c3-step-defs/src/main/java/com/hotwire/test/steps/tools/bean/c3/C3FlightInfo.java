/*
* Copyright 2014 Hotwire. All Rights Reserved.
*
* This software is the proprietary information of Hotwire.
* Use is subject to license terms.
*/

package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: v-ikomarov
 * Date: 10/17/14
 * Time: 2:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3FlightInfo extends ToolsAbstractBean {
    private List<Date> departingStartDateAndTime;
    private List<Date> departingEndDateAndTime;
    private List<String> departingFromLocation;
    private List<String> departingToLocation;
    private List<String> departingCarrier;
    private List<String> departingFlightNumber;
    private List<String> departingTotalTripTime;
    private String departingTypeOfFlight;

    private  List<Date>  returningStartDateAndTime;
    private  List<Date>  returningEndDateAndTime;

    private List<String> returningFromLocation;
    private List<String> returningToLocation;
    private List<String> returningCarrier;
    private List<String> returningFlightNumber;
    private List<String> returningTotalTripTime;
    private String returningTypeOfFlight;

    private List<String> airTicketsNumbers;
    private List<String> namesOfPassengers;

    private String numberOfTickets;
    private String confirmationNumber;


    public List<String> getDepartingFlightNumber() {
        return departingFlightNumber;
    }

    public void setDepartingFlightNumber(List<String> departingFlightNumber) {
        this.departingFlightNumber = departingFlightNumber;
    }

    public void setReturningFlightNumber(List<String> returningFlightNumber) {
        this.returningFlightNumber = returningFlightNumber;
    }

    public List<String> getReturningFlightNumber() {
        return returningFlightNumber;
    }

    public String getDepartingTypeOfFlight() {
        return departingTypeOfFlight;
    }

    public void setDepartingTypeOfFlight(String departingTypeOfFlight) {
        this.departingTypeOfFlight = departingTypeOfFlight;
    }

    public String getReturningTypeOfFlight() {
        return returningTypeOfFlight;
    }

    public void setReturningTypeOfFlight(String returningTypeOfFlight) {
        this.returningTypeOfFlight = returningTypeOfFlight;
    }


    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }


    public List<String> getAirTicketsNumbers() {
        return airTicketsNumbers;
    }

    public void setAirTicketsNumbers(List<String> airTicketsNumbers) {
        this.airTicketsNumbers = airTicketsNumbers;
    }

    public String getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(String numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public List<String> getNamesOfPassengers() {
        return namesOfPassengers;
    }

    public void setNamesOfPassengers(List<String> namesOfPassengers) {
        this.namesOfPassengers = namesOfPassengers;
    }

    public List<Date> getDepartingStartDateAndTime() {
        return departingStartDateAndTime;
    }

    public void setDepartingStartDateAndTime(List<Date> departingStartDateAndTime) {
        this.departingStartDateAndTime = departingStartDateAndTime;
    }

    public List<Date> getDepartingEndDateAndTime() {
        return departingEndDateAndTime;
    }

    public void setDepartingEndDateAndTime(List<Date> departingEndDateAndTime) {
        this.departingEndDateAndTime = departingEndDateAndTime;
    }

    public List<String> getDepartingFromLocation() {
        return departingFromLocation;
    }

    public void setDepartingFromLocation(List<String> departingFromLocation) {
        this.departingFromLocation = departingFromLocation;
    }

    public List<String> getDepartingToLocation() {
        return departingToLocation;
    }

    public void setDepartingToLocation(List<String> departingToLocation) {
        this.departingToLocation = departingToLocation;
    }

    public List<String> getDepartingCarrier() {
        return departingCarrier;
    }

    public void setDepartingCarrier(List<String> departingCarrier) {
        this.departingCarrier = departingCarrier;
    }

    public List<Date> getReturningStartDateAndTime() {
        return returningStartDateAndTime;
    }

    public void setReturningStartDateAndTime(List<Date> returningStartDateAndTime) {
        this.returningStartDateAndTime = returningStartDateAndTime;
    }

    public List<Date> getReturningEndDateAndTime() {
        return returningEndDateAndTime;
    }

    public void setReturningEndDateAndTime(List<Date> returningEndDateAndTime) {
        this.returningEndDateAndTime = returningEndDateAndTime;
    }

    public List<String> getReturningFromLocation() {
        return returningFromLocation;
    }

    public void setReturningFromLocation(List<String> returningFromLocation) {
        this.returningFromLocation = returningFromLocation;
    }

    public List<String> getReturningToLocation() {
        return returningToLocation;
    }

    public void setReturningToLocation(List<String> returningToLocation) {
        this.returningToLocation = returningToLocation;
    }

    public List<String> getReturningCarrier() {
        return returningCarrier;
    }

    public void setReturningCarrier(List<String> returningCarrier) {
        this.returningCarrier = returningCarrier;
    }

    public List<String> getDepartingTotalTripTime() {
        return departingTotalTripTime;
    }

    public void setDepartingTotalTripTime(List<String> departingTotalTripTime) {
        this.departingTotalTripTime = departingTotalTripTime;
    }

    public List<String> getReturningTotalTripTime() {
        return returningTotalTripTime;
    }

    public void setReturningTotalTripTime(List<String> returningTotalTripTime) {
        this.returningTotalTripTime = returningTotalTripTime;
    }
}
