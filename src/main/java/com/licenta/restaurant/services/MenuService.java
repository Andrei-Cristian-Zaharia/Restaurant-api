package com.licenta.restaurant.services;

import com.licenta.restaurant.enums.ObjectType;
import com.licenta.restaurant.exceptionHandlers.NotFoundException;
import com.licenta.restaurant.models.Menu;
import com.licenta.restaurant.models.MenuItem;
import com.licenta.restaurant.models.Restaurant;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuDTO;
import com.licenta.restaurant.models.responseDTO.MenuResponseDTO;
import com.licenta.restaurant.repositories.MenuRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuContainerService menuContainerService;

    private final RestaurantService restaurantService;

    public MenuService(MenuRepository menuRepository,
                       MenuContainerService menuContainerService,
                       RestaurantService restaurantService) {
        this.menuRepository = menuRepository;
        this.menuContainerService = menuContainerService;
        this.restaurantService = restaurantService;
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new NotFoundException(ObjectType.MENU, id));
    }

    public MenuResponseDTO getMenuInfoById(Long id) {

        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setName(getMenuById(id).getName());
        menuResponseDTO.setMenuItemList(menuContainerService.getAllByMenuId(id));

        return menuResponseDTO;
    }

    @Transactional
    public Menu createMenu(CreateMenuDTO createMenuDTO) {

        Menu menu = new Menu();
        menu.setName(createMenuDTO.getName());

        Restaurant restaurant = restaurantService.getRestaurantById(createMenuDTO.getRestaurantId());
        menu.setRestaurant(restaurant);

        menu = menuRepository.save(menu);

        menuContainerService.setItemsToMenu(createMenuDTO.getMenuItemIds(), menu);

        return menu;
    }

    @Transactional
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
