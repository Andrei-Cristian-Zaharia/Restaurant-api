package com.licenta.restaurant.repositories;

import com.licenta.restaurant.models.MenuContainer;
import com.licenta.restaurant.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuContainerRepository extends JpaRepository <MenuContainer, Long> {

    List<MenuContainer> findAllByMenu_Id(Long id);

    @Query(value = "SELECT DISTINCT MI.category as category" +
            " FROM MenuContainer as MC " +
            "INNER JOIN MenuItem as MI on MI.id = MC.menuItem.id " +
            "WHERE MC.menu.id = ?1",nativeQuery = false)
    List<String> findMenuCategories(Long id);

    @Query(value = "SELECT MI " +
            "FROM MenuContainer as MC " +
            "INNER JOIN MenuItem as MI on MI.id = MC.menuItem.id " +
            "WHERE MC.menu.id = ?1 and MI.category = ?2", nativeQuery = false)
    List<MenuItem> findAllByMenu_IdAndAndMenuItem_Category(Long menuId, String category);

    @Query(value = "SELECT MI " +
            "FROM MenuContainer as MC " +
            "INNER JOIN MenuItem as MI on MI.id = MC.menuItem.id " +
            "WHERE MC.menu.id = ?1", nativeQuery = false)
    List<MenuItem> findAllMenuItemsByMenu_Id(Long menuId);

    MenuContainer findByMenu_IdAndMenuItem_Id(Long menuId, Long menuItemId);
}
