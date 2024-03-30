
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.model.Author;

public interface AuthorService {

    public Author create(final Author author);

    public void delete(final String name);

    public Iterable<Author> getAll(final Pageable pageable);

    public Optional<Author> getOne(final String name);

}
