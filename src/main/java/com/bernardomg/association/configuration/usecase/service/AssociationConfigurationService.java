
package com.bernardomg.association.configuration.usecase.service;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;

public interface AssociationConfigurationService {

    public AssociationConfiguration getAll();

    public void update(final AssociationConfiguration configuration);

}
