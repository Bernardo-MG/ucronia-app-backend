
package com.bernardomg.association.library.test.config.factory;

import java.time.Month;
import java.time.YearMonth;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.member.test.config.factory.MemberConstants;

public final class BookLendings {

    public static final BookLending inPast() {
        return BookLending.builder()
            .withIndex(BookConstants.INDEX)
            .withMember(MemberConstants.NUMBER)
            .withLendingDate(YearMonth.of(2020, Month.JANUARY))
            .build();
    }

    public static final BookLending lentNow() {
        return BookLending.builder()
            .withIndex(BookConstants.INDEX)
            .withMember(MemberConstants.NUMBER)
            .withLendingDate(YearMonth.now())
            .build();
    }

    public static final BookLending returnedNow() {
        return BookLending.builder()
            .withIndex(BookConstants.INDEX)
            .withMember(MemberConstants.NUMBER)
            .withLendingDate(YearMonth.now())
            .withReturnDate(YearMonth.now())
            .build();
    }

}
