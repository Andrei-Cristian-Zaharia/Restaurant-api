package com.licenta.restaurant.controllers;

import com.licenta.restaurant.models.recipeModels.RecipeDTO;
import com.licenta.restaurant.services.RecipeRestTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/rest")
public class RestCalls {

    private final RecipeRestTemplateService recipeRestTemplateService;

    public RestCalls(RecipeRestTemplateService recipeRestTemplateService) {
        this.recipeRestTemplateService = recipeRestTemplateService;
    }

    @GetMapping("{id}")
    public @ResponseBody ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) throws URISyntaxException {
        return ResponseEntity.ok().body(recipeRestTemplateService.getRecipeById(id));
    }
}
