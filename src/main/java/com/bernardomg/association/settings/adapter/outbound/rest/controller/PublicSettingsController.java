/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.settings.adapter.outbound.rest.model.PublicSettingsDtoMapper;
import com.bernardomg.association.settings.domain.PublicSettings;
import com.bernardomg.association.settings.usecase.PublicSettingsService;
import com.bernardomg.security.access.annotation.Unsecured;
import com.bernardomg.ucronia.openapi.api.PublicSettingsApi;
import com.bernardomg.ucronia.openapi.model.PublicSettingsResponseDto;

/**
 * Settings REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class PublicSettingsController implements PublicSettingsApi {

    private final PublicSettingsService service;

    public PublicSettingsController(final PublicSettingsService service) {
        super();
        this.service = service;
    }

    @Override
    @Unsecured
    public PublicSettingsResponseDto getPublicSettings() {
        final PublicSettings publicSettings;

        publicSettings = service.getSettings();

        return PublicSettingsDtoMapper.toResponseDto(publicSettings);
    }

}
