package com.bernardomg.association.library.book.usecase.service;

import java.io.ByteArrayOutputStream;

import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.model.GameBook;

public interface ReportGenerator {

    public  ByteArrayOutputStream getReport(final Iterable<GameBook>    gameBooks,final Iterable<FictionBook> fictionBooks) ;
    
}
