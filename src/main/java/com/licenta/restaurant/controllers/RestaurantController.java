package com.licenta.restaurant.controllers;

import com.licenta.restaurant.models.DeleteRestaurantDTO;
import com.licenta.restaurant.models.FilterRestaurantDTO;
import com.licenta.restaurant.models.Restaurant;
import com.licenta.restaurant.models.RestaurantStatusDTO;
import com.licenta.restaurant.models.createRequestDTO.CreateRestaurantDTO;
import com.licenta.restaurant.models.responseDTO.RestaurantDTO;
import com.licenta.restaurant.models.responseDTO.RestaurantResponseDTO;
import com.licenta.restaurant.services.RestaurantService;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/all/filtered")
    public @ResponseBody ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurantsFiltered(
            @RequestBody FilterRestaurantDTO filterRestaurantDTO) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(restaurantService.getAllRestaurantsFiltered(filterRestaurantDTO));
    }

    @GetMapping("/id/{id}")
    public @ResponseBody ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok().body(restaurantService.getRestaurantById(id));
    }

    @GetMapping("/name")
    public @ResponseBody ResponseEntity<Restaurant> getRestaurantByName(@RequestParam String name) {
        return ResponseEntity.ok().body(restaurantService.getRestaurantByName(name));
    }

    @GetMapping("/owner")
    public @ResponseBody ResponseEntity<Restaurant> getRestaurantByOwner(@RequestParam String name) {
        return ResponseEntity.ok().body(restaurantService.getRestaurantByOwner(name));
    }

    @GetMapping("/all")
    public @ResponseBody ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok().body(restaurantService.getAllRestaurants());
    }

    @PostMapping("/save/status")
    public @ResponseBody ResponseEntity<Restaurant> saveStatus(@RequestBody RestaurantStatusDTO restaurantStatusDTO) {
        return ResponseEntity.ok().body(restaurantService.saveStatus(restaurantStatusDTO));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRestaurant(@RequestBody DeleteRestaurantDTO deleteRestaurantDTO,
                                                   @RequestHeader("email") String emailAddress) throws JSONException {

        restaurantService.deleteRestaurant(deleteRestaurantDTO, emailAddress);

        return ResponseEntity.ok().body("Restaurant deleted !");
    }
}
