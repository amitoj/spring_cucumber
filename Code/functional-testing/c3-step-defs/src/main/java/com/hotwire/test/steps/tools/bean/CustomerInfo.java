/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean;

import com.hotwire.util.db.c3.C3CustomerDao;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * Information about itinerary that used in C3 workflow.
 * User: v-ozelenov
 */

public class CustomerInfo extends ToolsAbstractBean {
    @Resource
    Map<String, CustomerInfo>  customerProfiles;

    private String email;
    private String customerId;
    private String altPhone;
    private String firstName;
    private String lastName;
    private String fullName;
    private String participantFN;
    private String participantLN;
    private String phoneNumber;
    private String phoneCountryCode;
    private String password;
    private String zipCode;
    private String country;
    private String countryCode;
    private CustomerTypes customerType = CustomerTypes.GUEST;
    private String accountType;
    private String watermark;
    private Double hotDollars;
    private Double hotDollarsChange;
    private Double recentlyUsedDollars;
    private String rawEmails;
    private DateTime expressDownGradeDate;
    private String ipAddress;

    private DateTime hotDollarsExpirationDate;

    private Map<String, Double> customersHotDollars;

    private String expressProgram;
    private String breadcrumbs;
    private String middleName;
    private DateTime accountCreated;

    public DateTime getHotDollarsExpirationDate() {
        return hotDollarsExpirationDate;
    }

    public void setHotDollarsExpirationDate(DateTime hotDollarsExpirationDate) {
        this.hotDollarsExpirationDate = hotDollarsExpirationDate;
    }

    public Map<String, Double> getCustomersHotDollars() {
        return customersHotDollars;
    }

    public void setCustomersHotDollars(String email, Double amount) {
        customersHotDollars.put(email, amount);
    }

    public void createCustomerHotDollars(Map<String, Double> map) {
        this.customersHotDollars = map;
    }

    public void addCustomersHotDollars(String amount) {
        for (String s : customersHotDollars.keySet()) {
            customersHotDollars.put(s, customersHotDollars.get(s) + Double.valueOf(amount));
        }
    }

    public String getHotDollars() {
        return new DecimalFormat("0.00").format(hotDollars);
    }

    public Double getHotDollarsAmount() {
        return hotDollars;
    }

    public String getCustomerHotDollars(String email) {
        return new DecimalFormat("0.##").format(customersHotDollars.get(email));
    }

    public String getHotDollarsWithZeros() {
        return new DecimalFormat("0.##").format(hotDollars);
    }

    public void setHotDollars(String hotDollars) {
        this.hotDollars = Double.valueOf(hotDollars);
    }

    public String getHotDollarsChange() {
        return new DecimalFormat("0.##").format(hotDollarsChange);
    }

    public Double getHotDollarsChangeAmount() {
        return hotDollarsChange;
    }

    public void setHotDollarsChange(String hotDollarsChange) {
        this.hotDollarsChange = Double.valueOf(hotDollarsChange);
    }

    public void addHotDollars() {
        this.hotDollars = hotDollars + hotDollarsChange;
    }

    public Double getRecentlyUsedDollars() {
        return recentlyUsedDollars;
    }

    public void setRecentlyUsedDollars(Double recentlyUsedDollars) {
        this.recentlyUsedDollars = recentlyUsedDollars;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

    public String getWatermark() {
        return watermark;
    }

    public DateTime getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(DateTime accountCreated) {
        this.accountCreated = accountCreated;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCustomerTypeByEmail(String email) {
        if (!new C3CustomerDao(getDataBaseConnection()).doesCustomerHavePassword(email)) {
            this.customerType = CustomerTypes.NEW;
        }
        else {
            this.customerType = CustomerTypes.NON_EXPRESS;
        }
    }

    public void setCustomerEmailByItinerary(String itinerary) {
        this.email = new C3CustomerDao(getDataBaseConnection()).getEmailOfItinerary(itinerary);
    }

    public void setExpressProgram(String expressProgram) {
        this.expressProgram = expressProgram;
    }

    public String getExpressProgram() {
        return expressProgram;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setCustomerInfoByItinerary(String itinerary) {
        Map info = new C3CustomerDao(getDataBaseConnection()).getCustomerByItinerary(itinerary);
        this.participantFN = info.get("PARTICIPANT_FN").toString();
        this.participantLN = info.get("PARTICIPANT_LN").toString();
        setCustomerName(info);
        this.email = info.get("EMAIL").toString();
    }

    public void setCustomerByUniqueLastName(String name) {
        Map info = new C3CustomerDao(getDataBaseConnection()).getCustomerByUniqueLastName(name);
        setCustomerInformation(info);
    }

    public void setBreadcrumbs(String breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public String getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setCustomerInformation(Map customerInformation) {
        setCustomerName(customerInformation);
        this.email = getValue(customerInformation, "EMAIL");
        this.accountCreated = DateTimeFormat.forPattern("yyyy-MM-dd")
                .parseDateTime(getValue(customerInformation, "CREATE_DATE").replaceAll(" .*$", ""));
        this.phoneNumber = getValue(customerInformation, "PHONE");
        this.altPhone = getValue(customerInformation, "ALTERNATE_PHONE");
        this.zipCode = getValue(customerInformation, "REGISTER_ZIP");
        this.countryCode = getValue(customerInformation, "REGISTER_COUNTRY_CODE");
        this.country = getValue(customerInformation, "COUNTRY");
    }

    public void setCustomerName(Map customerInformation) {
        this.customerId = getValue(customerInformation, "CUSTOMER_ID");
        this.firstName = getValue(customerInformation, "FIRST_NAME");
        this.lastName = getValue(customerInformation, "LAST_NAME");
        this.middleName = getValue(customerInformation, "middle_name");
        setFullName();
    }

    /**
     * Contains all available customer types
     */
    public enum CustomerTypes { GUEST, NEW, NON_EXPRESS, EX_EXPRESS, EXPRESS, EXPRESS_ELITE, PARTNER }


    public CustomerTypes getCustomerType() {
        return customerType;
    }

    public String getCustomerTypeString() {
        return customerType.name();
    }

    public void setCustomerType(CustomerTypes customerType) {
        this.customerType = customerType;
    }

    public void setCustomerType(String type) {
        this.customerType = CustomerTypes.valueOf(type.toUpperCase().replace(" ", "_").replace("-", "_"));
        CustomerInfo profile = customerProfiles.get(getCustomerTypeString());
        this.email = profile.getEmail();
        this.zipCode = profile.getZipCode();
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public boolean isGuest() {
        if (CustomerTypes.GUEST.equals(customerType)) {
            return true;
        }
        return false;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setFullName() {
        if (this.middleName.equals("")) {
            this.fullName = this.firstName + " " + this.lastName;
        }
        else {
            this.fullName = String.format("%s %s %s", this.firstName, this.middleName, this.lastName);
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String eMail) {
        this.email = eMail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAltPhone() {
        return altPhone;
    }

    public void setAltPhone(String altPhone) {
        this.altPhone = altPhone;
    }

    public void setCustomerRandomEmail() {
        this.email = "test_hotwire_" + System.currentTimeMillis() + "@hotwire.com";
    }

    public void setC3CustomerRandomEmail() {
        this.email = "test_c3_" + System.currentTimeMillis() + "@hotwire.com";
    }

    public String getRawEmails() {
        return rawEmails;
    }

    public void setRawEmails(String rawEmails) {
        this.rawEmails = rawEmails;
    }

    public DateTime getExpressDownGradeDate() {
        return expressDownGradeDate;
    }

    public void setExpressDownGradeDate(DateTime expressDownGradeDate) {
        this.expressDownGradeDate = expressDownGradeDate;
    }

    public String getParticipantFN() {
        return participantFN;
    }

    public void setParticipantFN(String participantFN) {
        this.participantFN = participantFN;
    }

    public String getParticipantLN() {
        return participantLN;
    }

    public void setParticipantLN(String participantLN) {
        this.participantLN = participantLN;
    }
}
