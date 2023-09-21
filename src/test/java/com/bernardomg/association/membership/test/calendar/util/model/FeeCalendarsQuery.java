
package com.bernardomg.association.membership.test.calendar.util.model;

import com.bernardomg.association.membership.calendar.model.request.DtoFeeCalendarQueryRequest;

public final class FeeCalendarsQuery {

    public static final DtoFeeCalendarQueryRequest onlyActive() {
        return DtoFeeCalendarQueryRequest.builder()
            .onlyActive(true)
            .build();
    }

}
