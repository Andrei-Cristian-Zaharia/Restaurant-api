package com.licenta.restaurant.models.createRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMenuDTO {

    private String name;
    private Long restaurantId;
}
