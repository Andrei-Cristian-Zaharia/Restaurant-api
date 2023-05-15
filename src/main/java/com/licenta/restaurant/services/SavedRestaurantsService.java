package com.licenta.restaurant.services;

import com.licenta.restaurant.exceptionHandlers.AlreadyExistsException;
import com.licenta.restaurant.models.AddFavoriteDTO;
import com.licenta.restaurant.models.SavedRestaurant;
import com.licenta.restaurant.repositories.SavedRestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SavedRestaurantsService {

    private final SavedRestaurantRepository savedRestaurantRepository;

    public List<Long> getAllRelationsForUserEmail(String email) {

        return savedRestaurantRepository.findRestaurantByEmail(email);
    }

    public void existItem(AddFavoriteDTO addFavoriteDTO) {
        if (savedRestaurantRepository.existsByRestaurantIdAndPersonId(
                addFavoriteDTO.getRestaurantId(),
                addFavoriteDTO.getUserId())
        ) {
            throw new AlreadyExistsException();
        }
    }

    @Transactional
    public void saveNewRelation(Long userId, Long recipeId) {
        SavedRestaurant relation = new SavedRestaurant();
        relation.setRestaurantId(recipeId);
        relation.setPersonId(userId);

        savedRestaurantRepository.save(relation);
    }

    @Transactional
    public void deleteRelation(Long restaurantId, Long userId) {
        savedRestaurantRepository.deleteByRestaurantIdAndPersonId(restaurantId, userId);
    }
}
