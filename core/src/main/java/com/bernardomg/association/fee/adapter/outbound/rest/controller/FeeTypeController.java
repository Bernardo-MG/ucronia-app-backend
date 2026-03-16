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

package com.bernardomg.association.fee.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeTypeDtoMapper;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.usecase.service.FeeTypeService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.FeeTypeApi;
import com.bernardomg.ucronia.openapi.model.FeeTypeCreationDto;
import com.bernardomg.ucronia.openapi.model.FeeTypePageResponseDto;
import com.bernardomg.ucronia.openapi.model.FeeTypeResponseDto;
import com.bernardomg.ucronia.openapi.model.FeeTypeUpdateDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * Fee type REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class FeeTypeController implements FeeTypeApi {

    /**
     * Fee service.
     */
    private final FeeTypeService service;

    public FeeTypeController(final FeeTypeService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE_TYPE", action = Actions.CREATE)
    public FeeTypeResponseDto createFeeType(@Valid final FeeTypeCreationDto feeTypeCreationDto) {
        final FeeType transaction;
        final FeeType toCreate;

        toCreate = FeeTypeDtoMapper.toDomain(feeTypeCreationDto);
        transaction = service.create(toCreate);

        return FeeTypeDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE_TYPE", action = Actions.DELETE)
    public FeeTypeResponseDto deleteFeeType(final Long index) {
        final FeeType transaction;

        transaction = service.delete(index);

        return FeeTypeDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE_TYPE", action = Actions.READ)
    public FeeTypePageResponseDto getAllFeeTypes(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<@Pattern(regexp = "^(number|name|amount)\\|(asc|desc)$") String> sort) {
        final Pagination    pagination;
        final Sorting       sorting;
        final Page<FeeType> transactions;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        transactions = service.getAll(pagination, sorting);

        return FeeTypeDtoMapper.toResponseDto(transactions);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE_TYPE", action = Actions.READ)
    public FeeTypeResponseDto getOneFeeType(final Long index) {
        final Optional<FeeType> transaction;

        transaction = service.getOne(index);

        return FeeTypeDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE_TYPE", action = Actions.UPDATE)
    public FeeTypeResponseDto updateFeeType(final Long index, @Valid final FeeTypeUpdateDto feeTypeUpdateDto) {
        final FeeType transaction;
        final FeeType updated;

        transaction = FeeTypeDtoMapper.toDomain(index, feeTypeUpdateDto);
        updated = service.update(transaction);
        return FeeTypeDtoMapper.toResponseDto(updated);
    }

}
