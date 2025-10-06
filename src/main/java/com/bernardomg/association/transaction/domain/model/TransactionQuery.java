
package com.bernardomg.association.transaction.domain.model;

import java.time.Instant;

public final record TransactionQuery(Instant date, Instant from, Instant to) {

}
