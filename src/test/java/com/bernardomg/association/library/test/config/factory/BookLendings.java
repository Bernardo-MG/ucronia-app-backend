
package com.bernardomg.association.library.test.config.factory;

import java.time.YearMonth;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class BookLendings {

    public static final BookLending lentNow() {
        return BookLending.builder()
            .withIsbn(BookConstants.ISBN)
            .withMember(MemberConstants.NUMBER)
            .withLendingDate(YearMonth.now())
            .build();
    }
    public static final BookLending returnedNow() {
        return BookLending.builder()
            .withIsbn(BookConstants.ISBN)
            .withMember(MemberConstants.NUMBER)
            .withLendingDate(YearMonth.now())
            .withReturnDate(YearMonth.now())            .build();
    }

}
