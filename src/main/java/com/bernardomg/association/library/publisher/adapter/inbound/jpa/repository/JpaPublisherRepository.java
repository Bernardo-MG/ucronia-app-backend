/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntityMapper;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;

@Service
@Transactional
public final class JpaPublisherRepository implements PublisherRepository {

    /**
     * Logger for the class.
     */
    private static final Logger             log = LoggerFactory.getLogger(JpaPublisherRepository.class);

    private final PublisherSpringRepository publisherSpringRepository;

    public JpaPublisherRepository(final PublisherSpringRepository publisherSpringRepo) {
        super();

        publisherSpringRepository = Objects.requireNonNull(publisherSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting publisher {}", number);

        publisherSpringRepository.deleteByNumber(number);

        log.debug("Deleted publisher {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if publisher {} exists", number);

        exists = publisherSpringRepository.existsByNumber(number);

        log.debug("Publisher {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final boolean existsByName(final String name) {
        final boolean exists;

        log.debug("Checking if publisher {} exists", name);

        exists = publisherSpringRepository.existsByName(name);

        log.debug("Publisher {} exists: {}", name, exists);

        return exists;
    }

    @Override
    public final boolean existsByNameForAnother(final String name, final Long number) {
        final boolean exists;

        log.debug("Checking if publisher {} exists for a publisher distinct from {}", name, number);

        exists = publisherSpringRepository.existsByNotNumberAndName(number, name);

        log.debug("Publisher {} exists for a publisher distinct from {}: {}", name, number, exists);

        return exists;
    }

    @Override
    public final Page<Publisher> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Publisher> read;
        final Pageable                                        pageable;

        log.debug("Finding publishers with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = publisherSpringRepository.findAll(pageable)
            .map(PublisherEntityMapper::toDomain);

        log.debug("Found publishers {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the publishers");

        number = publisherSpringRepository.findNextNumber();

        log.debug("Found next number for the publishers: {}", number);

        return number;
    }

    @Override
    public final Optional<Publisher> findOne(final long number) {
        final Optional<Publisher> publisher;

        log.debug("Finding publisher with name {}", number);

        publisher = publisherSpringRepository.findByNumber(number)
            .map(PublisherEntityMapper::toDomain);

        log.debug("Found publisher with name {}: {}", number, publisher);

        return publisher;
    }

    @Override
    public final Publisher save(final Publisher publisher) {
        final Optional<PublisherEntity> existing;
        final PublisherEntity           entity;
        final PublisherEntity           created;
        final Publisher                 saved;

        log.debug("Saving publisher {}", publisher);

        entity = PublisherEntityMapper.toEntity(publisher);

        existing = publisherSpringRepository.findByNumber(publisher.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = publisherSpringRepository.save(entity);
        saved = PublisherEntityMapper.toDomain(created);

        log.debug("Saved publisher {}", saved);

        return saved;
    }

}
