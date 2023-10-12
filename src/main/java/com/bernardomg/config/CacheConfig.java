
package com.bernardomg.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.CaffeineSpec;

/**
 * Cache configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
@EnableCaching
@EnableConfigurationProperties(CacheProperties.class)
public class CacheConfig {

    public CacheConfig() {
        super();
    }

    @Bean("CacheManager")
    public CacheManager getCacheManager(final CacheProperties cacheProperties) {
        return new CaffeineCacheManager();
    }

    @Bean("CaffeineSpec")
    public CaffeineSpec getCaffeineSpec(final CacheProperties cacheProperties) {
        final String spec;

        spec = String.format("maximumSize=%d,expireAfterAccess=%ds", cacheProperties.getMaximumSize(),
            cacheProperties.getExpireAfterAccess());
        return CaffeineSpec.parse(spec);
    }

}
