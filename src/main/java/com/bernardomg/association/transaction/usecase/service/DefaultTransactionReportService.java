
package com.bernardomg.association.transaction.usecase.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.domain.exception.TransactionReportException;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.data.domain.Sorting;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultTransactionReportService implements TransactionReportService {

    private static final DecimalFormat  decimalFormat = new DecimalFormat("0.00");

    private final DateTimeFormatter     dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final TransactionRepository transactionRepository;

    public DefaultTransactionReportService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final ByteArrayOutputStream getExcel() {
        final Collection<Transaction> transactions;
        final Workbook                workbook;
        final Sorting                 sort;

        log.debug("Creating excel");

        // TODO: how to test this?

        workbook = generateWorkbook();

        sort = Sorting.by("date", "index", "description");
        transactions = transactionRepository.findAll(sort);
        loadWorkbook(workbook, transactions);

        return toStream(workbook);
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

    private final void loadWorkbook(final Workbook workbook, final Collection<Transaction> transactions) {
        final CellStyle style;
        final Sheet     sheet;
        int             index;
        Row             row;
        Cell            cell;
        String          date;

        style = workbook.createCellStyle();
        style.setWrapText(true);

        sheet = workbook.getSheetAt(0);
        index = 1;
        for (final Transaction transaction : transactions) {
            row = sheet.createRow(index);

            cell = row.createCell(0);
            cell.setCellValue(transaction.index());
            cell.setCellStyle(style);

            date = transaction.date()
                .format(dateFormatter);
            cell = row.createCell(1);
            cell.setCellValue(date);
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(decimalFormat.format(transaction.amount()));
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(transaction.description());
            cell.setCellStyle(style);

            index++;
        }

    }

    private final ByteArrayOutputStream toStream(final Workbook workbook) {
        final ByteArrayOutputStream outputStream;

        outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
            workbook.close(); // Make sure to close the workbook
        } catch (final IOException e) {
            log.error(e.getLocalizedMessage(), e);
            throw new TransactionReportException();
        }

        return outputStream;
    }

}
