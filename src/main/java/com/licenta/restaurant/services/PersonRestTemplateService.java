package com.licenta.restaurant.services;

import com.licenta.restaurant.Config;
import com.licenta.restaurant.models.Person;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(3005)
                .path("v1/core-api")
                .path(PERSON_ROUTE)
                .path("id/{id}")
                .build(id);

        return restTemplate.getForEntity(uri, Person.class).getBody();
    }

    public Boolean validateAccount(String emailAddress, String password) throws JSONException {

        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(3005)
                .path("v1/core-api")
                .path(PERSON_ROUTE)
                .path("validate")
                .build().toUri();

        JSONObject body = new JSONObject();
        body.put("emailAddress", emailAddress);
        body.put("password", password);

        HttpEntity<String> entityCredentials = new HttpEntity<>(body.toString(), createHeaderBody());

        return Boolean.TRUE.equals(
                restTemplate.exchange(uri, HttpMethod.POST, entityCredentials, Boolean.class).getBody());
    }

    public Person getPersonByName(String name) {
        UriComponentsBuilder uriComponentsBuilder = Config.CORE_API_PATH;

        URI uri = uriComponentsBuilder
                .path(PERSON_ROUTE)
                .path("name/{name}")
                .build(name);

        return restTemplate.getForEntity(uri, Person.class).getBody();
    }

    private static HttpHeaders createHeaderBody() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        return httpHeaders;
    }
}
