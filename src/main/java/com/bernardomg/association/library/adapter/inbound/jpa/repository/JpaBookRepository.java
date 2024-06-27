
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.model.DonorName;
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
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaBookRepository implements BookRepository {

    private final AuthorSpringRepository     authorSpringRepository;

    private final BookSpringRepository       bookSpringRepository;

    private final BookTypeSpringRepository   bookTypeSpringRepository;

    private final GameSystemSpringRepository gameSystemSpringRepository;

    private final PersonSpringRepository     personSpringRepository;

    private final PublisherSpringRepository  publisherSpringRepository;

    public JpaBookRepository(final BookSpringRepository bookSpringRepo, final AuthorSpringRepository authorSpringRepo,
            final PublisherSpringRepository publisherSpringRepo, final BookTypeSpringRepository bookTypeSpringRepo,
            final GameSystemSpringRepository gameSystemSpringRepo, final PersonSpringRepository personSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        authorSpringRepository = Objects.requireNonNull(authorSpringRepo);
        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
        bookTypeSpringRepository = Objects.requireNonNull(bookTypeSpringRepo);
        gameSystemSpringRepository = Objects.requireNonNull(gameSystemSpringRepo);
        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting book {}", number);

        bookSpringRepository.deleteByNumber(number);

        log.debug("Deleted book {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if book {} exists", number);

        exists = bookSpringRepository.existsByNumber(number);

        log.debug("Book {} exists: {}", number, exists);

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
    public final boolean existsByIsbnForAnother(final Long number, final String isbn) {
        final boolean exists;

        log.debug("Checking if book with ISBN {} and number not {} exists", isbn, number);

        exists = bookSpringRepository.existsByIsbnAndNumberNot(isbn, number);

        log.debug("Book with ISBN {} and number not {} exists: {}", isbn, number, exists);

        return exists;
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the books");

        number = bookSpringRepository.findNextNumber();

        log.debug("Found number {}", number);

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
    public final Optional<Book> getOne(final long number) {
        final Optional<Book> book;

        log.debug("Finding book {}", number);

        book = bookSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found book {}: {}", number, book);

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

        existing = bookSpringRepository.findByNumber(book.getNumber());
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
        final Collection<Donor>  donors;
        final Collection<Author> authors;
        final boolean            lent;

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
        if (entity.getDonors() == null) {
            donors = List.of();
        } else {
            donors = entity.getDonors()
                .stream()
                .map(this::toDomain)
                .toList();
        }

        lent = bookSpringRepository.isLent(entity.getId());
        return Book.builder()
            .withNumber(entity.getNumber())
            .withIsbn(entity.getIsbn())
            .withTitle(entity.getTitle())
            .withLanguage(entity.getLanguage())
            .withAuthors(authors)
            .withPublisher(publisher)
            .withGameSystem(gameSystem)
            .withBookType(bookType)
            .withDonors(donors)
            .withLent(lent)
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

    private final Donor toDomain(final PersonEntity entity) {
        final DonorName name;

        name = DonorName.builder()
            .withFirstName(entity.getFirstName())
            .withLastName(entity.getLastName())
            .build();
        return Donor.builder()
            .withNumber(entity.getNumber())
            .withName(name)
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
        final Collection<PersonEntity>   donors;
        final Collection<AuthorEntity>   authors;

        if (domain.getPublisher() == null) {
            publisher = Optional.empty();
        } else {
            publisher = publisherSpringRepository.findByName(domain.getPublisher()
                .getName());
        }
        if (domain.getBookType() == null) {
            bookType = Optional.empty();
        } else {
            bookType = bookTypeSpringRepository.findByName(domain.getBookType()
                .getName());
        }
        if (domain.getGameSystem() == null) {
            gameSystem = Optional.empty();
        } else {
            gameSystem = gameSystemSpringRepository.findByName(domain.getGameSystem()
                .getName());
        }
        if (domain.getDonors() == null) {
            donors = List.of();
        } else {
            donors = domain.getDonors()
                .stream()
                .map(Donor::getNumber)
                .map(personSpringRepository::findByNumber)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
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
            .withDonors(donors)
            .build();
    }

}
