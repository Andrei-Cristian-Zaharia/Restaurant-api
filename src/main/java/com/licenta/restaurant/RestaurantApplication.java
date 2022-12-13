package com.licenta.restaurant;

import com.licenta.restaurant.filters.RouteFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestaurantApplication {

	private final RestaurantConfigurations restaurantConfigurations;

	public RestaurantApplication(RestaurantConfigurations restaurantConfigurations) {
		this.restaurantConfigurations = restaurantConfigurations;
	}

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public FilterRegistrationBean<RouteFilter> filterRoute() {
		FilterRegistrationBean<RouteFilter> filter = new FilterRegistrationBean<>();

		filter.setFilter(new RouteFilter(restaurantConfigurations));

		return filter;
	}
}
