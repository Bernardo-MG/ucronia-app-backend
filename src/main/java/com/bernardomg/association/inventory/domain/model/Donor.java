
package com.bernardomg.association.inventory.domain.model;

import lombok.Builder;

/**
 * TODO: use comparator
 */
@Builder(setterPrefix = "with")
public record Donor(DonorName name, Long number) {

}
