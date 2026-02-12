
package com.bernardomg.association.settings.usecase.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.settings.domain.PublicSettings;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

@Service
@Transactional
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
        final String email;
        final String instagram;

        calendarCode = getValue(AssociationSettingsKey.TEAMUP);
        mapCode = getValue(AssociationSettingsKey.GOOGLE_MAPS);
        email = getValue(AssociationSettingsKey.EMAIL);
        instagram = getValue(AssociationSettingsKey.INSTAGRAM);

        return new PublicSettings(mapCode, calendarCode, email, instagram);
    }

    private final String getValue(final String code) {
        final String value;

        log.debug("Reading setting {}", code);

        value = settingRepository.findOne(code)
            .map(Setting::value)
            .orElse("");

        log.debug("Read setting {}: {}", code, value);

        return value;
    }

}
