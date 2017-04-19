/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML utility class.
 */
public final class XmlUtil {

    private XmlUtil() {
    }

    public static List<String> getValuesFromXmlTagName(String xmlAsString, String tagName)
        throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        NodeList nodes = db.parse(new InputSource(new StringReader(xmlAsString))).getElementsByTagName(tagName);
        ArrayList<String> values = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            values.add(nodes.item(i).getTextContent());
        }
        return values;
    }

    public static List<String> getValuesFromNodeByTagName(Node node, String tagName) {
        NodeList nodes = ((Element) node).getElementsByTagName(tagName);
        ArrayList<String> values = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            values.add(nodes.item(i).getTextContent());
        }
        return values;
    }

    public static NodeList getNodeListFromTagName(String xmlAsString, String tagName)
        throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        return db.parse(new InputSource(new StringReader(xmlAsString))).getElementsByTagName(tagName);
    }
}
