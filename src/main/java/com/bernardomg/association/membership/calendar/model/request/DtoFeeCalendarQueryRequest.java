
package com.bernardomg.association.membership.calendar.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DtoFeeCalendarQueryRequest {

    @NotNull
    @Builder.Default
    private Boolean onlyActive = false;

}
