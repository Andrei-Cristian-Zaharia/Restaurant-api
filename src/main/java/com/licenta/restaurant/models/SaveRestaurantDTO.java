package com.licenta.restaurant.models;

import lombok.Data;

@Data
public class SaveRestaurantDTO {

    private Long id;
    private String description;
    private String pageText;
    private String employmentText;
    private String frontImage;
    private String bannerImage;
    private String instagramLink;
    private String facebookLink;
    private String webLink;
    private String address;
}
