package com.licenta.restaurant.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saved_restaurants")
public class SavedRestaurant {

    @Id
    @Column(name = "id_person_restaurant")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_person")
    private Long personId;

    @Column(name = "id_restaurant")
    private Long restaurantId;
}
