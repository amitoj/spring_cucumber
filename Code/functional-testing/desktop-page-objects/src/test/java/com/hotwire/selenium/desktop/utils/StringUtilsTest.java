package com.hotwire.selenium.desktop.utils;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StringUtilsTest {

    @Test
    public void parseUSD() {
        assertThat(StringUtils.parseFloat("$2,385.83")).isEqualTo(2385.83f);
    }

    @Test
    public void parseNOK() {
        assertThat(StringUtils.parseFloat("NOK 9,490.34")).isEqualTo(9490.34f);
    }

    @Test
    public void parseSEK() {
        assertThat(StringUtils.parseFloat("SEK 5 274,93")).isEqualTo(5274.93f);
    }
}
