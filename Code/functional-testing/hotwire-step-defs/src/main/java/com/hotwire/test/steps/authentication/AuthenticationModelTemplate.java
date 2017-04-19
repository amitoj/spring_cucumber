/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.authentication;

import com.hotwire.selenium.desktop.account.PasswordAssistancePage;
import com.hotwire.selenium.desktop.us.AbstractUSPage;
import com.hotwire.selenium.desktop.us.SignInPage;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.testing.UnimplementedTestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * User: v-mzabuga
 * Date: 7/18/12
 * Time: 5:04 AM
 */
public abstract class AuthenticationModelTemplate extends WebdriverAwareModel implements AuthenticationModel {

    protected AuthenticationParameters authenticationParameters;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setAuthenticationParameters(AuthenticationParameters authenticationParameters) {
        this.authenticationParameters = authenticationParameters;
    }

    @Override
    public Date getTripDepartureDateFromDb(String bookingType, String email) {
        String query;
        String bookingTable;
        switch (bookingType) {
            case "hotel":
                bookingTable = "shr";
                query = "select max(shr.start_date) " +
                        "FROM reservation r, sold_hotel_room shr, purchase_order p, customer c\n" +
                        "WHERE r.pgood_id = shr.pgood_id\n" +
                        "AND p.purchase_order_id = r.purchase_order_id\n" +
                        "AND p.customer_id = c.customer_id\n" +
                        "AND c.email = '" + email + "'\n" +
                        "AND p.status_code >= 30020";
                break;
            case "car":
                bookingTable = "src";
                query = "select max(src.start_date) " +
                        "FROM reservation r, sold_rental_car src, purchase_order p, customer c\n" +
                        "WHERE r.pgood_id = src.pgood_id\n" +
                        "AND p.purchase_order_id = r.purchase_order_id\n" +
                        "AND p.customer_id = c.customer_id\n" +
                        "AND c.email = '" + email + "'\n" +
                        "AND p.status_code >= 30020";
                break;
            default:
                throw new UnimplementedTestException("No such booking type.");
        }
        Map<String, Object> result = jdbcTemplate.queryForMap(query);
        return (Date) result.get("MAX(" + bookingTable.toUpperCase() + ".START_DATE)");
    }

    @Override
    public void attemptToAuthenticate(AuthenticationParameters parameters) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyAuthenticationExceptionRaised() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyValidationErrorOnPassword() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyValidationErrorOnUsername() {
        throw new UnimplementedTestException("Implement me!");
    }
    @Override
    public void loginWithCreditcardNumber(String emailid, String creditcardnumber) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void loginWithInvalidCreditCardNumber(String email) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void loginInFourTimesToGetErrorMessage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void optionalSignInStatus(String condition) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void openOptionalSignInPopUp() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void goToPasswordAssistance() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void logInByOptionalSignIn() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void submitOptionalSignInForm() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void writeToOptionalSignInField(String fieldType, String fieldValue) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void setPasswordAssistanceEmail(String email) {
        throw new UnimplementedTestException("Implement me!");
    }

    public void verifyPasswordAssistanceEmailPrefilled() { //ROW and domestic
        String preFilledEmail = new PasswordAssistancePage(getWebdriverInstance()).getEmailField();
        assertThat(preFilledEmail
                .equals(authenticationParameters.getUsername()))
                .as("Expected pre-filled value: " + authenticationParameters.getUsername() +
                        " but actual is: " + preFilledEmail)
                .isTrue();
    }

    @Override
    public void verifyPasswordAssistanceEmailConfirmation() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifyPrepopulatedPA_Email(String email) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void loginWithDepartureDate(String emailid) {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void verifySignInPage() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void goToSignInPage() { // ROW and domestic site
        new AbstractUSPage(getWebdriverInstance())
                .getGlobalHeader()
                .navigateToSignInPage();
    }

    @Override
    public void tryToSignIn(Integer iTimes) { // ROW and domestic site
        SignInPage signInPage = new SignInPage(getWebdriverInstance());
        String password = authenticationParameters.getPassword();
        signInPage.withUserName(authenticationParameters.getUsername());

        for (Integer i = 0; i < iTimes; i++) {
            //type password in a loop while if we type wrong and submit the form, password field will be cleared
            signInPage.withPassword(password);
            signInPage.signIn();
        }

    }

    @Override
    public void clickInAHurryLink() {
        throw new UnimplementedTestException("Implement me!");
    }


    @Override
    public String getBlackListEmail() {
        String sql = "select email from customer where is_active = 'N' " +
                "and verified_date = (select MAX(verified_date) " +
                "from customer where is_active = 'N')";
        Map<String, Object> result  = jdbcTemplate.queryForMap(sql);
        return result.get("EMAIL").toString();
    }

    @Override
    public void setPasswordAssistancePassword(String password, String confirmationPassword) {
        throw new UnimplementedTestException("Implement me!");
    }

    public void attemptToSignIn() {
        throw new UnimplementedTestException("Implement me!");
    }

    @Override
    public void confirmErrorHandling(String errorMsg) {
        throw new UnimplementedTestException("Implement me!");
    }
}
