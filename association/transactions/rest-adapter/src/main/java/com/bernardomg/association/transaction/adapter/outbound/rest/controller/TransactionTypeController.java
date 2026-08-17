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

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypeCreationDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypePageResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypeResponseDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.dto.TransactionTypeUpdateDto;
import com.bernardomg.association.transaction.adapter.outbound.rest.model.TransactionTypeDtoMapper;
import com.bernardomg.association.transaction.domain.model.TransactionType;
import com.bernardomg.association.transaction.usecase.service.TransactionTypeService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

/**
 * Transaction type REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class TransactionTypeController implements TransactionTypeApi {

    /**
     * Transaction type service.
     */
    private final TransactionTypeService service;

    public TransactionTypeController(final TransactionTypeService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION_TYPE", action = Actions.CREATE)
    public TransactionTypeResponseDto createTransactionType(final TransactionTypeCreationDto transactionCreationDto) {
        final TransactionType transactionType;
        final TransactionType toCreate;

        toCreate = TransactionTypeDtoMapper.toDomain(transactionCreationDto);
        transactionType = service.create(toCreate);

        return TransactionTypeDtoMapper.toResponseDto(transactionType);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION_TYPE", action = Actions.DELETE)
    public TransactionTypeResponseDto deleteTransactionType(final Long number) {
        final TransactionType transactionType;

        transactionType = service.delete(number);

        return TransactionTypeDtoMapper.toResponseDto(transactionType);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION_TYPE", action = Actions.READ)
    public TransactionTypePageResponseDto getAllTransactionTypes(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination            pagination;
        final Sorting               sorting;
        final Page<TransactionType> transactionsType;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        transactionsType = service.getAll(pagination, sorting);

        return TransactionTypeDtoMapper.toResponseDto(transactionsType);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION_TYPE", action = Actions.READ)
    public TransactionTypeResponseDto getOneTransactionType(final Long index) {
        final Optional<TransactionType> transactionType;

        transactionType = service.getOne(index);

        return TransactionTypeDtoMapper.toResponseDto(transactionType);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION_TYPE", action = Actions.UPDATE)
    public TransactionTypeResponseDto updateTransactionType(final Long number,
            final TransactionTypeUpdateDto transactionUpdateDto) {
        final TransactionType transactionType;
        final TransactionType updated;

        transactionType = TransactionTypeDtoMapper.toDomain(number, transactionUpdateDto);
        updated = service.update(transactionType);
        return TransactionTypeDtoMapper.toResponseDto(updated);
    }

}
