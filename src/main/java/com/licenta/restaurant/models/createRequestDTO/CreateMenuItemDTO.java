package com.licenta.restaurant.models.createRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateMenuItemDTO {

    private Float price;

    private String category;

    private Long recipeId;
}
