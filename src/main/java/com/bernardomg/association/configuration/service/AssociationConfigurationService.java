
package com.bernardomg.association.configuration.service;

import com.bernardomg.association.configuration.model.AssociationConfiguration;
import com.bernardomg.association.configuration.model.request.AssociationConfigurationRequest;

public interface AssociationConfigurationService {

    public AssociationConfiguration read();

    public void update(final AssociationConfigurationRequest request);

}
