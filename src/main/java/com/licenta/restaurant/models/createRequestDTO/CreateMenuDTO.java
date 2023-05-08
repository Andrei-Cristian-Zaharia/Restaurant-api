package com.licenta.restaurant.models.createRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateMenuDTO {

    private String name;
    private Long restaurantId;
}
