/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.help;

import com.hotwire.selenium.desktop.globalheader.GlobalHeader;
import com.hotwire.selenium.desktop.us.helpcenter.AirQASearchingBookingPage;
import com.hotwire.selenium.desktop.us.helpcenter.CarQASearchingBookingPage;
import com.hotwire.selenium.desktop.us.helpcenter.HelpCenterPage;
import com.hotwire.selenium.desktop.us.helpcenter.HotelQASearchingBookingPage;

import static org.fest.assertions.Assertions.assertThat;

public class HelpCenterModelWebApp extends HelpCenterModelTemplate {

    @Override
    public void openHelpCenterPage() {
        new GlobalHeader(getWebdriverInstance()).navigateToHelpCenterPage();
    }

    @Override
    public void verifyHelpCenterPage() {
        new HelpCenterPage(getWebdriverInstance());
    }

    @Override
    public void openQASearchingAndBookingPage(String vertical) {
        if ("hotel".equalsIgnoreCase(vertical)) {
            new HelpCenterPage(getWebdriverInstance()).getHotelsSearchingAndBookingQA().click();
        }
        else if ("car".equalsIgnoreCase(vertical)) {
            new HelpCenterPage(getWebdriverInstance()).getCarsSearchingAndBookingQA().click();
        }
        else if ("air".equalsIgnoreCase(vertical)) {
            new HelpCenterPage(getWebdriverInstance()).getAirsSearchingAndBookingQA().click();
        }
        else {
            throw new RuntimeException(
                "Error! Unsupported vertical type. Expecting air, car or hotel. Found " + vertical);
        }
    }

    @Override
    public void verifyQASearchingAndBookingPage(String vertical) {
        if ("hotel".equalsIgnoreCase(vertical)) {
            new HotelQASearchingBookingPage(getWebdriverInstance());
        }
        else if ("car".equalsIgnoreCase(vertical)) {
            new CarQASearchingBookingPage(getWebdriverInstance());
        }
        else if ("air".equalsIgnoreCase(vertical)) {
            new AirQASearchingBookingPage(getWebdriverInstance());
        }
        else {
            throw new RuntimeException(
                "Error! Unsupported vertical type. Expecting air, car or hotel. Found " + vertical);
        }
    }

    @Override
    public void verifyEmailMsgWithinHotwireCustomerCareModuleForCountry(String country) {
        HelpCenterPage hcPage = new HelpCenterPage(getWebdriverInstance());
        String emailLabel = null, emailLink = null;
        switch (country.toUpperCase()) {
            case "UNITED KINGDOM":
            case "IRELAND":
            case "AUSTRALIA":
            case "NEW ZEALAND":
            case "HONG KONG":
            case "SINGAPORE":
                emailLabel = "Email";
                emailLink = "Send us an email";
                break;
            case "SVERIGE":
                emailLabel = "E-post";
                emailLink = "Skicka oss ett e-postmeddeland";
                break;
            case "NORGE":
                emailLabel = "E-postadresse";
                emailLink = "Send oss en e-post";
                break;
            case "DANMARK":
                emailLabel = "E-mail";
                emailLink = "Send os en e-mail";
                break;
            case "DEUTSCHLAND":
                emailLabel = "E-Mail";
                emailLink = "Senden Sie uns eine E-Mail";
                break;
            case "MEXICO":
            case "MÉXICO":
                emailLabel = "Correo electrónico";
                emailLink = "Envíanos un correo electrónico";
                break;
            default:
                emailLabel = "Email";
                emailLink = "Send us an email";
                break;
        }
        assertThat(hcPage.getGroupEmailContent().contains(emailLabel)).as(
            "Email group for " + country + " should have as title: " + emailLabel).isTrue();
        assertThat(hcPage.getGroupEmailContent().contains(emailLink)).as(
            "Email group for " + country + " should contain a module link: " + emailLink).isTrue();
    }

    @Override
    public void verifyHoursOfAvailabilityMsgWithinHotwireCustomerCareModuleForCountry(String country) {
        HelpCenterPage hcPage = new HelpCenterPage(getWebdriverInstance());
        String availabiltyLabel = null, availabiltyMsgHrs = null, availabiltyMsgDays = null;
        switch (country.toUpperCase()) {
            case "UNITED KINGDOM":
            case "IRELAND":
            case "AUSTRALIA":
            case "NEW ZEALAND":
            case "HONG KONG":
            case "SINGAPORE":
                availabiltyLabel = "Hours of availability in English:";
                availabiltyMsgHrs = "24 hours a day";
                availabiltyMsgDays = "Seven days a week";
                break;
            case "SVERIGE":
                availabiltyLabel = "Öppettider för engelsk kundtjänst:";
                availabiltyMsgHrs = "Dygnet runt";
                availabiltyMsgDays = "Sju dagar i veckan";
                break;
            case "NORGE":
                availabiltyLabel = "Døgnet rundt"; //"Åpningstider:";
                availabiltyMsgHrs = "Døgnet rundt";
                availabiltyMsgDays = "Syv dager i uken";
                break;
            case "DANMARK":
                availabiltyLabel = "Åbningstider på engelsk:";
                availabiltyMsgHrs = "24 timer i døgnet";
                availabiltyMsgDays = "7 dage om ugen";
                break;
            case "DEUTSCHLAND":
                availabiltyLabel = "Öffnungszeiten des englischsprachigen Kundenservice:";
                availabiltyMsgHrs = "24 Stunden am Tag";
                availabiltyMsgDays = "7 Tage die Woche";
                break;
            case "MEXICO":
            case "MÉXICO":
                availabiltyLabel = "Horarios disponibles en inglés:";
                availabiltyMsgHrs = "24 horas al día";
                availabiltyMsgDays = "7 días a la semana";
                break;
            default:
                availabiltyLabel = "Hours of availability in English:";
                availabiltyMsgHrs = "24 hours a day";
                availabiltyMsgDays = "Seven days a week";
                break;
        }
        assertThat(hcPage.getSupportGroupContent().contains(availabiltyLabel))
            .as("Hours of availability group for " + country + " should have as title: " + availabiltyLabel)
            .isTrue();
        assertThat(hcPage.getSupportGroupContent().contains(availabiltyMsgHrs))
            .as("Hours of availability group for " + country + " should contain the message: " + availabiltyMsgHrs)
            .isTrue();
        assertThat(hcPage.getSupportGroupContent().contains(availabiltyMsgDays))
            .as("Hours of availability group for " + country + " should contain the message: " + availabiltyMsgDays)
            .isTrue();
    }

    @Override
    public void verifyPhoneContactsWithinHotwireCustomerCareModuleForCountry(String country) {
        String locationSupportInfo = null;
        switch (country.toUpperCase()) {
            case "UNITED KINGDOM":
            case "IRELAND":
            case "AUSTRALIA":
            case "NEW ZEALAND":
            case "HONG KONG":
            case "SINGAPORE":
                locationSupportInfo = "While you're travelling\n" + "00-800-46894732\n" + "or direct dial\n" +
                                      "+1-949-333-4942 (toll charges will apply)\n" +
                                      "Mobile charges from your carrier may apply";
                break;
            case "SVERIGE":
                locationSupportInfo = "Under resan\n" + "00-800-46894732\n" + "eller direktnummer\n" +
                                      "+1-949-333-4942 (samtalstaxa gäller)\n" +
                                      "Extra kostnader från din mobiloperatör kan tillkomma";
                break;
            case "NORGE":
                locationSupportInfo = "Mens du er på reise\n" + "00-800-46894732\n" + "eller ring direkte\n" +
                                      "+1-949-333-4942 (avgifter vil påløpe)\n" +
                                      "Ringer du fra mobiltelefon, kan nettverksoperatøren din belaste deg for bruken";
                break;
            case "DANMARK":
                locationSupportInfo = "Mens du rejser\n" + "00-800-46894732\n" + "eller ring direkte\n" +
                                      "+1-949-333-4942 (afgift pålægges)\n" +
                                      "Mobilgebyrer fra din udbyder kan være gældende";
                break;
            case "DEUTSCHLAND":
                locationSupportInfo = "Wenn Sie unterwegs sind\n" + "00-800-46894732\n" + "oder Direktdurchwahl\n" +
                                      "+1-949-333-4942 (kostenpflichtig)\n" +
                                      "Mobilfunkpreise variieren je nach Netzbetreiber";
                break;
            case "MEXICO":
            case "MÉXICO":
                locationSupportInfo = "Durante tu viaje\n" + "00-800-46894732\n" + "o la línea directa\n" +
                                      "+1-949-333-4942 (aplican cargos)\n" +
                                      "Es posible que apliquen cargos de tu proveedor de teléfono móvil";
                break;
            default:
                locationSupportInfo = "While you're travelling\n" + "00-800-46894732\n" + "or direct dial\n" +
                                      "+1-949-333-4942 (toll charges will apply)\n" +
                                      "Mobile charges from your carrier may apply";
                break;
        }
        HelpCenterPage hcPage = new HelpCenterPage(getWebdriverInstance());
        String countryPhone = HelpCenterPage.getCountryContactPhoneMap().get(country);
        System.out.println("UI contact phone: " + hcPage.getUIContactPhoneForCountry(country));
        System.out.println("country phone: " + countryPhone);
        assertThat(hcPage.getUIContactPhoneForCountry(country).contains(countryPhone))
                .as("Contact phone number for " + country + " should be: " + countryPhone).isTrue();

        assertThat(hcPage.getLocationSupporInfo().contains(locationSupportInfo))
                .as("Location support should contain: " + locationSupportInfo).isTrue();
    }
}
