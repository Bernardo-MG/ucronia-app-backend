/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.library.test.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.JpaAuthorRepository;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.FictionBookSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.GameBookSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.JpaBookRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.JpaFictionBookRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.JpaGameBookRepository;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.JpaBookTypeRepository;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.JpaGameSystemRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.JpaBookLendingRepository;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.JpaPublisherRepository;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberProfileSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;

@Configuration
@EnableJpaRepositories(basePackages = { "com.bernardomg.association.library.**.adapter.inbound.jpa",
        "com.bernardomg.association.member.adapter.inbound.jpa", "com.bernardomg.association.fee.adapter.inbound.jpa",
        "com.bernardomg.association.transaction.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa" })
@EntityScan(basePackages = { "com.bernardomg.association.library.**.adapter.inbound.jpa",
        "com.bernardomg.association.member.adapter.inbound.jpa", "com.bernardomg.association.fee.adapter.inbound.jpa",
        "com.bernardomg.association.transaction.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa" })
public class TestConfiguration {

    @Bean("authorRepository")
    public AuthorRepository getAuthorRepository(final AuthorSpringRepository authorSpringRepository) {
        return new JpaAuthorRepository(authorSpringRepository);
    }

    @Bean("bookLendingRepository")
    public BookLendingRepository getBookLendingRepository(final BookLendingSpringRepository bookLendingSpringRepository,
            final BookSpringRepository bookSpringRepository, final ProfileSpringRepository profileSpringRepository) {
        return new JpaBookLendingRepository(bookLendingSpringRepository, bookSpringRepository, profileSpringRepository);
    }

    @Bean("bookRepository")
    public BookRepository getBookRepository(final BookSpringRepository bookSpringRepository,
            final MemberProfileSpringRepository memberProfileSpringRepository,
            final BookLendingSpringRepository bookLendingSpringRepository) {
        return new JpaBookRepository(bookSpringRepository, memberProfileSpringRepository, bookLendingSpringRepository);
    }

    @Bean("bookTypeRepository")
    public BookTypeRepository getBookTypeRepository(final BookTypeSpringRepository bookTypeSpringRepository) {
        return new JpaBookTypeRepository(bookTypeSpringRepository);
    }

    @Bean("fictionBookRepository")
    public FictionBookRepository getFictionBookRepository(final FictionBookSpringRepository bookSpringRepository,
            final AuthorSpringRepository authorSpringRepository,
            final PublisherSpringRepository publisherSpringRepository,
            final MemberProfileSpringRepository memberProfileSpringRepository,
            final ProfileSpringRepository profileSpringRepository,
            final BookLendingSpringRepository bookLendingSpringRepository) {
        return new JpaFictionBookRepository(bookSpringRepository, authorSpringRepository, publisherSpringRepository,
            memberProfileSpringRepository, profileSpringRepository, bookLendingSpringRepository);
    }

    @Bean("gameBookRepository")
    public GameBookRepository getGameBookRepository(final GameBookSpringRepository bookSpringRepository,
            final AuthorSpringRepository authorSpringRepository,
            final PublisherSpringRepository publisherSpringRepository,
            final BookTypeSpringRepository bookTypeSpringRepository,
            final GameSystemSpringRepository gameSystemSpringRepository,
            final MemberProfileSpringRepository memberProfileSpringRepository,
            final ProfileSpringRepository profileSpringRepository,
            final BookLendingSpringRepository bookLendingSpringRepository) {
        return new JpaGameBookRepository(bookSpringRepository, authorSpringRepository, publisherSpringRepository,
            bookTypeSpringRepository, gameSystemSpringRepository, memberProfileSpringRepository,
            profileSpringRepository, bookLendingSpringRepository);
    }

    @Bean("gameSystemRepository")
    public GameSystemRepository getGameSystemRepository(final GameSystemSpringRepository gameSystemRepository) {
        return new JpaGameSystemRepository(gameSystemRepository);
    }

    @Bean("publisherRepository")
    public PublisherRepository getPublisherRepository(final PublisherSpringRepository publisherSpringRepository) {
        return new JpaPublisherRepository(publisherSpringRepository);
    }

}
