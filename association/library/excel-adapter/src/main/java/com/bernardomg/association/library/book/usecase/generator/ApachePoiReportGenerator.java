
package com.bernardomg.association.library.book.usecase.generator;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.excel.ExcelParsing;

public final class ApachePoiReportGenerator implements ReportGenerator {

    /**
     * Logger for the class.
     */
    private static final Logger                  log = LoggerFactory.getLogger(ApachePoiReportGenerator.class);

    private final ApachePoiWorkbookGenerator     excelGenerator;

    private final LibraryApachePoiWorkbookLoader workbookLoader;

    public ApachePoiReportGenerator(final ApachePoiWorkbookGenerator excelGen,
            final LibraryApachePoiWorkbookLoader workbookLoad) {
        super();

        excelGenerator = Objects.requireNonNull(excelGen);
        workbookLoader = Objects.requireNonNull(workbookLoad);
    }

    @Override
    public final ByteArrayOutputStream getReport(final Collection<GameBook> gameBooks,
            final Collection<FictionBook> fictionBooks) {
        final Workbook workbook;

        log.debug("Creating excel report");

        workbook = excelGenerator.generateWorkbook();
        workbookLoader.loadWorkbook(workbook, gameBooks, fictionBooks);

        return ExcelParsing.toStream(workbook);
    }

}
