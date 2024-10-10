
package com.bernardomg.association.library.author.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.author.domain.model.Author;

public interface AuthorRepository {

    public void delete(final Long number);

    public boolean exists(final Long number);

    public boolean existsByName(final String name);

    public Iterable<Author> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<Author> findOne(final Long number);

    public Author save(final Author author);

}
