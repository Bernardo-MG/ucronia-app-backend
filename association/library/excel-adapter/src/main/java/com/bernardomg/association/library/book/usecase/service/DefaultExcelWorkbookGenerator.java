
package com.bernardomg.association.library.book.usecase.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class DefaultExcelWorkbookGenerator implements ExcelWorkbookGenerator {

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
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 17000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 15000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 15000);

        header = sheet.createRow(0);

        headerStyle = workbook.createCellStyle();

        font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        headerCell = header.createCell(0);
        headerCell.setCellValue("Número");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Título completo");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Idioma");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("ISBN");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Publicación");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Autores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Editores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Donantes");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Donado en");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(9);
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
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 17000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 15000);
        sheet.setColumnWidth(10, 5000);
        sheet.setColumnWidth(11, 15000);

        header = sheet.createRow(0);

        headerStyle = workbook.createCellStyle();

        font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        headerCell = header.createCell(0);
        headerCell.setCellValue("Número");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Título completo");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Idioma");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("ISBN");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Publicación");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(5);
        headerCell.setCellValue("Sistema");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(6);
        headerCell.setCellValue("Tipo");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(7);
        headerCell.setCellValue("Autores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(8);
        headerCell.setCellValue("Editores");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(9);
        headerCell.setCellValue("Donantes");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(10);
        headerCell.setCellValue("Donado en");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(11);
        headerCell.setCellValue("Préstamo");
        headerCell.setCellStyle(headerStyle);

        return workbook;
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
