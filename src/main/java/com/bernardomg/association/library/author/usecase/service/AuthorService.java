
package com.bernardomg.association.library.author.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.author.domain.model.Author;

public interface AuthorService {

    public Author create(final Author author);

    public void delete(final Long number);

    public Iterable<Author> getAll(final Pageable pageable);

    public Optional<Author> getOne(final Long number);

    public Author update(final Author author);

}
