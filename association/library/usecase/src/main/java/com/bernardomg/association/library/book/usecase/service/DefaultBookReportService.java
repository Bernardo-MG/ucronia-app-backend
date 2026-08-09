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

package com.bernardomg.association.library.book.usecase.service;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.usecase.generator.ReportGenerator;
import com.bernardomg.pagination.domain.Sorting;

import jakarta.transaction.Transactional;

@Transactional
public final class DefaultBookReportService implements BookReportService {

    /**
     * Logger for the class.
     */
    private static final Logger         log = LoggerFactory.getLogger(DefaultBookReportService.class);

    private final FictionBookRepository fictionBookRepository;

    private final GameBookRepository    gameBookRepository;

    private final ReportGenerator       reportGenerator;

    public DefaultBookReportService(final GameBookRepository gameBookRepo, final FictionBookRepository fictionBookRepo,
            final ReportGenerator reportGen) {
        super();

        gameBookRepository = Objects.requireNonNull(gameBookRepo);
        fictionBookRepository = Objects.requireNonNull(fictionBookRepo);
        reportGenerator = Objects.requireNonNull(reportGen);
    }

    @Override
    public final ByteArrayOutputStream getReport() {
        final Collection<GameBook>    gameBooks;
        final Collection<FictionBook> fictionBooks;
        final Sorting                 sort;

        log.debug("Creating report");

        sort = Sorting.asc("title", "language", "isbn");
        gameBooks = gameBookRepository.findAll(sort);
        fictionBooks = fictionBookRepository.findAll(sort);

        return reportGenerator.getReport(gameBooks, fictionBooks);
    }

}
