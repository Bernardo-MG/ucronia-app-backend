
package com.bernardomg.association.library.book.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.FictionBookEntity;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.RootBookEntity;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.data.springframework.SpringSorting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@Transactional
public final class JpaFictionBookRepository implements FictionBookRepository {

    private final AuthorSpringRepository      authorSpringRepository;

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final RootBookSpringRepository    bookSpringRepository;

    private final PersonSpringRepository      personSpringRepository;

    private final PublisherSpringRepository   publisherSpringRepository;

    public JpaFictionBookRepository(final RootBookSpringRepository bookSpringRepo,
            final AuthorSpringRepository authorSpringRepo, final PublisherSpringRepository publisherSpringRepo,
            final PersonSpringRepository personSpringRepo, final BookLendingSpringRepository bookLendingSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        authorSpringRepository = Objects.requireNonNull(authorSpringRepo);
        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
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
    public final boolean existsByIsbnForAnother(final long number, final String isbn) {
        final boolean exists;

        log.debug("Checking if book with ISBN {} and number not {} exists", isbn, number);

        exists = bookSpringRepository.existsByIsbnAndNumberNot(isbn, number);

        log.debug("Book with ISBN {} and number not {} exists: {}", isbn, number, exists);

        return exists;
    }

    @Override
    public final Iterable<FictionBook> findAll(final Pagination pagination, final Sorting sorting) {
        final Iterable<FictionBook> read;
        final Pageable              pageable;

        log.debug("Finding books with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = bookSpringRepository.findAll(pageable)
            .map(this::toDomain);

        log.debug("Found books {}", read);

        return read;
    }

    @Override
    public final Collection<FictionBook> findAll(final Sorting sorting) {
        final Collection<FictionBook> read;
        final Sort                    sort;

        log.debug("Finding books with sorting {}", sorting);

        sort = SpringSorting.toSort(sorting);
        read = bookSpringRepository.findAll(sort)
            .stream()
            .map(this::toDomain)
            .toList();

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
    public final Optional<FictionBook> findOne(final long number) {
        final Optional<FictionBook> book;

        log.debug("Finding book {}", number);

        book = bookSpringRepository.findByNumber(number)
            .map(this::toDomain);

        log.debug("Found book {}: {}", number, book);

        return book;
    }

    @Override
    public final FictionBook save(final FictionBook book) {
        final Optional<RootBookEntity> existing;
        final FictionBookEntity        entity;
        final FictionBookEntity        created;
        final FictionBook              saved;

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

    private final FictionBook toDomain(final FictionBookEntity entity) {
        final Collection<Publisher>   publishers;
        final Collection<Donor>       donors;
        final Collection<Author>      authors;
        final boolean                 lent;
        final Collection<BookLending> lendings;
        final Title                   title;
        final String                  supertitle;
        final String                  subtitle;
        final Optional<Donation>      donation;

        // Publishers
        if (entity.getPublishers() == null) {
            publishers = List.of();
        } else {
            publishers = entity.getPublishers()
                .stream()
                .map(this::toDomain)
                .toList();
        }

        // Authors
        if (entity.getAuthors() == null) {
            authors = List.of();
        } else {
            authors = entity.getAuthors()
                .stream()
                .map(this::toDomain)
                .toList();
        }

        // Donation
        if (entity.getDonors() == null) {
            donors = List.of();
        } else {
            donors = entity.getDonors()
                .stream()
                .map(this::toDonorDomain)
                .toList();
        }
        if ((entity.getDonationDate() != null) && (!donors.isEmpty())) {
            donation = Optional.of(new Donation(entity.getDonationDate(), donors));
        } else if (entity.getDonationDate() != null) {
            donation = Optional.of(new Donation(entity.getDonationDate(), List.of()));
        } else if (!donors.isEmpty()) {
            donation = Optional.of(new Donation(null, donors));
        } else {
            donation = Optional.empty();
        }

        // Lendings
        lendings = bookLendingSpringRepository.findAllByBookId(entity.getId())
            .stream()
            .map(l -> toDomain(entity, l))
            .toList();

        if (entity.getSupertitle() == null) {
            supertitle = "";
        } else {
            supertitle = entity.getSupertitle();
        }
        if (entity.getSubtitle() == null) {
            subtitle = "";
        } else {
            subtitle = entity.getSubtitle();
        }
        title = new Title(supertitle, entity.getTitle(), subtitle);

        lent = bookSpringRepository.isLent(entity.getId());
        return FictionBook.builder()
            .withNumber(entity.getNumber())
            .withIsbn(entity.getIsbn())
            .withTitle(title)
            .withLanguage(entity.getLanguage())
            .withPublishDate(entity.getPublishDate())
            .withAuthors(authors)
            .withPublishers(publishers)
            .withDonation(donation)
            .withLent(lent)
            .withLendings(lendings)
            .build();
    }

    private final BookLending toDomain(final FictionBookEntity bookEntity, final BookLendingEntity entity) {
        final Optional<Borrower> borrower;
        final LentBook           lentBook;
        final Title              title;

        // TODO: should not contain all the member data
        borrower = personSpringRepository.findById(entity.getPersonId())
            .map(this::toDomain);
        title = new Title(bookEntity.getSupertitle(), bookEntity.getTitle(), bookEntity.getSubtitle());
        lentBook = new LentBook(bookEntity.getNumber(), title);
        return new BookLending(lentBook, borrower.get(), entity.getLendingDate(), entity.getReturnDate());
    }

    private final Borrower toDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new Borrower(entity.getNumber(), name);
    }

    private final Publisher toDomain(final PublisherEntity entity) {
        return new Publisher(entity.getNumber(), entity.getName());
    }

    private final FictionBook toDomain(final RootBookEntity entity) {
        final FictionBook book;

        if (entity instanceof FictionBookEntity) {
            book = toDomain((FictionBookEntity) entity);
        } else {
            book = null;
        }

        return book;
    }

    private final Donor toDonorDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new Donor(entity.getNumber(), name);
    }

    private final FictionBookEntity toEntity(final FictionBook domain) {
        final Collection<Long>            authorNumbers;
        final Collection<Long>            publisherNumbers;
        final Collection<Long>            donorNumbers;
        final Collection<PublisherEntity> publishers;
        final Collection<PersonEntity>    donors;
        final Collection<AuthorEntity>    authors;

        // Publishers
        publisherNumbers = domain.publishers()
            .stream()
            .map(Publisher::number)
            .toList();
        publishers = publisherSpringRepository.findAllByNumberIn(publisherNumbers);

        // Donors
        if (domain.donation()
            .isPresent()) {
            donorNumbers = domain.donation()
                .get()
                .donors()
                .stream()
                .map(Donor::number)
                .toList();
            donors = personSpringRepository.findAllByNumberIn(donorNumbers);
        } else {
            donors = List.of();
        }

        // Authors
        authorNumbers = domain.authors()
            .stream()
            .map(Author::number)
            .toList();
        authors = authorSpringRepository.findAllByNumberIn(authorNumbers);

        return FictionBookEntity.builder()
            .withNumber(domain.number())
            .withIsbn(domain.isbn())
            .withSupertitle(domain.title()
                .supertitle())
            .withTitle(domain.title()
                .title())
            .withSubtitle(domain.title()
                .subtitle())
            .withLanguage(domain.language())
            .withPublishDate(domain.publishDate())
            .withDonationDate(domain.donation()
                .map(Donation::date)
                .orElse(null))
            .withAuthors(authors)
            .withPublishers(publishers)
            .withDonors(donors)
            .build();
    }

}
