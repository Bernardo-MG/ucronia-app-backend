/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.domain.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.association.domain.model.PersistentExampleEntity;
import com.bernardomg.association.domain.repository.ExampleEntityRepository;
import com.bernardomg.association.pagination.model.PageIterable;
import com.bernardomg.association.pagination.model.Pagination;
import com.bernardomg.association.pagination.model.Sort;
import com.bernardomg.association.pagination.utils.Paginations;

/**
 * Default implementation of the example entity service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
public class DefaultExampleEntityService implements ExampleEntityService {

    /**
     * Repository for the domain entities handled by the service.
     */
    private final ExampleEntityRepository repository;

    /**
     * Constructs an entities service with the specified repository.
     *
     * @param repo
     *            the repository for the entity instances
     */
    @Autowired
    public DefaultExampleEntityService(final ExampleEntityRepository repo) {
        super();

        repository = Objects.requireNonNull(repo, "Received a null pointer as repository");
    }

    @Override
    public final PageIterable<PersistentExampleEntity> getAllEntities(final Pagination pagination, final Sort sort) {
        final Pageable                      pageable;
        final Page<PersistentExampleEntity> page;

        pageable = Paginations.toSpring(pagination, sort);

        page = repository.findAll(pageable);

        return Paginations.fromSpring(page);
    }

}
