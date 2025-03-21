
package com.bernardomg.association.transaction.usecase.service;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.excel.ExcelParsing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class ExcelPoiTransactionReportService implements TransactionReportService {

    private final TransactionRepository transactionRepository;

    public ExcelPoiTransactionReportService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final ByteArrayOutputStream getReport() {
        final Iterable<Transaction> transactions;
        final Workbook              workbook;
        final Sorting               sort;

        log.debug("Creating excel");

        // TODO: how to test this?

        workbook = generateWorkbook();

        sort = Sorting.asc("date", "index", "description");
        transactions = transactionRepository.findAll(sort);
        loadWorkbook(workbook, transactions);

        return ExcelParsing.toStream(workbook);
    }

    private final Workbook generateWorkbook() {
        final XSSFWorkbook workbook;
        final Sheet        sheet;
        final Row          header;
        final CellStyle    headerStyle;
        final XSSFFont     font;
        Cell               headerCell;

        workbook = new XSSFWorkbook();

        sheet = workbook.createSheet("Transacciones");
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 15000);

        header = sheet.createRow(0);

        headerStyle = workbook.createCellStyle();

        font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        headerCell = header.createCell(0);
        headerCell.setCellValue("Índice");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Fecha");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Importe");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Descripción");
        headerCell.setCellStyle(headerStyle);

        return workbook;
    }

    private final void loadWorkbook(final Workbook workbook, final Iterable<Transaction> transactions) {
        final CellStyle  style;
        final CellStyle  dateStyle;
        final CellStyle  decimalStyle;
        final Sheet      sheet;
        final DataFormat df;
        int              index;
        Row              row;
        Cell             cell;

        df = workbook.createDataFormat();

        style = workbook.createCellStyle();
        style.setWrapText(true);

        dateStyle = workbook.createCellStyle();
        dateStyle.setWrapText(true);
        dateStyle.setDataFormat(df.getFormat("dd/MM/yyyy"));

        decimalStyle = workbook.createCellStyle();
        decimalStyle.setWrapText(true);
        decimalStyle.setDataFormat(workbook.createDataFormat()
            .getFormat("0.00"));

        sheet = workbook.getSheetAt(0);
        index = 1;
        for (final Transaction transaction : transactions) {
            row = sheet.createRow(index);

            cell = row.createCell(0);
            cell.setCellValue(transaction.index());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(transaction.date());
            cell.setCellStyle(dateStyle);

            cell = row.createCell(2);
            cell.setCellValue(transaction.amount());
            cell.setCellStyle(decimalStyle);

            cell = row.createCell(3);
            cell.setCellValue(transaction.description());
            cell.setCellStyle(style);

            index++;
        }

    }

}
