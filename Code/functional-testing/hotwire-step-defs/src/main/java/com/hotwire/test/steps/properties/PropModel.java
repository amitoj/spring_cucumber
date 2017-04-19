/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.properties;

import java.util.List;

/**
 * @author adeshmukh
 *
 */
public interface PropModel {

    void setProperties(List<String> properties);

    void resetProperties(List<String> properties);

}
