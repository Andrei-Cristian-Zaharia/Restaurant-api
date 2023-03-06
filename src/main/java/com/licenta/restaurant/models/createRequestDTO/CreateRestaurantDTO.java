package com.licenta.restaurant.models.createRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRestaurantDTO {

    private String name;

    private String description;

    private String frontImage;

    private String address;

    private Long person_id;
}
