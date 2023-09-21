
package com.bernardomg.association.membership.calendar.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DtoFeeCalendarQueryRequest {

    private Boolean active;

}
