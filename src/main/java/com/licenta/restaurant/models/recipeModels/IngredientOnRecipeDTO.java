package com.licenta.restaurant.models.recipeModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientOnRecipeDTO {

    private String name;

    private String category;

    private Integer quantity;

    private String measurementUnit;
}
