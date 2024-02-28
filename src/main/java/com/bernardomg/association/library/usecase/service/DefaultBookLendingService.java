
package com.bernardomg.association.library.usecase.service;

import java.time.YearMonth;

import com.bernardomg.association.library.domain.exception.MissingBookException;
import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.member.domain.exception.MissingMemberIdException;
import com.bernardomg.association.member.domain.repository.MemberRepository;

public final class DefaultBookLendingService implements BookLendingService {

    private final BookLendingRepository bookLendingRepository;

    private final BookRepository        bookRepository;

    private final MemberRepository      memberRepository;

    public DefaultBookLendingService(final BookLendingRepository bookLendingRepo, final BookRepository bookRepo,
            final MemberRepository memberRepo) {
        super();

        bookLendingRepository = bookLendingRepo;
        bookRepository = bookRepo;
        memberRepository = memberRepo;
    }

    @Override
    public final void lendBook(final String isbn, final long member) {
        final BookLending lending;
        final YearMonth   now;

        // TODO: check the book and member exist

        if (!bookRepository.exists(isbn)) {
            throw new MissingBookException(isbn);
        }

        if (!memberRepository.exists(member)) {
            // TODO: change name
            throw new MissingMemberIdException(member);
        }

        now = YearMonth.now();
        lending = BookLending.builder()
            .withIsbn(isbn)
            .withMember(member)
            .withLendingDate(now)
            .build();

        bookLendingRepository.create(lending);
    }

}
