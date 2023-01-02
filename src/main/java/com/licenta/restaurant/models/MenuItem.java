package com.licenta.restaurant.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @Column(name = "id_menu_item")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Float price;

    @Column(name = "category")
    private String category;

    @Nullable
    @OneToOne
    @JoinColumn(name = "id_recipe")
    private Recipe recipe;
}
