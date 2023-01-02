package com.licenta.restaurant.controllers;

import com.licenta.restaurant.models.MenuItem;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuItemDTO;
import com.licenta.restaurant.services.MenuItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("menuItem")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<MenuItem> createMenuItem(@RequestBody CreateMenuItemDTO createMenuItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItemService.createMenuItem(createMenuItemDTO));
    }

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteMenuItem(@RequestParam Long id) {
        menuItemService.deleteMenuItem(id);

        return ResponseEntity.ok().body("Menu item deleted !");
    }
}
