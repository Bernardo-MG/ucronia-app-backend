
package com.bernardomg.association.library.adapter.inbound.jpa.repository;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.adapter.inbound.jpa.model.BookEntity;
import com.bernardomg.association.library.domain.model.Book;
import com.bernardomg.association.library.domain.repository.BookRepository;

public final class JpaBookRepository implements BookRepository {

    private final BookSpringRepository bookSpringRepository;

    public JpaBookRepository(final BookSpringRepository bookSpringRepo) {
        super();

        bookSpringRepository = bookSpringRepo;
    }

    @Override
    public Iterable<Book> findAll(final Pageable pageable) {
        return bookSpringRepository.findAll(pageable)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Book save(final Book book) {
        final BookEntity toCreate;
        final BookEntity created;

        toCreate = toEntity(book);
        created = bookSpringRepository.save(toCreate);

        return toDomain(created);
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
