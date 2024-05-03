
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.adapter.inbound.jpa.model.BookLendingEntity;
import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class JpaBookLendingRepository implements BookLendingRepository {

    private final BookLendingSpringRepository bookLendingSpringRepository;

    private final BookSpringRepository        bookSpringRepository;

    private final MemberSpringRepository      memberSpringRepository;

    public JpaBookLendingRepository(final BookLendingSpringRepository bookLendingSpringRepo,
            final BookSpringRepository bookSpringRepo, final MemberSpringRepository memberSpringRepo) {
        super();

        bookLendingSpringRepository = Objects.requireNonNull(bookLendingSpringRepo);
        bookSpringRepository = Objects.requireNonNull(bookSpringRepo);
        memberSpringRepository = Objects.requireNonNull(memberSpringRepo);
    }

    @Override
    public final Optional<BookLending> findOne(final long index, final long member) {
        final Optional<BookLending>  lending;
        final Optional<BookEntity>   bookEntity;
        final Optional<MemberEntity> memberEntity;

        log.debug("Finding book lending for book {} and member {}", index, member);

        bookEntity = bookSpringRepository.findOneByNumber(index);
        memberEntity = memberSpringRepository.findByNumber(member);

        if ((bookEntity.isPresent()) && (memberEntity.isPresent())) {
            lending = bookLendingSpringRepository.findOneByBookIdAndMemberId(bookEntity.get()
                .getId(),
                memberEntity.get()
                    .getId())
                .map(m -> toDomain(m, bookEntity.get(), memberEntity.get()));

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
        final Optional<MemberEntity> memberEntity;

        log.debug("Saving book lending {}", lending);

        bookEntity = bookSpringRepository.findOneByNumber(lending.getNumber());
        memberEntity = memberSpringRepository.findByNumber(lending.getMember());

        if ((bookEntity.isPresent()) && (memberEntity.isPresent())) {
            toCreate = toEntity(lending, bookEntity.get(), memberEntity.get());

            created = bookLendingSpringRepository.save(toCreate);

            saved = toDomain(created, bookEntity.get(), memberEntity.get());

            log.debug("Saved book lending {}", lending);
        } else {
            log.debug("Couldn't save book lending {}", lending);
            saved = null;
        }

        return saved;
    }

    private final BookLending toDomain(final BookLendingEntity entity, final BookEntity bookEntity,
            final MemberEntity memberEntity) {
        return BookLending.builder()
            .withNumber(bookEntity.getNumber())
            .withMember(memberEntity.getNumber())
            .withLendingDate(entity.getLendingDate())
            .withReturnDate(entity.getReturnDate())
            .build();
    }

    private final BookLendingEntity toEntity(final BookLending domain, final BookEntity bookEntity,
            final MemberEntity memberEntity) {
        return BookLendingEntity.builder()
            .withBookId(bookEntity.getId())
            .withMemberId(memberEntity.getId())
            .withLendingDate(domain.getLendingDate())
            .withReturnDate(domain.getReturnDate())
            .build();
    }

}
