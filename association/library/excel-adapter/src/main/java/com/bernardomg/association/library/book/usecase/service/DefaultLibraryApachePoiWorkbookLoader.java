
package com.bernardomg.association.library.book.usecase.service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.publisher.domain.model.Publisher;

public final class DefaultLibraryApachePoiWorkbookLoader implements LibraryApachePoiWorkbookLoader {

    private static final int               AUTO_SIZE_PADDING         = 1024;

    private static final int               DEFAULT_ROW_HEIGHT_POINTS = 15;

    private static final int               END_PADDING_COLUMN_WIDTH  = 1000;

    private static final String            EXCEL_DATE_FORMAT         = "dd/MM/yyyy";

    private static final int               FIRST_BOOK_COLUMN         = 1;

    private static final int               FIRST_BOOK_DATA_ROW       = 3;

    private static final DateTimeFormatter LENDING_DATE_FORMAT       = DateTimeFormatter.ofPattern(EXCEL_DATE_FORMAT)
        .withZone(ZoneId.systemDefault());

    private static final int               MAX_COLUMN_WIDTH          = 255 * 256;

    private final BorrowerNameResolver     borrowerNameResolver;

    public DefaultLibraryApachePoiWorkbookLoader(final BorrowerNameResolver resolver) {
        super();

        borrowerNameResolver = Objects.requireNonNull(resolver);
    }

    @Override
    public final void loadWorkbook(final Workbook workbook, final Iterable<GameBook> gameBooks,
            final Iterable<FictionBook> fictionBooks) {
        final CellStyle  lendingStyle;
        final CellStyle  lendingDateStyle;
        final CellStyle  alternateLendingStyle;
        final CellStyle  alternateLendingDateStyle;
        final CellStyle  bookStyle;
        final CellStyle  alternateBookStyle;
        final CellStyle  bookDateStyle;
        final CellStyle  alternateDateStyle;
        final Sheet      gameSheet;
        final Sheet      fictionSheet;
        final Sheet      lendingSheet;
        final Sheet      lendingHistorySheet;
        final DataFormat df;

        df = workbook.createDataFormat();

        lendingStyle = workbook.createCellStyle();
        configureBodyStyle(lendingStyle);

        alternateLendingStyle = workbook.createCellStyle();
        alternateLendingStyle.cloneStyleFrom(lendingStyle);
        alternateLendingStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        ((XSSFCellStyle) alternateLendingStyle).setFillForegroundColor(ExcelPalette.BAND_BACKGROUND);

        lendingDateStyle = workbook.createCellStyle();
        lendingDateStyle.cloneStyleFrom(lendingStyle);
        lendingDateStyle.setDataFormat(df.getFormat(EXCEL_DATE_FORMAT));
        lendingDateStyle.setAlignment(HorizontalAlignment.CENTER);

        alternateLendingDateStyle = workbook.createCellStyle();
        alternateLendingDateStyle.cloneStyleFrom(alternateLendingStyle);
        alternateLendingDateStyle.setDataFormat(df.getFormat(EXCEL_DATE_FORMAT));
        alternateLendingDateStyle.setAlignment(HorizontalAlignment.CENTER);

        bookStyle = workbook.createCellStyle();
        configureBodyStyle(bookStyle);

        alternateBookStyle = workbook.createCellStyle();
        alternateBookStyle.cloneStyleFrom(bookStyle);
        alternateBookStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        ((XSSFCellStyle) alternateBookStyle).setFillForegroundColor(ExcelPalette.BAND_BACKGROUND);

        bookDateStyle = workbook.createCellStyle();
        bookDateStyle.cloneStyleFrom(bookStyle);
        bookDateStyle.setDataFormat(df.getFormat(EXCEL_DATE_FORMAT));
        bookDateStyle.setAlignment(HorizontalAlignment.CENTER);

        alternateDateStyle = workbook.createCellStyle();
        alternateDateStyle.cloneStyleFrom(alternateBookStyle);
        alternateDateStyle.setDataFormat(df.getFormat(EXCEL_DATE_FORMAT));
        alternateDateStyle.setAlignment(HorizontalAlignment.CENTER);

        gameSheet = workbook.getSheetAt(0);
        loadGames(gameSheet, bookStyle, bookDateStyle, alternateBookStyle, alternateDateStyle, gameBooks);

        fictionSheet = workbook.getSheetAt(1);
        loadFiction(fictionSheet, bookStyle, bookDateStyle, alternateBookStyle, alternateDateStyle, fictionBooks);

        // Only active lendings
        lendingSheet = workbook.getSheetAt(2);
        loadLendings(lendingSheet, lendingStyle, lendingDateStyle, alternateLendingStyle, alternateLendingDateStyle,
            gameBooks, fictionBooks, true, false);

        // Complete history
        lendingHistorySheet = workbook.getSheetAt(3);
        loadLendings(lendingHistorySheet, lendingStyle, lendingDateStyle, alternateLendingStyle,
            alternateLendingDateStyle, gameBooks, fictionBooks, false, true);

        autoAdjustColumns(gameSheet, 12);
        autoAdjustColumns(fictionSheet, 10);
        autoAdjustColumns(lendingSheet, 5);
        autoAdjustColumns(lendingHistorySheet, 6);
    }

    private final void autoAdjustColumns(final Sheet sheet, final int lastBookColumn) {
        int width;

        for (int column = FIRST_BOOK_COLUMN; column <= lastBookColumn; column++) {
            sheet.autoSizeColumn(column, true);
            width = Math.min(MAX_COLUMN_WIDTH, sheet.getColumnWidth(column) + AUTO_SIZE_PADDING);
            sheet.setColumnWidth(column, width);
        }

        sheet.setColumnWidth(lastBookColumn + 1, END_PADDING_COLUMN_WIDTH);
    }

    private final void configureBodyStyle(final CellStyle style) {
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        // Keep body text on a single line so autoSizeColumn expands columns to full content width.
        style.setWrapText(false);
    }

    private final String getLendingStatus(final boolean lent, final Collection<BookLendingInfo> lendings) {
        final BookLendingInfo lending;
        final String          borrowerName;

        if (!lent) {
            return "Disponible";
        }

        lending = lendings.stream()
            .filter(info -> info.returnDate()
                .isEmpty())
            .reduce((first, second) -> second)
            .orElseThrow(() -> new IllegalStateException("A lent book has no active lending"));

        borrowerName = borrowerNameResolver.getBorrowerName(lending.borrower());

        return String.format("%s (%s) %d días", borrowerName, LENDING_DATE_FORMAT.format(lending.lendingDate()),
            lending.getDays());
    }

    private final void loadFiction(final Sheet sheet, final CellStyle style, final CellStyle dateStyle,
            final CellStyle alternateStyle, final CellStyle alternateDateStyle, final Iterable<FictionBook> books) {
        int       index;
        Row       row;
        Cell      cell;
        CellStyle rowStyle;
        CellStyle rowDateStyle;
        Donation  donation;

        index = FIRST_BOOK_DATA_ROW;
        for (final FictionBook book : books) {
            row = sheet.createRow(index);
            row.setHeightInPoints(DEFAULT_ROW_HEIGHT_POINTS);

            if (((index - FIRST_BOOK_DATA_ROW) % 2) == 0) {
                rowStyle = style;
                rowDateStyle = dateStyle;
            } else {
                rowStyle = alternateStyle;
                rowDateStyle = alternateDateStyle;
            }

            cell = row.createCell(1);
            cell.setCellValue(book.number());
            cell.setCellStyle(rowStyle);

            cell = row.createCell(2);
            cell.setCellValue(book.title()
                .fullTitle());
            cell.setCellStyle(rowStyle);

            cell = row.createCell(3);
            cell.setCellValue(translateLanguage(book.language()));
            cell.setCellStyle(rowStyle);

            cell = row.createCell(4);
            cell.setCellValue(book.isbn());
            cell.setCellStyle(rowStyle);

            cell = row.createCell(5);
            if (book.publishDate()
                .isPresent()) {
                cell.setCellValue(Date.from(book.publishDate()
                    .get()));
            }
            cell.setCellStyle(rowDateStyle);

            cell = row.createCell(6);
            cell.setCellValue(book.authors()
                .stream()
                .map(Author::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(rowStyle);

            cell = row.createCell(7);
            cell.setCellValue(book.publishers()
                .stream()
                .map(Publisher::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(rowStyle);

            if (book.donation()
                .isPresent()) {
                donation = book.donation()
                    .get();

                cell = row.createCell(8);
                cell.setCellValue(donation.donors()
                    .stream()
                    .map(Donor::name)
                    .map(Donor.Name::fullName)
                    .collect(Collectors.joining(", ")));
                cell.setCellStyle(rowStyle);

                cell = row.createCell(9);
                if (donation.date()
                    .isPresent()) {
                    cell.setCellValue(Date.from(donation.date()
                        .get()));
                }
                cell.setCellStyle(rowDateStyle);
            }

            cell = row.createCell(10);
            cell.setCellValue(getLendingStatus(book.lent(), book.lendings()));
            cell.setCellStyle(rowStyle);

            index++;
        }

        sheet.createRow(index)
            .setHeightInPoints(8);

    }

    private final void loadGames(final Sheet sheet, final CellStyle style, final CellStyle dateStyle,
            final CellStyle alternateStyle, final CellStyle alternateDateStyle, final Iterable<GameBook> books) {
        int       index;
        Row       row;
        Cell      cell;
        CellStyle rowStyle;
        CellStyle rowDateStyle;
        Donation  donation;

        index = FIRST_BOOK_DATA_ROW;
        for (final GameBook book : books) {
            row = sheet.createRow(index);
            row.setHeightInPoints(DEFAULT_ROW_HEIGHT_POINTS);

            if (((index - FIRST_BOOK_DATA_ROW) % 2) == 0) {
                rowStyle = style;
                rowDateStyle = dateStyle;
            } else {
                rowStyle = alternateStyle;
                rowDateStyle = alternateDateStyle;
            }

            cell = row.createCell(1);
            cell.setCellValue(book.number());
            cell.setCellStyle(rowStyle);

            cell = row.createCell(2);
            cell.setCellValue(book.title()
                .fullTitle());
            cell.setCellStyle(rowStyle);

            cell = row.createCell(3);
            cell.setCellValue(translateLanguage(book.language()));
            cell.setCellStyle(rowStyle);

            cell = row.createCell(4);
            cell.setCellValue(book.isbn());
            cell.setCellStyle(rowStyle);

            cell = row.createCell(5);
            if (book.publishDate()
                .isPresent()) {
                cell.setCellValue(Date.from(book.publishDate()
                    .get()));
            }
            cell.setCellStyle(rowDateStyle);

            cell = row.createCell(6);
            cell.setCellValue(book.gameSystem()
                .map(GameSystem::name)
                .orElse(""));
            cell.setCellStyle(rowStyle);

            cell = row.createCell(7);
            cell.setCellValue(book.bookType()
                .map(BookType::name)
                .orElse(""));
            cell.setCellStyle(rowStyle);

            cell = row.createCell(8);
            cell.setCellValue(book.authors()
                .stream()
                .map(Author::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(rowStyle);

            cell = row.createCell(9);
            cell.setCellValue(book.publishers()
                .stream()
                .map(Publisher::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(rowStyle);

            if (book.donation()
                .isPresent()) {
                donation = book.donation()
                    .get();

                cell = row.createCell(10);
                cell.setCellValue(donation.donors()
                    .stream()
                    .map(Donor::name)
                    .map(Donor.Name::fullName)
                    .collect(Collectors.joining(", ")));
                cell.setCellStyle(rowStyle);

                cell = row.createCell(11);
                if (donation.date()
                    .isPresent()) {
                    cell.setCellValue(Date.from(donation.date()
                        .get()));
                }
                cell.setCellStyle(rowDateStyle);
            }

            cell = row.createCell(12);
            cell.setCellValue(getLendingStatus(book.lent(), book.lendings()));
            cell.setCellStyle(rowStyle);

            index++;
        }

        sheet.createRow(index)
            .setHeightInPoints(8);

    }

    private final void loadLendingRow(final Sheet sheet, final int index, final CellStyle style,
            final CellStyle dateStyle, final String type, final long bookNumber, final String title,
            final BookLendingInfo lending, final boolean includeReturnDate) {
        final String borrowerName;
        final Row    row;
        Cell         cell;

        row = sheet.createRow(index);
        row.setHeightInPoints(DEFAULT_ROW_HEIGHT_POINTS);

        cell = row.createCell(1);
        cell.setCellValue(type);
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue(bookNumber);
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue(title);
        cell.setCellStyle(style);

        borrowerName = borrowerNameResolver.getBorrowerName(lending.borrower());

        cell = row.createCell(4);
        cell.setCellValue(borrowerName);
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue(Date.from(lending.lendingDate()));
        cell.setCellStyle(dateStyle);

        if (includeReturnDate) {
            cell = row.createCell(6);

            lending.returnDate()
                .map(Date::from)
                .ifPresent(cell::setCellValue);

            cell.setCellStyle(dateStyle);
        }
    }

    private final void loadLendings(final Sheet sheet, final CellStyle style, final CellStyle dateStyle,
            final CellStyle alternateStyle, final CellStyle alternateDateStyle, final Iterable<GameBook> gameBooks,
            final Iterable<FictionBook> fictionBooks, final boolean activeOnly, final boolean includeReturnDate) {
        int       index;
        CellStyle rowStyle;
        CellStyle rowDateStyle;

        index = FIRST_BOOK_DATA_ROW;

        for (final GameBook book : gameBooks) {
            for (final BookLendingInfo lending : book.lendings()) {
                if (!activeOnly || lending.returnDate()
                    .isEmpty()) {
                    if (((index - FIRST_BOOK_DATA_ROW) % 2) == 0) {
                        rowStyle = style;
                        rowDateStyle = dateStyle;
                    } else {
                        rowStyle = alternateStyle;
                        rowDateStyle = alternateDateStyle;
                    }

                    loadLendingRow(sheet, index, rowStyle, rowDateStyle, "Juego", book.number(), book.title()
                        .fullTitle(), lending, includeReturnDate);

                    index++;
                }
            }
        }

        for (final FictionBook book : fictionBooks) {
            for (final BookLendingInfo lending : book.lendings()) {
                if (!activeOnly || lending.returnDate()
                    .isEmpty()) {
                    if (((index - FIRST_BOOK_DATA_ROW) % 2) == 0) {
                        rowStyle = style;
                        rowDateStyle = dateStyle;
                    } else {
                        rowStyle = alternateStyle;
                        rowDateStyle = alternateDateStyle;
                    }

                    loadLendingRow(sheet, index, rowStyle, rowDateStyle, "Juego", book.number(), book.title()
                        .fullTitle(), lending, includeReturnDate);

                    index++;
                }
            }
        }

        sheet.createRow(index)
            .setHeightInPoints(8);
    }

    private final String translateLanguage(final String code) {
        final String language;

        if ("es".equalsIgnoreCase(code)) {
            language = "Español";
        } else if ("en".equalsIgnoreCase(code)) {
            language = "Inglés";
        } else {
            language = code;
        }

        return language;
    }

}
