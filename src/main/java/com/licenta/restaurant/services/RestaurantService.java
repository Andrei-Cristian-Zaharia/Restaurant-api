package com.licenta.restaurant.services;

import com.licenta.restaurant.enums.ObjectType;
import com.licenta.restaurant.enums.RestaurantStatus;
import com.licenta.restaurant.exceptionHandlers.AlreadyExistsException;
import com.licenta.restaurant.exceptionHandlers.NotFoundException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidDeleteRequestException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidUserAccount;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.UserAlreadyHasRestaurantException;
import com.licenta.restaurant.models.*;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuDTO;
import com.licenta.restaurant.models.createRequestDTO.CreateRestaurantDTO;
import com.licenta.restaurant.models.responseDTO.RestaurantDTO;
import com.licenta.restaurant.models.responseDTO.RestaurantResponseDTO;
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
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final PersonRestTemplateService personRestTemplateService;
    private final ReviewRestTemplateService reviewRestTemplateService;

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

    public Restaurant saveStatus(RestaurantStatusDTO dto) {
        Restaurant restaurant = getRestaurantById(dto.getId());
        restaurant.setName(dto.getName());
        restaurant.setEmailAddress(dto.getEmailAddress());
        restaurant.setTelephone(dto.getTelephone());

        switch (dto.getStatus()) {
            case "WAITING" -> restaurant.setStatus(RestaurantStatus.WAITING.toString());
            case "ACTIVE" -> restaurant.setStatus(RestaurantStatus.ACTIVE.toString());
            case "INACTIVE" -> restaurant.setStatus(RestaurantStatus.INACTIVE.toString());
        }

        return restaurantRepository.save(restaurant);
    }

    public List<RestaurantResponseDTO> getAllRestaurantsFiltered(FilterRestaurantDTO filterRestaurantDTO) {

        List<Restaurant> restaurantsFiltered = restaurantRepository.findAll();

        return restaurantsFiltered.stream().filter((Restaurant res) -> {
            if (!(filterRestaurantDTO.getFilterName().equals("") || filterRestaurantDTO.getFilterName().isEmpty())) {
                return res.getName().toLowerCase(Locale.getDefault())
                        .contains(filterRestaurantDTO.getFilterName()
                                .toLowerCase(Locale.getDefault()));
            }

            return true;
        }).filter((Restaurant res) -> {
            if (filterRestaurantDTO.getRating() != null && filterRestaurantDTO.getRating() != 0) {
                try {
                    return filterRestaurantDTO
                            .getRating()
                            .equals(reviewRestTemplateService.getRatingForRestaurant(res.getId()));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            return true;
        }).map((Restaurant res) -> {
            RestaurantResponseDTO r = modelMapper.map(res, RestaurantResponseDTO.class);
            try {
                r.setRating(reviewRestTemplateService.getRatingForRestaurant(res.getId()));
                return r;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }).toList();
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