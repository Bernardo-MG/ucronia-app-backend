
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookTypeEntity;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;

public final class JpaBookTypeRepository implements BookTypeRepository {

    private final BookTypeSpringRepository bookTypeSpringRepository;

    public JpaBookTypeRepository(final BookTypeSpringRepository bookTypeSpringRepo) {
        super();

        bookTypeSpringRepository = bookTypeSpringRepo;
    }

    @Override
    public final boolean exists(final String name) {
        return bookTypeSpringRepository.existsByName(name);
    }

    @Override
    public final Iterable<BookType> findAll(final Pageable pageable) {
        return bookTypeSpringRepository.findAll(pageable)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final BookType save(final BookType book) {
        final BookTypeEntity toCreate;
        final BookTypeEntity created;

        toCreate = toEntity(book);
        created = bookTypeSpringRepository.save(toCreate);

        return toDomain(created);
    }

    private final BookType toDomain(final BookTypeEntity entity) {
        return BookType.builder()
            .withName(entity.getName())
            .build();
    }

    private final BookTypeEntity toEntity(final BookType domain) {
        return BookTypeEntity.builder()
            .withName(domain.getName())
            .build();
    }

}
