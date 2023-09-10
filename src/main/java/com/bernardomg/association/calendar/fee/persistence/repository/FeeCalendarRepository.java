
package com.bernardomg.association.calendar.fee.persistence.repository;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;

public interface FeeCalendarRepository {

    public FeeCalendarRange findRange(final boolean onlyActive);

}
