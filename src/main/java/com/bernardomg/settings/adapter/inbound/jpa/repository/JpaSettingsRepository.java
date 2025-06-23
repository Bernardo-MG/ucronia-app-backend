
package com.bernardomg.settings.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaSettingsRepository implements SettingRepository {

    private final SettingsSpringRepository settingSpringRepository;

    public JpaSettingsRepository(final SettingsSpringRepository settingSpringRepo) {
        super();

        settingSpringRepository = Objects.requireNonNull(settingSpringRepo);
    }

    @Override
    public final Collection<Setting> findAll() {
        final Collection<Setting> settings;
        final Sort                sort;

        log.debug("Finding all settings");

        sort = Sort.by("code");

        settings = settingSpringRepository.findAll(sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.trace("Found all the settings: {}", settings);

        return settings;
    }

    @Override
    public final Optional<Setting> findOne(final String code) {
        final Optional<Setting> setting;

        log.trace("Finding setting with code {}", code);

        setting = settingSpringRepository.findByCode(code)
            .map(this::toDomain);

        log.trace("Found setting with code {}: {}", code, setting);

        return setting;
    }

    @Override
    public final Float getFloat(final String code) {
        final Optional<Setting> read;
        final String            text;
        final Float             value;

        log.trace("Finding float setting value with code {}", code);

        read = settingSpringRepository.findByCode(code)
            .map(this::toDomain);
        if (read.isPresent()) {
            text = read.get()
                .value();
            value = Float.valueOf(text);
        } else {
            value = 0f;
        }

        log.trace("Found float setting value with code {}: {}", code, value);

        return value;
    }

    @Override
    public final Setting save(final Setting setting) {
        final SettingsEntity           entity;
        final SettingsEntity           saved;
        final Optional<SettingsEntity> existing;

        log.trace("Saving setting {}", setting);

        entity = toEntity(setting);

        existing = settingSpringRepository.findByCode(setting.code());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        saved = settingSpringRepository.save(entity);

        log.trace("Saved setting {}", saved);

        return toDomain(saved);
    }

    private final Setting toDomain(final SettingsEntity entity) {
        return new Setting(entity.getType(), entity.getCode(), entity.getValue());
    }

    private final SettingsEntity toEntity(final Setting model) {
        SettingsEntity entity;

        entity = new SettingsEntity();
        entity.setCode(model.code());
        entity.setValue(model.value());
        entity.setType(model.type());

        return entity;
    }

}
