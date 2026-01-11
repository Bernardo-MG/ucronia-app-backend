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

package com.bernardomg.association.sponsor.adapter.inbound.jpa.specification;

import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.guest.adapter.inbound.jpa.model.GuestEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public final class SponsorSpecifications {

    public static Optional<Specification<SponsorEntity>> query(final SponsorFilter filter) {
        final Optional<Specification<SponsorEntity>> nameSpec;
        final Specification<SponsorEntity>           spec;

        if (filter.name()
            .isBlank()) {
            nameSpec = Optional.empty();
        } else {
            nameSpec = Optional.of(name(filter.name()));
        }

        spec = List.of(nameSpec)
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .reduce((BinaryOperator<Specification<SponsorEntity>>) Specification::and)
            .orElse(null);
        return Optional.ofNullable(spec);
    }

    /**
     * Name, surname of combination of both. Accepting partial matching.
     *
     * @param pattern
     *            pattern to match
     * @return name specification
     */
    private static Specification<SponsorEntity> name(final String pattern) {
        final String likePattern = "%" + pattern.toLowerCase() + "%";

        return (root, query, cb) -> {
            final Join<GuestEntity, ProfileEntity> profile = root.join("profile", JoinType.INNER);

            return cb.or(cb.like(cb.lower(profile.get("firstName")), likePattern),
                cb.like(cb.lower(profile.get("lastName")), likePattern),
                cb.like(cb.lower(cb.concat(profile.get("firstName"), cb.concat(" ", profile.get("lastName")))),
                    likePattern));
        };
    }

    private SponsorSpecifications() {
        super();
    }

}
