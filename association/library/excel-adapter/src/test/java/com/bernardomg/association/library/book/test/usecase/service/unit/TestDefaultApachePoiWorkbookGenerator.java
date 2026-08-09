
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.library.book.usecase.service.DefaultApachePoiWorkbookGenerator;

@DisplayName("DefaultApachePoiWorkbookGenerator")
class TestDefaultApachePoiWorkbookGenerator {

    private static final List<Integer> FICTION_COLUMN_WIDTHS         = List.of(4000, 20000, 4200, 6500, 6500, 8500,
        8500, 22000, 7000, 24000);

    private static final List<String>  FICTION_HEADERS               = List.of("Número", "Título completo", "Idioma",
        "ISBN", "Publicación", "Autores", "Editores", "Donantes", "Donado en", "Préstamo");

    private static final String        FICTION_TAB_COLOR             = "79BDAA";

    private static final List<Integer> GAME_COLUMN_WIDTHS            = List.of(4000, 20000, 4200, 6500, 6500, 7500,
        6500, 8500, 8500, 22000, 7000, 24000);

    private static final List<String>  GAME_HEADERS                  = List.of("Número", "Título completo", "Idioma",
        "ISBN", "Publicación", "Sistema", "Tipo", "Autores", "Editores", "Donantes", "Donado en", "Préstamo");

    private static final String        GAMES_TAB_COLOR               = "EBAE45";

    private static final String        HEADER_BACKGROUND             = "EBAE45";

    private static final String        HISTORY_TAB_COLOR             = "239297";

    private static final List<Integer> LENDING_COLUMN_WIDTHS         = List.of(6000, 3800, 20000, 17000, 8000);

    private static final List<String>  LENDING_HEADERS               = List.of("Tipo", "Número", "Título", "Socio",
        "Prestado en");

    private static final List<Integer> LENDING_HISTORY_COLUMN_WIDTHS = List.of(6000, 3800, 20000, 17000, 8000, 8000);

    private static final List<String>  LENDING_HISTORY_HEADERS       = List.of("Tipo", "Número", "Título", "Socio",
        "Prestado en", "Devuelto en");

    private static final String        LENDINGS_TAB_COLOR            = "E34925";

    private static final String        TITLE_BACKGROUND              = "3F4350";

    private static void assertBookSheet(final Workbook workbook, final String sheetName,
            final List<String> expectedHeaders, final List<Integer> expectedWidths, final String expectedTabColor) {
        final Row   headerRow;
        final Cell  firstHeader;
        final Cell  title;
        final Sheet sheet;
        final int   rightPaddingColumn;

        sheet = workbook.getSheet(sheetName);
        assertSheet(workbook, sheetName, expectedHeaders, expectedWidths, 2, 1);

        title = sheet.getRow(1)
            .getCell(1);
        headerRow = sheet.getRow(2);
        firstHeader = headerRow.getCell(1);
        rightPaddingColumn = expectedHeaders.size() + 1;

        assertAll(() -> assertEquals("Biblioteca de A.R. Ucronía", title.getStringCellValue()),
            () -> assertEquals(1, sheet.getNumMergedRegions()), () -> assertEquals(1000, sheet.getColumnWidth(0)),
            () -> assertEquals(1000, sheet.getColumnWidth(rightPaddingColumn)),
            () -> assertFalse(sheet.isDisplayGridlines()), () -> assertFalse(sheet.isDisplayRowColHeadings()),
            () -> assertTitleStyle((XSSFWorkbook) workbook, title),
            () -> assertHeaderStyle((XSSFWorkbook) workbook, firstHeader),
            () -> assertEquals(expectedTabColor, toHex(((XSSFSheet) sheet).getTabColor())));
    }

    private static void assertHeaderStyle(final XSSFWorkbook workbook, final Cell cell) {
        final XSSFFont font;

        font = workbook.getFontAt(cell.getCellStyle()
            .getFontIndex());

        assertAll(() -> assertEquals("Arial", font.getFontName()), () -> assertEquals(12, font.getFontHeightInPoints()),
            () -> assertTrue(font.getBold()), () -> assertEquals(HEADER_BACKGROUND,
                toHex(((XSSFCellStyle) cell.getCellStyle()).getFillForegroundColorColor())));
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

    private static void assertTitleStyle(final XSSFWorkbook workbook, final Cell cell) {
        final XSSFFont font;

        font = workbook.getFontAt(cell.getCellStyle()
            .getFontIndex());

        assertAll(() -> assertEquals("Arial", font.getFontName()), () -> assertEquals(15, font.getFontHeightInPoints()),
            () -> assertTrue(font.getBold()), () -> assertEquals(TITLE_BACKGROUND,
                toHex(((XSSFCellStyle) cell.getCellStyle()).getFillForegroundColorColor())));
    }

    private static String toHex(final Color color) {
        final byte[]        rgb;
        final StringBuilder builder;

        rgb = ((XSSFColor) color).getRGB();
        builder = new StringBuilder();
        for (final byte component : rgb) {
            builder.append(String.format(Locale.ROOT, "%02X", Byte.toUnsignedInt(component)));
        }

        return builder.toString();
    }

    private final DefaultApachePoiWorkbookGenerator generator = new DefaultApachePoiWorkbookGenerator();

    @Test
    @DisplayName("The fiction sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesFictionSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Ficción", FICTION_HEADERS, FICTION_COLUMN_WIDTHS, FICTION_TAB_COLOR);
    }

    @Test
    @DisplayName("The games sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesGamesSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Juegos", GAME_HEADERS, GAME_COLUMN_WIDTHS, GAMES_TAB_COLOR);
    }

    @Test
    @DisplayName("The lending history sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesLendingHistorySheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Historial de préstamos", LENDING_HISTORY_HEADERS, LENDING_HISTORY_COLUMN_WIDTHS,
            HISTORY_TAB_COLOR);
    }

    @Test
    @DisplayName("The lending sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesLendingSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Préstamos", LENDING_HEADERS, LENDING_COLUMN_WIDTHS, LENDINGS_TAB_COLOR);
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
