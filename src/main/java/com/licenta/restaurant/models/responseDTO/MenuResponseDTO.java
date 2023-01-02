package com.licenta.restaurant.models.responseDTO;

import com.licenta.restaurant.models.MenuItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuResponseDTO {

    private String name;
    private List<MenuItem> menuItemList;
}
