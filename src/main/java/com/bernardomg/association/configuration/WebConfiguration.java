
package com.bernardomg.association.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bernardomg.security.web.whitelist.WhitelistRoute;
import com.bernardomg.ws.springframework.request.PaginationArgumentResolver;
import com.bernardomg.ws.springframework.request.SortingArgumentResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PaginationArgumentResolver());
        resolvers.add(new SortingArgumentResolver());
    }
    @Bean("swaggerApiWhitelist")
    public WhitelistRoute getSwaggerApiWhitelist() {
        return WhitelistRoute.of("/swagger-ui/**", HttpMethod.GET);
    }
    @Bean("openApiWhitelist")
    public WhitelistRoute getOpenApiWhitelist() {
        return WhitelistRoute.of("/api-docs/**", HttpMethod.GET);
    }


}
