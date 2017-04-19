/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.test.openapi;

/**
 * Created with IntelliJ IDEA.
 * User: v-ozelenov
 * Date: 2/19/15
 * Time: 10:16 AM
 * API types
 */
public enum ApiType {
    PUBLIC_API("PUBLIC", "OPEN", "OAPI"),
    PRIVATE_API("PRIVATE", "DISTRIBUTED OPAQUE", "DO");


    private ApiType apiType;
    private String simpleName;
    private String nickName;
    private String acronym;

    ApiType(String simpleName, String nickName, String acronym) {
        this.simpleName = simpleName;
        this.nickName = nickName;
        this.acronym = acronym;
    }


    public static ApiType validate(String solution) {
        for (ApiType t : ApiType.values()) {
            if (String.valueOf(t.simpleName).equals(solution.toUpperCase())) {
                return t;
            }
            else if (String.valueOf(t.nickName).equals(solution.toUpperCase())) {
                return t;
            }
            else if (String.valueOf(t.acronym).equals(solution.toUpperCase())) {
                return t;
            }
        }
        throw new RuntimeException("Not expected reservation type: " + solution);
    }

    private ApiType getApiType() {
        return apiType;
    }

    private void setApiType(ApiType apiType) {
        this.apiType = apiType;
    }
}
