
package com.bernardomg.association.fee.domain.dto;

import java.time.Instant;
import java.time.YearMonth;
import java.util.Collection;

public record FeePayments(Long member, Instant paymentDate, Collection<YearMonth> months) {}
