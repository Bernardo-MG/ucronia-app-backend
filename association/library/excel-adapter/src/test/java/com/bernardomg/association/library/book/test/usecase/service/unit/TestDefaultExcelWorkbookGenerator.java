
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    private static final List<Integer> FICTION_COLUMN_WIDTHS = List.of(3000, 17000, 3000, 5000, 5000, 5000, 5000, 15000,
        5000, 5000, 5000, 5000, 3000);

    private static final List<String>  FICTION_HEADERS       = List.of("Número", "Título completo", "Idioma", "ISBN",
        "Publicación", "Autores", "Editores", "Donantes", "Donado en", "Prestado", "Socio", "Prestado en", "Días");

    private static final List<Integer> GAME_COLUMN_WIDTHS    = List.of(3000, 17000, 3000, 5000, 5000, 5000, 5000, 5000,
        5000, 15000, 5000, 5000, 5000, 5000, 3000);

    private static final List<String>  GAME_HEADERS          = List.of("Número", "Título completo", "Idioma", "ISBN",
        "Publicación", "Sistema", "Tipo", "Autores", "Editores", "Donantes", "Donado en", "Prestado", "Socio",
        "Prestado en", "Días");

    private static final List<Integer> LENDING_COLUMN_WIDTHS = List.of(4000, 3000, 17000, 9000, 5000, 5000);

    private static final List<String>  LENDING_HEADERS       = List.of("Tipo", "Número", "Título", "Socio",
        "Prestado en", "Devuelto en");

    private static void assertHeaderStyle(final XSSFWorkbook workbook, final Cell cell) {
        final XSSFFont font;

        font = workbook.getFontAt(cell.getCellStyle()
            .getFontIndex());

        assertAll(() -> assertEquals("Arial", font.getFontName()), () -> assertEquals(16, font.getFontHeightInPoints()),
            () -> assertEquals(true, font.getBold()));
    }

    private static void assertSheet(final Workbook workbook, final String sheetName, final List<String> expectedHeaders,
            final List<Integer> expectedWidths) {
        final Sheet sheet;
        final Row   header;

        sheet = workbook.getSheet(sheetName);
        header = sheet.getRow(0);
        assertAll(() -> assertNotNull(sheet), () -> assertNotNull(header),
            () -> assertEquals(expectedHeaders.size(), header.getPhysicalNumberOfCells()));

        for (int index = 0; index < expectedHeaders.size(); index++) {
            final int  column = index;
            final Cell cell   = header.getCell(column);

            assertAll(() -> assertNotNull(cell, "Missing header cell at column " + column),
                () -> assertEquals(expectedHeaders.get(column), cell.getStringCellValue()),
                () -> assertEquals(expectedWidths.get(column)
                    .intValue(), sheet.getColumnWidth(column)));

            assertHeaderStyle((XSSFWorkbook) workbook, cell);
        }
    }

    private final DefaultExcelWorkbookGenerator generator = new DefaultExcelWorkbookGenerator();

    @Test
    @DisplayName("The fiction sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesFictionSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertSheet(workbook, "Ficción", FICTION_HEADERS, FICTION_COLUMN_WIDTHS);
    }

    @Test
    @DisplayName("The games sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesGamesSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertSheet(workbook, "Juegos", GAME_HEADERS, GAME_COLUMN_WIDTHS);
    }

    @Test
    @DisplayName("The lending sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesLendingSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertSheet(workbook, "Préstamos", LENDING_HEADERS, LENDING_COLUMN_WIDTHS);
    }

    @Test
    @DisplayName("The workbook contains the games, fiction and lending sheets")
    void testGenerateWorkbookCreatesSheets() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertAll(() -> assertEquals(3, workbook.getNumberOfSheets()),
            () -> assertEquals("Juegos", workbook.getSheetName(0)),
            () -> assertEquals("Ficción", workbook.getSheetName(1)),
            () -> assertEquals("Préstamos", workbook.getSheetName(2)), () -> assertNotNull(workbook.getSheet("Juegos")),
            () -> assertNotNull(workbook.getSheet("Ficción")), () -> assertNotNull(workbook.getSheet("Préstamos")));
    }

}
