
package com.bernardomg.configuration.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.configuration.domain.model.Configuration;

public interface ConfigurationRepository {

    public boolean exists(final String key);

    public Collection<Configuration> findAll();

    public Optional<Configuration> findOne(final String key);

    public Float getFloat(final String key);

    public Configuration save(final Configuration configuration);

}
