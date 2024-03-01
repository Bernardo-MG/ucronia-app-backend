
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
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
    public final void delete(final String isbn) {
        log.debug("Deleting book {}", isbn);

        bookSpringRepository.deleteByIsbn(isbn);

        log.debug("Deleted book {}", isbn);
    }

    @Override
    public final boolean exists(final String isbn) {
        final boolean exists;

        log.debug("Checking if book {} exists", isbn);

        exists = bookSpringRepository.existsByIsbn(isbn);

        log.debug("Book {} exists: {}", isbn, exists);

        return exists;
    }

    @Override
    public final Iterable<Book> findAll(final Pageable pageable) {
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

    private final Author toDomain(final AuthorEntity entity) {
        return Author.builder()
            .withName(entity.getName())
            .build();
    }

    private final Book toDomain(final BookEntity entity) {
        final GameSystem         gameSystem;
        final BookType           bookType;
        final Collection<Author> authors;

        if (entity.getGameSystem() == null) {
            gameSystem = null;
        } else {
            gameSystem = toDomain(entity.getGameSystem());
        }
        if (entity.getBookType() == null) {
            bookType = null;
        } else {
            bookType = toDomain(entity.getBookType());
        }
        if (entity.getAuthors() == null) {
            authors = List.of();
        } else {
            authors = entity.getAuthors()
                .stream()
                .map(this::toDomain)
                .toList();
        }
        return Book.builder()
            .withIsbn(entity.getIsbn())
            .withTitle(entity.getTitle())
            .withLanguage(entity.getLanguage())
            .withGameSystem(gameSystem)
            .withBookType(bookType)
            .withAuthors(authors)
            .build();
    }

    private final BookType toDomain(final BookTypeEntity entity) {
        return BookType.builder()
            .withName(entity.getName())
            .build();
    }

    private final GameSystem toDomain(final GameSystemEntity entity) {
        return GameSystem.builder()
            .withName(entity.getName())
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
