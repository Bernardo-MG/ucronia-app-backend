/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.adapter.outbound.rest.api.TransactionReportApi;
import com.bernardomg.association.transaction.usecase.service.TransactionReportService;
import com.bernardomg.excel.web.ExcelResponses;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;

/**
 * Transaction report REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class TransactionReportController implements TransactionReportApi {

    /**
     * Transaction report service.
     */
    private final TransactionReportService service;

    public TransactionReportController(final TransactionReportService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.READ)
    public Resource getTransactionReport() {
        try {
            final ByteArrayOutputStream stream;

            stream = service.getReport();

            return ExcelResponses.response(stream, "transactions")
                .getBody();
        } catch (final IOException ex) {
            throw new UncheckedIOException("Failed to generate Excel report", ex);
        }
    }

}
