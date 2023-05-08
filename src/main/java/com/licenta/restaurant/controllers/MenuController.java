package com.licenta.restaurant.controllers;

import com.licenta.restaurant.models.Menu;
import com.licenta.restaurant.models.UpdateMenuItemDTO;
import com.licenta.restaurant.models.createRequestDTO.CreateMenuDTO;
import com.licenta.restaurant.models.responseDTO.MenuCategorised;
import com.licenta.restaurant.models.responseDTO.MenuResponseDTO;
import com.licenta.restaurant.services.MenuContainerService;
import com.licenta.restaurant.services.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("menu")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/info")
    public @ResponseBody ResponseEntity<MenuResponseDTO> getMenuInfoById(@RequestParam Long id) {
        return ResponseEntity.ok().body(menuService.getMenuInfoById(id));
    }

    @GetMapping("/getCategorised")
    public @ResponseBody ResponseEntity<MenuCategorised> getMenugetCategorisedById(@RequestParam Long id) {
        return ResponseEntity.ok().body(menuService.getMenuCategorised(id));
    }

    @PostMapping("/create")
    public @ResponseBody ResponseEntity<Menu> createMenu(@RequestBody CreateMenuDTO createMenuDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(menuService.createMenu(createMenuDTO));
    }

    @PostMapping("/updateItems")
    public @ResponseBody ResponseEntity<String> updateMenuItems(@RequestBody UpdateMenuItemDTO updateMenuItemDTO) {

        menuService.updateMenuItems(updateMenuItemDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Menu updated");
    }

    @DeleteMapping("/delete")
    public @ResponseBody ResponseEntity<String> deleteMenu(@RequestParam Long id) {
        menuService.deleteMenu(id);

        return ResponseEntity.ok().body("Menu deleted !");
    }
}
