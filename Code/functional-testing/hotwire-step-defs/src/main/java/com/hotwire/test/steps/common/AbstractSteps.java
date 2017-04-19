/*
 * Copyright 2013 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */
package com.hotwire.test.steps.common;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * Base step definition class.
 */
@ContextConfiguration("classpath:cucumber.xml")
@DirtiesContext
public abstract class AbstractSteps {

}
