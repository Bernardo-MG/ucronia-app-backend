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

package com.bernardomg.association.settings.adapter.outbound.rest.controller;

import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.settings.adapter.outbound.cache.SettingsCaches;
import com.bernardomg.association.settings.adapter.outbound.rest.model.SettingChange;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.access.Unsecured;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.usecase.service.SettingService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Settings REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/settings")
@AllArgsConstructor
public class AssociationSettingController {

    private final SettingService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Unsecured
    public Collection<Setting> readAll() {
        return service.getAll();
    }

    @GetMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Unsecured
    public Setting readOne(@PathVariable("code") final String code) {
        // TODO: improve security, not all the configuration can be read by everybody
        return service.getOne(code)
            .orElse(null);
    }

    @PutMapping(path = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "ASSOCIATION_SETTINGS", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { SettingsCaches.PUBLIC }, allEntries = true) })
    public Setting update(@PathVariable("code") final String code,
            @Valid @RequestBody final SettingChange configuration) {
        return service.update(code, configuration.value());
    }

}
