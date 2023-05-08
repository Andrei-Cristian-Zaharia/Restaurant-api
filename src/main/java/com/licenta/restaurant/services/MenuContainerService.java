package com.licenta.restaurant.services;

import com.google.common.collect.Sets;
import com.licenta.restaurant.models.Menu;
import com.licenta.restaurant.models.MenuContainer;
import com.licenta.restaurant.models.MenuItem;
import com.licenta.restaurant.models.UpdateMenuItemDTO;
import com.licenta.restaurant.repositories.MenuContainerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuContainerService {

    private final MenuContainerRepository menuContainerRepository;

    public List<String> getMenuCategories(Long menuId) {
        return menuContainerRepository.findMenuCategories(menuId);
    }

    public List<MenuItem> getAllByMenuIdAndCategory(Long menuId, String category) {
        return menuContainerRepository.findAllByMenu_IdAndAndMenuItem_Category(menuId, category);
    }

    public List<MenuItem> getAllByMenuId(Long id) {
        return menuContainerRepository.findAllByMenu_Id(id).stream().map(MenuContainer::getMenuItem).toList();
    }

    public List<MenuItem> findAllMenuItemsByMenu_Id(Long menuId) {
        return menuContainerRepository.findAllMenuItemsByMenu_Id(menuId);
    }

    public MenuContainer findByMenu_IdAndMenuItem_Id(Long menuId, Long menuItemId) {
        return menuContainerRepository.findByMenu_IdAndMenuItem_Id(menuId, menuItemId);
    }

    public void save(MenuContainer menuContainer) {
        menuContainerRepository.save(menuContainer);
    }

    public void delete(MenuContainer menuContainer) {
        menuContainerRepository.delete(menuContainer);
    }
}