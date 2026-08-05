
package com.bernardomg.association.library.book.usecase.service;

import org.apache.poi.ss.SpreadsheetVersion;
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
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class DefaultExcelWorkbookGenerator implements ExcelWorkbookGenerator {

    private static final int BOOK_HEADER_ROW = 2;

    private static final int BOOK_TITLE_ROW  = 1;

    private static final int FIRST_BOOK_COLUMN = 1;

    public DefaultExcelWorkbookGenerator() {
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

    private final Workbook generateFictionSheet(final XSSFWorkbook workbook) {
        final Sheet     sheet;
        final Row       header;
        final CellStyle headerStyle;
        final XSSFFont  font;
        Cell            headerCell;

        sheet = workbook.createSheet("Ficción");
        configureBookSheet(sheet, 10);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 17000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 15000);
        sheet.setColumnWidth(9, 5000);
        sheet.setColumnWidth(10, 15000);

        generateBookTitle(workbook, sheet, 10);
        header = sheet.createRow(BOOK_HEADER_ROW);

        headerStyle = workbook.createCellStyle();

        font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);
        configureHeaderStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Número");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Título completo");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Idioma");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("ISBN");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Publicación");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Autores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Editores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Donantes");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(9);
        headerCell.setCellValue("Donado en");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(10);
        headerCell.setCellValue("Préstamo");
        headerCell.setCellStyle(headerStyle);

        return workbook;
    }

    private final Workbook generateGamesSheet(final XSSFWorkbook workbook) {
        final Sheet     sheet;
        final Row       header;
        final CellStyle headerStyle;
        final XSSFFont  font;
        Cell            headerCell;

        sheet = workbook.createSheet("Juegos");
        configureBookSheet(sheet, 12);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 17000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 5000);
        sheet.setColumnWidth(10, 15000);
        sheet.setColumnWidth(11, 5000);
        sheet.setColumnWidth(12, 15000);

        generateBookTitle(workbook, sheet, 12);
        header = sheet.createRow(BOOK_HEADER_ROW);

        headerStyle = workbook.createCellStyle();

        font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);
        configureHeaderStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Número");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Título completo");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Idioma");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("ISBN");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Publicación");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Sistema");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Tipo");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Autores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(9);
        headerCell.setCellValue("Editores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(10);
        headerCell.setCellValue("Donantes");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(11);
        headerCell.setCellValue("Donado en");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(12);
        headerCell.setCellValue("Préstamo");
        headerCell.setCellStyle(headerStyle);

        return workbook;
    }

    private final void configureBookSheet(final Sheet sheet, final int lastBookColumn) {
        final int lastVisibleColumn;

        lastVisibleColumn = lastBookColumn + 1;
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(lastVisibleColumn, 1000);
        sheet.setDisplayGridlines(false);
        sheet.setDisplayRowColHeadings(false);

        for (int column = lastVisibleColumn + 1;
                column <= SpreadsheetVersion.EXCEL2007.getLastColumnIndex(); column++) {
            sheet.setColumnHidden(column, true);
        }
    }

    private final void configureHeaderStyle(final CellStyle style) {
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setWrapText(true);
    }

    private final void generateBookTitle(final XSSFWorkbook workbook, final Sheet sheet,
            final int lastBookColumn) {
        final Cell      titleCell;
        final CellStyle titleStyle;
        final Row       titleRow;
        final XSSFFont  titleFont;

        titleRow = sheet.createRow(BOOK_TITLE_ROW);
        titleRow.setHeightInPoints(30);
        titleCell = titleRow.createCell(FIRST_BOOK_COLUMN);
        titleCell.setCellValue("Biblioteca de A.R. Ucronía");

        titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        titleFont = workbook.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBold(true);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(BOOK_TITLE_ROW, BOOK_TITLE_ROW, FIRST_BOOK_COLUMN,
            lastBookColumn));
    }

    private final Workbook generateLendingHistorySheet(final XSSFWorkbook workbook) {
        return generateLendingSheet(workbook, "Historial de préstamos",
            new String[] { "Tipo", "Número", "Título", "Socio", "Prestado en", "Devuelto en" },
            new int[] { 4000, 3000, 17000, 9000, 5000, 5000 });
    }

    private final Workbook generateLendingSheet(final XSSFWorkbook workbook, final String sheetName,
            final String[] headers, final int[] columnWidths) {
        final Sheet     sheet;
        final Row       header;
        final CellStyle headerStyle;
        final XSSFFont  font;
        Cell            headerCell;

        sheet = workbook.createSheet(sheetName);

        for (int index = 0; index < columnWidths.length; index++) {
            sheet.setColumnWidth(index, columnWidths[index]);
        }

        header = sheet.createRow(0);
        headerStyle = workbook.createCellStyle();

        font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        for (int index = 0; index < headers.length; index++) {
            headerCell = header.createCell(index);
            headerCell.setCellValue(headers[index]);
            headerCell.setCellStyle(headerStyle);
        }

        return workbook;
    }

    private final Workbook generateLendingsSheet(final XSSFWorkbook workbook) {
        return generateLendingSheet(workbook, "Préstamos",
            new String[] { "Tipo", "Número", "Título", "Socio", "Prestado en" },
            new int[] { 4000, 3000, 17000, 9000, 5000 });
    }

}
