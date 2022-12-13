package com.licenta.restaurant.models.responseDTO;

import com.licenta.restaurant.models.Person;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class RestaurantDTO {

    private String name;

    private String description;

    private String address;

    private LocalDate partnerSince;

    private Person owner;
}
