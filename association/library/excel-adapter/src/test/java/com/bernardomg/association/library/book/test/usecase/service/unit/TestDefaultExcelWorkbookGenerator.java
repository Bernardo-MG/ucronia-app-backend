
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.library.book.usecase.service.DefaultExcelWorkbookGenerator;

@DisplayName("DefaultExcelWorkbookGenerator")
class TestDefaultExcelWorkbookGenerator {

    private static final List<Integer> FICTION_COLUMN_WIDTHS         = List.of(3000, 17000, 3000, 5000, 5000, 5000,
        5000, 15000, 5000, 15000);

    private static final List<String>  FICTION_HEADERS               = List.of("Número", "Título completo", "Idioma",
        "ISBN", "Publicación", "Autores", "Editores", "Donantes", "Donado en", "Préstamo");

    private static final List<Integer> GAME_COLUMN_WIDTHS            = List.of(3000, 17000, 3000, 5000, 5000, 5000,
        5000, 5000, 5000, 15000, 5000, 15000);

    private static final List<String>  GAME_HEADERS                  = List.of("Número", "Título completo", "Idioma",
        "ISBN", "Publicación", "Sistema", "Tipo", "Autores", "Editores", "Donantes", "Donado en", "Préstamo");

    private static final List<Integer> LENDING_COLUMN_WIDTHS         = List.of(4000, 3000, 17000, 9000, 5000);

    private static final List<String>  LENDING_HEADERS               = List.of("Tipo", "Número", "Título", "Socio",
        "Prestado en");

    private static final List<Integer> LENDING_HISTORY_COLUMN_WIDTHS = List.of(4000, 3000, 17000, 9000, 5000, 5000);

    private static final List<String>  LENDING_HISTORY_HEADERS       = List.of("Tipo", "Número", "Título", "Socio",
        "Prestado en", "Devuelto en");

    private static void assertHeaderStyle(final XSSFWorkbook workbook, final Cell cell) {
        final XSSFFont font;

        font = workbook.getFontAt(cell.getCellStyle()
            .getFontIndex());

        assertAll(() -> assertEquals("Arial", font.getFontName()), () -> assertEquals(16, font.getFontHeightInPoints()),
            () -> assertEquals(true, font.getBold()));
    }

    private static void assertSheet(final Workbook workbook, final String sheetName, final List<String> expectedHeaders,
            final List<Integer> expectedWidths, final int headerRow, final int firstColumn) {
        final Sheet sheet;
        final Row   header;

        sheet = workbook.getSheet(sheetName);
        assertNotNull(sheet);
        header = sheet.getRow(headerRow);
        assertAll(() -> assertNotNull(header),
            () -> assertEquals(expectedHeaders.size(), header.getPhysicalNumberOfCells()));

        for (int index = 0; index < expectedHeaders.size(); index++) {
            final int  column = index + firstColumn;
            final Cell cell   = header.getCell(column);

            assertAll(() -> assertNotNull(cell, "Missing header cell at column " + column),
                () -> assertEquals(expectedHeaders.get(column - firstColumn), cell.getStringCellValue()),
                () -> assertEquals(expectedWidths.get(column - firstColumn)
                    .intValue(), sheet.getColumnWidth(column)));

            assertHeaderStyle((XSSFWorkbook) workbook, cell);
        }
    }

    private static void assertBookSheet(final Workbook workbook, final String sheetName,
            final List<String> expectedHeaders, final List<Integer> expectedWidths) {
        final Cell  title;
        final Sheet sheet;
        final int   rightPaddingColumn;

        sheet = workbook.getSheet(sheetName);
        assertSheet(workbook, sheetName, expectedHeaders, expectedWidths, 2, 1);

        title = sheet.getRow(1)
            .getCell(1);
        rightPaddingColumn = expectedHeaders.size() + 1;

        assertAll(() -> assertEquals("Biblioteca de A.R. Ucronía", title.getStringCellValue()),
            () -> assertEquals(1, sheet.getNumMergedRegions()),
            () -> assertEquals(1000, sheet.getColumnWidth(0)),
            () -> assertEquals(1000, sheet.getColumnWidth(rightPaddingColumn)),
            () -> assertTrue(sheet.isColumnHidden(rightPaddingColumn + 1)),
            () -> assertFalse(sheet.isDisplayGridlines()),
            () -> assertFalse(sheet.isDisplayRowColHeadings()));
    }

    private final DefaultExcelWorkbookGenerator generator = new DefaultExcelWorkbookGenerator();

    @Test
    @DisplayName("The fiction sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesFictionSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Ficción", FICTION_HEADERS, FICTION_COLUMN_WIDTHS);
    }

    @Test
    @DisplayName("The games sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesGamesSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Juegos", GAME_HEADERS, GAME_COLUMN_WIDTHS);
    }

    @Test
    @DisplayName("The lending history sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesLendingHistorySheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertSheet(workbook, "Historial de préstamos", LENDING_HISTORY_HEADERS, LENDING_HISTORY_COLUMN_WIDTHS, 0, 0);
    }

    @Test
    @DisplayName("The lending sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesLendingSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertSheet(workbook, "Préstamos", LENDING_HEADERS, LENDING_COLUMN_WIDTHS, 0, 0);
    }

    @Test
    @DisplayName("The workbook contains the games, fiction and lending sheets")
    void testGenerateWorkbookCreatesSheets() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertAll(() -> assertEquals(4, workbook.getNumberOfSheets()),
            () -> assertEquals("Juegos", workbook.getSheetName(0)),
            () -> assertEquals("Ficción", workbook.getSheetName(1)),
            () -> assertEquals("Préstamos", workbook.getSheetName(2)),
            () -> assertEquals("Historial de préstamos", workbook.getSheetName(3)));
    }

}
