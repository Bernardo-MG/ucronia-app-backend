
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.BorrowerNameResolver;
import com.bernardomg.association.library.book.usecase.service.DefaultExcelWorkbookGenerator;
import com.bernardomg.association.library.book.usecase.service.DefaultLibraryExcelWorkbookLoader;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.publisher.domain.model.Publisher;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultLibraryExcelWorkbookLoader")
class TestDefaultLibraryExcelWorkbookLoader {

    private static final String            DATE_FORMAT         = "dd/MM/yyyy";

    private static final DateTimeFormatter LENDING_DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_FORMAT)
        .withZone(ZoneId.systemDefault());

    private static void assertAllCellsWrapText(final Row row, final int firstColumn, final int cellCount) {
        for (int column = firstColumn; column < firstColumn + cellCount; column++) {
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

    private static void assertFictionRow(final Row row, final FictionBook book, final String expectedLendingStatus) {
        final Donation donation;

        donation = book.donation()
            .orElseThrow();

        assertThat(row.getCell(1)
            .getNumericCellValue()).isEqualTo(book.number());
        assertThat(row.getCell(2)
            .getStringCellValue()).isEqualTo(book.title()
                .fullTitle());
        assertThat(row.getCell(3)
            .getStringCellValue()).isEqualTo("Inglés");
        assertThat(row.getCell(4)
            .getStringCellValue()).isEqualTo(book.isbn());
        assertDateCell(row.getCell(5), Date.from(book.publishDate()
            .orElseThrow()));
        assertThat(row.getCell(6)
            .getStringCellValue()).isEqualTo(authorNames(book));
        assertThat(row.getCell(7)
            .getStringCellValue()).isEqualTo(publisherNames(book));
        assertThat(row.getCell(8)
            .getStringCellValue()).isEqualTo(donorNames(donation));
        assertDateCell(row.getCell(9), Date.from(donation.date()
            .orElseThrow()));
        assertThat(row.getCell(10)
            .getStringCellValue()).isEqualTo(expectedLendingStatus);

        assertThat(row.getPhysicalNumberOfCells()).isEqualTo(10);
        assertAllCellsWrapText(row, 1, 10);
    }

    private static void assertGameRow(final Row row, final GameBook book, final String expectedLendingStatus) {
        final Donation donation;

        donation = book.donation()
            .orElseThrow();

        assertThat(row.getCell(1)
            .getNumericCellValue()).isEqualTo(book.number());
        assertThat(row.getCell(2)
            .getStringCellValue()).isEqualTo(book.title()
                .fullTitle());
        assertThat(row.getCell(3)
            .getStringCellValue()).isEqualTo("Inglés");
        assertThat(row.getCell(4)
            .getStringCellValue()).isEqualTo(book.isbn());
        assertDateCell(row.getCell(5), Date.from(book.publishDate()
            .orElseThrow()));
        assertThat(row.getCell(6)
            .getStringCellValue()).isEqualTo(book.gameSystem()
                .map(GameSystem::name)
                .orElse(""));
        assertThat(row.getCell(7)
            .getStringCellValue()).isEqualTo(book.bookType()
                .map(BookType::name)
                .orElse(""));
        assertThat(row.getCell(8)
            .getStringCellValue()).isEqualTo(authorNames(book));
        assertThat(row.getCell(9)
            .getStringCellValue()).isEqualTo(publisherNames(book));
        assertThat(row.getCell(10)
            .getStringCellValue()).isEqualTo(donorNames(donation));
        assertDateCell(row.getCell(11), Date.from(donation.date()
            .orElseThrow()));
        assertThat(row.getCell(12)
            .getStringCellValue()).isEqualTo(expectedLendingStatus);

        assertThat(row.getPhysicalNumberOfCells()).isEqualTo(12);
        assertAllCellsWrapText(row, 1, 12);
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
    private DefaultLibraryExcelWorkbookLoader loader;

    @Mock
    private BorrowerNameResolver              borrowerNameResolver;

    @Test
    @DisplayName("A borrower name resolver is required")
    void testConstructorRejectsNullRepository() {
        final BorrowerNameResolver repository;
        final ThrowingCallable  callable;

        // GIVEN
        repository = null;

        // WHEN
        callable = () -> new DefaultLibraryExcelWorkbookLoader(repository);

        // THEN
        assertThatThrownBy(callable).isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Empty collections leave all sheets without data rows")
    @SuppressWarnings("resource")
    void testLoadWorkbookWithNoBooks() {
        final Workbook workbook;

        // GIVEN
        workbook = new DefaultExcelWorkbookGenerator().generateWorkbook();

        // WHEN
        loader.loadWorkbook(workbook, List.of(), List.of());

        // THEN
        assertThat(workbook.getSheetAt(0)
            .getRow(3)
            .getPhysicalNumberOfCells()).isZero();
        assertThat(workbook.getSheetAt(1)
            .getRow(3)
            .getPhysicalNumberOfCells()).isZero();
        assertThat(workbook.getSheetAt(2)
            .getRow(3)
            .getPhysicalNumberOfCells()).isZero();
        assertThat(workbook.getSheetAt(3)
            .getRow(3)
            .getPhysicalNumberOfCells()).isZero();
    }

    @Test
    @DisplayName("Lent books show the borrower, lending date and number of days")
    @SuppressWarnings("resource")
    void testLoadWorkbookWritesActiveLendingStatus() {
        final FictionBook     availableFiction;
        final GameBook        availableGame;
        final FictionBook     lentFiction;
        final GameBook        lentGame;
        final BookLendingInfo lending;
        final Workbook        workbook;
        final String          expectedStatus;

        // GIVEN
        availableFiction = FictionBooks.full();
        availableGame = GameBooks.full();

        lending = new BookLendingInfo(42L, Instant.parse("2026-08-01T10:00:00Z"), Optional.empty());

        lentFiction = new FictionBook(availableFiction.number(), availableFiction.title(), availableFiction.isbn(),
            availableFiction.language(), availableFiction.publishDate(), true, availableFiction.authors(),
            List.of(lending), availableFiction.publishers(), availableFiction.donation(), availableFiction.audit());

        lentGame = new GameBook(availableGame.number(), availableGame.title(), availableGame.isbn(),
            availableGame.language(), availableGame.publishDate(), true, availableGame.authors(), List.of(lending),
            availableGame.publishers(), availableGame.donation(), availableGame.bookType(), availableGame.gameSystem(),
            availableGame.audit());

        given(borrowerNameResolver.getBorrowerName(42L)).willReturn("Ana García");

        expectedStatus = String.format("Ana García (%s) %d días", LENDING_DATE_FORMAT.format(lending.lendingDate()),
            lending.getDays());

        workbook = new DefaultExcelWorkbookGenerator().generateWorkbook();

        // WHEN
        loader.loadWorkbook(workbook, List.of(lentGame), List.of(lentFiction));

        // THEN
        assertThat(workbook.getSheet("Juegos")
            .getRow(3)
            .getCell(12)
            .getStringCellValue()).isEqualTo(expectedStatus);

        assertThat(workbook.getSheet("Ficción")
            .getRow(3)
            .getCell(10)
            .getStringCellValue()).isEqualTo(expectedStatus);
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
            .getRow(3), gameBook, "Disponible");
        assertFictionRow(workbook.getSheetAt(1)
            .getRow(3), fictionBook, "Disponible");
    }

}
