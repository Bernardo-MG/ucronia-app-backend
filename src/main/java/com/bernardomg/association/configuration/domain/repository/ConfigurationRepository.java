
package com.bernardomg.association.configuration.domain.repository;

import java.util.Optional;

import com.bernardomg.association.configuration.domain.model.Configuration;

public interface ConfigurationRepository {

    public Optional<Configuration> findOne(final String key);

    public Configuration save(final Configuration configuration);

}
