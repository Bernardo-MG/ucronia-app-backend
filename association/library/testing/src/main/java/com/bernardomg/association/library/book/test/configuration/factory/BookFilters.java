
package com.bernardomg.association.library.book.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.library.book.domain.filter.BookFilter;

public final class BookFilters {

    public static final BookFilter all() {
        return new BookFilter(Optional.empty());
    }

    public static final BookFilter partialTitle() {
        return new BookFilter(Optional.of(BookConstants.TITLE.substring(0, BookConstants.TITLE.length() - 2)));
    }

    public static final BookFilter subtitle() {
        return new BookFilter(Optional.of(BookConstants.SUBTITLE));
    }

    public static final BookFilter supertitle() {
        return new BookFilter(Optional.of(BookConstants.SUPERTITLE));
    }

    public static final BookFilter title() {
        return new BookFilter(Optional.of(BookConstants.TITLE));
    }

    private BookFilters() {
        super();
    }

}
