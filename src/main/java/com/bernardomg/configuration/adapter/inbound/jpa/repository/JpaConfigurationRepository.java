
package com.bernardomg.configuration.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;

@Transactional
public final class JpaConfigurationRepository implements ConfigurationRepository {

    private final ConfigurationSpringRepository configurationSpringRepository;

    public JpaConfigurationRepository(final ConfigurationSpringRepository configurationSpringRepo) {
        super();

        configurationSpringRepository = configurationSpringRepo;
    }

    @Override
    public final boolean exists(final String key) {
        return configurationSpringRepository.existsByKey(key);
    }

    @Override
    public final Collection<Configuration> findAll() {
        return configurationSpringRepository.findAll()
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Optional<Configuration> findOne(final String key) {
        return configurationSpringRepository.findByKey(key)
            .map(this::toDomain);
    }

    @Override
    public final Float getFloat(final String key) {
        final Optional<Configuration> read;
        final String                  text;
        final Float                   value;

        read = configurationSpringRepository.findByKey(key)
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
    public final Configuration save(final Configuration configuration) {
        final ConfigurationEntity           entity;
        final ConfigurationEntity           saved;
        final Optional<ConfigurationEntity> existing;

        entity = toEntity(configuration);

        existing = configurationSpringRepository.findByKey(configuration.getKey());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        saved = configurationSpringRepository.save(entity);

        return toDomain(saved);
    }

    private final Configuration toDomain(final ConfigurationEntity entity) {
        return Configuration.builder()
            .withKey(entity.getKey())
            .withValue(entity.getValue())
            .build();
    }

    private final ConfigurationEntity toEntity(final Configuration entity) {
        return ConfigurationEntity.builder()
            .withKey(entity.getKey())
            .withValue(entity.getValue())
            .build();
    }

}
