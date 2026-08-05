
package com.bernardomg.association.library.book.usecase.service;

import org.apache.poi.ss.usermodel.Workbook;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;

public interface LibraryExcelWorkbookLoader {

    public void loadWorkbook(final Workbook workbook, final Iterable<GameBook> gameBooks,
            final Iterable<FictionBook> fictionBooks);

}
