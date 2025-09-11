
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
        return new SettingResponseDto().content(SettingsDtoMapper.toDto(setting.orElse(null)));
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
