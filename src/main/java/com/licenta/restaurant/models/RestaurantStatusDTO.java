package com.licenta.restaurant.models;

import lombok.Data;

@Data
public class RestaurantStatusDTO {
    private Long id;
    private String status;
    private String name;
    private String emailAddress;
    private Integer telephone;
}
