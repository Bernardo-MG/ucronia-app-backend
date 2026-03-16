
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
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
import com.bernardomg.data.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExcelPoiBookReportService - get report")
class TestExcelPoiBookReportServiceGetReport {

    @Mock
    private FictionBookRepository     fictionBookRepository;

    @Mock
    private GameBookRepository        gameBookRepository;

    @InjectMocks
    private ExcelPoiBookReportService service;

    @Test
    @DisplayName("When there are books, an Excel file is generated")
    @SuppressWarnings("resource")
    void testGetReport() throws Exception {
        final GameBook              gameBook;
        final FictionBook           fictionBook;
        final ByteArrayOutputStream report;
        final Workbook              workbook;
        final Sheet                 gamesSheet;
        final Sheet                 fictionSheet;

        // GIVEN
        gameBook = GameBooks.full();
        fictionBook = FictionBooks.full();

        given(gameBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(gameBook));

        given(fictionBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(fictionBook));

        // WHEN
        report = service.getReport();

        workbook = new XSSFWorkbook(new ByteArrayInputStream(report.toByteArray()));

        gamesSheet = workbook.getSheetAt(0);
        fictionSheet = workbook.getSheetAt(1);

        // THEN
        Assertions.assertThat(gamesSheet.getPhysicalNumberOfRows())
            .as("game rows")
            .isEqualTo(2);

        Assertions.assertThat(fictionSheet.getPhysicalNumberOfRows())
            .as("fiction rows")
            .isEqualTo(2);
    }

    @Test
    @DisplayName("When there are books with donation without date, an Excel file is generated")
    @SuppressWarnings("resource")
    void testGetReport_DonationNoDate() throws Exception {
        final GameBook              gameBook;
        final FictionBook           fictionBook;
        final ByteArrayOutputStream report;
        final Workbook              workbook;
        final Sheet                 gamesSheet;
        final Sheet                 fictionSheet;

        // GIVEN
        gameBook = GameBooks.donationNoDate();
        fictionBook = FictionBooks.donationNoDate();

        given(gameBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(gameBook));

        given(fictionBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(fictionBook));

        // WHEN
        report = service.getReport();

        workbook = new XSSFWorkbook(new ByteArrayInputStream(report.toByteArray()));

        gamesSheet = workbook.getSheetAt(0);
        fictionSheet = workbook.getSheetAt(1);

        // THEN
        Assertions.assertThat(gamesSheet.getPhysicalNumberOfRows())
            .as("game rows")
            .isEqualTo(2);

        Assertions.assertThat(fictionSheet.getPhysicalNumberOfRows())
            .as("fiction rows")
            .isEqualTo(2);
    }

    @Test
    @DisplayName("Generated Excel contains correct headers")
    @SuppressWarnings("resource")
    void testGetReport_Headers() throws Exception {
        final ByteArrayOutputStream report;
        final Workbook              workbook;
        final Sheet                 gamesSheet;
        final Row                   header;

        // GIVEN
        given(gameBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of());

        given(fictionBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of());

        // WHEN
        report = service.getReport();

        workbook = new XSSFWorkbook(new ByteArrayInputStream(report.toByteArray()));
        gamesSheet = workbook.getSheetAt(0);
        header = gamesSheet.getRow(0);

        // THEN
        Assertions.assertThat(header.getCell(0)
            .getStringCellValue())
            .as("header number")
            .isEqualTo("Número");

        Assertions.assertThat(header.getCell(1)
            .getStringCellValue())
            .as("header title")
            .isEqualTo("Título completo");

        Assertions.assertThat(header.getCell(2)
            .getStringCellValue())
            .as("header language")
            .isEqualTo("Idioma");
    }

    @Test
    @DisplayName("When there are lent books, an Excel file is generated")
    @SuppressWarnings("resource")
    void testGetReport_Lent() throws Exception {
        final GameBook              gameBook;
        final FictionBook           fictionBook;
        final ByteArrayOutputStream report;
        final Workbook              workbook;
        final Sheet                 gamesSheet;
        final Sheet                 fictionSheet;

        // GIVEN
        gameBook = GameBooks.lent();
        fictionBook = FictionBooks.lent();

        given(gameBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(gameBook));

        given(fictionBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(fictionBook));

        // WHEN
        report = service.getReport();

        workbook = new XSSFWorkbook(new ByteArrayInputStream(report.toByteArray()));

        gamesSheet = workbook.getSheetAt(0);
        fictionSheet = workbook.getSheetAt(1);

        // THEN
        Assertions.assertThat(gamesSheet.getPhysicalNumberOfRows())
            .as("game rows")
            .isEqualTo(2);

        Assertions.assertThat(fictionSheet.getPhysicalNumberOfRows())
            .as("fiction rows")
            .isEqualTo(2);
    }

    @Test
    @DisplayName("When there are books with minimal data, an Excel file is generated")
    @SuppressWarnings("resource")
    void testGetReport_Minimal() throws Exception {
        final GameBook              gameBook;
        final FictionBook           fictionBook;
        final ByteArrayOutputStream report;
        final Workbook              workbook;
        final Sheet                 gamesSheet;
        final Sheet                 fictionSheet;

        // GIVEN
        gameBook = GameBooks.minimal();
        fictionBook = FictionBooks.minimal();

        given(gameBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(gameBook));

        given(fictionBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of(fictionBook));

        // WHEN
        report = service.getReport();

        workbook = new XSSFWorkbook(new ByteArrayInputStream(report.toByteArray()));

        gamesSheet = workbook.getSheetAt(0);
        fictionSheet = workbook.getSheetAt(1);

        // THEN
        Assertions.assertThat(gamesSheet.getPhysicalNumberOfRows())
            .as("game rows")
            .isEqualTo(2);

        Assertions.assertThat(fictionSheet.getPhysicalNumberOfRows())
            .as("fiction rows")
            .isEqualTo(2);
    }

    @Test
    @DisplayName("When there are no books, only the headers are generated")
    @SuppressWarnings("resource")
    void testGetReport_NoData() throws Exception {
        final ByteArrayOutputStream report;
        final Workbook              workbook;
        final Sheet                 gamesSheet;
        final Sheet                 fictionSheet;

        // GIVEN
        given(gameBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of());

        given(fictionBookRepository.findAll(Sorting.asc("title", "language", "isbn"))).willReturn(List.of());

        // WHEN
        report = service.getReport();

        workbook = new XSSFWorkbook(new ByteArrayInputStream(report.toByteArray()));

        gamesSheet = workbook.getSheetAt(0);
        fictionSheet = workbook.getSheetAt(1);

        // THEN
        Assertions.assertThat(gamesSheet.getPhysicalNumberOfRows())
            .as("games rows")
            .isEqualTo(1);

        Assertions.assertThat(fictionSheet.getPhysicalNumberOfRows())
            .as("fiction rows")
            .isEqualTo(1);
    }

}
