/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.util.db.c3;

import com.hotwire.util.db.AbstractDao;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

/**
* This is main Data Acess Object class for C3
 * All other C3 DAO should extend it
 *
 * Data Access Objects is user for getting customer purchase from DataBase with predefined parameters
 *
 * Explanation of common SQL lines:
 * ░rownum = 1 ░ get only first result ░ <br/>
 * ░pgood_class ░vertical name (Car, Hotel, Air)░  <br/>
 * ░c.email not like '%@expedia.com' ░ filtering emails that is used for live bookings░ <br/>
 * ░po.status_code = '30030'  ░30030 code means that this purchase is confirmed   ░<br/>
 * ░po.site_id = 1  ░US/Canada purchase   ░<br/>
 * ░r.hw_credit_card_id != -10 ░ this purchase is could be refunded ░  <br/>
 * ░sa.start_date > sysdate + 1 ░ booking check-in date is in the future ░   <br/>
 * ░po.create_date > sysdate - 30 ░ purchase was made not less than 30 days ago ░  <br/>
 * ░r.quantity > 1 ░ used for partial refunds, rooms, days or sits are more than one  ░  <br/>
 * ░po.partner_id is NULL░ filter Not Hotwire purchases (e.g. Expedia) ░ <br/>
 * ░po.site_id <> 1 ░Non-US purchase (International)░
 * ░AND po.total_amount > 0 ░  filter out HotDollars purchases or other discounts ░ <br/>
 * ░AND c.password is NULL░ only guest purchases░ <br/>
 * ░oao.type_code = 1 ░ purchase with insurance ░ <br/>
 * ░AND (select count(r.reservation_id) from reservation r where r.purchase_order_id = po.purchase_order_id) = '1'
 * ░ filter out packages<br/>
 */
public abstract class C3AbstractDao extends AbstractDao {

    protected C3AbstractDao(SimpleJdbcDaoSupport dataSource) {
        super(dataSource);
    }

    public abstract String getAirItinerary();

    public abstract String getCarItinerary();

    public abstract String getHotelItinerary();

    public abstract String getAirItinerary(String opacityCode);

    public abstract String getCarItinerary(String opacityCode);

    public abstract String getHotelItinerary(String opacityCode);
}
