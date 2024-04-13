
package com.bernardomg.association.configuration.usecase.service;

import java.util.Optional;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.domain.model.Configuration;

public interface AssociationConfigurationService {

    public AssociationConfiguration getAll();

    public Optional<Configuration> getOne(final String key);

    public void update(final AssociationConfiguration configuration);

}
