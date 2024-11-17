
package com.bernardomg.association.library.book.domain.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.publisher.domain.model.Publisher;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record Book(long number, Title title, String isbn, String language, LocalDate publishDate, boolean lent,
        Collection<Author> authors, Collection<BookLending> lendings, Collection<Publisher> publishers,
        Optional<Donation> donation, Optional<BookType> bookType, Optional<GameSystem> gameSystem) {

    public record Donation(LocalDate date, Collection<Donor> donors) {

    }

}
