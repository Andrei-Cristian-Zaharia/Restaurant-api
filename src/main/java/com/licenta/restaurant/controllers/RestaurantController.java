package com.licenta.restaurant.controllers;

import com.licenta.restaurant.models.DeleteRestaurantDTO;
import com.licenta.restaurant.models.Restaurant;
import com.licenta.restaurant.models.createRequestDTO.CreateRestaurantDTO;
import com.licenta.restaurant.models.responseDTO.RestaurantDTO;
import com.licenta.restaurant.services.RestaurantService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<RestaurantDTO> createNewRestaurant(
            @RequestBody CreateRestaurantDTO createRestaurantDTO) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restaurantService.createRestaurant(createRestaurantDTO));
    }

    @GetMapping("/id/{id}")
    public @ResponseBody ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok().body(restaurantService.getRestaurantById(id));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRestaurant(@RequestBody DeleteRestaurantDTO deleteRestaurantDTO,
                                                   @RequestHeader("email") String emailAddress) throws JSONException {

        restaurantService.deleteRestaurant(deleteRestaurantDTO, emailAddress);

        return ResponseEntity.ok().body("Restaurant deleted !");
    }
}
