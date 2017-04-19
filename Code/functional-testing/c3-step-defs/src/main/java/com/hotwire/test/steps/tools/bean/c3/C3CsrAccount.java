/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;

/**
 * User: v-ozelenov
 * Date: 12/12/13
 *  Aggregated CSR account information
 * <table border="1">
 * <tr><th>Username</th><th>Responsibilities</th></tr>
 * <tr><td>csrcroz1</td><td>HW_Customer_Operations</td></tr>
 * <tr><td>csrcroz2</td><td>Research</td></tr>
 * <tr><td>csrcroz3</td><td>View_Only</td></tr>
 * <tr><td>csrcroz12</td><td>ITA_Green_Screen_access_only</td></tr>
 * <tr><td>csrcroz4</td><td>Call_Center_Management</td></tr>
 * <tr><td>csrcroz5</td><td>Call_Center_Service_CSR</td></tr>
 * <tr><td>csrcroz6</td><td>TRX</td></tr>
 * <tr><td>csrcroz9</td><td>Finance, overcharges</td></tr>
 * <tr><td>csrcroz18</td><td>Corporate_Escalations</td></tr>
 * <tr><td>csrcroz21</td><td>Hotel_Operations_Admin</td></tr>
 * <tr><td>csrcroz19</td><td>Hotel_Review_Moderator</td></tr>
 * <tr><td>csrcroz20</td><td>Hotel_Review_Supervisor</td></tr>
 * <tr><td>csrcroz22</td><td>Call_Center_Sales_CSR</td></tr>
 * <tr><td>csrcroz23</td><td>Call_Center_Management_2</td></tr>
 * <tr><td>csrcroz24</td><td>Hotel_Operations</td></tr>
 * <tr><td>csrcroz25</td><td>Call_Center_Management_PPP</td></tr>
 * <tr><td>csrcroz26</td><td>Call_Center_Service_CSR_PPP</td></tr>
 * <tr><td>csrcroz30</td><td>Contact_Center_International_Agent</td></tr>
 * <tr><td>csrcroz27</td><td>Travel_Ticker_Billing_Admin</td></tr>
 * </table>
 */
public class C3CsrAccount extends ToolsAbstractBean {
    /**
     * All available CSR users
     */
    private enum UserNames {
        csrcroz1,
        csrcroz2,
        csrcroz3,
        csrcroz12,
        csrcroz4,
        csrcroz5,
        csrcroz6,
        csrcroz8,
        csrcroz9,
        csrcroz10,
        csrcroz13,
        csrcroz14,
        csrcroz18,
        csrcroz21,
        csrcroz19,
        csrcroz20,
        csrcroz22,
        csrcroz23,
        csrcroz24,
        csrcroz25,
        csrcroz26,
        csrcroz30,
        csrcroz27
    }
    private UserNames username;

    //Only used in editCSRAccount
    private String agentName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordLockTimestamp;


    public String getPasswordLockTimestamp() {
        return passwordLockTimestamp;
    }

    public void setPasswordLockTimestamp(String passwordLockTimestamp) {
        this.passwordLockTimestamp = passwordLockTimestamp;
    }
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }


    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[" + username + "] CSR account..\n");
        out.append("FirstName: " + firstName + "; LastName: " + lastName + "; Email: " + email + "\n");
        return out.toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username.toString();
    }

    public void setUsername(String username) {
        this.username = UserNames.valueOf(username);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
