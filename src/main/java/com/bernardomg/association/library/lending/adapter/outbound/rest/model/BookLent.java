
package com.bernardomg.association.library.lending.adapter.outbound.rest.model;

import java.time.Instant;

public record BookLent(long book, long person, Instant lendingDate) {

}
