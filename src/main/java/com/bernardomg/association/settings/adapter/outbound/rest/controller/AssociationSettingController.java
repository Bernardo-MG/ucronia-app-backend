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
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.settings.adapter.outbound.cache.SettingsCaches;
import com.bernardomg.association.settings.adapter.outbound.rest.model.SettingsDtoMapper;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.access.Unsecured;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.usecase.service.SettingService;
import com.bernardomg.ucronia.openapi.api.AssociationSettingsApi;
import com.bernardomg.ucronia.openapi.model.SettingChangeDto;
import com.bernardomg.ucronia.openapi.model.SettingResponseDto;
import com.bernardomg.ucronia.openapi.model.SettingsResponseDto;

import jakarta.validation.Valid;

/**
 * Settings REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class AssociationSettingController implements AssociationSettingsApi {

    private final SettingService service;

    public AssociationSettingController(final SettingService service) {
        super();
        // TODO: are the permissions correct?
        this.service = service;
    }

    @Override
    @Unsecured
    public SettingsResponseDto getAllAssociationSettings() {
        final Collection<Setting> settings;

        settings = service.getAll();
        return SettingsDtoMapper.toResponseDto(settings);
    }

    @Override
    @Unsecured
    public SettingResponseDto getAssociationSettingByCode(final String code) {
        final Optional<Setting> setting;

        setting = service.getOne(code);
        return SettingsDtoMapper.toResponseDto(setting);
    }

    @Override
    @RequireResourceAccess(resource = "ASSOCIATION_SETTINGS", action = Actions.UPDATE)
    @Caching(evict = { @CacheEvict(cacheNames = { SettingsCaches.PUBLIC }, allEntries = true) })
    public SettingResponseDto updateAssociationSetting(final String code,
            @Valid final SettingChangeDto settingChangeDto) {
        final Setting setting;

        setting = service.update(code, settingChangeDto.getValue());
        return SettingsDtoMapper.toResponseDto(setting);
    }

}
