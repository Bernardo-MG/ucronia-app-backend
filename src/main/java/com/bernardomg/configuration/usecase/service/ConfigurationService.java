
package com.bernardomg.configuration.usecase.service;

import java.util.Optional;

import com.bernardomg.configuration.domain.model.Configuration;

public interface ConfigurationService {

    public Optional<Configuration> getOne(final String key);

}
