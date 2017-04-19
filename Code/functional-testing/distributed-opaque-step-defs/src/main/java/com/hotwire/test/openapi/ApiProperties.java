/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.openapi;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.parsing.Parser;
import com.jayway.restassured.path.xml.XmlPath;
import com.jayway.restassured.response.Response;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 2/19/15
 * Time: 2:37 PM
 * This class contain main service properties.
 */
public class ApiProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiProperties.class);
    private String useCluster;
    private XmlPath response;
    private Response deeplinkResponse;
    private HotwireProduct hotwireProduct;
    private ApiType apiType;
    private URI apiUrl;
    private String apiKey;


    public void setApiUrl(URI apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return String.valueOf(apiUrl);
    }

    public void setUseCluster(String useCluster) {
        this.useCluster = useCluster;
    }

    public String getUseCluster() {
        return useCluster;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public XmlPath getResponse() {
        return response;
    }

    public void setResponse(XmlPath response) {
        this.response = response;
    }

    public Response getDeeplinkResponse() {
        return deeplinkResponse;
    }

    public void setDeeplinkResponse(Response deeplinkResponse) {
        this.deeplinkResponse = deeplinkResponse;
    }

    public HotwireProduct getHotwireProduct() {
        return hotwireProduct;
    }

    public void setHotwireProduct(HotwireProduct hotwireProduct) {
        this.hotwireProduct = hotwireProduct;
    }

    public ApiType getApiType() {
        return apiType;
    }

    public void setApiType(ApiType apiType) {
        this.apiType = apiType;
    }

    public void setEndpoint(String type) {
        setApiType(ApiType.validate(type));
        RequestSpecBuilder apiConfig = new RequestSpecBuilder();
        URIBuilder endPoint;
        try {
            endPoint = new URIBuilder(getApiUrl());
        }
        catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URL!");
        }
        String host = endPoint.getHost();
        if (host.contains("api.")) {
            LOGGER.info("All requests will go through mashery for this endpoint " + getApiUrl());
            if (ApiType.PRIVATE_API.equals(getApiType())) {
                endPoint.setScheme("https");
                endPoint.setHost("private." + host);
                apiConfig.setRelaxedHTTPSValidation();
                apiConfig.addHeader("PartnerId", "-1");
            }
            apiConfig.setBasePath("/v1/");
        }
        else {
            LOGGER.info("Mashery is not used for this endpoint " + getApiUrl());
            apiConfig.setBasePath("/api/v1/");
        }
        apiConfig.setAccept(ContentType.XML)
                .setContentType(ContentType.XML)
                .setBaseUri(String.valueOf(endPoint))
                .and()
                .addQueryParam("apikey", getApiKey())
                .addQueryParam("useCluster", getUseCluster());

        RestAssured.requestSpecification =  apiConfig.build();
        RestAssured.defaultParser = Parser.XML;
    }
}
