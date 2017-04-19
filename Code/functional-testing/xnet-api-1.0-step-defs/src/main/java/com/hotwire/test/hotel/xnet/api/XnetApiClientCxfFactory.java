/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet.api;

import hotwire.xnet.api.XnetHotelService;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.common.gzip.GZIPInInterceptor;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;
import org.apache.cxf.transport.http.HTTPConduit;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

import javax.xml.namespace.QName;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Factory for Xnet API Client. Stateless.
 *
 * @author Renat Zhilkibaev
 */
class XnetApiClientCxfFactory {

    public static XnetHotelService createXnetHotelServiceClient(String address) {
        URL wsdlURL;
        try {
            if (!address.contains("v1.1")) {
                if (address.contains("https")) {
                    address = address + "/v1/xnetApi/XnetHotelService";
                }
                else {
                    address = address + "/xnetApi/v1.0/XnetHotelService";
                }
            }
            wsdlURL = new URL(address + "?wsdl");
        }
        catch (MalformedURLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        QName serviceName = new QName("http://impl.api.xnet.hotwire/", "XnetHotelService");
        hotwire.xnet.api.impl.XnetHotelService service =
            new hotwire.xnet.api.impl.XnetHotelService(wsdlURL, serviceName);
        XnetHotelService xnetHotelService = service.getXnetHotelServiceImplPort();
        return xnetHotelService;
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

    public static AuthorizationPolicy createXnetHotelServiceConduit(XnetHotelService service,
                                                                    String apikey) throws Exception {
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

}
