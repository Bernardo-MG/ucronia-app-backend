/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.configuration.adapter.outbound.rest.controller;

import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.configuration.adapter.outbound.cache.ConfigurationCaches;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.usecase.service.ConfigurationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Configuration REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/configuration")
@AllArgsConstructor
public class ConfigurationController {

    private final ConfigurationService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(cacheNames = ConfigurationCaches.CONFIGURATIONS)
    public Collection<Configuration> readAll() {
        return service.getAll();
    }

    @GetMapping(path = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Cacheable(cacheNames = ConfigurationCaches.CONFIGURATION, key = "#result.code")
    public Configuration readOne(@PathVariable("key") final String key) {
        // TODO: improve security, not all the configuration can be read by everybody
        return service.getOne(key)
            .orElse(null);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Caching(put = { @CachePut(cacheNames = ConfigurationCaches.CONFIGURATION, key = "#result.code") },
            evict = { @CacheEvict(cacheNames = { ConfigurationCaches.CONFIGURATIONS }, allEntries = true) })
    public Configuration update(@PathVariable("key") final String key,
            @Valid @RequestBody final Configuration configuration) {
        return service.update(key, configuration);
    }

}
