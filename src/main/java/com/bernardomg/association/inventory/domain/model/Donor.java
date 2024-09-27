
package com.bernardomg.association.inventory.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * TODO: use comparator
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "with")
public final class Donor {

    private final DonorName name;

    @Builder.Default
    @EqualsAndHashCode.Include
    private final long      number = -1L;

}
