
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.ApachePoiReportGenerator;
import com.bernardomg.association.library.book.usecase.service.ApachePoiWorkbookGenerator;
import com.bernardomg.association.library.book.usecase.service.LibraryApachePoiWorkbookLoader;

@ExtendWith(MockitoExtension.class)
@DisplayName("ApachePoiReportGenerator")
class TestApachePoiReportGenerator {

    @Mock
    private ApachePoiWorkbookGenerator     excelGenerator;

    @InjectMocks
    private ApachePoiReportGenerator       generator;

    @Mock
    private LibraryApachePoiWorkbookLoader workbookLoader;

    @Test
    @DisplayName("The generated workbook is loaded and returned as an Excel stream")
    void testGetReport() throws IOException {
        final List<GameBook>        gameBooks;
        final List<FictionBook>     fictionBooks;
        final Workbook              workbook;
        final ByteArrayOutputStream result;

        // GIVEN
        gameBooks = List.of(GameBooks.full());
        fictionBooks = List.of(FictionBooks.full());
        workbook = new XSSFWorkbook();

        given(excelGenerator.generateWorkbook()).willReturn(workbook);

        // WHEN
        result = generator.getReport(gameBooks, fictionBooks);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.size()).isPositive();
    }

}
