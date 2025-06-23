
package com.bernardomg.association.transaction.domain.model;

import java.time.LocalDate;

public final record TransactionQuery(LocalDate date, LocalDate startDate, LocalDate endDate) {

}
