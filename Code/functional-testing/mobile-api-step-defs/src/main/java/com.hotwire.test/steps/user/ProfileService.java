/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.user;

import com.hotwire.test.steps.MobileApiService;
import com.hotwire.test.steps.RequestPaths;
import com.hotwire.test.steps.RequestProperties;
import com.hotwire.test.steps.UserInformation;
import hotwire.view.jaxb.domain.mobileapi.AccountData;
import hotwire.view.jaxb.domain.mobileapi.Address;
import hotwire.view.jaxb.domain.mobileapi.CardHolderInfo;
import hotwire.view.jaxb.domain.mobileapi.ClientInfoType;
import hotwire.view.jaxb.domain.mobileapi.HotDollars;
import hotwire.view.jaxb.domain.mobileapi.Name;
import hotwire.view.jaxb.domain.mobileapi.PaymentCard;
import hotwire.view.jaxb.domain.mobileapi.customer.CreateCustomerAccountRQ;
import hotwire.view.jaxb.domain.mobileapi.customer.CreateCustomerAccountRS;
import hotwire.view.jaxb.domain.mobileapi.customer.CustomerProfile;
import hotwire.view.jaxb.domain.mobileapi.customer.EmailSubscriptions;
import hotwire.view.jaxb.domain.mobileapi.customer.FavoriteAirport;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveCustomerProfileRQ;
import hotwire.view.jaxb.domain.mobileapi.customer.RetrieveCustomerProfileRS;
import hotwire.view.jaxb.domain.mobileapi.customer.TravelerInfo;
import hotwire.view.jaxb.domain.mobileapi.customer.UpdateCustomerProfileRQ;
import hotwire.view.jaxb.domain.mobileapi.customer.UpdateCustomerProfileRS;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

/**
 * User: v-dsobko
 * Since: 01/19/15
 */
public class ProfileService extends MobileApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileService.class.getName());
    private RetrieveCustomerProfileRQ retrieveProfileRequest;
    private RetrieveCustomerProfileRS retrieveCustomerProfileRS;
    private UpdateCustomerProfileRS updateProfileRS;
    private UpdateCustomerProfileRQ updateProfileRQ;
    private CreateCustomerAccountRS createCustomerResponse;
    private RequestProperties requestProperties;
    private RequestPaths paths;
    private UserInformation userInformation;

    public void setRequestProperties(RequestProperties requestProperties) {
        this.requestProperties = requestProperties;
    }

    public void setPaths(RequestPaths paths) {
        this.paths = paths;
    }

    public void setUserInformation(UserInformation userInformation) {
        this.userInformation = userInformation;
    }

    public void retrieveProfileWithAccountData() {
        setClientInfo();
        createRetrieveProfileRequestWithAccountData();
        executeRetrieveCustomerProfileRequest();
    }

    private void executeRetrieveCustomerProfileRequest() {
        try {
            client.reset()
                .path(paths.getRetrieveCustomerProfilePath())
                .accept(MediaType.APPLICATION_XML_TYPE)
                .header("Authorization", "Bearer " + requestProperties.getOauthToken());

            retrieveCustomerProfileRS = client.post(retrieveProfileRequest, RetrieveCustomerProfileRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Retrieve customer profile request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Retrieve customer profile request has failed with  " + message, e);
        }
    }

    private void setClientInfo() {
        retrieveProfileRequest = new RetrieveCustomerProfileRQ();
        ClientInfoType clientInfo = new ClientInfoType();
        clientInfo.setCustomerID(userInformation.getCustomerID());
        clientInfo.setClientID(requestProperties.getClientId());
        retrieveProfileRequest.setClientInfo(clientInfo);
    }

    private void createRetrieveProfileRequestWithAccountData() {
        retrieveProfileRequest.getAccountData().add(new CustomerProfile());
        retrieveProfileRequest.getAccountData().add(new EmailSubscriptions());
        retrieveProfileRequest.getAccountData().add(new TravelerInfo());
        retrieveProfileRequest.getAccountData().add(new FavoriteAirport());
        retrieveProfileRequest.getAccountData().add(new HotDollars());
        retrieveProfileRequest.getAccountData().add(new PaymentCard());
    }

    public void retrieveProfileWithPaymentData() {
        setClientInfo();
        retrieveProfileRequest.getAccountData().add(new PaymentCard());
        executeRetrieveCustomerProfileRequest();
        PaymentCard paymentCard = (PaymentCard) retrieveCustomerProfileRS.getAccountData().get(0);
        requestProperties.getUserInformation().setSavedCardName(paymentCard.getCardNickName());
    }

    public void retrieveProfileFavoriteAirport() {
        setClientInfo();
        retrieveProfileRequest.getAccountData().add(new FavoriteAirport());
        executeRetrieveCustomerProfileRequest();
    }

    public void retrieveProfileTravelerInfo() {
        setClientInfo();
        retrieveProfileRequest.getAccountData().add(new TravelerInfo());
        executeRetrieveCustomerProfileRequest();
    }

    public String setUpSavedCard() {
        retrieveProfileWithPaymentData();
        List<AccountData> paymentCards = retrieveCustomerProfileRS.getAccountData();
        PaymentCard paymentCard = (PaymentCard) paymentCards.get(0);
        String cardNickName = paymentCard.getCardNickName();
        requestProperties.getUserInformation().setSavedCardName(cardNickName);
        LOGGER.info("Setting payment card to change: " + cardNickName);
        requestProperties.setQtyOfSavedCards(paymentCards.size());

        return cardNickName;
    }

    public void executeRegistrationRequest() {
        CreateCustomerAccountRQ createCustomerRequest = createCustomerCreateRequest();
        try {
            client.reset()
                .path(paths.getCreateAccountPath())
                .accept(MediaType.APPLICATION_XML_TYPE);
            createCustomerResponse = client.post(createCustomerRequest, CreateCustomerAccountRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Registration request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Registration request has failed with  " + message, e);
        }
    }

    public void verifyErrorOnRegistration(String errorCode, String expectedMessage) {
        CreateCustomerAccountRQ createCustomerRequest = createCustomerCreateRequest();
        try {
            client.reset()
                .path(paths.getCreateAccountPath())
                .accept(MediaType.APPLICATION_XML_TYPE);
            createCustomerResponse = client.post(createCustomerRequest, CreateCustomerAccountRS.class);
        }
        catch (WebApplicationException e) {
            e.getResponse().bufferEntity();
            LOGGER.error("Customer response returned status " + e.getResponse().getStatus());
            LOGGER.error("Response body:\n" + e.getResponse().getEntity());
            String response = e.getResponse().readEntity(String.class);
            assertThat(response).contains(expectedMessage);
            assertThat(response).contains(errorCode);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CreateCustomerAccountRQ createCustomerCreateRequest() {
        CreateCustomerAccountRQ createCustomerRequest = new CreateCustomerAccountRQ();
        CustomerProfile profileInfo = new CustomerProfile();
        Name name = setName(userInformation);
        profileInfo.setName(name);
        profileInfo.setPrimaryPhoneNumber(userInformation.getPrimaryPhoneNumber());
        createCustomerRequest.setProfileInfo(profileInfo);
        profileInfo.setEmailAddress(userInformation.getEmailId());
        Address address = setAddress(userInformation);
        profileInfo.setAddress(address);
        createCustomerRequest.setPassword(userInformation.getPassword());
        createCustomerRequest.setPasswordConfirm(userInformation.getPasswordConfirmation());
        EmailSubscriptions emailSubscriptions = new EmailSubscriptions();
        emailSubscriptions.setBigDeals(false);
        emailSubscriptions.setCruiseNews(false);
        emailSubscriptions.setHotRateAlert(false);
        emailSubscriptions.setSavingNotices(false);
        emailSubscriptions.setTripWatcher(false);
        createCustomerRequest.setEmailSubscriptions(emailSubscriptions);
        return createCustomerRequest;
    }

    private Name setName(UserInformation userInformation) {
        Name name = new Name();
        name.setFirstName(userInformation.getFirstName());
        name.setLastName(userInformation.getLastName());
        return name;
    }

    private Address setAddress(UserInformation userInformation) {
        Address address = new Address();
        address.setCountry(userInformation.getCountry());
        address.setCity(userInformation.getCity());
        address.setAddressLine1(userInformation.getBillingAddress());
        address.setPostalCode(userInformation.getZipCode());
        return address;
    }

    public void verifyCreateCustomerResponse() {
        String oAuthToken = createCustomerResponse.getOAuthToken();
        assertThat(oAuthToken).isNotEmpty();
        requestProperties.setOauthToken(oAuthToken);
        long customerID = createCustomerResponse.getCustomerID();
        assertThat(customerID).isNotNull();
        LOGGER.info("Customer's id is: " + customerID);
    }

    public void retrieveProfileExist() {
        assertThat(retrieveCustomerProfileRS.getAccountData()).isNotNull();
        assertThat(retrieveCustomerProfileRS.getErrors()).isNull();
    }

    public void verifyAccountData() {
        List<AccountData> accountData = retrieveCustomerProfileRS.getAccountData();
        for (int i = 0; i < accountData.size(); i++) {
            if (accountData.get(i) instanceof HotDollars) {
                HotDollars hotDollarsData = (HotDollars) accountData.get(i);
                assertThat(hotDollarsData.getLocalCurrencyCode()).isEqualTo(requestProperties.getCurrencyCode());
                assertThat(hotDollarsData.getAvailableHotDollars()).isEmpty();
                assertThat(hotDollarsData.getExpiredHotDollars()).isEmpty();
                assertThat(hotDollarsData.getHotDollarActivity()).isEmpty();
                assertThat(hotDollarsData.getErrors()).isNull();
            }
            if (accountData.get(i) instanceof EmailSubscriptions) {
                EmailSubscriptions subscriptionsData = (EmailSubscriptions) accountData.get(i);
                assertThat(subscriptionsData.isHotRateAlert()).isFalse();
                assertThat(subscriptionsData.isSavingNotices()).isFalse();
                assertThat(subscriptionsData.isBigDeals()).isFalse();
                assertThat(subscriptionsData.isTripWatcher()).isFalse();
                assertThat(subscriptionsData.isCruiseNews()).isFalse();
                assertThat(subscriptionsData.getErrors()).isNull();
            }
            if (accountData.get(i) instanceof CustomerProfile) {
                CustomerProfile profileData = (CustomerProfile) accountData.get(i);
                requestProperties.setCreatedUserEmail(profileData.getEmailAddress());
                assertThat(profileData.isIsExpressCustomer()).isFalse();
                assertThat(profileData.getHFCStatus()).isNotEmpty();
                assertThat(profileData.getPrimaryPhoneNumber()).isEqualTo(userInformation.getPrimaryPhoneNumber());
                assertThat(profileData.getEmailAddress()).isEqualTo(userInformation.getEmailId());
                assertThat(profileData.getName().getFirstName()).isEqualTo(userInformation.getFirstName());
                assertThat(profileData.getName().getLastName()).isEqualTo(userInformation.getLastName());
                assertThat(profileData.getAddress().getCountry()).isEqualToIgnoringCase(userInformation.getCountry());
                assertThat(profileData.isPromotionalDeals()).isFalse();
                assertThat(profileData.isCreditEmailNotify()).isFalse();
                assertThat(profileData.getErrors()).isNull();
            }
        }
    }

    public void updateCustomerProfile() {
        try {
            client.reset()
                .path(paths.getUpdateCustomerProfilePath())
                .accept(MediaType.APPLICATION_XML_TYPE)
                .header("Authorization", "Bearer " + requestProperties.getOauthToken());

            updateProfileRS = client.post(updateProfileRQ, UpdateCustomerProfileRS.class);
        }
        catch (WebApplicationException e) {
            LOGGER.error("Update customer profile request returned status " + e.getResponse().getStatus());
            String message = e.getResponse().readEntity(String.class);
            LOGGER.error("Response body:\n" + message);
            fail("Update customer profile request has failed with   " + message, e);
        }
    }

    public void createUpdateEmailSubscriptionsRequest(String subscriptionsState) {
        updateProfileRQ = new UpdateCustomerProfileRQ();
        EmailSubscriptions subscriptions = new EmailSubscriptions();
        boolean subscriptionValue = ("all true".equalsIgnoreCase(subscriptionsState)) ? true : false;

        subscriptions.setBigDeals(subscriptionValue);
        subscriptions.setSavingNotices(subscriptionValue);
        subscriptions.setTripWatcher(subscriptionValue);
        subscriptions.setHotRateAlert(subscriptionValue);
        subscriptions.setCruiseNews(subscriptionValue);

        userInformation.setIsBigDeals(subscriptionValue);
        userInformation.setIsTripWatcher(subscriptionValue);
        userInformation.setIsCruizeNews(subscriptionValue);
        userInformation.setIsSavingsNotices(subscriptionValue);
        userInformation.setIsHotAlert(subscriptionValue);

        updateProfileRQ.getAccountData().add(subscriptions);
    }

    public void verifyEmailSubscriptionsData() {
        List<AccountData> accountData = retrieveCustomerProfileRS.getAccountData();
        for (int i = 0; i < accountData.size(); i++) {
            if (accountData.get(i) instanceof EmailSubscriptions) {
                EmailSubscriptions subscriptionsData = (EmailSubscriptions) accountData.get(i);
                assertThat(subscriptionsData.isHotRateAlert()).isEqualTo(userInformation.getIsHotAlert());
                assertThat(subscriptionsData.isSavingNotices()).isEqualTo(userInformation.getIsSavingsNotices());
                assertThat(subscriptionsData.isBigDeals()).isEqualTo(userInformation.getIsBigDeals());
                assertThat(subscriptionsData.isTripWatcher()).isEqualTo(userInformation.getIsTripWatcher());
                assertThat(subscriptionsData.isCruiseNews()).isEqualTo(userInformation.getIsCruizeNews());
                assertThat(subscriptionsData.getErrors()).isNull();
            }
        }
    }

    public void createUpdateCustomerGeneralDataRequest() {
        updateProfileRQ = new UpdateCustomerProfileRQ();
        CustomerProfile profile = new CustomerProfile();
        UserInformation updatedUser = requestProperties.getUserInformation();
        Name name  = setName(updatedUser);
        profile.setName(name);

        Address address = setAddress(updatedUser);
        profile.setAddress(address);

        profile.setPrimaryPhoneNumber(updatedUser.getPrimaryPhoneNumber());
        profile.setPromotionalDeals(updatedUser.isPromotionalDeals());
        profile.setEmailAddress(updatedUser.getEmailId());
        updateProfileRQ.getAccountData().add(profile);
    }

    public void verifyCustomerGeneralInfo() {
        List<AccountData> accountData = retrieveCustomerProfileRS.getAccountData();
        for (int i = 0; i < accountData.size(); i++) {
            if (accountData.get(i) instanceof CustomerProfile) {
                CustomerProfile profileData = (CustomerProfile) accountData.get(i);
                UserInformation expectedUserInfo = requestProperties.getUserInformation();
                assertThat(profileData.getPrimaryPhoneNumber()).isEqualTo(expectedUserInfo.getPrimaryPhoneNumber());
                assertThat(profileData.getEmailAddress()).isEqualTo(expectedUserInfo.getEmailId());
                assertThat(profileData.getName().getFirstName()).isEqualTo(expectedUserInfo.getFirstName());
                assertThat(profileData.getName().getLastName()).isEqualTo(expectedUserInfo.getLastName());
                //assertThat(profileData.isPromotionalDeals()).isEqualTo(expectedUserInfo.isPromotionalDeals());

                assertThat(profileData.isCreditEmailNotify()).isEqualTo(expectedUserInfo.isCreditEmailNotify());
                assertThat(profileData.getErrors()).isNull();
            }
        }
    }

    public void createUpdateCustomerPaymentDataRequest(String cardToUpdate,
                                                       boolean isDeleteCard) throws DatatypeConfigurationException {
        updateProfileRQ = new UpdateCustomerProfileRQ();
        PaymentCard paymentCard = new PaymentCard();
        UserInformation userInfo = requestProperties.getUserInformation();
        paymentCard.setCardNumber(userInfo.getCcNumber());
        CardHolderInfo cardHolder = new CardHolderInfo();
        Address billingAddress = new Address();
        billingAddress.setPostalCode(userInfo.getZipCode());
        billingAddress.setAddressLine1(userInfo.getBillingAddress());
        billingAddress.setCity(userInfo.getCity());
        billingAddress.setState(userInfo.getState());
        billingAddress.setCountry(userInfo.getCountry());
        cardHolder.setBillingAddress(billingAddress);
        paymentCard.setCardHolderInfo(cardHolder);
        paymentCard.setCardNickName(cardToUpdate);
        paymentCard.setDelete(isDeleteCard);
        paymentCard.setPaymentCardType(userInfo.getCcType());
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(new DateTime().plusYears(4).toGregorianCalendar().getTime());

        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        paymentCard.setExpirationDate(datatypeFactory.newXMLGregorianCalendar(gregorianCalendar));

        updateProfileRQ.getAccountData().add(paymentCard);
    }

    public void verifyCustomerPaymentData() {
        List<AccountData> accountData = retrieveCustomerProfileRS.getAccountData();
        PaymentCard paymentCard = (PaymentCard) accountData.get(0);
        UserInformation expectedUserInfo = requestProperties.getUserInformation();

        CardHolderInfo cardHolderInfo = paymentCard.getCardHolderInfo();
        assertThat(cardHolderInfo.getBillingAddress().getCity()).isEqualToIgnoringCase(expectedUserInfo.getCity());
        assertThat(cardHolderInfo.getBillingAddress().getPostalCode())
            .isEqualToIgnoringCase(expectedUserInfo.getZipCode());
        assertThat(cardHolderInfo.getBillingAddress().getAddressLine1())
            .isEqualToIgnoringCase(expectedUserInfo.getBillingAddress());
        assertThat(paymentCard.getErrors()).isNull();
    }

    public void verifyCustomerFavoriteAirport() {
        List<AccountData> accountData = retrieveCustomerProfileRS.getAccountData();
        FavoriteAirport airport = (FavoriteAirport) accountData.get(0);
        assertThat(airport.getValue())
            .isEqualToIgnoringCase(requestProperties.getUserInformation().getFavoriteAirport());
    }

    public void verifyCustomersTravelerInfo() {
        List<AccountData> accountData = retrieveCustomerProfileRS.getAccountData();
        TravelerInfo traveler = (TravelerInfo) accountData.get(0);
        assertThat(traveler.getPrimary()).isTrue();
        assertThat(traveler.getGender()).isEqualToIgnoringCase("F");
        assertThat(traveler.getName().getFirstName())
            .isEqualToIgnoringCase(requestProperties.getUserInformation().getFirstName());
        assertThat(traveler.getName().getLastName())
            .isEqualToIgnoringCase(requestProperties.getUserInformation().getLastName());
    }

    public void verifyCustomerDoesNotHaveFavoriteAirport() {
        assertThat(retrieveCustomerProfileRS.getAccountData().size()).isEqualTo(0);
    }

    public void createUpdateCustomerFavoriteAirportRequest(boolean isDelete) {
        updateProfileRQ = new UpdateCustomerProfileRQ();
        FavoriteAirport airport = new FavoriteAirport();
        airport.setIndex(0);
        if (isDelete) {
            airport.setDelete(isDelete);
            airport.setValue(requestProperties.getUserInformation().getFavoriteAirport());
        }
        else {
            String randomAirportCode = RandomStringUtils.randomAlphanumeric(3).toUpperCase();
            airport.setValue(randomAirportCode);
            requestProperties.getUserInformation().setFavoriteAirport(randomAirportCode);
        }
        updateProfileRQ.getAccountData().add(airport);
    }

    public void createUpdateCustomerTravelerInfoRequest() {
        updateProfileRQ = new UpdateCustomerProfileRQ();
        TravelerInfo travelerInfo = new TravelerInfo();
        travelerInfo.setIndex(0);
        travelerInfo.setPrimary(true);
        travelerInfo.setDelete(false);
        travelerInfo.setGender("F");

        UserInformation userInfo = requestProperties.getUserInformation();
        Name name = setName(userInfo);
        travelerInfo.setName(name);
        updateProfileRQ.getAccountData().add(travelerInfo);
    }

    public void addFavoriteAirportIfCustomerDoesNotHaveIt() {
        retrieveProfileFavoriteAirport();
        List<AccountData> accountData = retrieveCustomerProfileRS.getAccountData();
        if (accountData.size() != 0) {
            FavoriteAirport favoriteAirport = (FavoriteAirport) accountData.get(0);
            requestProperties.getUserInformation().setFavoriteAirport(favoriteAirport.getValue());
        }
        createUpdateCustomerFavoriteAirportRequest(false);
        updateCustomerProfile();
    }
}
