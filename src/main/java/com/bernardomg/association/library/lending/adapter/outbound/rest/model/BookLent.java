
package com.bernardomg.association.library.lending.adapter.outbound.rest.model;

import java.time.LocalDate;

public record BookLent(long book, long person, LocalDate lendingDate) {

}
