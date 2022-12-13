package com.licenta.restaurant;

import org.springframework.web.util.UriComponentsBuilder;

public class Config {

    public static final UriComponentsBuilder FOOD_API_PATH;

    public static final UriComponentsBuilder CORE_API_PATH;

    private static final Integer GATEWAY_PORT = 3005;

    private static final String SCHEME = "http";

    private static final String HOST = "localhost";

    private Config() {}

    static {
        FOOD_API_PATH = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(HOST)
                .port(GATEWAY_PORT)
                .path("v1/food-api");
    }

    static {
        CORE_API_PATH = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(HOST)
                .port(GATEWAY_PORT)
                .path("v1/core-api");
    }
}
