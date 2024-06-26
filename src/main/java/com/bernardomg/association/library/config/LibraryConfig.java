/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.JpaAuthorRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.JpaBookLendingRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.JpaBookRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.JpaBookTypeRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.JpaGameSystemRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.JpaPublisherRepository;
import com.bernardomg.association.library.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.domain.repository.BookRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.domain.repository.PublisherRepository;
import com.bernardomg.association.library.usecase.service.AuthorService;
import com.bernardomg.association.library.usecase.service.BookLendingService;
import com.bernardomg.association.library.usecase.service.BookService;
import com.bernardomg.association.library.usecase.service.BookTypeService;
import com.bernardomg.association.library.usecase.service.DefaultAuthorService;
import com.bernardomg.association.library.usecase.service.DefaultBookLendingService;
import com.bernardomg.association.library.usecase.service.DefaultBookService;
import com.bernardomg.association.library.usecase.service.DefaultBookTypeService;
import com.bernardomg.association.library.usecase.service.DefaultGameSystemService;
import com.bernardomg.association.library.usecase.service.DefaultPublisherService;
import com.bernardomg.association.library.usecase.service.GameSystemService;
import com.bernardomg.association.library.usecase.service.PublisherService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;

/**
 * Library configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class LibraryConfig {

    public LibraryConfig() {
        super();
    }

    @Bean("authorRepository")
    public AuthorRepository getAuthorRepository(final AuthorSpringRepository authorSpringRepo) {
        return new JpaAuthorRepository(authorSpringRepo);
    }

    @Bean("authorService")
    public AuthorService getAuthorService(final AuthorRepository authorRepo) {
        return new DefaultAuthorService(authorRepo);
    }

    @Bean("bookLendingRepository")
    public BookLendingRepository getBookLendingRepository(final BookLendingSpringRepository bookLendingSpringRepo,
            final BookSpringRepository bookSpringRepo, final PersonSpringRepository personSpringRepo) {
        return new JpaBookLendingRepository(bookLendingSpringRepo, bookSpringRepo, personSpringRepo);
    }

    @Bean("bookLendingService")
    public BookLendingService getBookLendingService(final BookLendingRepository bookLendingRepo,
            final BookRepository bookRepo, final MemberRepository memberRepo) {
        return new DefaultBookLendingService(bookLendingRepo, bookRepo, memberRepo);
    }

    @Bean("bookRepository")
    public BookRepository getBookRepository(final BookSpringRepository bookSpringRepo,
            final AuthorSpringRepository authorSpringRepo, final PublisherSpringRepository publisherSpringRepository,
            final BookTypeSpringRepository bookTypeSpringRepo, final GameSystemSpringRepository gameSystemSpringRepo,
            final PersonSpringRepository personSpringRepo) {
        return new JpaBookRepository(bookSpringRepo, authorSpringRepo, publisherSpringRepository, bookTypeSpringRepo,
            gameSystemSpringRepo, personSpringRepo);
    }

    @Bean("bookService")
    public BookService getBookService(final BookRepository bookRepo, final AuthorRepository authorRepo,
            final PublisherRepository publisherRepo, final BookTypeRepository bookTypeRepo,
            final GameSystemRepository gameSystemRepo, final DonorRepository donorRepo) {
        return new DefaultBookService(bookRepo, authorRepo, publisherRepo, bookTypeRepo, gameSystemRepo, donorRepo);
    }

    @Bean("bookTypeRepository")
    public BookTypeRepository getBookTypeRepository(final BookTypeSpringRepository bookTypeSpringRepo) {
        return new JpaBookTypeRepository(bookTypeSpringRepo);
    }

    @Bean("bookTypeService")
    public BookTypeService getBookTypeService(final BookTypeRepository bookTypeRepo) {
        return new DefaultBookTypeService(bookTypeRepo);
    }

    @Bean("gameSystemRepository")
    public GameSystemRepository getGameSystemRepository(final GameSystemSpringRepository gameSystemRepo) {
        return new JpaGameSystemRepository(gameSystemRepo);
    }

    @Bean("gameSystemService")
    public GameSystemService getGameSystemService(final GameSystemRepository gameSystemRepo) {
        return new DefaultGameSystemService(gameSystemRepo);
    }

    @Bean("publisherRepository")
    public PublisherRepository getPublisherRepository(final PublisherSpringRepository publisherSpringRepo) {
        return new JpaPublisherRepository(publisherSpringRepo);
    }

    @Bean("publisherService")
    public PublisherService getPublisherService(final PublisherRepository publisherRepository) {
        return new DefaultPublisherService(publisherRepository);
    }

}
