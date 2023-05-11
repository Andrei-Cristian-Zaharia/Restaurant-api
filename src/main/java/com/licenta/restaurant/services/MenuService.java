package com.licenta.restaurant.services;

import com.google.common.collect.Sets;
import com.licenta.restaurant.enums.ObjectType;
import com.licenta.restaurant.exceptionHandlers.NotFoundException;
import com.licenta.restaurant.models.*;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuDTO;
import com.licenta.restaurant.models.responseDTO.MenuCategorised;
import com.licenta.restaurant.models.responseDTO.MenuResponseDTO;
import com.licenta.restaurant.repositories.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuContainerService menuContainerService;
    private final MenuItemService menuItemService;

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id).orElseThrow(() -> new NotFoundException(ObjectType.MENU, id));
    }

    public MenuResponseDTO getMenuInfoById(Long id) {

        MenuResponseDTO menuResponseDTO = new MenuResponseDTO();
        menuResponseDTO.setName(getMenuById(id).getName());
        menuResponseDTO.setMenuItemList(menuContainerService.getAllByMenuId(id));

        return menuResponseDTO;
    }

    public MenuCategorised getMenuCategorised(Long id) {

        Optional<Menu> menu = menuRepository.findById(id);

        if (menu.isEmpty()) {
            throw new NotFoundException(ObjectType.MENU, id);
        }

        MenuCategorised result = new MenuCategorised();
        result.setMenuId(menu.get().getId());
        result.setName(menu.get().getName());

        menuContainerService.getMenuCategories(id).forEach((String category) -> {
            MenuCategorised.MenuCategory menuCategory = new MenuCategorised.MenuCategory(category);
            menuContainerService.getAllByMenuIdAndCategory(id, category).forEach((MenuItem item) -> {
                menuCategory.getItems().add(new MenuCategorised.Item(
                        item.getId(),
                        item.getPrice(),
                        item.getName(),
                        item.getDescription(),
                        item.getRecipe()));
            });
            result.getCategories().add(menuCategory);
        });

        return result;
    }

    public void updateMenuItems(UpdateMenuItemDTO updateMenuItemDTO) {

        if (updateMenuItemDTO.getItemsList() != null && updateMenuItemDTO.getItemsList().isEmpty()) {
            return;
        }

        Menu menu = getMenuById(updateMenuItemDTO.getMenuId());

        Set<MenuItem> currentItems = new HashSet<>(menuContainerService.findAllMenuItemsByMenu_Id(menu.getId()));
        Set<MenuItem> updatedItems = new HashSet<>(menuItemService.getAllById(updateMenuItemDTO.getItemsList()));

        Set<MenuItem> toAdd = Sets.difference(updatedItems, currentItems);
        Set<MenuItem> toRemove = Sets.difference(currentItems, updatedItems);

        toAdd.forEach((MenuItem item) -> {
            MenuContainer menuContainer = new MenuContainer();
            menuContainer.setMenuItem(item);
            menuContainer.setMenu(menu);

            menuContainerService.save(menuContainer);
        });

        toRemove.forEach((MenuItem item) -> menuContainerService.delete(
                menuContainerService.findByMenu_IdAndMenuItem_Id(menu.getId(), item.getId())));
    }

    @Transactional
    public Menu createMenu(CreateMenuDTO createMenuDTO) {

        Menu menu = new Menu();
        menu.setName(createMenuDTO.getName());
        menu = menuRepository.save(menu);

        return menu;
    }

    @Transactional
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
