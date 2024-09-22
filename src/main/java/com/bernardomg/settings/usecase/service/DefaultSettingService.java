
package com.bernardomg.settings.usecase.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.settings.domain.exception.MissingSettingException;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultSettingService implements SettingService {

    private final SettingRepository settingRepository;

    public DefaultSettingService(final SettingRepository settingRepo) {
        super();

        settingRepository = Objects.requireNonNull(settingRepo);
    }

    @Override
    public final Collection<Setting> getAll() {
        return settingRepository.findAll();
    }

    @Override
    public final Optional<Setting> getOne(final String code) {
        final Optional<Setting> configuration;

        configuration = settingRepository.findOne(code);
        if (configuration.isEmpty()) {
            log.error("Missing configuration {}", code);
            throw new MissingSettingException(code);
        }

        return configuration;
    }

    @Override
    public final Setting update(final String code, final String value) {
        final Setting toSave;
        final Setting existing;

        existing = settingRepository.findOne(code)
            .orElseThrow(() -> {
                log.error("Missing configuration {}", code);
                throw new MissingSettingException(code);
            });

        toSave = Setting.builder()
            .withCode(code)
            .withType(existing.getType())
            .withValue(value)
            .build();

        return settingRepository.save(toSave);
    }

}
