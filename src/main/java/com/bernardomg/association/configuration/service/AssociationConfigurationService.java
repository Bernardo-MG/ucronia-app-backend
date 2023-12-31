
package com.bernardomg.association.configuration.service;

import com.bernardomg.association.configuration.model.AssociationConfiguration;

public interface AssociationConfigurationService {

    public AssociationConfiguration read();

    public void update(final AssociationConfiguration request);

}
