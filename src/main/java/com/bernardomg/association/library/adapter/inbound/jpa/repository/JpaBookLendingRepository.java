
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.PersonSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaBookLendingRepository implements BookLendingRepository {

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final BookSpringRepository        bookSpringRepository;

    private final PersonSpringRepository      personSpringRepository;

    public JpaBookLendingRepository(final BookLendingSpringRepository bookLendingSpringRepo,
            final BookSpringRepository bookSpringRepo, final PersonSpringRepository personSpringRepo) {
        super();

        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        personSpringRepository = Objects.requireNonNull(personSpringRepo);
    }

    @Override
    public final Optional<BookLending> findOne(final long index, final long member) {
        final Optional<BookLending>  lending;
        final Optional<BookEntity>   bookEntity;
        final Optional<PersonEntity> personEntity;

        log.debug("Finding book lending for book {} and member {}", index, member);

        bookEntity = bookSpringRepository.findByNumber(index);
        personEntity = personSpringRepository.findByNumber(member);

        if ((bookEntity.isPresent()) && (personEntity.isPresent())) {
            lending = bookLendingSpringRepository.findByBookIdAndPersonId(bookEntity.get()
                .getId(),
                personEntity.get()
                    .getId())
                .map(m -> toDomain(m, bookEntity.get(), personEntity.get()));

            log.debug("Found book lending for book {} and member {}: {}", index, member, lending);
        } else {
            log.debug("No book lending found for book {} and member {}:", index, member);
            lending = Optional.empty();
        }

        return lending;
    }

    @Override
    public final BookLending save(final BookLending lending) {
        final BookLendingEntity      toCreate;
        final BookLendingEntity      created;
        final BookLending            saved;
        final Optional<BookEntity>   bookEntity;
        final Optional<PersonEntity> personEntity;

        log.debug("Saving book lending {}", lending);

        bookEntity = bookSpringRepository.findByNumber(lending.getNumber());
        personEntity = personSpringRepository.findByNumber(lending.getMember());

        if ((bookEntity.isPresent()) && (personEntity.isPresent())) {
            toCreate = toEntity(lending, bookEntity.get(), personEntity.get());

            created = bookLendingSpringRepository.save(toCreate);

            saved = toDomain(created, bookEntity.get(), personEntity.get());

            log.debug("Saved book lending {}", lending);
        } else {
            log.debug("Couldn't save book lending {}", lending);
            saved = null;
        }

        return saved;
    }

    private final BookLending toDomain(final BookLendingEntity entity, final BookEntity bookEntity,
            final PersonEntity personEntity) {
        return BookLending.builder()
            .withNumber(bookEntity.getNumber())
            .withMember(personEntity.getNumber())
            .withLendingDate(entity.getLendingDate())
            .withReturnDate(entity.getReturnDate())
            .build();
    }

    private final BookLendingEntity toEntity(final BookLending domain, final BookEntity bookEntity,
            final PersonEntity personEntity) {
        return BookLendingEntity.builder()
            .withBookId(bookEntity.getId())
            .withPersonId(personEntity.getId())
            .withLendingDate(domain.getLendingDate())
            .withReturnDate(domain.getReturnDate())
            .build();
    }

}
