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

package com.bernardomg.association.library.book.adapter.inbound.jpa.specification;

import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.library.book.adapter.inbound.jpa.model.GameBookEntity;
import com.bernardomg.association.library.book.domain.model.BookFilter;

import jakarta.persistence.criteria.Expression;

public final class GameBookSpecifications {

    public static Optional<Specification<GameBookEntity>> filter(final BookFilter filter) {
        final Optional<Specification<GameBookEntity>> titleSpec;

        if (filter.title()
            .isEmpty()) {
            titleSpec = Optional.empty();
        } else {
            titleSpec = Optional.of(title(filter.title()
                .get()));
        }

        return titleSpec;
    }

    /**
     * Name, surname of combination of both. Accepting partial matching.
     *
     * @param pattern
     *            pattern to match
     * @return name specification
     */
    private static Specification<GameBookEntity> title(final String pattern) {
        final String likePattern = "%" + pattern.toLowerCase() + "%";

        return (root, query, cb) -> {
            Expression<String> fullTitle = cb.concat(
                cb.concat(
                    root.get("subtitle"),
                    cb.literal(" ")
                ),
                cb.concat(
                    cb.concat(root.get("title"), cb.literal(" ")),
                    root.get("supertitle")
                )
            );

            return cb.like(cb.lower(fullTitle), likePattern);
        };
    }

    private GameBookSpecifications() {
        super();
    }

}
