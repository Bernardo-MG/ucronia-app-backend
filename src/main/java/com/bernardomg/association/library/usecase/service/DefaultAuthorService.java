
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.usecase.validation.CreateAuthorValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultAuthorService implements AuthorService {

    private final AuthorRepository      authorRepository;

    private final CreateAuthorValidator createAuthorValidator = new CreateAuthorValidator();

    public DefaultAuthorService(final AuthorRepository authorRepo) {
        super();

        authorRepository = authorRepo;
    }

    @Override
    public final Author create(final Author author) {
        log.debug("Creating author {}", author);

        createAuthorValidator.validate(author);

        return authorRepository.save(author);
    }

    @Override
    public final void delete(final String name) {

        log.debug("Deleting author {}", name);

        if (!authorRepository.exists(name)) {
            throw new MissingAuthorException(name);
        }

        authorRepository.delete(name);
    }

    @Override
    public final Iterable<Author> getAll(final Pageable pageable) {
        return authorRepository.getAll(pageable);
    }

    @Override
    public final Optional<Author> getOne(final String name) {
        final Optional<Author> author;

        log.debug("Reading author {}", name);

        author = authorRepository.getOne(name);
        if (author.isEmpty()) {
            throw new MissingAuthorException(name);
        }

        return author;
    }

}
