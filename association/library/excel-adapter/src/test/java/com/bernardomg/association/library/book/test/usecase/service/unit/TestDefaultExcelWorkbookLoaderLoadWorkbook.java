
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultExcelWorkbookGenerator;
import com.bernardomg.association.library.book.usecase.service.DefaultExcelWorkbookLoader;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultExcelWorkbookLoader")
class TestDefaultExcelWorkbookLoaderLoadWorkbook {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    private static void assertAllCellsWrapText(final Row row, final int cellCount) {
        for (int column = 0; column < cellCount; column++) {
            assertThat(row.getCell(column)
                .getCellStyle()
                .getWrapText()).as("Cell %s should wrap text", column)
                    .isTrue();
        }
    }

    private static void assertDateCell(final Cell cell, final Date expected) {
        assertThat(cell.getDateCellValue()).isEqualTo(expected);
        assertThat(cell.getCellStyle()
            .getDataFormatString()).isEqualTo(DATE_FORMAT);
        assertThat(cell.getCellStyle()
            .getWrapText()).isTrue();
    }

    private static void assertFictionRow(final Row row, final FictionBook book) {
        final Donation donation;

        donation = book.donation()
            .orElseThrow();

        assertThat(row.getCell(0)
            .getNumericCellValue()).isEqualTo(book.number());
        assertThat(row.getCell(1)
            .getStringCellValue()).isEqualTo(book.title()
                .fullTitle());
        assertThat(row.getCell(2)
            .getStringCellValue()).isEqualTo(book.language());
        assertThat(row.getCell(3)
            .getStringCellValue()).isEqualTo(book.isbn());
        assertDateCell(row.getCell(4), Date.from(book.publishDate()
            .orElseThrow()));
        assertThat(row.getCell(5)
            .getStringCellValue()).isEqualTo(authorNames(book));
        assertThat(row.getCell(6)
            .getStringCellValue()).isEqualTo(publisherNames(book));
        assertThat(row.getCell(7)
            .getStringCellValue()).isEqualTo(donorNames(donation));
        assertDateCell(row.getCell(8), Date.from(donation.date()
            .orElseThrow()));
        assertThat(row.getCell(9)
            .getBooleanCellValue()).isEqualTo(book.lent());
        assertThat(row.getCell(10)).isNull();
        assertThat(row.getCell(11)).isNull();
        assertThat(row.getCell(12)).isNull();
        assertAllCellsWrapText(row, 10);
    }

    private static void assertGameRow(final Row row, final GameBook book) {
        final Donation donation;

        donation = book.donation()
            .orElseThrow();

        assertThat(row.getCell(0)
            .getNumericCellValue()).isEqualTo(book.number());
        assertThat(row.getCell(1)
            .getStringCellValue()).isEqualTo(book.title()
                .fullTitle());
        assertThat(row.getCell(2)
            .getStringCellValue()).isEqualTo(book.language());
        assertThat(row.getCell(3)
            .getStringCellValue()).isEqualTo(book.isbn());
        assertDateCell(row.getCell(4), Date.from(book.publishDate()
            .orElseThrow()));
        assertThat(row.getCell(5)
            .getStringCellValue()).isEqualTo(book.gameSystem()
                .map(GameSystem::name)
                .orElse(""));
        assertThat(row.getCell(6)
            .getStringCellValue()).isEqualTo(book.bookType()
                .map(BookType::name)
                .orElse(""));
        assertThat(row.getCell(7)
            .getStringCellValue()).isEqualTo(authorNames(book));
        assertThat(row.getCell(8)
            .getStringCellValue()).isEqualTo(publisherNames(book));
        assertThat(row.getCell(9)
            .getStringCellValue()).isEqualTo(donorNames(donation));
        assertDateCell(row.getCell(10), Date.from(donation.date()
            .orElseThrow()));
        assertThat(row.getCell(11)
            .getBooleanCellValue()).isEqualTo(book.lent());
        assertThat(row.getCell(12)).isNull();
        assertThat(row.getCell(13)).isNull();
        assertThat(row.getCell(14)).isNull();
        assertAllCellsWrapText(row, 12);
    }

    private static String authorNames(final FictionBook book) {
        return book.authors()
            .stream()
            .map(Author::name)
            .collect(Collectors.joining(", "));
    }

    private static String authorNames(final GameBook book) {
        return book.authors()
            .stream()
            .map(Author::name)
            .collect(Collectors.joining(", "));
    }

    private static String donorNames(final Donation donation) {
        return donation.donors()
            .stream()
            .map(Donor::name)
            .map(Donor.Name::fullName)
            .collect(Collectors.joining(", "));
    }

    private static String publisherNames(final FictionBook book) {
        return book.publishers()
            .stream()
            .map(Publisher::name)
            .collect(Collectors.joining(", "));
    }

    private static String publisherNames(final GameBook book) {
        return book.publishers()
            .stream()
            .map(Publisher::name)
            .collect(Collectors.joining(", "));
    }

    @InjectMocks
    private DefaultExcelWorkbookLoader loader;

    @Mock
    private ProfileRepository          profileRepository;

    @Test
    @DisplayName("A profile repository is required")
    void testConstructorRejectsNullRepository() {
        final ProfileRepository repository;
        final ThrowingCallable  callable;

        // GIVEN
        repository = null;

        // WHEN
        callable = () -> new DefaultExcelWorkbookLoader(repository);

        // THEN
        assertThatThrownBy(callable).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Empty collections leave both sheets without data rows")
    @SuppressWarnings("resource")
    void testLoadWorkbookWithNoBooks() {
        final Workbook workbook;

        // GIVEN
        workbook = new DefaultExcelWorkbookGenerator().generateWorkbook();

        // WHEN
        loader.loadWorkbook(workbook, List.of(), List.of());

        // THEN
        assertThat(workbook.getSheetAt(0)
            .getRow(1)).isNull();
        assertThat(workbook.getSheetAt(1)
            .getRow(1)).isNull();
    }

    @Test
    @DisplayName("Full game and fiction books are written to their sheets")
    @SuppressWarnings("resource")
    void testLoadWorkbookWritesBooks() {
        final GameBook    gameBook;
        final FictionBook fictionBook;
        final Workbook    workbook;

        // GIVEN
        gameBook = GameBooks.full();
        fictionBook = FictionBooks.full();
        workbook = new DefaultExcelWorkbookGenerator().generateWorkbook();

        // WHEN
        loader.loadWorkbook(workbook, List.of(gameBook), List.of(fictionBook));

        // THEN
        assertGameRow(workbook.getSheetAt(0)
            .getRow(1), gameBook);
        assertFictionRow(workbook.getSheetAt(1)
            .getRow(1), fictionBook);
    }

}
