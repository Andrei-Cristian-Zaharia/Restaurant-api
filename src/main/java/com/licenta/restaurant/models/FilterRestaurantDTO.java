package com.licenta.restaurant.models;

import lombok.Data;

@Data
public class FilterRestaurantDTO {

    private String filterName;
    private String filterAddress;
    private Boolean showActive;
    private Integer rating;
}
