package com.licenta.restaurant.services;

import com.licenta.restaurant.ApiConfig;
import com.licenta.restaurant.models.recipeModels.RecipeDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class RecipeRestTemplateService {

    private static final String RECIPE_ROUTE = "/recipe/";

    private final RestTemplate restTemplate;

    public RecipeRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RecipeDTO getRecipeById(Long id) {
        URI uri = ApiConfig.foodApiPath()
                .path(RECIPE_ROUTE)
                .path("id/{id}")
                .build(id);

        return restTemplate.getForEntity(uri, RecipeDTO.class).getBody();
    }
}
