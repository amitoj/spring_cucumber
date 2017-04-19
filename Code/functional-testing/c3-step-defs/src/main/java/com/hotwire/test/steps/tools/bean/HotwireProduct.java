/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean;

import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3CarItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3HotelItineraryPage;
import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3ItineraryDetailsPage;
import com.hotwire.selenium.tools.c3.purchase.C3InterstitialPage;
import com.hotwire.selenium.tools.c3.purchase.ResultsPage;
import com.hotwire.selenium.tools.c3.purchase.air.C3AirResultsPage;
import com.hotwire.selenium.tools.c3.purchase.car.C3CarResultsPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelInterstitialPage;
import com.hotwire.selenium.tools.c3.purchase.hotel.C3HotelResultsPage;
import com.hotwire.test.steps.tools.c3.purchase.C3CarPurchaseModel;
import com.hotwire.test.steps.tools.c3.purchase.C3PurchaseModel;
import com.hotwire.testing.UnimplementedTestException;
import com.hotwire.util.db.c3.C3AbstractDao;
import com.hotwire.util.db.c3.C3SearchDao;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 10/14/14
 * Time: 6:55 AM
 * Factory class for operating with different types of Hotwire products
 */
public class HotwireProduct extends ToolsAbstractBean {
    @Resource
    Map<String, C3PurchaseModel> purchaseModels;

    private ProductVertical productVertical;
    private ProductType opacity = null;

    public void setVertical(String verticalName) {
        boolean isVerticalSet = !StringUtils.isEmpty(verticalName);
        if (isVerticalSet) {
            setProductVertical(verticalName);
        }
        else {
            setProductVertical(getRandomPGoodCode());
        }
    }

    public ProductVertical getRandomPGoodCode() {
        List<ProductVertical> commonPGoods =  Arrays.asList(ProductVertical.HOTEL, ProductVertical.CAR);
        Collections.shuffle(commonPGoods);
        return commonPGoods.get(0);
    }

    public ProductVertical getProductVertical() {
        return productVertical;
    }

    public String getProductVerticalName() {
        return productVertical.getProductName();
    }

    public void setProductVertical(ProductVertical productVertical) {
        this.productVertical = productVertical;
    }

    public ProductType getOpacity() {
        return opacity;
    }

    public void setOpacity(ProductType opacity) {
        this.opacity = opacity;
    }

    public void setProductVerticalByItinerary(String itinerary) {
        setProductVertical(new C3SearchDao(getDataBaseConnection())
                .getVerticalOfItinerary(itinerary));
    }

    public void setProductVertical(String productVertical) {
        this.productVertical = ProductVertical.validate(productVertical);
    }

    public String getItinerary(C3AbstractDao dao) {
        if (getOpacity() == null) {
            String result;
            switch (getProductVertical()) {
                case AIR:
                    result = dao.getAirItinerary();
                    break;
                case CAR:
                    result = dao.getCarItinerary();
                    break;
                case HOTEL:
                    result = dao.getHotelItinerary();
                    break;
                default:
                    throw new UnimplementedTestException("No such vertical!");
            }
            return result;
        }
        else {
            return getItineraryWithOpacity(dao);
        }
    }

    public String getItineraryWithOpacity(C3AbstractDao dao) {
        String result;
        switch (getProductVertical()) {
            case AIR:
                result = dao.getAirItinerary(getOpacity().getOpacityCode());
                break;
            case CAR:
                result = dao.getCarItinerary(getOpacity().getOpacityCode());
                break;
            case HOTEL:
                result = dao.getHotelItinerary(getOpacity().getOpacityCode());
                break;
            default:
                throw new UnimplementedTestException("No such vertical!");
        }
        return result;
    }

    public C3ItineraryDetailsPage getItineraryPage(WebDriver webDriver) {
        switch (getProductVertical()) {
            case HOTEL:
                return new C3HotelItineraryPage(webDriver);
            case CAR:
                return new C3CarItineraryPage(webDriver);
            case AIR:
                return new C3ItineraryDetailsPage(webDriver);
            default:
                throw new UnimplementedTestException("No such vertical!");
        }
    }

    public void setOpacity(String productType) {
        this.opacity = ProductType.validate(productType);
    }

    public boolean isOpaque() {
        return ProductType.OPAQUE.equals(opacity);
    }

    public C3PurchaseModel getPurchaseModel() {
        return purchaseModels.get(getProductVertical().getProductName());
    }

    public C3InterstitialPage getInterstitialPage(WebDriver webDriver) {
        if (ProductVertical.HOTEL == getProductVertical())  {
            return new C3HotelInterstitialPage(webDriver);
        }
        else {
            return new C3InterstitialPage(webDriver);
        }
    }

    public ResultsPage getResultsPage(WebDriver webDriver) {
        switch (getProductVertical()) {
            case AIR:
                return new C3AirResultsPage(webDriver);
            case CAR:
                return new C3CarResultsPage(webDriver);
            case HOTEL:
                return new C3HotelResultsPage(webDriver);
            default:
                throw new UnimplementedTestException("No such vertical!");
        }

    }

    public C3CarPurchaseModel getCarPurchaseModel() {
        this.setProductVertical(productVertical.CAR);
        return (C3CarPurchaseModel) getPurchaseModel();
    }
}
