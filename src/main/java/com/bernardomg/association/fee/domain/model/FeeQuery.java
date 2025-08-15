
package com.bernardomg.association.fee.domain.model;

import java.time.Instant;

public final record FeeQuery(Instant date, Instant startDate, Instant endDate) {

}
