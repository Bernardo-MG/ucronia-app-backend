
package com.bernardomg.association.library.book.usecase.service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.Donation;
import com.bernardomg.association.library.book.domain.model.Donor;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.gamesystem.domain.model.GameSystem;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;

public final class DefaultExcelWorkbookLoader implements ExcelWorkbookLoader {

    private static final DateTimeFormatter LENDING_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(ZoneId.systemDefault());

    private final ProfileRepository        profileRepository;

    public DefaultExcelWorkbookLoader(final ProfileRepository profileRepo) {
        super();

        // TODO: avoid depending on profile
        profileRepository = Objects.requireNonNull(profileRepo);
    }

    @Override
    public final void loadWorkbook(final Workbook workbook, final Iterable<GameBook> gameBooks,
            final Iterable<FictionBook> fictionBooks) {
        final CellStyle  style;
        final CellStyle  dateStyle;
        final Sheet      gameSheet;
        final Sheet      fictionSheet;
        final Sheet      lendingSheet;
        final Sheet      lendingHistorySheet;
        final DataFormat df;

        df = workbook.createDataFormat();

        style = workbook.createCellStyle();
        style.setWrapText(true);

        dateStyle = workbook.createCellStyle();
        dateStyle.setWrapText(true);
        dateStyle.setDataFormat(df.getFormat("dd/MM/yyyy"));

        gameSheet = workbook.getSheetAt(0);
        loadGames(gameSheet, style, dateStyle, gameBooks);

        fictionSheet = workbook.getSheetAt(1);
        loadFiction(fictionSheet, style, dateStyle, fictionBooks);

        // Only active lendings
        lendingSheet = workbook.getSheetAt(2);
        loadLendings(lendingSheet, style, dateStyle, gameBooks, fictionBooks, true, false);

        // Complete history
        lendingHistorySheet = workbook.getSheetAt(3);
        loadLendings(lendingHistorySheet, style, dateStyle, gameBooks, fictionBooks, false, true);
    }

    private final String getLendingStatus(final boolean lent, final Collection<BookLendingInfo> lendings) {
        final BookLendingInfo lending;
        final Profile         borrower;

        if (!lent) {
            return "Disponible";
        }

        lending = lendings.stream()
            .filter(info -> info.returnDate()
                .isEmpty())
            .reduce((first, second) -> second)
            .orElseThrow(() -> new IllegalStateException("A lent book has no active lending"));

        borrower = profileRepository.findOne(lending.borrower())
            .orElseThrow(() -> new IllegalStateException("Profile not found: " + lending.borrower()));

        return String.format("%s (%s) %d días", borrower.name()
            .fullName(), LENDING_DATE_FORMAT.format(lending.lendingDate()), lending.getDays());
    }

    private final void loadFiction(final Sheet sheet, final CellStyle style, final CellStyle dateStyle,
            final Iterable<FictionBook> books) {
        int      index;
        Row      row;
        Cell     cell;
        Donation donation;

        index = 1;
        for (final FictionBook book : books) {
            row = sheet.createRow(index);

            cell = row.createCell(0);
            cell.setCellValue(book.number());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(book.title()
                .fullTitle());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(translateLanguage(book.language()));
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(book.isbn());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            if (book.publishDate()
                .isPresent()) {
                cell.setCellValue(Date.from(book.publishDate()
                    .get()));
            }
            cell.setCellStyle(dateStyle);

            cell = row.createCell(5);
            cell.setCellValue(book.authors()
                .stream()
                .map(Author::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue(book.publishers()
                .stream()
                .map(Publisher::name)
                .collect(Collectors.joining(", ")));
            cell.setCellStyle(style);

            if (book.donation()
                .isPresent()) {
                donation = book.donation()
                    .get();

                cell = row.createCell(7);
                cell.setCellValue(donation.donors()
                    .stream()
                    .map(Donor::name)
                    .map(Donor.Name::fullName)
                    .collect(Collectors.joining(", ")));
                cell.setCellStyle(style);

                cell = row.createCell(8);
                if (donation.date()
                    .isPresent()) {
                    cell.setCellValue(Date.from(donation.date()
                        .get()));
                }
                cell.setCellStyle(dateStyle);
            }

            cell = row.createCell(9);
            cell.setCellValue(getLendingStatus(book.lent(), book.lendings()));
            cell.setCellStyle(style);

            index++;
        }

    }

    private final void loadGames(final Sheet sheet, final CellStyle style, final CellStyle dateStyle,
            final Iterable<GameBook> books) {
        int      index;
        Row      row;
        Cell     cell;
        Donation donation;

        index = 1;
        for (final GameBook book : books) {
            row = sheet.createRow(index);

            cell = row.createCell(0);
            cell.setCellValue(book.number());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(book.title()
                .fullTitle());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(translateLanguage(book.language()));
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue(book.isbn());
            cell.setCellStyle(style);

            cell = row.createCell(4);
            if (book.publishDate()
                .isPresent()) {
                cell.setCellValue(Date.from(book.publishDate()
                    .get()));
            }
            cell.setCellStyle(dateStyle);

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

            if (book.donation()
                .isPresent()) {
                donation = book.donation()
                    .get();

                cell = row.createCell(9);
                cell.setCellValue(donation.donors()
                    .stream()
                    .map(Donor::name)
                    .map(Donor.Name::fullName)
                    .collect(Collectors.joining(", ")));
                cell.setCellStyle(style);

                cell = row.createCell(10);
                if (donation.date()
                    .isPresent()) {
                    cell.setCellValue(Date.from(donation.date()
                        .get()));
                }
                cell.setCellStyle(dateStyle);
            }

            cell = row.createCell(11);
            cell.setCellValue(getLendingStatus(book.lent(), book.lendings()));
            cell.setCellStyle(style);

            index++;
        }

    }

    private final void loadLendingRow(final Sheet sheet, final int index, final CellStyle style,
            final CellStyle dateStyle, final String type, final long bookNumber, final String title,
            final BookLendingInfo lending, final boolean includeReturnDate) {
        final Profile borrower;
        final Row     row;
        Cell          cell;

        row = sheet.createRow(index);

        cell = row.createCell(0);
        cell.setCellValue(type);
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(bookNumber);
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue(title);
        cell.setCellStyle(style);

        borrower = profileRepository.findOne(lending.borrower())
            .orElseThrow(() -> new IllegalStateException("Profile not found: " + lending.borrower()));

        cell = row.createCell(3);
        cell.setCellValue(borrower.name()
            .fullName());
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue(Date.from(lending.lendingDate()));
        cell.setCellStyle(dateStyle);

        if (includeReturnDate) {
            cell = row.createCell(5);

            lending.returnDate()
                .map(Date::from)
                .ifPresent(cell::setCellValue);

            cell.setCellStyle(dateStyle);
        }
    }

    private final void loadLendings(final Sheet sheet, final CellStyle style, final CellStyle dateStyle,
            final Iterable<GameBook> gameBooks, final Iterable<FictionBook> fictionBooks, final boolean activeOnly,
            final boolean includeReturnDate) {
        int index;

        index = 1;

        for (final GameBook book : gameBooks) {
            for (final BookLendingInfo lending : book.lendings()) {
                if (!activeOnly || lending.returnDate()
                    .isEmpty()) {
                    loadLendingRow(sheet, index, style, dateStyle, "Juego", book.number(), book.title()
                        .fullTitle(), lending, includeReturnDate);

                    index++;
                }
            }
        }

        for (final FictionBook book : fictionBooks) {
            for (final BookLendingInfo lending : book.lendings()) {
                if (!activeOnly || lending.returnDate()
                    .isEmpty()) {
                    loadLendingRow(sheet, index, style, dateStyle, "Juego", book.number(), book.title()
                        .fullTitle(), lending, includeReturnDate);

                    index++;
                }
            }
        }
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
