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

package com.bernardomg.association.contact.adapter.inbound.jpa.specification;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.domain.filter.ContactQuery;

public final class ContactSpecifications {

    public static Optional<Specification<ContactEntity>> filter(final ContactQuery filter) {
        final Optional<Specification<ContactEntity>> nameSpec;

        if (filter.name()
            .isBlank()) {
            nameSpec = Optional.empty();
        } else {
            nameSpec = Optional.of(name(filter.name()));
        }

        return nameSpec;
    }

    /**
     * Name, surname of combination of both. Accepting partial matching.
     *
     * @param pattern
     *            pattern to match
     * @return name specification
     */
    private static Specification<ContactEntity> name(final String pattern) {
        final String likePattern = "%" + pattern + "%";
        return (root, query, cb) -> cb.or(cb.like(cb.lower(root.get("firstName")), likePattern.toLowerCase()),
            cb.like(cb.lower(root.get("lastName")), likePattern.toLowerCase()),
            cb.like(cb.lower(cb.concat(root.get("firstName"), cb.concat(" ", root.get("lastName")))),
                likePattern.toLowerCase()));
    }

    private ContactSpecifications() {
        super();
    }

}
