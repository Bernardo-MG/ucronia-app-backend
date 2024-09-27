
package com.bernardomg.association.fee.domain.model;

import java.time.LocalDate;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record FeeTransaction(LocalDate date, Long index) {

}
