
package com.bernardomg.association.calendar.test.fee.util.model;

import com.bernardomg.association.calendar.fee.model.request.DtoFeeCalendarQueryRequest;

public final class FeeCalendarsQuery {

    public static final DtoFeeCalendarQueryRequest onlyActive() {
        return DtoFeeCalendarQueryRequest.builder()
            .onlyActive(true)
            .build();
    }

}
