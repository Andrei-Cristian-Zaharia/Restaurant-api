package com.licenta.restaurant.controllers;

import com.licenta.restaurant.models.Menu;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuDTO;
import com.licenta.restaurant.models.responseDTO.MenuResponseDTO;
import com.licenta.restaurant.services.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/info")
    public @ResponseBody ResponseEntity<MenuResponseDTO> getMenuInfoById(@RequestParam Long id) {
        return ResponseEntity.ok().body(menuService.getMenuInfoById(id));
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<Menu> createMenu(@RequestBody CreateMenuDTO createMenuDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createMenu(createMenuDTO));
    }

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteMenu(@RequestParam Long id) {
        menuService.deleteMenu(id);

        return ResponseEntity.ok().body("Menu deleted !");
    }
}
