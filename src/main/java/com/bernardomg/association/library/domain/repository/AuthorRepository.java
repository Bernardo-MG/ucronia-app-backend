
package com.bernardomg.association.library.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Author;

public interface AuthorRepository {

    public boolean exists(final String name);

    public Iterable<Author> findAll(final Pageable pageable);

    public Optional<Author> findOne(final String name);

    public Author save(final Author author);

}
