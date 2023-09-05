
package com.bernardomg.association.configuration.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidatedAssociationConfigurationRequest implements AssociationConfigurationRequest {

    private float feeAmount;

}
