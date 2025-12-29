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

package com.bernardomg.association.profile.adapter.inbound.jpa.specification;

import java.util.Optional;
import java.util.function.BiFunction;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.domain.filter.ProfileQuery;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public final class ContactSpecifications {

    public static Optional<Specification<ProfileEntity>> query(final ProfileQuery query) {
        final Optional<Specification<ProfileEntity>> nameSpec;

        if (query.name()
            .isBlank()) {
            nameSpec = Optional.empty();
        } else {
            nameSpec = Optional.of(name(query.name()));
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
    private static Specification<ProfileEntity> name(final String pattern) {
        final String                                                      likePattern;
        final BiFunction<Root<ProfileEntity>, CriteriaBuilder, Predicate> likeFirstName;
        final BiFunction<Root<ProfileEntity>, CriteriaBuilder, Predicate> likeLastName;
        final BiFunction<Root<ProfileEntity>, CriteriaBuilder, Predicate> likeFullName;

        likePattern = "%" + pattern.toLowerCase() + "%";
        likeFirstName = (root, cb) -> cb.like(cb.lower(root.get("firstName")), likePattern);
        likeLastName = (root, cb) -> cb.like(cb.lower(root.get("lastName")), likePattern);
        likeFullName = (root, cb) -> cb
            .like(cb.lower(cb.concat(root.get("firstName"), cb.concat(" ", root.get("lastName")))), likePattern);

        return (root, query, cb) -> cb.or(likeFirstName.apply(root, cb), likeLastName.apply(root, cb),
            likeFullName.apply(root, cb));
    }

    private ContactSpecifications() {
        super();
    }

}
