
package com.bernardomg.association.transaction.usecase.service;

import java.util.Collection;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class DefaultTransactionFileService implements TransactionFileService {

    private final TransactionRepository transactionRepository;

    public DefaultTransactionFileService(final TransactionRepository transactionRepo) {
        super();

        transactionRepository = Objects.requireNonNull(transactionRepo);
    }

    @Override
    public final Workbook getExcel() {
        final Collection<Transaction> transactions;
        final Workbook                workbook;

        log.debug("Creating excel");

        // TODO: how to test this?

        workbook = generateWorkbook();

        transactions = transactionRepository.findAll();
        loadWorkbook(workbook, transactions);

        return workbook;
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
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);

        header = sheet.createRow(0);

        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

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

        style = workbook.createCellStyle();
        style.setWrapText(true);

        sheet = workbook.getSheetAt(0);
        index = 1;
        for (final Transaction transaction : transactions) {
            row = sheet.createRow(index);

            cell = row.createCell(0);
            cell.setCellValue(transaction.getIndex());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(transaction.getDate());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(transaction.getAmount());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(transaction.getDescription());
            cell.setCellStyle(style);

            index++;
        }

    }

}
