package com.reqres.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input =
                ConfigReader.class.getClassLoader()
                    .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                    "config.properties was not found in test resources");
            }

            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConfigReader() {
        // Prevent object creation
    }

    public static String getBaseUrl() {
        String baseUrl = PROPERTIES.getProperty("base.url");

        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException(
                "Property 'base.url' is missing or empty");
        }

        return baseUrl.trim();
    }
}