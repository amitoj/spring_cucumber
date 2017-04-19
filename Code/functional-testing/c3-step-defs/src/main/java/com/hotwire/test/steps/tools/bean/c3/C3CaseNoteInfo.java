/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean.c3;

import com.hotwire.test.steps.tools.bean.ToolsAbstractBean;

/**
 * Information about case note that agent uses during his work with C3.
 * User: v-asnitko
 */

public class C3CaseNoteInfo extends ToolsAbstractBean {
    private String caseId;
    private String methodOfContact;
    private String notes;
    private String nameOfContact;
    private String chatEmailTrackingNumber;
    private Boolean isSupplierContacted;
    private String language;
    private String termsOfUse;
    private String helpDeskContacted;
    private String firstLevelDisposition;
    private String secondLevelDisposition;
    private String thirdLevelDisposition;
    private String outcome;
    private String privacyPolicy;

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getMethodOfContact() {
        return methodOfContact;
    }

    public void setMethodOfContact(String methodOfContact) {
        this.methodOfContact = methodOfContact;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNameOfContact() {
        return nameOfContact;
    }

    public void setNameOfContact(String nameOfContact) {
        this.nameOfContact = nameOfContact;
    }

    public String getChatEmailTrackingNumber() {
        return chatEmailTrackingNumber;
    }

    public void setChatEmailTrackingNumber(String chatEmailTrackingNumber) {
        this.chatEmailTrackingNumber = chatEmailTrackingNumber;
    }

    public Boolean getSupplierContacted() {
        return isSupplierContacted;
    }

    public void setSupplierContacted(Boolean supplierContacted) {
        isSupplierContacted = supplierContacted;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTermsOfUse() {
        return termsOfUse;
    }

    public void setTermsOfUse(String termsOfUse) {
        this.termsOfUse = termsOfUse;
    }

    public String getHelpDeskContacted() {
        return helpDeskContacted;
    }

    public void setHelpDeskContacted(String helpDeskContacted) {
        this.helpDeskContacted = helpDeskContacted;
    }

    public String getFirstLevelDisposition() {
        return firstLevelDisposition;
    }

    public void setFirstLevelDisposition(String firstLevelDisposition) {
        this.firstLevelDisposition = firstLevelDisposition;
    }

    public String getSecondLevelDisposition() {
        return secondLevelDisposition;
    }

    public void setSecondLevelDisposition(String secondLevelDisposition) {
        this.secondLevelDisposition = secondLevelDisposition;
    }

    public String getThirdLevelDisposition() {
        return thirdLevelDisposition;
    }

    public void setThirdLevelDisposition(String thirdLevelDisposition) {
        this.thirdLevelDisposition = thirdLevelDisposition;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

}
