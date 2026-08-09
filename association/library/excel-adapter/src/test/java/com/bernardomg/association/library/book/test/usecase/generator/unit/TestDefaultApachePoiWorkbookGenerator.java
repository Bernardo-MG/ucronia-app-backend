
package com.bernardomg.association.library.book.test.usecase.generator.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.library.book.usecase.generator.DefaultApachePoiWorkbookGenerator;

@DisplayName("DefaultApachePoiWorkbookGenerator")
class TestDefaultApachePoiWorkbookGenerator {

    private static final List<String> FICTION_HEADERS         = List.of("Número", "Título completo", "Idioma", "ISBN",
        "Publicación", "Autores", "Editores", "Donantes", "Donado en", "Préstamo");

    private static final String       FICTION_TAB_COLOR       = "79BDAA";

    private static final List<String> GAME_HEADERS            = List.of("Número", "Título completo", "Idioma", "ISBN",
        "Publicación", "Sistema", "Tipo", "Autores", "Editores", "Donantes", "Donado en", "Préstamo");

    private static final String       GAMES_TAB_COLOR         = "EBAE45";

    private static final String       HISTORY_TAB_COLOR       = "239297";

    private static final List<String> LENDING_HEADERS         = List.of("Tipo", "Número", "Título", "Socio",
        "Prestado en");

    private static final List<String> LENDING_HISTORY_HEADERS = List.of("Tipo", "Número", "Título", "Socio",
        "Prestado en", "Devuelto en");

    private static final String       LENDINGS_TAB_COLOR      = "E34925";

    private static void assertBookSheet(final Workbook workbook, final String sheetName,
            final List<String> expectedHeaders, final String expectedTabColor) {
        final Cell  title;
        final Sheet sheet;

        sheet = workbook.getSheet(sheetName);
        assertSheet(workbook, sheetName, expectedHeaders, 2, 1);

        title = sheet.getRow(1)
            .getCell(1);

        assertSoftly(softly -> {
            softly.assertThat(title.getStringCellValue())
                .isEqualTo("Biblioteca de A.R. Ucronía");
            softly.assertThat(sheet.getNumMergedRegions())
                .isEqualTo(1);
            softly.assertThat(sheet.isDisplayGridlines())
                .isFalse();
            softly.assertThat(sheet.isDisplayRowColHeadings())
                .isFalse();
            softly.assertThat(toHex(((XSSFSheet) sheet).getTabColor()))
                .isEqualTo(expectedTabColor);
        });
    }

    private static void assertSheet(final Workbook workbook, final String sheetName, final List<String> expectedHeaders,
            final int headerRow, final int firstColumn) {
        final Sheet sheet;
        final Row   header;

        sheet = workbook.getSheet(sheetName);
        header = sheet.getRow(headerRow);
        assertThat(header.getPhysicalNumberOfCells()).isEqualTo(expectedHeaders.size());

        for (int index = 0; index < expectedHeaders.size(); index++) {
            final int  column = index + firstColumn;
            final Cell cell   = header.getCell(column);

            assertThat(cell.getStringCellValue()).isEqualTo(expectedHeaders.get(column - firstColumn));
        }
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
        assertBookSheet(workbook, "Ficción", FICTION_HEADERS, FICTION_TAB_COLOR);
    }

    @Test
    @DisplayName("The games sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesGamesSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Juegos", GAME_HEADERS, GAMES_TAB_COLOR);
    }

    @Test
    @DisplayName("The lending history sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesLendingHistorySheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Historial de préstamos", LENDING_HISTORY_HEADERS, HISTORY_TAB_COLOR);
    }

    @Test
    @DisplayName("The lending sheet has the expected headers, widths and style")
    void testGenerateWorkbookCreatesLendingSheet() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertBookSheet(workbook, "Préstamos", LENDING_HEADERS, LENDINGS_TAB_COLOR);
    }

    @Test
    @DisplayName("The workbook contains the games, fiction and lending sheets")
    void testGenerateWorkbookCreatesSheets() {
        final Workbook workbook;

        // WHEN
        workbook = generator.generateWorkbook();

        // THEN
        assertSoftly(softly -> {
            softly.assertThat(workbook.getNumberOfSheets())
                .isEqualTo(4);
            softly.assertThat(workbook.getSheetName(0))
                .isEqualTo("Juegos");
            softly.assertThat(workbook.getSheetName(1))
                .isEqualTo("Ficción");
            softly.assertThat(workbook.getSheetName(2))
                .isEqualTo("Préstamos");
            softly.assertThat(workbook.getSheetName(3))
                .isEqualTo("Historial de préstamos");
        });
    }

}
