package com.licenta.restaurant.models;

import lombok.Data;

import java.util.List;

@Data
public class UpdateMenuItemDTO {

    private Long menuId;
    private List<Long> itemsList;
}
