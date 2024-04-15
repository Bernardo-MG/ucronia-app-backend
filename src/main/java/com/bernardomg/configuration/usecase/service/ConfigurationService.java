
package com.bernardomg.configuration.usecase.service;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.configuration.domain.model.Configuration;

public interface ConfigurationService {

    public Collection<Configuration> getAll();

    public Optional<Configuration> getOne(final String code);

    public Optional<Configuration> getOnePublic(final String code);

    public Configuration update(final String code, final String value);

}
