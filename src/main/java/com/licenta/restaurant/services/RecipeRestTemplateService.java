package com.licenta.restaurant.services;

import com.licenta.restaurant.Config;
import com.licenta.restaurant.models.recipeModels.RecipeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeRestTemplateService {

    private static final String RECIPE_ROUTE = "/recipe/";

    private final RestTemplate restTemplate;

    public RecipeRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RecipeDTO getRecipeById(Long id) {
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(3005)
                .path("v1/food-api")
                .path(RECIPE_ROUTE)
                .path("id/{id}")
                .build(id);

        return restTemplate.getForEntity(uri, RecipeDTO.class).getBody();
    }

    public RecipeDTO getRecipeByName(String name) {
        UriComponentsBuilder uriComponentsBuilder = Config.FOOD_API_PATH;

        URI uri = uriComponentsBuilder
                .path(RECIPE_ROUTE)
                .path("name/{name}")
                .build(name);

        return restTemplate.getForEntity(uri, RecipeDTO.class).getBody();
    }
}
