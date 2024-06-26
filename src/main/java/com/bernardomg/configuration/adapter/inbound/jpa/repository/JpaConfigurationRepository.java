
package com.bernardomg.configuration.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;

@Transactional
public final class JpaConfigurationRepository implements ConfigurationRepository {

    private final ConfigurationSpringRepository configurationSpringRepository;

    public JpaConfigurationRepository(final ConfigurationSpringRepository configurationSpringRepo) {
        super();

        configurationSpringRepository = Objects.requireNonNull(configurationSpringRepo);
    }

    @Override
    public final Collection<Configuration> findAll() {
        final Sort sort;

        sort = Sort.by("code");
        return configurationSpringRepository.findAll(sort)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Optional<Configuration> findOne(final String key) {
        return configurationSpringRepository.findByCode(key)
            .map(this::toDomain);
    }

    @Override
    public final Float getFloat(final String key) {
        final Optional<Configuration> read;
        final String                  text;
        final Float                   value;

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
    public final Configuration save(final Configuration configuration) {
        final ConfigurationEntity           entity;
        final ConfigurationEntity           saved;
        final Optional<ConfigurationEntity> existing;

        entity = toEntity(configuration);

        existing = configurationSpringRepository.findByCode(configuration.getCode());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        saved = configurationSpringRepository.save(entity);

        return toDomain(saved);
    }

    private final Configuration toDomain(final ConfigurationEntity entity) {
        return Configuration.builder()
            .withCode(entity.getCode())
            .withValue(entity.getValue())
            .withType(entity.getType())
            .build();
    }

    private final ConfigurationEntity toEntity(final Configuration model) {
        return ConfigurationEntity.builder()
            .withCode(model.getCode())
            .withValue(model.getValue())
            .withType(model.getType())
            .build();
    }

}
