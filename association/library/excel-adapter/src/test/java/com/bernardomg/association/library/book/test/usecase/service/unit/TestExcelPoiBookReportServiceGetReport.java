
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.io.ByteArrayOutputStream;
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
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.ExcelPoiBookReportService;
import com.bernardomg.association.library.book.usecase.service.ExcelWorkbookGenerator;
import com.bernardomg.association.library.book.usecase.service.ExcelWorkbookLoader;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExcelPoiBookReportService")
class TestExcelPoiBookReportServiceGetReport {

    @Mock
    private ExcelWorkbookGenerator    excelGenerator;

    @Mock
    private FictionBookRepository     fictionBookRepository;

    @Mock
    private GameBookRepository        gameBookRepository;

    @InjectMocks
    private ExcelPoiBookReportService service;

    @Mock
    private ExcelWorkbookLoader       workbookLoader;

    @Test
    @DisplayName("The generated workbook is loaded with the books")
    void testGetReport() {
        final GameBook              gameBook;
        final FictionBook           fictionBook;
        final List<GameBook>        gameBooks;
        final List<FictionBook>     fictionBooks;
        final Workbook              workbook;
        final ByteArrayOutputStream report;

        // GIVEN
        gameBook = GameBooks.full();
        fictionBook = FictionBooks.full();
        gameBooks = List.of(gameBook);
        fictionBooks = List.of(fictionBook);
        workbook = new XSSFWorkbook();

        given(excelGenerator.generateWorkbook()).willReturn(workbook);
        given(gameBookRepository.findAll(any())).willReturn(gameBooks);
        given(fictionBookRepository.findAll(any())).willReturn(fictionBooks);

        // WHEN
        report = service.getReport();

        // THEN
        assertThat(report).isNotNull()
            .extracting(ByteArrayOutputStream::size)
            .isNotEqualTo(0);

        then(workbookLoader).should()
            .loadWorkbook(workbook, gameBooks, fictionBooks);
    }

    @Test
    @DisplayName("An empty workbook is generated when there are no books")
    void testGetReport_NoData() {
        final List<GameBook>        gameBooks;
        final List<FictionBook>     fictionBooks;
        final Workbook              workbook;
        final ByteArrayOutputStream report;

        // GIVEN
        gameBooks = List.of();
        fictionBooks = List.of();
        workbook = new XSSFWorkbook();

        given(excelGenerator.generateWorkbook()).willReturn(workbook);
        given(gameBookRepository.findAll(any())).willReturn(gameBooks);
        given(fictionBookRepository.findAll(any())).willReturn(fictionBooks);

        // WHEN
        report = service.getReport();

        // THEN
        assertThat(report).isNotNull()
            .extracting(ByteArrayOutputStream::size)
            .isNotEqualTo(0);

        then(workbookLoader).should()
            .loadWorkbook(workbook, gameBooks, fictionBooks);
    }

}
