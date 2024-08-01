
package com.bernardomg.association.library.book.domain.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.lending.domain.model.BookBookLending;
import com.bernardomg.association.library.publisher.domain.model.Publisher;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * TODO: include lent status
 * <p>
 * TODO: some fields are optional
 */
@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "with")
public final class Book {

    private Collection<Author>          authors;

    private Optional<BookType>          bookType;

    @Builder.Default
    private Collection<Donor>           donors     = List.of();

    @Builder.Default
    private Optional<GameSystem>        gameSystem = Optional.empty();

    private String                      isbn;

    private String                      language;

    @Builder.Default
    private Collection<BookBookLending> lendings   = List.of();

    @Builder.Default
    private boolean                     lent       = false;

    @EqualsAndHashCode.Include
    private Long                        number;

    @Builder.Default
    private Collection<Publisher>       publishers = List.of();

    private String                      title;

}
