
package com.bernardomg.association.transaction.usecase.service;

import org.apache.poi.ss.usermodel.Workbook;

public interface TransactionFileService {

    /**
     * TODO: don't return library specific types.
     *
     * @return
     */
    public Workbook getExcel();

}
