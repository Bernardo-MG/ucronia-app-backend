
package com.bernardomg.association.contacts.usecase.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.contacts.domain.model.ContactInfo;
import com.bernardomg.association.contacts.usecase.constants.AssociationSettingsKey;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultContactInfoService implements ContactInfoService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultContactInfoService.class);

    private final SettingRepository settingRepository;

    public DefaultContactInfoService(final SettingRepository settingRepo) {
        super();

        settingRepository = Objects.requireNonNull(settingRepo);
    }

    @Override
    public final ContactInfo getContactInfo() {
        final String calendarCode;
        final String mapCode;
        final String email;
        final String instagram;
        final String telegram;

        calendarCode = getValue(AssociationSettingsKey.TEAMUP);
        mapCode = getValue(AssociationSettingsKey.GOOGLE_MAPS);
        email = getValue(AssociationSettingsKey.EMAIL);
        instagram = getValue(AssociationSettingsKey.INSTAGRAM);
        telegram = getValue(AssociationSettingsKey.TELEGRAM);

        return new ContactInfo(mapCode, calendarCode, email, instagram, telegram);
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
