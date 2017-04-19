/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.db;

import static org.fest.assertions.Assertions.assertThat;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeoutException;

import com.hotwire.util.db.c3.C3CustomerDao;
import com.hotwire.util.db.car.CarsDao;
import com.hotwire.util.db.hotel.HotelsDao;
import cucumber.api.DataTable;
import org.htmlcleaner.XPatherException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hotwire.qa.client.db.Db;
import com.hotwire.qa.client.db.ResRow;
import com.hotwire.qa.client.db.Selected;
import com.hotwire.qa.data.UEnum;
import com.hotwire.qa.systems.HwRunEnv;
import com.hotwire.selenium.ParserHelper;
import com.hotwire.selenium.desktop.us.confirm.HotelNewConfirmationPage;
import com.hotwire.test.steps.application.ApplicationModel;
import com.hotwire.test.steps.authentication.AuthenticationParameters;
import com.hotwire.test.steps.common.WebdriverAwareModel;
import com.hotwire.test.steps.mail.MailModelImpl;
import com.hotwire.test.steps.purchase.hotel.HotelPurchaseModel;
import com.hotwire.test.steps.search.SearchParameters;
import com.hotwire.test.steps.search.air.AirSearchParameters;
import com.hotwire.test.steps.search.car.CarSearchParameters;
import com.hotwire.test.steps.search.hotel.HotelSearchParameters;


/**
 * @author adeshmukh
 *
 */
public class DbModelImpl extends WebdriverAwareModel implements DbModel {
    private static String[] amenities;
    private static String[] amenitiesAfterUpdate;

    private static Map<String, String[]> marketing = new HashMap<String, String[]>() {
        {
            String[] onArray = {"O", "S", "B", "F"};
            String[] dbArray = {"N", "N-", "D-", "F"};
            String[] offArray = {"I", "I", "P", "F"};
            String[] affArray = {"A", "g4Ikhy632oI-567fghrty567iuyg76111", "B", "F"};
            put("ONLINE", onArray);
            put("DB", dbArray);
            put("OFFLINE", offArray);
            put("AFFILIATE", affArray);
        }
    };




    private static String TRIP_DATE_DB_FORMAT = "MM/dd/YYYY";
    private static Logger LOGGER = LoggerFactory.getLogger(DbModelImpl.class);
    protected AuthenticationParameters authenticationParameters;
    private String runEnv_name;


    @Autowired
    @Qualifier("hotelPurchaseModel")
    private HotelPurchaseModel model;

    @Autowired
    @Qualifier("searchParameters")
    private SearchParameters searchParameters;

    @Autowired
    @Qualifier("carSearchParameters")
    private CarSearchParameters carSearchParameters;

    @Autowired
    @Qualifier("airSearchParameters")
    private AirSearchParameters airSearchParameters;

    @Autowired
    @Qualifier("hotelSearchParameters")
    private HotelSearchParameters hotelSearchParameters;

    @Autowired
    @Qualifier("applicationModel")
    private ApplicationModel applicationModel;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setAuthenticationParameters(AuthenticationParameters authenticationParameters) {
        this.authenticationParameters = authenticationParameters;
    }

    @Override
    public boolean assertProcessingStatusOfFaxes(String processingStatus) {
        Db db = getEnv().dbCfg.getDb("rpt_user", "rpt_user");
        String itineraryNumber = model.getItineraryNumber().trim();
        LOGGER.info("select processing_statuses from notification_hw h inner join purchase_order p on " +
            "h.source_object_id = p.purchase_order_id where " + "p.display_number = '" + itineraryNumber + "'");

        Selected selected = db.
            select("select processing_statuses from notification_hw h inner join purchase_order p on " +
                "h.source_object_id = p.purchase_order_id where " + "p.display_number = '" + itineraryNumber + "'");

        ResRow[] rows = selected.getRes(1);

        if (processingStatus.toLowerCase().equals("not created")) {
            if (rows.length == 0) {
                LOGGER.info("Asserting whether or not a notification_hw is generated for next day pegasus booking");
                return true;
            }
            else {
                return false;
            }
        }

        LOGGER.info("Processing Status:" + rows[0].toString());
        if (rows[0].toString().toLowerCase().contains(processingStatus.toLowerCase())) {
            LOGGER.info("Processing status is correct");
            return true;
        }
        else {
            LOGGER.info("Processing status is incorrect");
            return false;
        }
    }

    @Override
    public boolean validateXnetBooking(String roomType, String baseAmount) {
        Db db = getEnv().dbCfg.getDb("phoenix_user", "phoenix_user");
        String itineraryNumber = model.getItineraryNumber().trim();
        LOGGER.info("select s.local_base_amount, s.room_type_code from sold_hotel_room s, reservation r " +
            "where s.pgood_id = r.pgood_id and r.purchase_order_id in " +
            "(select purchase_order_id from purchase_order where display_number = '" + itineraryNumber + "')");
        Selected selected = db.
            select("select s.local_base_amount, s.room_type_code from sold_hotel_room s, reservation r " +
                "where s.pgood_id = r.pgood_id and r.purchase_order_id in " +
                "(select purchase_order_id from purchase_order where display_number = '" + itineraryNumber + "')");
        ResRow[] rows = selected.getRes(1);
        if (rows[0].get("room_type_code").toString().contains(roomType) &&
            rows[0].get("local_base_amount").toString().contains(baseAmount)) {
            LOGGER.info("Xnet Booking was successfull");
            return true;
        }
        else {
            LOGGER.info("Booking might have been successfully, but baseamount or room type was incorrect");
            return false;
        }
    }



    /**
     * @param runEnv_name the runEnv_name to set
     */
    public void setRunEnv_name(String runEnv_name) {
        this.runEnv_name = runEnv_name;
    }



    /**
     * This method allows you to set the environment.
     * runEnv_name is a machine where the tests run
     * @return
     */
    private HwRunEnv getEnv() {
        HwRunEnv runEnv = UEnum.match(HwRunEnv.class, runEnv_name, false);
        return runEnv;
    }

    @Override
    public void verifyTripTypeAndActiveDate(String trip_type, String dateVerifying) {
        Map<String, Object> watchedTrip;
        String email = authenticationParameters.getUsername();
        String customer_id = applicationModel.getCustomerIDFromDB(email);

        String sql1 = "select type_code, watched_trip_id, is_active,  active_date, start_date, end_date" +
                " from watched_trip" +
                " where customer_id = \'" + customer_id + "\'";

        watchedTrip = jdbcTemplate.queryForMap(sql1);

        assertThat(watchedTrip.get("TYPE_CODE").toString().equals(trip_type))
                .as("Type_code is equals \'" + trip_type + "\'")
                .isTrue();

        if (null != dateVerifying) {
            long activeDate = ((Timestamp) watchedTrip.get("Active_date")).getTime();
            long endDate = ((Timestamp) watchedTrip.get("End_date")).getTime();
            assertThat((activeDate - endDate) / (1000 * 60 * 60 * 24) == 1)
                    .as("Active_date is one day more then end_date")
                    .isTrue();
        }
    }

    @Override
    public void getCustomerWithoutWatchedTrips() {
        String customer = new C3CustomerDao(getDataBaseConnection()).getCustomerWithoutWatchedTrips();
        authenticationParameters.setCredentials(customer, "hotwire");
    }

    @Override
    public void checkWatchedTripInDB(String vertical) {
        DateFormat tripDateDBFormat = new SimpleDateFormat(TRIP_DATE_DB_FORMAT);
        String email = authenticationParameters.getUsername();
        String customer_id = applicationModel.getCustomerIDFromDB(email);
        String start_date = tripDateDBFormat.format(searchParameters.getStartDate());
        String end_date = tripDateDBFormat.format(searchParameters.getEndDate());
        String carDestination = carSearchParameters.getGlobalSearchParameters().getDestinationLocation();
        String watchedTripId = null;

        if (vertical.equals("hotel")) {
            watchedTripId = new HotelsDao(getDataBaseConnection())
                    .getHotelWatchedTrip(customer_id, start_date, end_date);
        }
        else if (vertical.equals("car")) {
            watchedTripId = new CarsDao(getDataBaseConnection())
                    .getCarWatchedTrip(customer_id, start_date, end_date, carDestination);

        }
        else if (vertical.equals("air")) {
            throw new RuntimeException("Test case is obsoleted. Delete it please");
        }
        assertThat(watchedTripId).as("Watched trip doesn't exist in DB").isNotNull();

    }

    @Override
    public void setXnetDefaultUser() {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        db.update("update xnet_hotel set is_default_user_created = 'Y'");
        db.update("update xnet_hotel set is_default_user_created = 'N' where hotel_id = 1160");
    }

    @Override
    public boolean validateXnetDefaultUserData() {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        Selected selected = db.select("select hotel_id from xnet_hotel where is_default_user_created = 'N'");
        ResRow[] rows = selected.getRes(10);
        if (rows.length != 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean retailHotelFetchData(int limit) {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        Selected selected = db.select("select count(*) from hotel_crs_channel c " +
                            "inner join hotel h on c.hotel_id = h.hotel_id where c.crs_type_code = 'I' " +
                            "and c.is_active_in_crs='Y' and c.is_active='Y' and h.active_ind='Y' " +
                            "and c.RETAIL_MARKETING_LEVEL in (0,2)");
        ResRow[] rows = selected.getRes(limit);
        if (rows.length != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateWEXActivationData() {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        Selected selected = db.select("select * from hw_credit_card where mcc = '50' and create_date > sysdate - 1/24");
        ResRow[] rows = selected.getRes(50);
        if (rows.length != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateWEXDeativationData() {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        Selected selected = db.select("select * from hw_credit_card where update_date > sysdate -1/24 and mcc = '50'");
        ResRow[] rows = selected.getRes(50);
        if (rows.length != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateCurrencyExchangeJob() {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        Selected selected = db.select("select * from exchange_rate where effective_date > sysdate - 1/24");
        ResRow[] rows = selected.getRes(50);
        if (rows.length != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateOrderAddOnJob() {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        Selected selected = db.select("select * from order_add_on where transmit_date > sysdate - 1/24");
        ResRow[] rows = selected.getRes(50);
        if (rows.length != 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateAuthReversalJob() {
        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        Selected selected = db.select("select * from payment_receipt where is_reversed = 'Y' and " +
            "reversal_date > sysdate - 1/24");
        ResRow[] rows = selected.getRes(50);
        if (rows.length != 0) {
            return true;
        }
        return false;
    }

    private String setDateForQuery() {
        String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEPT", "OCT", "NOV", "DEC"};

        Calendar cal = Calendar.getInstance();
        String year = Integer.toString(cal.get(Calendar.YEAR));
        int monthInCalendar = cal.get(Calendar.MONTH);
        String day = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(day);
        strBuilder.append("-");
        strBuilder.append(monthNames[monthInCalendar]);
        strBuilder.append("-");
        strBuilder.append(year.substring(2, 4));

        return strBuilder.toString();
    }

    @Override
    public void verifyValuesInReferralTables(String country, String marketingType, String referralType) {
        String refNumber = null, sql = null;
        switch (country.toUpperCase()) {
            case "US":
                refNumber = "1";
                break;
            case "UK":
                refNumber = "2";
                break;
            case "IE":
                refNumber = "3";
                break;
            default:
                throw new RuntimeException("Verification is not implemented for country: " + country);
        }
        // Set Date to use in query
        String dateForQuery = setDateForQuery();

        Map<String, Object> results = null;
        switch (referralType.toUpperCase()) {
            case "REGISTRATION":
                results = new C3CustomerDao(getDataBaseConnection())
                        .getCustomerReferralRegistration(dateForQuery, authenticationParameters.getUsername());
                break;
            case "SEARCH":
                results = new C3CustomerDao(getDataBaseConnection())
                        .getCustomerReferralSearch(dateForQuery, authenticationParameters.getUsername());
                break;
            case "PURCHASE":
                results = new C3CustomerDao(getDataBaseConnection())
                        .getCustomerReferralPurchase(dateForQuery, authenticationParameters.getUsername());
                break;
            default:
                break;
        }

        String[] auxMarketingType = marketing.get(marketingType);

        assertThat(results.get("REFERRAL_TYPE").equals(auxMarketingType[0]))
                .as("Field REFERRAL_TYPE: " + results.get("REFERRAL_TYPE") + " but expected: " +
                        auxMarketingType[0]).isTrue();
        assertThat(results.get("REFERRER_ID").equals(auxMarketingType[1] + refNumber)).as(
                "Field REFERRER_ID: " + results.get("REFERRER_ID") + " but expected: " + auxMarketingType[1] +
                        refNumber).isTrue();
        assertThat(results.get("LINK_ID").equals(auxMarketingType[2] + refNumber)).as(
                "Field LINK_ID: " + results.get("LINK_ID") + " but expected: " + auxMarketingType[2] + refNumber)
                .isTrue();
        assertThat(results.get("REFERRAL_COUNTRY_ID").equals(auxMarketingType[3] + refNumber)).as(
                "Field REFERRAL_COUNTRY_ID: " + results.get("REFERRAL_COUNTRY_ID") + " but expected: " +
                        auxMarketingType[2] + refNumber).isTrue();

    }

    @Override
    public void extractDBDetailsAndCompareMail() {
        //UI attributes
        String itinerary = null;
        String confirmation = null;
        //Back End attributes
        String hotelName = null;
        String hotelAddress = null;

        //Interact with ui to initialize
        //itinerary and confirmation (extracting them from the UI)
        HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
        itinerary = page.getItineraryNumber();
        confirmation = page.getConfirmationNumber();


        Map res = new HotelsDao(getDataBaseConnection()).getHotelNameAndAddress(itinerary);
        hotelName = (String) res.get("hotel_name");
        hotelAddress = (String) res.get("address_1");

        //Set the subject to be searched, according to locale
        String subject = setSubjectForLocale(itinerary);

        String mailContent = null;
        try {
            Thread.sleep(20000);
            mailContent = getEmailIfItineraryIsMatched(subject, itinerary, 3);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (mailContent != null) {
            try {
                String emailItinerary = ParserHelper.extractAttributeFromHTML(mailContent, "//div/div[2]");
                String emailHotelName = ParserHelper.extractAttributeFromHTML(mailContent, "//td[2]/div/div[2]");
                String emailHotelAddress = ParserHelper.extractAttributeFromHTML(mailContent, "//div[3]");
                String emailHotelConfirmation = ParserHelper.extractAttributeFromHTML(mailContent, "//div[6]");
                String emailCCPhone = ParserHelper.extractAttributeFromHTML(mailContent, "//div[9]");
                String emailCCLink = ParserHelper.extractAttributeFromHTML(mailContent, "//div[11]/a");

                assertThat(emailItinerary.contains(itinerary))
                        .as("values mismatch " + emailItinerary + " vs " + itinerary).isTrue();
                assertThat(emailHotelName.equals(hotelName))
                        .as("values mismatch " + emailHotelName + " vs " + hotelName).isTrue();
                assertThat(emailHotelAddress.equals(hotelAddress))
                        .as("values mismatch " +  emailHotelAddress + " vs " + hotelAddress).isTrue();
                assertThat(emailHotelConfirmation.contains(confirmation))
                        .as("values mismatch " + emailHotelConfirmation + " vs " + confirmation).isTrue();
                assertThat(emailCCLink.equals(setLinkText(itinerary)))
                        .as("values mismatch").isTrue();

            }
            catch (XPatherException e) {
                e.printStackTrace();
            }
        }

    }

    private String getEmailIfItineraryIsMatched(
            String locale, String expectedItinerary, int counter) throws TimeoutException {
        MailModelImpl email = new MailModelImpl();
        String mailContent = email.getEmailHTML(locale);

        String emailItinerary = null;
        try {
            emailItinerary = ParserHelper.extractAttributeFromHTML(mailContent, "//div/div[2]");
            System.out.println("-----------------------------");
            System.out.println(emailItinerary);
            System.out.println("-----------------------------");


            if (emailItinerary.contains(expectedItinerary)) {
                return mailContent;
            }
            else if (counter != 0) {
                getEmailIfItineraryIsMatched(locale, expectedItinerary, --counter);
            }
            else {
                throw new TimeoutException("No mail found containing itinerary");
            }

        }
        catch (XPatherException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }



    public String setSubjectForLocale(String itinerary) {

        Hashtable<String, String> subjects = new Hashtable<>();
        subjects.put("no_NO", "Bekreftelse");
        subjects.put("sv_SE", "Hotwires hotellbekr");
        subjects.put("de_DE", "Hotel-Kaufbest");

        String query = "select locale from purchase_order " +
            "where display_number = " + itinerary;



        String localeInDb = (String) jdbcTemplate.queryForObject(
            query, String.class);

        String subject = "Hotwire hotel purchase confirmation";

        if (subjects.containsKey(localeInDb)) {
            subject = subjects.get(localeInDb);
            return subject;
        }


        return subject;
    }

    public String setLinkText(String itinerary) {
        Hashtable<String, String> linkContents = new Hashtable<>();
        linkContents.put("no_NO", "send oss en e-post");
        linkContents.put("sv_SE", "skicka ett e-postmeddelande");
        linkContents.put("de_DE", "senden Sie uns bitte eine E-Mail.");

        String query = "select locale from purchase_order " +
            "where display_number = " + itinerary;



        String localeInDb = (String) jdbcTemplate.queryForObject(
            query, String.class);

            //System.out.println("The locale is : " + localeInDb);

        String content = null;

        boolean isLocale = linkContents.containsKey(localeInDb);

            //System.out.println("isLocale : " + isLocale);

        if (isLocale) {
            content = linkContents.get(localeInDb);
            //System.out.println("If has been accesed, " + content);
            return content;
        }
        else {
            content = "send us an email";
        }

        return content;
    }

    public void updateOpaqueAmenitiesCodes() {
        HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
        String hotelId = extractHotelIdFromDB();
        assertThat(isTripAdvisorRankingShown(page)).as("Ranking is not displayed").isTrue();

        Db db = getEnv().dbCfg.getDb("dmu", "dmu");
        db.echo();
        db.update("update hotel set opaque_amenity_codes = 'AS/FC/PO' where hotel_id = " + hotelId);
        List<WebElement> listedAmenities = page.getAmenities();
        amenitiesAfterUpdate = new String[listedAmenities.size()];
        int i = 0;
        for (WebElement webElement : listedAmenities) {
            amenitiesAfterUpdate[i] = webElement.getText();
            i++;
        }
    }

    private String extractHotelIdFromDB() {
        HotelNewConfirmationPage page = new HotelNewConfirmationPage(getWebdriverInstance());
        String confirmationItinerary = page.getItineraryNumber();
        assertThat(isTripAdvisorRankingShown(page)).as("Ranking is not displayed").isTrue();

        List<WebElement> listedAmenities = page.getAmenities();
        amenities = new String[listedAmenities.size()];
        int i = 0;
        for (WebElement webElement : listedAmenities) {
            amenities[i] = webElement.getText();
            i++;
        }

        String sql = "select h.hotel_id " +
            "from hotel h, sold_hotel_room s, reservation r, purchase_order p " +
            "where p.purchase_order_id = r.purchase_order_id " +
            "and r.pgood_id = s.pgood_id and s.hotel_id = h.hotel_id " +
            "and p.display_number = " + confirmationItinerary;

        Db db = getEnv().dbCfg.getDb("rpt_user", "rpt_user");
        db.echo();

        Selected selected = db.select(sql);
        ResRow[] rows = selected.getAllRes();

        String hotelId = rows[0].get("HOTEL_ID").toString();

        return hotelId;
    }

    @Override
    public void verifyPurchaseStatusAmountAndCurrency() {
        HotelNewConfirmationPage hotelConfirmationPage = new HotelNewConfirmationPage(getWebdriverInstance());
        String hotwireItineraryNumber = hotelConfirmationPage.getItineraryNumber();
        System.out.println("Hotwire itinerary: " + hotwireItineraryNumber);
        String tripTotal = hotelConfirmationPage.getTripTotal();
        System.out.println("Hotwire trip total: " + tripTotal);
        // Set Date to use in query
        String dateForQuery = setDateForQuery();

        // Provide query and extract values for specific mail and current date
        Db db = getEnv().dbCfg.getDb("rpt_user", "rpt_user");
        db.echo();

        String query = "select pr.status_code, po.customer_currency_total_amount, po.customer_currency_code " +
            "from purchase_order po, payment_receipt pr " +
            "where pr.purchase_order_id = po.purchase_order_id " +
            "and po.display_number = '" + hotwireItineraryNumber + "'";

        Selected selected = db.select(query);
        ResRow[] rows = selected.getAllRes();
        System.out.println(rows.length  + " Result Set has been extracted");
        System.out.println("All rows: ");
        int count = 1;
        String status = "30200";
        for (ResRow resRow : rows) {
            if (count > 2) {
                status = "30400";
            }

            assertThat(resRow.get("STATUS_CODE").toString().equals(status)).as(
                "Field STATUS_CODE: " + resRow.get("STATUS_CODE") + " but expected: " + status).isTrue();

            assertThat(tripTotal.contains(resRow.get("CUSTOMER_CURRENCY_TOTAL_AMOUNT").toString())).as(
                "Field CUSTOMER_CURRENCY_TOTAL_AMOUNT: " + resRow.get("CUSTOMER_CURRENCY_TOTAL_AMOUNT") +
                " but expected: " + tripTotal).isTrue();

            String currencyCode = (String) resRow.get("CUSTOMER_CURRENCY_CODE");
            if (currencyCode.equals("USD")) {
                currencyCode = "$";
            }
            else if (currencyCode.equals("EUR")) {
                currencyCode = "€";
            }
            else if (currencyCode.equals("GBP")) {
                currencyCode = "£";
            }

            assertThat(tripTotal.contains(currencyCode)).as(
                "Field CUSTOMER_CURRENCY_CODE: " + currencyCode + " but expected: " + tripTotal).isTrue();

            count = count + 1;
        }
    }

    private boolean isTripAdvisorRankingShown(HotelNewConfirmationPage page) {
        try {
            page.getTripAdvisorRanking();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public void verifyAmenitiesMatchAfterUpdate() {
        for (int i = 0; i < amenities.length; i++) {
            String beforeUpdate = amenities[i];
            String afterUpdate = amenitiesAfterUpdate[i];
            assertThat(beforeUpdate.equals(afterUpdate)).as("Expected : " + beforeUpdate +
                " does not match actual" + afterUpdate).isTrue();
        }
    }

    @Override
    public void checkPartnersInClickTrackingTable(DataTable table) {
        List<Map<String, Object>> rawPartnerCodesDB = new CarsDao(getDataBaseConnection()).getSearchPartnersCode();
        List<String> expectedPartnerCodes = table.asList(String.class);
        List<String> partnerCodesDB = new ArrayList<String>();

        for (Map<String, Object> elem : rawPartnerCodesDB) {
            partnerCodesDB.add((String) elem.entrySet().iterator().next().getValue());
        }

        assertThat(rawPartnerCodesDB.size())
                .as("Partners code tables (gherkin and DB) have the different size")
                .isEqualTo(expectedPartnerCodes.size());

        assertThat(partnerCodesDB.containsAll(expectedPartnerCodes))
                .as("Partner codes table in DB is not correct").isTrue();
    }
}
