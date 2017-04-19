/*
 * Copyright 2012 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.hotel.xnet.api;

import hotwire.xnet.api.XnetApiException;
import hotwire.xnet.api.XnetHotelService;
import org.apache.cxf.transport.http.HTTPException;

import javax.xml.ws.WebServiceException;
import java.util.Map;

/**
 * This proxy delays creation of the actual {@link XnetHotelService} instance till the time of the interface method
 * call. By that time following properties must be set in the proxy context:<br/> address - the end point URL
 * address<br/> username - the user name<br/> password - the password
 *
 * @author Renat Zhilkibaev
 */
class ContextAwareXnetHotelServiceLazyProxy implements XnetHotelService {

    /**
     * Service context (property name to value map).
     */
    private Map<String, String> serviceCtx;
    /**
     * Client factory.
     */
    private XnetApiClientCxfFactory clientFactory;

    /**
     * Lazy initialized instance of {@link XnetHotelService}. Access only using {@link
     * ContextAwareXnetHotelServiceLazyProxy#getTarget()}.
     */
    private XnetHotelService lazyXnetHotelService;

    @Override
    public hotwire.xnet.api.AvailRateUpdateRS updateInventory(hotwire.xnet.api.AvailRateUpdateRQ availRateUpdateRQ)
        throws XnetApiException {
        XnetHotelService target = getTarget();
        try {
            return target.updateInventory(availRateUpdateRQ);
        }
        catch (WebServiceException e) {
            if (e.getCause() instanceof HTTPException) {
                throw new javax.xml.ws.http.HTTPException(((HTTPException) e.getCause()).getResponseCode());
            }
            throw e;
        }
    }

    private synchronized XnetHotelService getTarget() {
//        if (lazyXnetHotelService == null) {
//            lazyXnetHotelService = clientFactory.createXnetHotelServiceClient(serviceCtx.get("address"),
//                                                                              serviceCtx.get("username"),
//                                                                              serviceCtx.get("password"));
//        }
//        return lazyXnetHotelService;
        return null;
    }

    public void setServiceCtx(Map<String, String> serviceCtx) {
        this.serviceCtx = serviceCtx;
    }

    public void setClientFactory(XnetApiClientCxfFactory clientFactory) {
        this.clientFactory = clientFactory;
    }

}
