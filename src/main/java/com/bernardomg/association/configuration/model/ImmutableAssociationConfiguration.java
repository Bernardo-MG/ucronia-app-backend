
package com.bernardomg.association.configuration.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableAssociationConfiguration implements AssociationConfiguration {

    private final float feeAmount;

}
