package com.licenta.restaurant.services;

import com.licenta.restaurant.enums.ObjectType;
import com.licenta.restaurant.exceptionHandlers.AlreadyExistsException;
import com.licenta.restaurant.exceptionHandlers.NotFoundException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidDeleteRequestException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidUserAccount;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.UserAlreadyHasRestaurantException;
import com.licenta.restaurant.models.DeleteRestaurantDTO;
import com.licenta.restaurant.models.Menu;
import com.licenta.restaurant.models.Person;
import com.licenta.restaurant.models.Restaurant;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuDTO;
import com.licenta.restaurant.models.createRequestDTO.CreateRestaurantDTO;
import com.licenta.restaurant.models.responseDTO.RestaurantDTO;
import com.licenta.restaurant.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final PersonRestTemplateService personRestTemplateService;

    private final MenuService menuService;
    private final ModelMapper modelMapper;


    public Restaurant getRestaurantById(Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        return restaurant.orElseThrow(() -> new NotFoundException(ObjectType.RESTAURANT, id));
    }

    public Restaurant getRestaurantByName(String name) {
        Optional<Restaurant> restaurant = restaurantRepository.findByName(name);

        return restaurant.orElseThrow(() -> new NotFoundException(ObjectType.RESTAURANT, name));
    }

    public Restaurant getRestaurantByOwner(String username) {
        Optional<Restaurant> restaurant = restaurantRepository.getRestaurantByOwnerUsername(username);

        return restaurant.orElseThrow(() -> new NotFoundException(ObjectType.RESTAURANT, restaurant.get().getId()));
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Transactional
    public void updateMenu(Menu menu, Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if (restaurant.isEmpty()) {
            throw new NotFoundException(ObjectType.RESTAURANT, restaurantId);
        }

        if (restaurant.get().getMenu() != null) {
            // TODO: HANDLE EXCEPTION
            return;
        }

        restaurant.get().setMenu(menu);
        restaurantRepository.save(restaurant.get());
    }

    @Transactional
    public RestaurantDTO createRestaurant(CreateRestaurantDTO createRestaurantDTO) {

        if (Boolean.TRUE.equals(restaurantRepository.existsByName(createRestaurantDTO.getName()))) {
            throw new AlreadyExistsException("restaurant");
        }

        Person person = personRestTemplateService.getPersonById(createRestaurantDTO.getPerson_id());

        if (Boolean.TRUE.equals(restaurantRepository.existsByOwner(person))) {
            throw new UserAlreadyHasRestaurantException();
        }

        Restaurant restaurant = modelMapper.map(createRestaurantDTO, Restaurant.class);
        restaurant.setPartnerSince(LocalDate.now());
        restaurant.setOwner(person);

        try {
            personRestTemplateService.changePersonStatus(true, createRestaurantDTO.getPerson_id());
        } catch (JSONException e) {
            log.error(e.getMessage(), e);
        }

        RestaurantDTO result = modelMapper.map(restaurantRepository.save(restaurant), RestaurantDTO.class);
        updateMenu(menuService.createMenu(new CreateMenuDTO("Main menu", result.getId())), restaurant.getId());
        return result;
    }

    @Transactional
    public void deleteRestaurant(DeleteRestaurantDTO deleteRestaurantDTO,
                                 @Valid String emailAddress) throws JSONException {

        if (restaurantRepository.findById(deleteRestaurantDTO.getId()).isEmpty()) {
            throw new NotFoundException(ObjectType.RESTAURANT, deleteRestaurantDTO.getId());
        }

        Long personId = personRestTemplateService.validateAccount(emailAddress, deleteRestaurantDTO.getPassword());

        if (personId == null) {
            throw new InvalidUserAccount();
        }

        if (Boolean.FALSE.equals(restaurantRepository.existsByOwnerEmailAddressAndId(
                emailAddress,
                deleteRestaurantDTO.getId()))) {
            throw new InvalidDeleteRequestException();
        }

        personRestTemplateService.changePersonStatus(false, personId);

        restaurantRepository.deleteById(deleteRestaurantDTO.getId());
    }
}