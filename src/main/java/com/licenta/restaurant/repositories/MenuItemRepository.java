package com.licenta.restaurant.repositories;

import com.licenta.restaurant.models.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository <MenuItem, Long> {
}
