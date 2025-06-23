
package com.bernardomg.association.library.book.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.PersonName;

@Repository
@Transactional
public final class JpaBookRepository implements BookRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaBookRepository.class);

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final BookSpringRepository        bookSpringRepository;

    private final PersonSpringRepository      personSpringRepository;

    public JpaBookRepository(final BookSpringRepository bookSpringRepo, final PersonSpringRepository personSpringRepo,
            final BookLendingSpringRepository bookLendingSpringRepo) {
        super();

        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        personSpringRepository = Objects.requireNonNull(personSpringRepo);
        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
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

    private final Author toDomain(final AuthorEntity entity) {
        return new Author(entity.getNumber(), entity.getName());
    }

    private final Book toDomain(final BookEntity entity) {
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
        return new Book(entity.getNumber(), title, entity.getIsbn(), entity.getLanguage(), entity.getPublishDate(),
            lent, authors, lendings, publishers, donation);
    }

    private final BookLending toDomain(final BookEntity bookEntity, final BookLendingEntity entity) {
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

    private final Donor toDonorDomain(final PersonEntity entity) {
        final PersonName name;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        return new Donor(entity.getNumber(), name);
    }

}
