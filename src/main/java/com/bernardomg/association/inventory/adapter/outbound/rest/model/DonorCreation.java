
package com.bernardomg.association.inventory.adapter.outbound.rest.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class DonorCreation {

    @NotNull
    @Valid
    private MemberCreationName name;

}
