package com.licenta.restaurant.models.recipeModels;

import com.licenta.restaurant.models.Person;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeDTO {

    private String name;
    private String description;
    private String howToPrepare;
    private Person person;
    private List<IngredientOnRecipeDTO> ingredientList;
}
