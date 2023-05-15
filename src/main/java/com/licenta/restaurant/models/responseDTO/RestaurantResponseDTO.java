package com.licenta.restaurant.models.responseDTO;

import com.licenta.restaurant.models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class RestaurantResponseDTO extends Restaurant {

    private Integer rating;
}
