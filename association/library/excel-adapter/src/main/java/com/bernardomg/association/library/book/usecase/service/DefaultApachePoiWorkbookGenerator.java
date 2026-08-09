
package com.bernardomg.association.library.book.usecase.service;

import java.util.Collection;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class DefaultApachePoiWorkbookGenerator implements ApachePoiWorkbookGenerator {

    private static final int   BOOK_HEADER_ROW           = 2;

    private static final int   BOOK_TITLE_ROW            = 1;

    private static final int   DEFAULT_ROW_HEIGHT_POINTS = 15;

    private static final int   FIRST_BOOK_COLUMN         = 1;

    private static final short HEADER_FONT_SIZE          = 12;

    private static final short TITLE_FONT_SIZE           = 15;

    public DefaultApachePoiWorkbookGenerator() {
        super();
    }

    @Override
    public final Workbook generateWorkbook() {
        final XSSFWorkbook workbook;

        workbook = new XSSFWorkbook();

        generateGamesSheet(workbook);
        generateFictionSheet(workbook);
        generateLendingsSheet(workbook);
        generateLendingHistorySheet(workbook);

        return workbook;
    }

    private final void configureHeader(final Sheet sheet, final int lastBookColumn) {
        final Row topPaddingRow;

        sheet.setDisplayGridlines(false);
        sheet.setDisplayRowColHeadings(false);
        sheet.setDefaultRowHeightInPoints(DEFAULT_ROW_HEIGHT_POINTS);

        topPaddingRow = sheet.createRow(0);
        topPaddingRow.setHeightInPoints(DEFAULT_ROW_HEIGHT_POINTS);

        // Keep padding/title/header rows and first data column visible while scrolling.
        sheet.createFreezePane(FIRST_BOOK_COLUMN + 1, BOOK_HEADER_ROW + 1);

        // Add filter dropdowns to the header row for easier sorting and filtering.
        sheet.setAutoFilter(new CellRangeAddress(BOOK_HEADER_ROW, BOOK_HEADER_ROW, FIRST_BOOK_COLUMN, lastBookColumn));
    }

    private final void configureHeaderStyle(final CellStyle style) {
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        ((XSSFCellStyle) style).setFillForegroundColor(ExcelPalette.HEADER_BACKGROUND);
    }

    private final Workbook generateFictionSheet(final XSSFWorkbook workbook) {
        return generateSheet(workbook, "Ficción",
            List.of("Número", "Título completo", "Idioma", "ISBN", "Publicación", "Autores", "Editores", "Donantes",
                "Donado en", "Préstamo"),
            List.of(4000, 20000, 4200, 6500, 6500, 8500, 8500, 22000, 7000, 24000), ExcelPalette.FICTION_TAB);
    }

    private final Workbook generateGamesSheet(final XSSFWorkbook workbook) {
        return generateSheet(workbook, "Juegos",
            List.of("Número", "Título completo", "Idioma", "ISBN", "Publicación", "Sistema", "Tipo", "Autores",
                "Editores", "Donantes", "Donado en", "Préstamo"),
            List.of(4000, 20000, 4200, 6500, 6500, 7500, 6500, 8500, 8500, 22000, 7000, 24000), ExcelPalette.GAMES_TAB);
    }

    private final Workbook generateLendingHistorySheet(final XSSFWorkbook workbook) {
        return generateSheet(workbook, "Historial de préstamos",
            List.of("Tipo", "Número", "Título", "Socio", "Prestado en", "Devuelto en"),
            List.of(6000, 3800, 20000, 17000, 8000, 8000), ExcelPalette.HISTORY_TAB);
    }

    private final Workbook generateLendingsSheet(final XSSFWorkbook workbook) {
        return generateSheet(workbook, "Préstamos", List.of("Tipo", "Número", "Título", "Socio", "Prestado en"),
            List.of(6000, 3800, 20000, 17000, 8000), ExcelPalette.LENDINGS_TAB);
    }

    private final Workbook generateSheet(final XSSFWorkbook workbook, final String sheetName,
            final Collection<String> headerTexts, final Collection<Integer> columnWidths, final XSSFColor tabColor) {
        final Sheet     sheet;
        final Row       header;
        final CellStyle headerStyle;
        final XSSFFont  font;
        Cell            headerCell;
        int             index;

        sheet = workbook.createSheet(sheetName);

        ((XSSFSheet) sheet).setTabColor(tabColor);

        configureHeader(sheet, columnWidths.size());

        index = 0;
        for (final Integer width : columnWidths) {
            sheet.setColumnWidth(index + FIRST_BOOK_COLUMN, width);
            index++;
        }

        generateTitle(workbook, sheet, columnWidths.size());
        header = sheet.createRow(BOOK_HEADER_ROW);
        headerStyle = workbook.createCellStyle();

        font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints(HEADER_FONT_SIZE);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);
        configureHeaderStyle(headerStyle);

        index = 0;
        for (final String headerText : headerTexts) {
            headerCell = header.createCell(index + FIRST_BOOK_COLUMN);
            headerCell.setCellValue(headerText);
            headerCell.setCellStyle(headerStyle);
            index++;
        }

        header.setHeightInPoints(DEFAULT_ROW_HEIGHT_POINTS);

        return workbook;
    }

    private final void generateTitle(final XSSFWorkbook workbook, final Sheet sheet, final int lastBookColumn) {
        final Cell      titleCell;
        final CellStyle titleStyle;
        final Row       titleRow;
        final XSSFFont  titleFont;

        titleRow = sheet.createRow(BOOK_TITLE_ROW);
        titleCell = titleRow.createCell(FIRST_BOOK_COLUMN);
        titleCell.setCellValue("Biblioteca de A.R. Ucronía");

        titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        ((XSSFCellStyle) titleStyle).setFillForegroundColor(ExcelPalette.TITLE_BACKGROUND);

        titleFont = workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints(TITLE_FONT_SIZE);
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(BOOK_TITLE_ROW, BOOK_TITLE_ROW, FIRST_BOOK_COLUMN, lastBookColumn));
    }

}
