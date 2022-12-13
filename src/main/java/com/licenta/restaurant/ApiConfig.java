package com.licenta.restaurant;

import org.springframework.web.util.UriComponentsBuilder;

public class ApiConfig {

    private static final Integer FOOD_PORT = 3000;

    private static final Integer CORE_PORT = 3001;

    private static final String SCHEME = "http";

    private static final String HOST = "localhost";

    private ApiConfig() {}

    public static UriComponentsBuilder foodApiPath() {
        return UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(HOST)
                .port(FOOD_PORT)
                .path("v1/food-api");
    }

    public static UriComponentsBuilder coreApiPath() {
        return UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(HOST)
                .port(CORE_PORT)
                .path("v1/core-api");
    }
}
