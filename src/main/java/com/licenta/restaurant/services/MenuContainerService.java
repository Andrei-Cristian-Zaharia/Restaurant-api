package com.licenta.restaurant.services;

import com.licenta.restaurant.models.Menu;
import com.licenta.restaurant.models.MenuContainer;
import com.licenta.restaurant.models.MenuItem;
import com.licenta.restaurant.repositories.MenuContainerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuContainerService {

    private final MenuContainerRepository menuContainerRepository;

    private final MenuItemService menuItemService;

    public MenuContainerService(MenuContainerRepository menuContainerRepository, MenuItemService menuItemService) {
        this.menuContainerRepository = menuContainerRepository;
        this.menuItemService = menuItemService;
    }

    public void setItemsToMenu(List<Long> itemIds, Menu menu) {

        List<MenuItem> menuItems = menuItemService.getAllById(itemIds);

        menuItems.forEach((MenuItem item) -> {
            MenuContainer menuContainer = new MenuContainer();
            menuContainer.setMenuItem(item);
            menuContainer.setMenu(menu);

            menuContainerRepository.save(menuContainer);
        });
    }

    public List<MenuItem> getAllByMenuId(Long id) {
        return menuContainerRepository.findAllByMenu_Id(id).stream().map(MenuContainer::getMenuItem).toList();
    }
}