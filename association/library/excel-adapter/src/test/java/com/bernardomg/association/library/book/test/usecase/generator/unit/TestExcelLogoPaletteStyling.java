
package com.bernardomg.association.library.book.test.usecase.generator.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.book.domain.model.BookLendingInfo;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.generator.DefaultApachePoiWorkbookGenerator;
import com.bernardomg.association.library.book.usecase.generator.DefaultLibraryApachePoiWorkbookLoader;
import com.bernardomg.association.library.book.usecase.generator.NameResolver;

@ExtendWith(MockitoExtension.class)
@DisplayName("Excel logo palette styling")
class TestExcelLogoPaletteStyling {

    private static final String BAND_BACKGROUND = "D8ECE4";

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

    @Mock
    private NameResolver borrowerNameResolver;

    @Test
    @DisplayName("Alternating book and lending rows use the logo mint band color")
    void testAlternatingRowsUseLogoBandColor() {
        final BookLendingInfo                       activeLending;
        final FictionBook                           fictionBook;
        final FictionBook                           fictionBookSecond;
        final GameBook                              gameBook;
        final GameBook                              gameBookSecond;
        final DefaultLibraryApachePoiWorkbookLoader loader;
        final Workbook                              workbook;

        activeLending = new BookLendingInfo(7L, Instant.parse("2026-08-01T10:00:00Z"), Optional.empty());

        gameBook = new GameBook(GameBooks.full()
            .number(),
            GameBooks.full()
                .title(),
            GameBooks.full()
                .isbn(),
            GameBooks.full()
                .language(),
            GameBooks.full()
                .publishDate(),
            true, GameBooks.full()
                .authors(),
            List.of(activeLending), GameBooks.full()
                .publishers(),
            GameBooks.full()
                .donation(),
            GameBooks.full()
                .bookType(),
            GameBooks.full()
                .gameSystem(),
            GameBooks.full()
                .audit());
        gameBookSecond = new GameBook(GameBooks.full()
            .number() + 1, GameBooks.full()
                .title(),
            GameBooks.full()
                .isbn(),
            GameBooks.full()
                .language(),
            GameBooks.full()
                .publishDate(),
            true, GameBooks.full()
                .authors(),
            List.of(activeLending), GameBooks.full()
                .publishers(),
            GameBooks.full()
                .donation(),
            GameBooks.full()
                .bookType(),
            GameBooks.full()
                .gameSystem(),
            GameBooks.full()
                .audit());

        fictionBook = new FictionBook(FictionBooks.full()
            .number(),
            FictionBooks.full()
                .title(),
            FictionBooks.full()
                .isbn(),
            FictionBooks.full()
                .language(),
            FictionBooks.full()
                .publishDate(),
            true, FictionBooks.full()
                .authors(),
            List.of(activeLending), FictionBooks.full()
                .publishers(),
            FictionBooks.full()
                .donation(),
            FictionBooks.full()
                .audit());
        fictionBookSecond = new FictionBook(FictionBooks.full()
            .number() + 1, FictionBooks.full()
                .title(),
            FictionBooks.full()
                .isbn(),
            FictionBooks.full()
                .language(),
            FictionBooks.full()
                .publishDate(),
            true, FictionBooks.full()
                .authors(),
            List.of(activeLending), FictionBooks.full()
                .publishers(),
            FictionBooks.full()
                .donation(),
            FictionBooks.full()
                .audit());

        given(borrowerNameResolver.getName(7L)).willReturn("Ana García");

        loader = new DefaultLibraryApachePoiWorkbookLoader(borrowerNameResolver);
        workbook = new DefaultApachePoiWorkbookGenerator().generateWorkbook();

        loader.loadWorkbook(workbook, List.of(gameBook, gameBookSecond), List.of(fictionBook, fictionBookSecond));

        assertThat(toHex(((XSSFCellStyle) workbook.getSheet("Juegos")
            .getRow(4)
            .getCell(1)
            .getCellStyle()).getFillForegroundColorColor())).isEqualTo(BAND_BACKGROUND);

        assertThat(toHex(((XSSFCellStyle) workbook.getSheet("Préstamos")
            .getRow(4)
            .getCell(1)
            .getCellStyle()).getFillForegroundColorColor())).isEqualTo(BAND_BACKGROUND);
    }

}
