
package com.bernardomg.association.library.book.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.model.GameSystemEntity;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookBookLending;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaBookRepository implements BookRepository {

    private final AuthorSpringRepository      authorSpringRepository;

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final BookSpringRepository        bookSpringRepository;

    private final BookTypeSpringRepository    bookTypeSpringRepository;

    private final GameSystemSpringRepository  gameSystemSpringRepository;

    private final PersonSpringRepository      personSpringRepository;

    private final PublisherSpringRepository   publisherSpringRepository;

    public JpaBookRepository(final BookSpringRepository bookSpringRepo, final AuthorSpringRepository authorSpringRepo,
            final PublisherSpringRepository publisherSpringRepo, final BookTypeSpringRepository bookTypeSpringRepo,
            final GameSystemSpringRepository gameSystemSpringRepo, final PersonSpringRepository personSpringRepo,
            final BookLendingSpringRepository bookLendingSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        authorSpringRepository = Objects.requireNonNull(authorSpringRepo);
        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
        bookTypeSpringRepository = Objects.requireNonNull(bookTypeSpringRepo);
        gameSystemSpringRepository = Objects.requireNonNull(gameSystemSpringRepo);
        personSpringRepository = Objects.requireNonNull(personSpringRepo);
        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
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
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the books");

        number = bookSpringRepository.findNextNumber();

        log.debug("Found number {}", number);

        return number;
    }

    @Override
    public final Optional<Book> findOne(final long number) {
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

        existing = bookSpringRepository.findByNumber(book.number());
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
        return new Author(entity.getNumber(), entity.getName());
    }

    private final Book toDomain(final BookEntity entity) {
        final Collection<Publisher>       publishers;
        final Optional<GameSystem>        gameSystem;
        final Optional<BookType>          bookType;
        final Collection<Donor>           donors;
        final Collection<Author>          authors;
        final boolean                     lent;
        final Collection<BookBookLending> lendings;

        if (entity.getGameSystem() == null) {
            gameSystem = Optional.empty();
        } else {
            gameSystem = Optional.of(toDomain(entity.getGameSystem()));
        }

        if (entity.getBookType() == null) {
            bookType = Optional.empty();
        } else {
            bookType = Optional.of(toDomain(entity.getBookType()));
        }

        if (entity.getPublishers() == null) {
            publishers = List.of();
        } else {
            publishers = entity.getPublishers()
                .stream()
                .map(this::toDomain)
                .toList();
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
                .map(this::toDonorDomain)
                .toList();
        }

        lendings = bookLendingSpringRepository.findAllByBookId(entity.getId())
            .stream()
            .map(this::toDomain)
            .toList();

        lent = bookSpringRepository.isLent(entity.getId());
        return Book.builder()
            .withNumber(entity.getNumber())
            .withIsbn(entity.getIsbn())
            .withTitle(entity.getTitle())
            .withLanguage(entity.getLanguage())
            .withAuthors(authors)
            .withPublishers(publishers)
            .withGameSystem(gameSystem)
            .withBookType(bookType)
            .withDonors(donors)
            .withLent(lent)
            .withLendings(lendings)
            .build();
    }

    private final BookBookLending toDomain(final BookLendingEntity entity) {
        final Optional<Person> person;

        person = personSpringRepository.findById(entity.getPersonId())
            .map(this::toDomain);
        return new BookBookLending(person.orElse(new Person(null, null, null, null)), entity.getLendingDate(),
            entity.getReturnDate());
    }

    private final BookType toDomain(final BookTypeEntity entity) {
        return new BookType(entity.getNumber(), entity.getName());
    }

    private final GameSystem toDomain(final GameSystemEntity entity) {
        return new GameSystem(entity.getNumber(), entity.getName());
    }

    private final Person toDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new Person(entity.getIdentifier(), entity.getNumber(), name, entity.getPhone());
    }

    private final Publisher toDomain(final PublisherEntity entity) {
        return new Publisher(entity.getNumber(), entity.getName());
    }

    private final Donor toDonorDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new Donor(entity.getNumber(), name);
    }

    private final BookEntity toEntity(final Book domain) {
        final Collection<Long>            authorNumbers;
        final Collection<Long>            publisherNumbers;
        final Collection<Long>            donorNumbers;
        final Collection<PublisherEntity> publishers;
        final Optional<BookTypeEntity>    bookType;
        final Optional<GameSystemEntity>  gameSystem;
        final Collection<PersonEntity>    donors;
        final Collection<AuthorEntity>    authors;

        if (domain.bookType()
            .isPresent()) {
            bookType = bookTypeSpringRepository.findByNumber(domain.bookType()
                .get()
                .number());
        } else {
            bookType = Optional.empty();
        }
        if (domain.gameSystem()
            .isPresent()) {
            gameSystem = gameSystemSpringRepository.findByNumber(domain.gameSystem()
                .get()
                .number());
        } else {
            gameSystem = Optional.empty();
        }

        publisherNumbers = domain.publishers()
            .stream()
            .map(Publisher::number)
            .toList();
        publishers = publisherSpringRepository.findAllByNumberIn(publisherNumbers);

        donorNumbers = domain.donors()
            .stream()
            .map(Donor::number)
            .toList();
        donors = personSpringRepository.findAllByNumberIn(donorNumbers);

        authorNumbers = domain.authors()
            .stream()
            .map(Author::number)
            .toList();
        authors = authorSpringRepository.findAllByNumberIn(authorNumbers);

        return BookEntity.builder()
            .withNumber(domain.number())
            .withIsbn(domain.isbn())
            .withTitle(domain.title())
            .withLanguage(domain.language())
            .withBookType(bookType.orElse(null))
            .withGameSystem(gameSystem.orElse(null))
            .withAuthors(authors)
            .withPublishers(publishers)
            .withDonors(donors)
            .build();
    }

}
