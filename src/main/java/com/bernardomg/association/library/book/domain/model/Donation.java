
package com.bernardomg.association.library.book.domain.model;

import java.time.LocalDate;
import java.util.Collection;

public record Donation(LocalDate date, Collection<Donor> donors) {

}
