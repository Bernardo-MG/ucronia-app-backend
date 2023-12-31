
package com.bernardomg.association.membership.fee.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class FeeUpdate {

    private String  name;

    @NotNull
    private Boolean paid;

    private String  surname;

}
