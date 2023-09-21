
package com.bernardomg.association.membership.test.calendar.util.model;

import com.bernardomg.association.membership.calendar.model.request.DtoFeeCalendarQueryRequest;

public final class FeeCalendarsQuery {

    public static final DtoFeeCalendarQueryRequest active() {
        return DtoFeeCalendarQueryRequest.builder()
            .active(true)
            .build();
    }

    public static final DtoFeeCalendarQueryRequest all() {
        return DtoFeeCalendarQueryRequest.builder()
            .build();
    }

    public static final DtoFeeCalendarQueryRequest inactive() {
        return DtoFeeCalendarQueryRequest.builder()
            .active(false)
            .build();
    }

}
