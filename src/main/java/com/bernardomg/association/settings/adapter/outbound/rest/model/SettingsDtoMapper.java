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

package com.bernardomg.association.settings.adapter.outbound.rest.model;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.ucronia.openapi.model.SettingDto;
import com.bernardomg.ucronia.openapi.model.SettingResponseDto;
import com.bernardomg.ucronia.openapi.model.SettingsResponseDto;

public final class SettingsDtoMapper {

    public static final SettingsResponseDto toResponseDto(final Collection<Setting> settings) {
        return new SettingsResponseDto().content(settings.stream()
            .map(SettingsDtoMapper::toDto)
            .toList());
    }

    public static final SettingResponseDto toResponseDto(final Optional<Setting> setting) {
        return new SettingResponseDto().content(setting.map(SettingsDtoMapper::toDto)
            .orElse(null));
    }

    public static final SettingResponseDto toResponseDto(final Setting setting) {
        return new SettingResponseDto().content(SettingsDtoMapper.toDto(setting));
    }

    private static final SettingDto toDto(final Setting setting) {
        return new SettingDto().code(setting.code())
            .value(setting.value())
            .type(setting.type());
    }

    private SettingsDtoMapper() {
        super();
    }

}
