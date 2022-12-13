package com.licenta.restaurant.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteRestaurantDTO {

    private Long id;
    private String password;
}
