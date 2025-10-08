
package com.bernardomg.association.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import com.bernardomg.security.web.whitelist.WhitelistRoute;

@Configuration
@ConditionalOnProperty(name = "openapi.enabled", havingValue = "true")
public class OpenApiConfiguration {

    @Bean("openApiWhitelist")
    public WhitelistRoute getOpenApiWhitelist() {
        return WhitelistRoute.of("/api-docs/**", HttpMethod.GET);
    }

    @Bean("swaggerApiWhitelist")
    public WhitelistRoute getSwaggerApiWhitelist() {
        return WhitelistRoute.of("/swagger-ui/**", HttpMethod.GET);
    }

}
