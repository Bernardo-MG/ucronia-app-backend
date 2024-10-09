
package com.bernardomg.association.library.author.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.author.domain.model.Author;

public interface AuthorRepository {

    public void delete(final String name);

    public boolean exists(final String name);

    public Iterable<Author> findAll(final Pageable pageable);

    public long findNextNumber();

    public Optional<Author> findOne(final String name);

    public boolean hasRelationships(final String name);

    public Author save(final Author author);

}
