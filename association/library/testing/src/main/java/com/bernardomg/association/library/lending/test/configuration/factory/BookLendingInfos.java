
package com.bernardomg.association.library.lending.test.configuration.factory;

import java.time.Instant;
import java.util.Optional;

import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.Borrowers;

public final class BookLendingInfos {

    public static final BookLendingInfo lent() {
        return new BookLendingInfo(Borrowers.valid(), BookConstants.LENT_DATE, Optional.empty());
    }

    public static final BookLendingInfo lent(final Instant lent) {
        return new BookLendingInfo(Borrowers.valid(), lent, Optional.empty());
    }

    public static final BookLendingInfo returned() {
        return new BookLendingInfo(Borrowers.valid(), BookConstants.LENT_DATE,
            Optional.of(BookConstants.RETURNED_DATE));
    }

    public static final BookLendingInfo returned(final Instant lent, final Instant returned) {
        return new BookLendingInfo(Borrowers.valid(), lent, Optional.of(returned));
    }

    public static final BookLendingInfo returnedAlternative(final Instant lent, final Instant returned) {
        return new BookLendingInfo(Borrowers.alternative(), lent, Optional.of(returned));
    }

}
