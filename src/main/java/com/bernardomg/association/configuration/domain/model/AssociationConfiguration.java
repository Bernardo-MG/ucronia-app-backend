
package com.bernardomg.association.configuration.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public final class AssociationConfiguration {

    private float feeAmount;

}
