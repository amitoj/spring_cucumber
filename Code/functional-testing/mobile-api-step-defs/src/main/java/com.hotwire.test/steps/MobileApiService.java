/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps;


import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

/**
 * User: v-ngolodiuk
 * Since: 1/8/15
 */
public class MobileApiService {

    protected WebClient client;

    public void setWebClient(WebClient webClient) {
        this.client = webClient;
    }

    public void init() {
        HTTPConduit httpConduit = WebClient.getConfig(client).getHttpConduit();
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setReceiveTimeout(120000);
        httpClientPolicy.setConnectionTimeout(120000);
        httpClientPolicy.setAllowChunking(false);
        httpConduit.setClient(httpClientPolicy);
    }
}
