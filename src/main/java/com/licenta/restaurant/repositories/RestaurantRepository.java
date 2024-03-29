package com.licenta.restaurant.repositories;

import com.licenta.restaurant.models.Person;
import com.licenta.restaurant.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

     Boolean existsByName(String name);

     Boolean existsByOwner(Person owner);
     Optional<Restaurant> findByName(String name);

     Boolean existsByOwnerEmailAddressAndId(String ownerEmailAddress, Long id);

     Optional<Restaurant> getRestaurantByOwnerUsername(String username);

     List<Restaurant> findAllByIdIn(List<Long> ids);
}
