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

package com.bernardomg.association.member.adapter.inbound.jpa.specification;

import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.member.adapter.inbound.jpa.model.PublicMemberEntity;
import com.bernardomg.association.member.domain.filter.PublicMemberFilter;

public final class PublicMemberSpecifications {

    public static Optional<Specification<PublicMemberEntity>> query(final PublicMemberFilter filter) {
        final Optional<Specification<PublicMemberEntity>> nameSpec;
        final Optional<Specification<PublicMemberEntity>> activeSpec;
        final Specification<PublicMemberEntity>           spec;

        if (filter.name()
            .isBlank()) {
            nameSpec = Optional.empty();
        } else {
            nameSpec = Optional.of(name(filter.name()));
        }

        activeSpec = Optional.of(active());

        spec = List.of(nameSpec, activeSpec)
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce((BinaryOperator<Specification<PublicMemberEntity>>) Specification::and)
            .orElse(null);
        return Optional.ofNullable(spec);
    }

    private static Specification<PublicMemberEntity> active() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    /**
     * Name, surname of combination of both. Accepting partial matching.
     *
     * @param pattern
     *            pattern to match
     * @return name specification
     */
    private static Specification<PublicMemberEntity> name(final String pattern) {
        final String likePattern = "%" + pattern + "%";
        return (root, query, cb) -> cb.or(cb.like(cb.lower(root.get("firstName")), likePattern.toLowerCase()),
            cb.like(cb.lower(root.get("lastName")), likePattern.toLowerCase()),
            cb.like(cb.lower(cb.concat(root.get("firstName"), cb.concat(" ", root.get("lastName")))),
                likePattern.toLowerCase()));
    }

    private PublicMemberSpecifications() {
        super();
    }

}
