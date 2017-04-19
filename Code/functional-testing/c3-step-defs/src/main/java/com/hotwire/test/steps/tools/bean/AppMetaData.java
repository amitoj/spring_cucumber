/*
 * Copyright 2014 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.steps.tools.bean;

import com.hotwire.test.steps.tools.bean.dmu.DMUAccountInfo;
import com.hotwire.testing.UnimplementedTestException;


import javax.annotation.Resource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 7/18/14
 * Time: 6:27 AM
 * Contains all metadata of application
 */
public class AppMetaData  extends ToolsAbstractBean {
    private static final Map<String, String> US_VT = new HashMap<String, String>()
    {
        {
            put("vt.ARO01", "2");
            put("vt.ITS13", "2");
            put("vt.MAB14", "1");
            put("vt.MUH14", "1");
            put("vt.DUH14", "1");
            put("vt.RCU14", "1");
            put("vt.DPA13", "2");
            put("vt.NDP13", "2");
            put("vt.NDT13", "1");
            put("vt.LPB13", "1");
            put("vt.PTW13", "2");
            put("vt.HTW14", "1");
            put("vt.FBR13", "1");
            put("vt.TLM13", "1");
            put("vt.FVR13", "1");
            put("vt.NOA13", "1");
            put("vt.CCD13", "1");
            put("vt.CSI14", "2");
            put("vt.NHP14", "2");
            put("vt.HDC14", "2");
            put("vt.RAG14", "1");
            put("vt.DDD14", "1");
            put("vt.PDC01", "2");
            put("vt.HPC14", "1");
            put("vt.TAB14", "1");
            put("vt.RAD13", "3");
        }
    };
    @Resource
    Map<String, DMUAccountInfo> dmuProfiles;


    private boolean intl = false;
    private Map<String, String> referralParams = new HashMap<>();
    private Map<String, String> versionTests = new HashMap<>();
    private AppType appType;
    private int port;
    private String host;
    private String protocol;
    private String url;
    private String deeplinkResultsUrl;
    private String deeplinkDetailsUrl;
    /**
     * Env = Environment
     */
    private String environment;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeeplinkResultsUrl() {
        return deeplinkResultsUrl;
    }

    public void setDeeplinkResultsUrl(String deeplinkResultsUrl) {
        this.deeplinkResultsUrl = deeplinkResultsUrl;
    }

    public String getDeeplinkDetailsUrl() {
        return deeplinkDetailsUrl;
    }

    public void setDeeplinkDetailsUrl(String deeplinkDetailsUrl) {
        this.deeplinkDetailsUrl = deeplinkDetailsUrl;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String env) {
        this.environment = env;
    }

    public boolean isIntl() {
        return intl;
    }

    public void setIntl(boolean intl) {
        this.intl = intl;
    }

    public Map<String, String> getReferralParams() {
        return referralParams;
    }

    public void setReferralParams(String key, String value) {
        getReferralParams().put(key, value);
    }

    public Map<String, String> getVersionTests() {
        return versionTests;
    }

    public void setVersionTests(Map<String, String> versionTests) {
        this.versionTests = versionTests;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(String applicationName) {
        this.appType = AppType.validate(applicationName);
        switch (appType) {
            case US:
                setVersionTests(US_VT);
                break;
            case UK:
            case IE:
                setIntl(true);
                break;
            case C3:
                break;
            case DMU:
                setPort(8000);
                setProtocol("https");
                setHost(dmuProfiles.get(environment).getHost());
                break;
            default:
                throw new UnimplementedTestException("Application type is not supported by Tools");
        }
    }

    public void addVersionTest(String vtRaw) {
        if (!vtRaw.contains("vt.") || !vtRaw.contains("=")) {
            throw new UnimplementedTestException("Version test must be in format like vt.SFHS1=1");
        }
        final String[] vt = vtRaw.split("=");
        getVersionTests().put(vt[0], vt[1]);
    }


    public void setApplicationUrl(URL applicationUrl) {
        this.protocol = applicationUrl.getProtocol();
        this.port = applicationUrl.getPort();
        this.host = applicationUrl.getHost();
    }

    public URL getApplicationUrl() throws MalformedURLException {
        return new URL(getProtocol(), getHost(), getPort(), getAppType().getAppPath());
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
