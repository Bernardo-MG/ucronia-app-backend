
package com.bernardomg.association.fee.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class FeePaymentReport {

    private final long paid;

    private final long unpaid;

}
