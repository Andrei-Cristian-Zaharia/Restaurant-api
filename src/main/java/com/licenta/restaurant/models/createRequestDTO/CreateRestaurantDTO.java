package com.licenta.restaurant.models.createRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRestaurantDTO {

    private String name;

    private String frontImage;

    private String bannerImage;

    private Integer telephone;

    private String emailAddress;

    private String websiteAddress;

    private String addressLocation;

    private String schedule;

    private String instagramLink;

    private String facebookLink;

    private Long person_id;
}
