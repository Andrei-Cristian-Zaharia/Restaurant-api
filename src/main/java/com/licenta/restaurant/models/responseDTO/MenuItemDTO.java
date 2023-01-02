package com.licenta.restaurant.models.responseDTO;

import com.licenta.restaurant.models.Recipe;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MenuItemDTO {

    private Float price;

    private String category;

    private Recipe recipe;
}
