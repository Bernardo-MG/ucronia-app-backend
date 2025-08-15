
package com.bernardomg.association.library.lending.adapter.outbound.rest.model;

import java.time.Instant;

public record BookReturned(long book, long borrower, Instant returnDate) {

}
