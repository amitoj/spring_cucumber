/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet.api;

import hotwire.xnet.api.XnetHotelService;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.common.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

/**
 * Factory for Xnet API Client. Stateless.
 *
 * @author Renat Zhilkibaev
 */
class XnetApiClientCxfFactory {

    public static XnetHotelService createXnetHotelServiceClient(String address) {
        URL wsdlURL;
        try {
            wsdlURL = new URL(address + "?wsdl");
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        QName serviceName = new QName("http://impl.api.xnet.hotwire/", "XnetHotelService");
        hotwire.xnet.api.impl.XnetHotelService service = new hotwire.xnet.api.impl.XnetHotelService(wsdlURL,
            serviceName);
        return service.getXnetHotelServiceImplPort();
    }

    private static Object unwrapProxy(Object proxy) {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            try {
                Object o = Proxy.getInvocationHandler(proxy);
                Field f = o.getClass().getDeclaredField("advised");
                f.setAccessible(true);
                AdvisedSupport a = (AdvisedSupport) f.get(o);
                return a.getTargetSource().getTarget();
            }
            catch (Exception e) {
                throw new RuntimeException("Could not unwrap " + proxy.getClass().getName(), e);
            }
        }

        return proxy;
    }

    public static AuthorizationPolicy createXnetHotelServiceConduit(XnetHotelService service, String apikey)
        throws Exception {
        Object o = unwrapProxy(service);
        Client client = ClientProxy.getClient(o);

        if (apikey != null && apikey.length() > 0) {
            client.getRequestContext().put(Message.QUERY_STRING, "apikey=6hbe2w6ehhepdc53ua8ewbbn");
        }
        client.getInInterceptors().add(new GZIPInInterceptor());
        client.getOutInterceptors().add(new GZIPOutInterceptor());
        HTTPConduit http = (HTTPConduit) client.getConduit();
        AuthorizationPolicy authPolicy = new AuthorizationPolicy();
        http.setAuthorization(authPolicy);
        return authPolicy;
    }

    /**
     * Set superUserID in the header - X-XNET-USER-ID 840151
     */
    public static void setXnetUserIdInXnetHotelServiceHttpHeader(XnetHotelService service) throws Exception {
        Object o = unwrapProxy(service);
        Client client = ClientProxy.getClient(o);
        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("X-XNET-USER-ID", Arrays.asList("840151"));
        client.getRequestContext().put(Message.PROTOCOL_HEADERS, headers);
    }

}
