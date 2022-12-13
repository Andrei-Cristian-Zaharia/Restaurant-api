package com.licenta.restaurant.services;

import com.licenta.restaurant.enums.ObjectType;
import com.licenta.restaurant.exceptionHandlers.AlreadyExistsException;
import com.licenta.restaurant.exceptionHandlers.NotFoundException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidDeleteRequestException;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.InvalidUserAccount;
import com.licenta.restaurant.exceptionHandlers.restaurantExceptions.UserAlreadyHasRestaurantException;
import com.licenta.restaurant.models.DeleteRestaurantDTO;
import com.licenta.restaurant.models.Person;
import com.licenta.restaurant.models.Restaurant;
import com.licenta.restaurant.models.createRequestDTO.CreateRestaurantDTO;
import com.licenta.restaurant.models.responseDTO.RestaurantDTO;
import com.licenta.restaurant.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final PersonRestTemplateService personRestTemplateService;

    private final ModelMapper modelMapper;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             PersonRestTemplateService personRestTemplateService,
                             ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.personRestTemplateService = personRestTemplateService;
        this.modelMapper = modelMapper;
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

        return modelMapper.map(restaurantRepository.save(restaurant), RestaurantDTO.class);
    }

    @Transactional
    public void deleteRestaurant(DeleteRestaurantDTO deleteRestaurantDTO) throws JSONException {

        if (restaurantRepository.findById(deleteRestaurantDTO.getId()).isEmpty()) {
            throw new NotFoundException(ObjectType.RESTAURANT, deleteRestaurantDTO.getId());
        }

        if (Boolean.FALSE.equals(personRestTemplateService.validateAccount(
                deleteRestaurantDTO.getEmailAddress(),
                deleteRestaurantDTO.getPassword()
                ))) {

            throw new InvalidUserAccount();
        }

        if (Boolean.FALSE.equals(restaurantRepository.existsByOwnerEmailAddressAndId(
                deleteRestaurantDTO.getEmailAddress(),
                deleteRestaurantDTO.getId()
        ))) {
            throw new InvalidDeleteRequestException();
        }

        restaurantRepository.deleteById(deleteRestaurantDTO.getId());
    }
}