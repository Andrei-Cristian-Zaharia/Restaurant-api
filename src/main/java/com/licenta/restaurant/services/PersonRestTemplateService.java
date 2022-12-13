package com.licenta.restaurant.services;

import com.licenta.restaurant.ApiConfig;
import com.licenta.restaurant.models.Person;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class PersonRestTemplateService {

    private static final String PERSON_ROUTE = "/person/";

    private final RestTemplate restTemplate;

    public PersonRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Person getPersonById(Long id) {
        URI uri = ApiConfig.coreApiPath()
                .path(PERSON_ROUTE)
                .path("id/{id}")
                .build(id);

        HttpEntity<String> entityCredentials = new HttpEntity<>(null, createHeaderBody());

        return restTemplate.exchange(uri, HttpMethod.GET, entityCredentials, Person.class).getBody();
    }

    public Long validateAccount(String emailAddress, String password) throws JSONException {

        URI uri = ApiConfig.coreApiPath()
                .path(PERSON_ROUTE)
                .path("validate")
                .build().toUri();

        JSONObject body = new JSONObject();
        body.put("emailAddress", emailAddress);
        body.put("password", password);

        HttpEntity<String> entityCredentials = new HttpEntity<>(body.toString(), createHeaderBody());

        return restTemplate.exchange(uri, HttpMethod.POST, entityCredentials, Long.class).getBody();
    }

    public void changePersonStatus(Boolean newStatus, Long id) throws JSONException {

        URI uri = ApiConfig.coreApiPath()
                .path(PERSON_ROUTE)
                .path("updateRestaurantStatus")
                .build().toUri();

        JSONObject body = new JSONObject();
        body.put("id", id);
        body.put("status", newStatus);

        HttpEntity<String> entityCredentials = new HttpEntity<>(body.toString(), createHeaderBody());

        restTemplate.exchange(uri, HttpMethod.POST, entityCredentials, Void.class);
    }

    private static HttpHeaders createHeaderBody() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.add("authorities", "[PERMIT]");

        return httpHeaders;
    }
}
