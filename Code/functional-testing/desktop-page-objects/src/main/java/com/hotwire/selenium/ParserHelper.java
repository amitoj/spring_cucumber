/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.selenium;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

/**
 * @author v-jhernandeziniguez
 *
 */
public final class ParserHelper {


    private ParserHelper() {
        //not called
    }


    public static String extractAttributeFromHTML(String readFile, String xpathExpression) throws XPatherException {

        //System.out.println("4. Accessing parser");

        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setAllowHtmlInsideAttributes(true);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);

        TagNode node = cleaner.clean(readFile);

        Object[] nodes = node.evaluateXPath(xpathExpression);

        TagNode itinerary = (TagNode) nodes[0];

        String confItinerary = itinerary.getText().toString();
        // System.out.println(itinerary.getText());
        return confItinerary;
    }

}
