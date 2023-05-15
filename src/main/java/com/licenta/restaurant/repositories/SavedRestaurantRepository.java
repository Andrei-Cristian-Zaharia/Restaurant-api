package com.licenta.restaurant.repositories;

import com.licenta.restaurant.models.SavedRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedRestaurantRepository extends JpaRepository<SavedRestaurant, Long> {

    boolean existsByRestaurantIdAndPersonId(Long restaurantId, Long personId);

    @Query(value = "SELECT id_restaurant " +
            "FROM saved_restaurants sr " +
            "JOIN person p " +
            "using(id_person) " +
            "WHERE p.email_address = ?1", nativeQuery = true)
    List<Long> findRestaurantByEmail(String email);

    void deleteByRestaurantIdAndPersonId(Long restaurantId, Long userId);
}
