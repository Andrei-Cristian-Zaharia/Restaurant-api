package com.licenta.restaurant.filters;

import com.licenta.restaurant.RestaurantConfigurations;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
@Component
public class RouteFilter implements Filter {

    private static final String URI_PREFIX = "/v1/restaurant-api";

    private static final String AUTH_HEADER = "authorities";

    private final RestaurantConfigurations restaurantConfigurations;

    public RouteFilter(RestaurantConfigurations restaurantConfigurations) {
        this.restaurantConfigurations = restaurantConfigurations;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String header = req.getHeader(AUTH_HEADER);
        Enumeration<String> headers = req.getHeaderNames();
        String uri = req.getRequestURI().toString();


        if (("[ADMIN]").equals(req.getHeader(AUTH_HEADER))) {
            chain.doFilter(request, response);
            return;
        }

        if (("[GUEST]").equals(req.getHeader(AUTH_HEADER)) || ("[PERMIT]").equals(req.getHeader(AUTH_HEADER))) {
            chain.doFilter(request, response);
            return;
        }

        if (("[REGULAR_USER]").equals(req.getHeader(AUTH_HEADER))) {

            for (String route: restaurantConfigurations.getUserPaths()) {
                if (req.getRequestURI().equals(URI_PREFIX + route)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        res.setStatus(401);
        res.getOutputStream()
                .write(("Given authorities for request: " + req.getRequestURI() + " are not enough.").getBytes());
    }
}
