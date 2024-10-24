/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.usecase.service.TransactionReportService;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;

import lombok.AllArgsConstructor;

/**
 * Transaction report REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/funds/transaction")
@AllArgsConstructor
public class TransactionReportController {

    /**
     * Transaction report service.
     */
    private final TransactionReportService service;

    /**
     * Returns an Excel report with all the transactions.
     *
     * @return an Excel report with all the transactions
     * @throws IOException
     *             if there was a problem processing the excel
     */
    @GetMapping(produces = "application/vnd.ms-excel")
    @RequireResourceAccess(resource = "TRANSACTION", action = Actions.READ)
    public ResponseEntity<InputStreamResource> readAll() throws IOException {
        final ByteArrayOutputStream stream;
        final byte[]                bytes;
        final HttpHeaders           headers;

        stream = service.getExcel();

        bytes = stream.toByteArray();

        // Set headers for the response
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transactions.xlsx");

        return ResponseEntity.ok()
            .headers(headers)
            .contentLength(bytes.length)
            .contentType(new MediaType("application/vnd.ms-excel"))
            .body(new InputStreamResource(new ByteArrayInputStream(bytes)));
    }

}
