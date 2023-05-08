package com.licenta.restaurant.models.createRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMenuItemDTO {

    private Float price;

    private String name;

    private String category;

    private String description;

    private Long recipeId;
}
