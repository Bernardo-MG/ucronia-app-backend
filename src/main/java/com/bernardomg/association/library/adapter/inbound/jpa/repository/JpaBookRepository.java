
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaBookRepository implements BookRepository {

    private final BookSpringRepository bookSpringRepository;

    public JpaBookRepository(final BookSpringRepository bookSpringRepo) {
        super();

        bookSpringRepository = bookSpringRepo;
    }

    @Override
    public Iterable<Book> findAll(final Pageable pageable) {
        final Page<BookEntity> page;
        final Iterable<Book>   read;

        log.debug("Finding books with pagination {}", pageable);

        page = bookSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found books {}", read);

        return read;
    }

    @Override
    public final Optional<Book> findOne(final String isbn) {
        final Optional<Book> book;

        log.debug("Finding book with isbn {}", isbn);

        book = bookSpringRepository.findOneByIsbn(isbn)
            .map(this::toDomain);

        log.debug("Found book with isbn {}: {}", isbn, book);

        return book;
    }

    @Override
    public final Book save(final Book book) {
        final BookEntity toCreate;
        final BookEntity created;
        final Book       saved;

        log.debug("Saving book {}", book);

        toCreate = toEntity(book);

        created = bookSpringRepository.save(toCreate);

        saved = toDomain(created);

        log.debug("Saved book {}", saved);

        return saved;
    }

    private final Book toDomain(final BookEntity entity) {
        return Book.builder()
            .withIsbn(entity.getIsbn())
            .withTitle(entity.getTitle())
            .withLanguage(entity.getLanguage())
            .build();
    }

    private final BookEntity toEntity(final Book domain) {
        return BookEntity.builder()
            .withIsbn(domain.getIsbn())
            .withTitle(domain.getTitle())
            .withLanguage(domain.getLanguage())
            .build();
    }

}
