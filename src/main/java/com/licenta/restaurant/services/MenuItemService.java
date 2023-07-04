package com.licenta.restaurant.services;

import com.licenta.restaurant.enums.ObjectType;
import com.licenta.restaurant.exceptionHandlers.NotFoundException;
import com.licenta.restaurant.models.MenuItem;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuItemDTO;
import com.licenta.restaurant.models.responseDTO.MenuItemDTO;
import com.licenta.restaurant.repositories.MenuItemRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    private final RecipeRestTemplateService recipeRestTemplateService;

    private final ModelMapper modelMapper;

    public MenuItemService(MenuItemRepository menuItemRepository,
                           RecipeRestTemplateService recipeRestTemplateService,
                           ModelMapper modelMapper) {
        this.menuItemRepository = menuItemRepository;
        this.recipeRestTemplateService = recipeRestTemplateService;
        this.modelMapper = modelMapper;
    }

    public List<MenuItem> getAllById(List<Long> id) {
        return menuItemRepository.findAllById(id);
    }

    public MenuItem getById(Long id) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(id);

        return menuItem.orElseThrow(() -> {throw new NotFoundException(ObjectType.MENU_ITEM, id);});
    }

    @Transactional
    public MenuItem createMenuItem(CreateMenuItemDTO createMenuItemDTO) {

        MenuItem menuItemDTO = modelMapper.map(createMenuItemDTO, MenuItem.class);
        if (createMenuItemDTO.getRecipeId() != null) {
            menuItemDTO.setRecipe(recipeRestTemplateService.getRecipeById(createMenuItemDTO.getRecipeId()));
        }

        return menuItemRepository.save(menuItemDTO);
    }

    @Transactional
    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
