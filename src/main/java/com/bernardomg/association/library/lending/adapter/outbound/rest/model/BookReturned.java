
package com.bernardomg.association.library.lending.adapter.outbound.rest.model;

import java.time.LocalDate;

public record BookReturned(long book, long borrower, LocalDate returnDate) {

}
