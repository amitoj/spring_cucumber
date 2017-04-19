/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.util;

import org.joda.time.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.Random;


/**
 * User: v-dsobko
 * Since: 01/06/15
 */
public final class RandomCreditCardGenerator {
    public static final int VISA_NUMBER_DIGITS_COUNT = 16;

    private RandomCreditCardGenerator() {
    }

    private static int[] generateRandCardNumber() {
        Random rand = new Random();
        StringBuffer buf = new StringBuffer();
        for (int a = 0; a < VISA_NUMBER_DIGITS_COUNT - 1; a++) {
            buf.append(rand.nextInt(10));
        }

        int[] cardNumber = new int[VISA_NUMBER_DIGITS_COUNT];
        String s = "4" + buf.toString();
        for (int i = 0; i < VISA_NUMBER_DIGITS_COUNT; i++) {
            cardNumber[i] = Integer.parseInt(String.valueOf(s.charAt(i)));
        }

        return  cardNumber;
    }

    public static String generateRandCardNickName() {
        Random rand = new Random();
        StringBuffer buf = new StringBuffer();
        for (int a = 0; a < 5; a++) {
            buf.append(rand.nextInt(10));
        }

        return "BDD_CARD_" + buf.toString();
    }

    public static XMLGregorianCalendar generateRandomExpirationDate() throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new DateTime().plusYears(4).toGregorianCalendar();
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();

        return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
    }

    private static boolean validateCreditCardNumber(int[] digits) {
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {

            // get digits in reverse order
            int digit = digits[length - i - 1];

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }


    public static String useRandomCreditCardNumber() {

        boolean success;
        int[] generatedCreditCard;

        do {
            generatedCreditCard = generateRandCardNumber();
            success = validateCreditCardNumber(generatedCreditCard);
        } while (!success);

        StringBuffer sb = new StringBuffer(generatedCreditCard.length);
        for (int i : generatedCreditCard) {
            sb.append(i);
        }
        return sb.toString();
    }


}
