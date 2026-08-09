
package com.bernardomg.association.library.book.test.usecase.service.manual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.ApachePoiReportGenerator;
import com.bernardomg.association.library.book.usecase.service.BorrowerNameResolver;
import com.bernardomg.association.library.book.usecase.service.DefaultApachePoiWorkbookGenerator;
import com.bernardomg.association.library.book.usecase.service.DefaultLibraryApachePoiWorkbookLoader;
import com.bernardomg.association.library.book.usecase.service.ReportGenerator;

/**
 * Manual utility to generate a sample Excel report.
 * <p>
 * Run this class as a Java application from IDE or with Maven test classpath.
 */
public final class ManualExcelReportGenerator {

    private static final Logger log = LoggerFactory.getLogger(ManualExcelReportGenerator.class);

    public static void main(final String[] args) throws IOException {
        final BorrowerNameResolver  resolver;
        final ReportGenerator       generator;
        final List<GameBook>        gameBooks;
        final List<FictionBook>     fictionBooks;
        final ByteArrayOutputStream excel;
        final Path                  output;

        log.info("Generating test library excel report");

        resolver = borrower -> "Socio " + borrower;
        generator = new ApachePoiReportGenerator(new DefaultApachePoiWorkbookGenerator(),
            new DefaultLibraryApachePoiWorkbookLoader(resolver));

        gameBooks = List.of(GameBooks.full());
        fictionBooks = List.of(FictionBooks.full());

        excel = generator.getReport(gameBooks, fictionBooks);

        output = Path.of("target", "manual", "library-report.xlsx");
        Files.createDirectories(output.getParent());
        Files.write(output, excel.toByteArray());

        log.info("Excel report generated at: {}", output.toAbsolutePath());
        log.info("File size: {} bytes", Files.size(output));
    }

    private ManualExcelReportGenerator() {
        super();
    }

}
