package com.licenta.restaurant.models.responseDTO;

import com.licenta.restaurant.models.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuCategorised {

    private Long menuId;
    private String name;
    private List<MenuCategory> categories;

    public MenuCategorised() {
        categories = new ArrayList<>();
    }

    @Data
    public static class MenuCategory {
        private String category;
        private List<Item> items;

        public MenuCategory(String category) {
            this.category = category;
            items = new ArrayList<>();
        }
    }

    @Data
    @AllArgsConstructor
    public static class Item {
        private Long id;
        private Float price;
        private String name;
        private String description;
        private Recipe recipe;
    }
}
