
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.generator.ReportGenerator;
import com.bernardomg.association.library.book.usecase.service.DefaultBookReportService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookReportService")
class TestDefaultBookReportService {

    @Mock
    private FictionBookRepository    fictionBookRepository;

    @Mock
    private GameBookRepository       gameBookRepository;

    @Mock
    private ReportGenerator          reportGenerator;

    @InjectMocks
    private DefaultBookReportService service;

    @Test
    @DisplayName("The report is generated from the sorted game and fiction books")
    void testGetReport() {
        final List<GameBook>        gameBooks;
        final List<FictionBook>     fictionBooks;
        final ByteArrayOutputStream expected;
        final ByteArrayOutputStream result;

        // GIVEN
        gameBooks = List.of(GameBooks.full());
        fictionBooks = List.of(FictionBooks.full());
        expected = new ByteArrayOutputStream();

        given(gameBookRepository.findAll(any())).willReturn(gameBooks);
        given(fictionBookRepository.findAll(any())).willReturn(fictionBooks);
        given(reportGenerator.getReport(gameBooks, fictionBooks)).willReturn(expected);

        // WHEN
        result = service.getReport();

        // THEN
        assertThat(result).isSameAs(expected);
    }

}
