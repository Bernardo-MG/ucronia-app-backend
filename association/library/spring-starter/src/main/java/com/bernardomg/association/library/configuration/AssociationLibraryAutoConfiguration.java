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

package com.bernardomg.association.library.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.JpaAuthorRepository;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.usecase.service.AuthorService;
import com.bernardomg.association.library.author.usecase.service.DefaultAuthorService;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.BookSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.DonorSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.FictionBookSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.GameBookSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.JpaBookRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.JpaDonorRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.JpaFictionBookRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.JpaGameBookRepository;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.usecase.service.BookReportService;
import com.bernardomg.association.library.book.usecase.service.DefaultGameBookService;
import com.bernardomg.association.library.book.usecase.service.ExcelPoiBookReportService;
import com.bernardomg.association.library.book.usecase.service.GameBookService;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.JpaBookTypeRepository;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.usecase.service.BookTypeService;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.JpaGameSystemRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.usecase.service.DefaultGameSystemService;
import com.bernardomg.association.library.gamesystem.usecase.service.GameSystemService;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BorrowerSpringRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.JpaBookLendingRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.JpaBorrowerRepository;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.domain.repository.BorrowerRepository;
import com.bernardomg.association.library.lending.usecase.service.BookLendingService;
import com.bernardomg.association.library.lending.usecase.service.DefaultBookLendingService;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.JpaPublisherRepository;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.usecase.service.DefaultPublisherService;
import com.bernardomg.association.library.publisher.usecase.service.PublisherService;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.association.library.**.adapter.outbound.rest.controller",
        "com.bernardomg.association.library.**.adapter.inbound.jpa" })
public class AssociationLibraryAutoConfiguration {

    @Bean("authorRepository")
    public AuthorRepository getAuthorRepository(final AuthorSpringRepository authorSpringRepository) {
        return new JpaAuthorRepository(authorSpringRepository);
    }

    @Bean("authorService")
    public AuthorService getAuthorService(final AuthorRepository authorRepository) {
        return new DefaultAuthorService(authorRepository);
    }

    @Bean("bookLendingRepository")
    public BookLendingRepository getBookLendingRepository(final BookLendingSpringRepository bookLendingSpringRepository,
            final BookSpringRepository bookSpringRepository, final BorrowerSpringRepository borrowerSpringRepository) {
        return new JpaBookLendingRepository(bookLendingSpringRepository, bookSpringRepository,
            borrowerSpringRepository);
    }

    @Bean("bookLendingService")
    public BookLendingService getBookLendingService(final BookLendingRepository bookLendingRepository,
            final BookRepository bookRepository, final BorrowerRepository borrowerRepository) {
        return new DefaultBookLendingService(bookLendingRepository, bookRepository, borrowerRepository);
    }

    @Bean("bookReportService")
    public BookReportService getBookReportService(final GameBookRepository gameBookRepository,
            final FictionBookRepository fictionBookRepository) {
        return new ExcelPoiBookReportService(gameBookRepository, fictionBookRepository);
    }

    @Bean("bookRepository")
    public BookRepository getBookRepository(final BookSpringRepository bookSpringRepository,
            final BorrowerSpringRepository borrowerSpringRepository,
            final BookLendingSpringRepository bookLendingSpringRepository) {
        return new JpaBookRepository(bookSpringRepository, borrowerSpringRepository, bookLendingSpringRepository);
    }

    @Bean("bookTypeRepository")
    public BookTypeRepository getBookTypeRepository(final BookTypeSpringRepository bookTypeSpringRepository) {
        return new JpaBookTypeRepository(bookTypeSpringRepository);
    }

    @Bean("bookTypeService")
    public BookTypeService getBookTypeService(final BookTypeRepository bookTypeRepository) {
        return new DefaultBookTypeService(bookTypeRepository);
    }

    @Bean("borrowerRepository")
    public BorrowerRepository getBorrowerRepository(final BorrowerSpringRepository borrowerSpringRepository) {
        return new JpaBorrowerRepository(borrowerSpringRepository);
    }

    @Bean("donorRepository")
    public DonorRepository getDonorRepository(final DonorSpringRepository donorSpringRepository) {
        return new JpaDonorRepository(donorSpringRepository);
    }

    @Bean("fictionBookRepository")
    public FictionBookRepository getFictionBookRepository(final FictionBookSpringRepository bookSpringRepository,
            final AuthorSpringRepository authorSpringRepository,
            final PublisherSpringRepository publisherSpringRepository,
            final BorrowerSpringRepository borrowerSpringRepository, final DonorSpringRepository donorSpringRepository,
            final BookLendingSpringRepository bookLendingSpringRepository) {
        return new JpaFictionBookRepository(bookSpringRepository, authorSpringRepository, publisherSpringRepository,
            borrowerSpringRepository, donorSpringRepository, bookLendingSpringRepository);
    }

    @Bean("gameBookRepository")
    public GameBookRepository getGameBookRepository(final GameBookSpringRepository bookSpringRepository,
            final AuthorSpringRepository authorSpringRepository,
            final PublisherSpringRepository publisherSpringRepository,
            final BookTypeSpringRepository bookTypeSpringRepository,
            final GameSystemSpringRepository gameSystemSpringRepository,
            final BorrowerSpringRepository borrowerSpringRepository, final DonorSpringRepository donorSpringRepository,
            final BookLendingSpringRepository bookLendingSpringRepository) {
        return new JpaGameBookRepository(bookSpringRepository, authorSpringRepository, publisherSpringRepository,
            bookTypeSpringRepository, gameSystemSpringRepository, borrowerSpringRepository, donorSpringRepository,
            bookLendingSpringRepository);
    }

    @Bean("gameBookService")
    public GameBookService getGameBookService(final GameBookRepository bookRepository,
            final AuthorRepository authorRepository, final PublisherRepository publisherRepository,
            final BookTypeRepository bookTypeRepository, final GameSystemRepository gameSystemRepository,
            final DonorRepository donorRepository) {
        return new DefaultGameBookService(bookRepository, authorRepository, publisherRepository, bookTypeRepository,
            gameSystemRepository, donorRepository);
    }

    @Bean("gameSystemRepository")
    public GameSystemRepository getGameSystemRepository(final GameSystemSpringRepository gameSystemRepository) {
        return new JpaGameSystemRepository(gameSystemRepository);
    }

    @Bean("gameSystemService")
    public GameSystemService getGameSystemService(final GameSystemRepository gameSystemRepository) {
        return new DefaultGameSystemService(gameSystemRepository);
    }

    @Bean("publisherRepository")
    public PublisherRepository getPublisherRepository(final PublisherSpringRepository publisherSpringRepository) {
        return new JpaPublisherRepository(publisherSpringRepository);
    }

    @Bean("publisherService")
    public PublisherService getPublisherService(final PublisherRepository publisherRepository) {
        return new DefaultPublisherService(publisherRepository);
    }

}
