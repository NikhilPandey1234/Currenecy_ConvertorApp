package com.currncyconversions.SCurrencyConversion.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

class WebLConfigTest {

    private WebLConfig webLConfigUnderTest;

    @BeforeEach
    void setUp() {
        webLConfigUnderTest = new WebLConfig();
    }

    @Test
    void testAddInterceptors() {
        // Setup
        final InterceptorRegistry registry = new InterceptorRegistry();

        // Run the test
        webLConfigUnderTest.addInterceptors(registry);

        // Verify the results
    }

    @Test
    void testCharacterEncodingFilter() {
        // Setup
        // Run the test
        final CharacterEncodingFilter result = webLConfigUnderTest.characterEncodingFilter();

        // Verify the results
    }

    @Test
    void testLocaleChangeInterceptor() {
        // Setup
        // Run the test
        final LocaleChangeInterceptor result = webLConfigUnderTest.localeChangeInterceptor();

        // Verify the results
    }

    @Test
    void testLocaleResolver() {
        // Setup
        // Run the test
        final LocaleResolver result = webLConfigUnderTest.localeResolver();

        // Verify the results
    }
}
