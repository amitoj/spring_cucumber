/*
 * Copyright 2015 Hotwire. All Rights Reserved.
 *
 * This software is the proprietary information of Hotwire.
 * Use is subject to license terms.
 */

package com.hotwire.selenium.desktop.row;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;


public final class FieldWrapper extends DefaultFieldDecorator {

    public FieldWrapper(final SearchContext searchContext) {
        super(new DefaultElementLocatorFactory(searchContext));
    }

    private static Class<?> getWrappableClass(final Field field) {
        final Class<?> clazz = field.getType();

        try {
            clazz.getConstructor(WebElement.class);
        }
        catch (Exception e) {
            return null;
        }

        return clazz;
    }

    private <T> T createWrapper(final ClassLoader loader, final ElementLocator locator, final Class<T> clazz) {
        final WebElement proxy = proxyForLocator(loader, locator);

        try {
            return clazz.getConstructor(WebElement.class).newInstance(proxy);
        }
        catch (Exception e) {
            throw new WebDriverException("Can't wrap WebElement by " + clazz, e);
        }
    }

    @Override
    public Object decorate(final ClassLoader loader, final Field field) {
        final Class<?> clazz = getWrappableClass(field);

        if (clazz != null) {
            final ElementLocator locator = factory.createLocator(field);

            if (locator != null) {
                return createWrapper(loader, locator, clazz);
            }
        }

        return super.decorate(loader, field);
    }
}
