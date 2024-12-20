
package com.bernardomg.association.library.book.usecase.service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.excel.ExcelParsing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public final class ExcelPoiBookReportService implements BookReportService {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final BookRepository           bookRepository;

    public ExcelPoiBookReportService(final BookRepository bookRepo) {
        super();

        bookRepository = Objects.requireNonNull(bookRepo);
    }

    @Override
    public final ByteArrayOutputStream getReport() {
        final Iterable<Book> books;
        final Workbook       workbook;
        final Sorting        sort;

        log.debug("Creating excel");

        // TODO: how to test this?

        workbook = generateWorkbook();

        sort = Sorting.by("title", "language", "isbn");
        books = bookRepository.findAll(sort);
        loadWorkbook(workbook, books);

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

        sheet = workbook.createSheet("Libros");
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 17000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 5000);
        sheet.setColumnWidth(4, 3000);
        sheet.setColumnWidth(4, 5000);
        sheet.setColumnWidth(5, 5000);
        sheet.setColumnWidth(6, 5000);
        sheet.setColumnWidth(7, 5000);
        sheet.setColumnWidth(8, 5000);
        sheet.setColumnWidth(9, 3000);

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
        headerCell.setCellValue("Prestado");
        headerCell.setCellStyle(headerStyle);

        return workbook;
    }

    private final void loadWorkbook(final Workbook workbook, final Iterable<Book> books) {
        final CellStyle style;
        final Sheet     sheet;
        int             index;
        Row             row;
        Cell            cell;

        style = workbook.createCellStyle();
        style.setWrapText(true);

        sheet = workbook.getSheetAt(0);
        index = 1;
        for (final Book book : books) {
            row = sheet.createRow(index);

            cell = row.createCell(0);
            cell.setCellValue(book.number());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(book.title()
                .fullTitle());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(book.language());
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(book.isbn());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            if (book.publishDate() != null) {
                cell.setCellValue(book.publishDate()
                    .format(dateFormatter));
            }
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue(book.gameSystem()
                .map(GameSystem::name)
                .orElse(""));
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(book.bookType()
                .map(BookType::name)
                .orElse(""));
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(book.authors()
                .stream()
                .map(Author::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue(book.publishers()
                .stream()
                .map(Publisher::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue(book.lent());
            cell.setCellStyle(style);

            index++;
        }

    }

}
