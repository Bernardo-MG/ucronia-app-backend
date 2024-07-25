
package com.bernardomg.association.library.domain.model;

import java.util.Collection;
import java.util.List;

import com.bernardomg.association.inventory.domain.model.Donor;

import lombok.Builder;
import lombok.Value;

/**
 * TODO: include lent status
 */
@Value
@Builder(setterPrefix = "with")
public final class Book {

    private Collection<Author>          authors;

    private BookType                    bookType;

    @Builder.Default
    private Collection<Donor>           donors     = List.of();

    private GameSystem                  gameSystem;

    private String                      isbn;

    private String                      language;

    @Builder.Default
    private Collection<BookBookLending> lendings   = List.of();

    private boolean                     lent;

    private Long                        number;

    @Builder.Default
    private Collection<Publisher>       publishers = List.of();

    private String                      title;

}
