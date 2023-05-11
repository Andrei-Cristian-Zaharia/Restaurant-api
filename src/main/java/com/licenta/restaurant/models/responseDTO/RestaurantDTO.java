package com.licenta.restaurant.models.responseDTO;

import com.licenta.restaurant.models.Person;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RestaurantDTO {

    private Long id;
    private String name;

    private String description;

    private String employAnnounce;

    private String pageText;

    private String frontImage;

    private String bannerImage;

    private Integer telephone;

    private String emailAddress;

    private String websiteAddress;

    private String addressLocation;

    private String status;

    private String schedule;

    private String instagramLink;

    private String facebookLink;

    private LocalDate partnerSince;

    private Person owner;
}
