
package com.bernardomg.settings.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

@Transactional
public final class JpaSettingsRepository implements SettingRepository {

    private final SettingsSpringRepository configurationSpringRepository;

    public JpaSettingsRepository(final SettingsSpringRepository configurationSpringRepo) {
        super();

        configurationSpringRepository = Objects.requireNonNull(configurationSpringRepo);
    }

    @Override
    public final Collection<Setting> findAll() {
        final Sort sort;

        sort = Sort.by("code");
        return configurationSpringRepository.findAll(sort)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Optional<Setting> findOne(final String key) {
        return configurationSpringRepository.findByCode(key)
            .map(this::toDomain);
    }

    @Override
    public final Float getFloat(final String key) {
        final Optional<Setting> read;
        final String            text;
        final Float             value;

        read = configurationSpringRepository.findByCode(key)
            .map(this::toDomain);
        if (read.isPresent()) {
            text = read.get()
                .getValue();
            value = Float.valueOf(text);
        } else {
            value = 0f;
        }

        return value;
    }

    @Override
    public final Setting save(final Setting configuration) {
        final SettingsEntity           entity;
        final SettingsEntity           saved;
        final Optional<SettingsEntity> existing;

        entity = toEntity(configuration);

        existing = configurationSpringRepository.findByCode(configuration.getCode());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        saved = configurationSpringRepository.save(entity);

        return toDomain(saved);
    }

    private final Setting toDomain(final SettingsEntity entity) {
        return Setting.builder()
            .withCode(entity.getCode())
            .withValue(entity.getValue())
            .withType(entity.getType())
            .build();
    }

    private final SettingsEntity toEntity(final Setting model) {
        return SettingsEntity.builder()
            .withCode(model.getCode())
            .withValue(model.getValue())
            .withType(model.getType())
            .build();
    }

}
