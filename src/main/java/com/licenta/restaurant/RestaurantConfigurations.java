package com.licenta.restaurant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "restaurant")
public class RestaurantConfigurations {

    List<String> userPaths;
}
