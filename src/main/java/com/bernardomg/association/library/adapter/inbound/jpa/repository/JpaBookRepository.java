
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.model.GameSystem;
import com.bernardomg.association.library.domain.model.Publisher;
import com.bernardomg.association.library.domain.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaBookRepository implements BookRepository {

    private final AuthorSpringRepository     authorSpringRepository;

    private final BookSpringRepository       bookSpringRepository;

    private final BookTypeSpringRepository   bookTypeSpringRepository;

    private final GameSystemSpringRepository gameSystemSpringRepository;

    private final PublisherSpringRepository  publisherSpringRepository;

    public JpaBookRepository(final BookSpringRepository bookSpringRepo, final AuthorSpringRepository authorSpringRepo,
            final PublisherSpringRepository publisherSpringRepo, final BookTypeSpringRepository bookTypeSpringRepo,
            final GameSystemSpringRepository gameSystemSpringRepo) {
        super();

        bookSpringRepository = bookSpringRepo;
        authorSpringRepository = authorSpringRepo;
        publisherSpringRepository = publisherSpringRepo;
        bookTypeSpringRepository = bookTypeSpringRepo;
        gameSystemSpringRepository = gameSystemSpringRepo;
    }

    @Override
    public final void delete(final long index) {
        log.debug("Deleting book {}", index);

        bookSpringRepository.deleteByNumber(index);

        log.debug("Deleted book {}", index);
    }

    @Override
    public final boolean exists(final long index) {
        final boolean exists;

        log.debug("Checking if book {} exists", index);

        exists = bookSpringRepository.existsByNumber(index);

        log.debug("Book {} exists: {}", index, exists);

        return exists;
    }

    @Override
    public final boolean existsByIsbn(final Long number, final String isbn) {
        final boolean exists;

        log.debug("Checking if book with ISBN {} and number not {} exists", isbn, number);

        exists = bookSpringRepository.existsByIsbnAndNumberNot(isbn, number);

        log.debug("Book with ISBN {} and number not {} exists: {}", isbn, number, exists);

        return exists;
    }

    @Override
    public final boolean existsByIsbn(final String isbn) {
        final boolean exists;

        log.debug("Checking if book with ISBN {} exists", isbn);

        exists = bookSpringRepository.existsByIsbn(isbn);

        log.debug("Book with ISBN {} exists: {}", isbn, exists);

        return exists;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next index for the books");

        number = bookSpringRepository.findNextNumber();

        log.debug("Found index {}", number);

        return number;
    }

    @Override
    public final Iterable<Book> getAll(final Pageable pageable) {
        final Page<BookEntity> page;
        final Iterable<Book>   read;

        log.debug("Finding books with pagination {}", pageable);

        page = bookSpringRepository.findAll(pageable);

        read = page.map(this::toDomain);

        log.debug("Found books {}", read);

        return read;
    }

    @Override
    public final Optional<Book> getOne(final long index) {
        final Optional<Book> book;

        log.debug("Finding book {}", index);

        book = bookSpringRepository.findOneByNumber(index)
            .map(this::toDomain);

        log.debug("Found book {}: {}", index, book);

        return book;
    }

    @Override
    public final Book save(final Book book) {
        final Optional<BookEntity> existing;
        final BookEntity           entity;
        final BookEntity           created;
        final Book                 saved;

        log.debug("Saving book {}", book);

        entity = toEntity(book);

        existing = bookSpringRepository.findOneByNumber(book.getNumber());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = bookSpringRepository.save(entity);

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
        final Publisher          publisher;
        final GameSystem         gameSystem;
        final BookType           bookType;
        final Collection<Author> authors;

        if (entity.getPublisher() == null) {
            publisher = Publisher.builder()
                .build();
        } else {
            publisher = toDomain(entity.getPublisher());
        }
        if (entity.getGameSystem() == null) {
            gameSystem = GameSystem.builder()
                .build();
        } else {
            gameSystem = toDomain(entity.getGameSystem());
        }
        if (entity.getBookType() == null) {
            bookType = BookType.builder()
                .build();
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
            .withNumber(entity.getNumber())
            .withIsbn(entity.getIsbn())
            .withTitle(entity.getTitle())
            .withLanguage(entity.getLanguage())
            .withAuthors(authors)
            .withPublisher(publisher)
            .withGameSystem(gameSystem)
            .withBookType(bookType)
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

    private final Publisher toDomain(final PublisherEntity entity) {
        return Publisher.builder()
            .withName(entity.getName())
            .build();
    }

    private final BookEntity toEntity(final Book domain) {
        final Collection<String>         authorNames;
        final Optional<PublisherEntity>  publisher;
        final Optional<BookTypeEntity>   bookType;
        final Optional<GameSystemEntity> gameSystem;
        final Collection<AuthorEntity>   authors;

        if (domain.getPublisher() == null) {
            publisher = Optional.empty();
        } else {
            publisher = publisherSpringRepository.findOneByName(domain.getPublisher()
                .getName());
        }
        if (domain.getBookType() == null) {
            bookType = Optional.empty();
        } else {
            bookType = bookTypeSpringRepository.findOneByName(domain.getBookType()
                .getName());
        }
        if (domain.getGameSystem() == null) {
            gameSystem = Optional.empty();
        } else {
            gameSystem = gameSystemSpringRepository.findOneByName(domain.getGameSystem()
                .getName());
        }

        authorNames = domain.getAuthors()
            .stream()
            .map(Author::getName)
            .toList();
        authors = authorSpringRepository.findAllByNameIn(authorNames);

        return BookEntity.builder()
            .withNumber(domain.getNumber())
            .withIsbn(domain.getIsbn())
            .withTitle(domain.getTitle())
            .withLanguage(domain.getLanguage())
            .withAuthors(authors)
            .withPublisher(publisher.orElse(null))
            .withBookType(bookType.orElse(null))
            .withGameSystem(gameSystem.orElse(null))
            .build();
    }

}
