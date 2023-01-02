package com.licenta.restaurant.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "menu_container")
public class MenuContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu_container")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_menu_item")
    private MenuItem menuItem;

    @OneToOne
    @JoinColumn(name = "id_menu")
    private Menu menu;
}
