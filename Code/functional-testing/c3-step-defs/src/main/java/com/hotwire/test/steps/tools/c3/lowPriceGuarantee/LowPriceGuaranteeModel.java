/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.c3.lowPriceGuarantee;

import com.hotwire.selenium.tools.c3.customer.itineraryDetails.C3ItineraryDetailsPage;
import com.hotwire.selenium.tools.c3.customer.lpg.AirLPGRefundRequestForm;
import com.hotwire.selenium.tools.c3.customer.lpg.C3NewLowPriceGuaranteeForm;
import com.hotwire.selenium.tools.c3.customer.lpg.HotelLPGModule;
import com.hotwire.selenium.tools.c3.customer.lpg.HotelLPGRefundRequestForm;
import com.hotwire.selenium.tools.c3.customer.lpg.LPGRefundRequestForm;
import com.hotwire.selenium.tools.c3.customer.lpg.LowPriceGuaranteeModule;
import com.hotwire.test.steps.tools.ToolsAbstractModel;
import com.hotwire.test.steps.tools.bean.HotwireProduct;
import com.hotwire.test.steps.tools.bean.c3.C3ItineraryInfo;
import org.fest.assertions.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 4/3/14
 * Time: 7:35 AM
 */
public class LowPriceGuaranteeModel extends ToolsAbstractModel {

    private static final String LPG_REFUND_FAILED_MSG = "Please note that we will verify that the Hotwire reservation" +
            " in question has been completed without cancellation or refund for the date(s) of the claimed booking." +
            " You can expect either a refund on your card or a refund check to be issued" +
            " within 14 days of Hotwire receiving validated proof of the completed Hotwire reservation.";

    @Autowired
    private C3ItineraryInfo c3ItineraryInfo;

    @Autowired
    private HotwireProduct hotwireProduct;


    public void clickOnLPGClaimLink() {
        C3ItineraryDetailsPage itPage = hotwireProduct.getItineraryPage(getWebdriverInstance());
        c3ItineraryInfo.setTotalCost(itPage.getTotalCost());
        itPage.clickOnLPGClaimLink();
    }

    public void fillLPGform() {
        Double amount = new C3NewLowPriceGuaranteeForm(getWebdriverInstance())
                .fill(c3ItineraryInfo.getTotalCost());
        c3ItineraryInfo.setRefundAmount(amount);
    }


    public void verifyLPGMsg() {
        Assertions.assertThat(new C3NewLowPriceGuaranteeForm(getWebdriverInstance()).getConfirmationMsg())
                .contains("This claim was processed successfully for the amount of")
                .contains(c3ItineraryInfo.getRefundAmount().toString().replaceAll("\\.*.$", ""));
    }

    public void verifyLPGlinkNotPresent() {
        hotwireProduct.getItineraryPage(getWebdriverInstance()).isLPGLinkDisplayed();
    }

    public void verifyFailedLPGMsg() {
        Assertions.assertThat(new C3NewLowPriceGuaranteeForm(getWebdriverInstance()).getConfirmationMsg())
                .contains("This claim cannot be processed as a claim has already been made against this reservation.");
    }

    public void verifyLPGBtn(boolean state) {
        boolean rfnBtAvailability = getLPGModule().isLPGRefundBtnDisplayed();
        if (state) {
            Assertions.assertThat(rfnBtAvailability).isTrue();
        }
        else {
            Assertions.assertThat(rfnBtAvailability).isFalse();
//            Assertions.assertThat(getLPGModule().getExpiredMsg()).isEqualTo(LPG_EXPIRED_MSG);
        }
    }

    private LowPriceGuaranteeModule getLPGModule() {
        switch (hotwireProduct.getProductVertical()) {
            case HOTEL:
                return new HotelLPGModule(getWebdriverInstance());
            default:
                return new LowPriceGuaranteeModule(getWebdriverInstance());
        }
    }

    private LPGRefundRequestForm getLPGRefundRequestForm() {
        switch (hotwireProduct.getProductVertical()) {
            case AIR:
                return new AirLPGRefundRequestForm(getWebdriverInstance());
            case HOTEL:
                return new HotelLPGRefundRequestForm(getWebdriverInstance());
            default:
                return new LPGRefundRequestForm(getWebdriverInstance());
        }
    }

    public void verifyLPGModule() {
        getLPGModule();
    }

    public void clickLPGRefundRequest() {
        getLPGModule().clickOnLPGRefund();
    }

    public void verifyLPGRefundRequestForm() {
        new LPGRefundRequestForm(getWebdriverInstance());
    }

    public void fillLPGForm() {
        LPGRefundRequestForm lpg = getLPGRefundRequestForm();
        Double amount = c3ItineraryInfo.parseAmount(lpg.getHotwireTripPrice());
        lpg.fill(amount, hotwireProduct.isOpaque());
        lpg.submit();
    }

    public void verifyLPGRefundRequestMsg() {
        LPGRefundRequestForm lpg = getLPGRefundRequestForm();
        Assertions.assertThat(lpg.getConfirmationMsg()).isEqualTo(lpg.getMsg());
    }

    public void verifyInvalidLPGRefundMSG() {
        Assertions.assertThat(new LPGRefundRequestForm(getWebdriverInstance())
                .getConfirmationMsg()).isEqualTo(LPG_REFUND_FAILED_MSG);
    }
}
