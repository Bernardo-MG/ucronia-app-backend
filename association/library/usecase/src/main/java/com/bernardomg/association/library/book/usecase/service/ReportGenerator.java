
package com.bernardomg.association.library.book.usecase.service;

import java.io.ByteArrayOutputStream;
import java.util.Collection;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;

public interface ReportGenerator {

    public ByteArrayOutputStream getReport(final Collection<GameBook> gameBooks,
            final Collection<FictionBook> fictionBooks);

}
