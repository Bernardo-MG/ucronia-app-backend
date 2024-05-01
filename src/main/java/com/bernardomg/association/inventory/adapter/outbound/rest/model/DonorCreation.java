
package com.bernardomg.association.inventory.adapter.outbound.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class DonorCreation {

    private Long   member;

    private String name;

}
