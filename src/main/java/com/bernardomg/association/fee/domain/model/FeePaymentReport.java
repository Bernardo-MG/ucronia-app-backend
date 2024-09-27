
package com.bernardomg.association.fee.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record FeePaymentReport(Long paid, Long unpaid) {

}
