
package com.bernardomg.association.inventory.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class Donor {

    private final DonorName name;

    @Builder.Default
    private final long      number = -1L;

}
