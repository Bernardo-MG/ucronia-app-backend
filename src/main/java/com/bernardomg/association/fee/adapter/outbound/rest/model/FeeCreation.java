
package com.bernardomg.association.fee.adapter.outbound.rest.model;

import java.time.YearMonth;

import jakarta.validation.constraints.NotNull;

public final record FeeCreation(@NotNull YearMonth month, @NotNull FeeCreationMember person) {

    public static final record FeeCreationMember(@NotNull Long number) {

    }

}
