package com.licenta.restaurant.controllers;

import com.licenta.restaurant.services.SavedRestaurantsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("savedRestaurants")
public class SavedRestaurantsController {

    private final SavedRestaurantsService savedRestaurantsService;

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteRelation(@RequestParam Long restaurantId,
                                                               @RequestParam Long userId) {

        savedRestaurantsService.deleteRelation(restaurantId, userId);
        return ResponseEntity.noContent().build();
    }
}
