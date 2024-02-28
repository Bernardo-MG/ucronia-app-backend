
package com.bernardomg.association.library.usecase.service;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.bernardomg.association.library.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.domain.model.BookType;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultBookTypeService implements BookTypeService {

    private final BookTypeRepository bookTypeRepository;

    public DefaultBookTypeService(final BookTypeRepository bookTypeRepo) {
        super();

        bookTypeRepository = bookTypeRepo;
    }

    @Override
    public final BookType create(final BookType type) {
        log.debug("Creating book type {}", type);

        return bookTypeRepository.save(type);
    }

    @Override
    public final void delete(final String name) {

        log.debug("Deleting book type {}", name);

        if (!bookTypeRepository.exists(name)) {
            throw new MissingBookTypeException(name);
        }

        bookTypeRepository.delete(name);
    }

    @Override
    public final Iterable<BookType> getAll(final Pageable pageable) {
        return bookTypeRepository.findAll(pageable);
    }

    @Override
    public final Optional<BookType> getOne(final String name) {
        final Optional<BookType> bookType;

        log.debug("Reading book type {}", name);

        bookType = bookTypeRepository.findOne(name);
        if (bookType.isEmpty()) {
            throw new MissingBookTypeException(name);
        }

        return bookType;
    }

}