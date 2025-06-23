
package com.bernardomg.settings.usecase.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.settings.domain.exception.MissingSettingException;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

@Transactional
public final class DefaultSettingService implements SettingService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultSettingService.class);

    private final SettingRepository settingRepository;

    public DefaultSettingService(final SettingRepository settingRepo) {
        super();

        settingRepository = Objects.requireNonNull(settingRepo);
    }

    @Override
    public final Collection<Setting> getAll() {
        log.debug("Reading settings");

        return settingRepository.findAll();
    }

    @Override
    public final Optional<Setting> getOne(final String code) {
        final Optional<Setting> setting;

        log.debug("Reading setting {}", code);

        setting = settingRepository.findOne(code);
        if (setting.isEmpty()) {
            log.error("Missing setting {}", code);
            throw new MissingSettingException(code);
        }

        return setting;
    }

    @Override
    public final Setting update(final String code, final String value) {
        final Setting toSave;
        final Setting existing;

        log.debug("Updating code {} with value {}", code, value);

        existing = settingRepository.findOne(code)
            .orElseThrow(() -> {
                log.error("Missing configuration {}", code);
                throw new MissingSettingException(code);
            });

        toSave = new Setting(existing.type(), code, value);

        return settingRepository.save(toSave);
    }

}
