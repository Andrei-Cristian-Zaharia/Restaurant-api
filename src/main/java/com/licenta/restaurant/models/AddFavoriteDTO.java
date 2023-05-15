package com.licenta.restaurant.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddFavoriteDTO {

    private Long restaurantId;
    private Long userId;
}
