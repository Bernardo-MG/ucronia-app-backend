
package com.bernardomg.association.fee.calendar.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DtoFeeCalendarQueryRequest {

    private Boolean onlyActive;

}
