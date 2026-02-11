
package com.bernardomg.association.settings.usecase;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.settings.domain.PublicSettings;
import com.bernardomg.settings.domain.exception.MissingSettingException;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

public final class DefaultPublicSettingsService implements PublicSettingsService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultPublicSettingsService.class);

    private final SettingRepository settingRepository;

    public DefaultPublicSettingsService(final SettingRepository settingRepo) {
        super();

        settingRepository = Objects.requireNonNull(settingRepo);
    }

    @Override
    public final PublicSettings getSettings() {
        final String calendarCode;
        final String mapCode;

        calendarCode = getOne(AssociationSettingsKey.TEAMUP).map(Setting::value)
            .orElse(null);
        mapCode = getOne(AssociationSettingsKey.GOOGLE_MAPS).map(Setting::value)
            .orElse(null);

        return new PublicSettings(mapCode, calendarCode);
    }

    private final Optional<Setting> getOne(final String code) {
        final Optional<Setting> setting;

        log.debug("Reading setting {}", code);

        setting = settingRepository.findOne(code);
        if (setting.isEmpty()) {
            log.error("Missing setting {}", code);
            throw new MissingSettingException(code);
        }

        log.debug("Read setting {}", setting);

        return setting;
    }

}
