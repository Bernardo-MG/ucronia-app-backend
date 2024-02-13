
package com.bernardomg.association.configuration.adapter.inbound.jpa.repository;

import java.util.Optional;

import com.bernardomg.association.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.association.configuration.domain.model.Configuration;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;

public final class JpaConfigurationRepository implements ConfigurationRepository {

    private final ConfigurationSpringRepository configurationSpringRepository;

    public JpaConfigurationRepository(final ConfigurationSpringRepository configurationSpringRepo) {
        super();

        configurationSpringRepository = configurationSpringRepo;
    }

    @Override
    public final Optional<Configuration> findOne(final String key) {
        return configurationSpringRepository.findOneByKey(key)
            .map(this::toDomain);
    }

    @Override
    public final Configuration save(final Configuration configuration) {
        final ConfigurationEntity entity;
        final ConfigurationEntity saved;

        entity = toEntity(configuration);
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
