
package com.bernardomg.association.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bernardomg.data.springframework.PaginationArgumentResolver;
import com.bernardomg.data.springframework.SortingArgumentResolver;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PaginationArgumentResolver());
        resolvers.add(new SortingArgumentResolver());
    }

}
