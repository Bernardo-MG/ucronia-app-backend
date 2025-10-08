
package com.bernardomg.association.library.book.domain.model;

import java.time.Instant;
import java.util.Collection;

public record Donation(Instant date, Collection<Donor> donors) {

}
