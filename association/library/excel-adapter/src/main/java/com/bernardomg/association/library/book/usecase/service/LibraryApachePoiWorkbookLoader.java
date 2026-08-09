
package com.bernardomg.association.library.book.usecase.service;

import java.util.Collection;

import org.apache.poi.ss.usermodel.Workbook;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;

public interface LibraryApachePoiWorkbookLoader {

    public void loadWorkbook(final Workbook workbook, final Collection<GameBook> gameBooks,
            final Collection<FictionBook> fictionBooks);

}
