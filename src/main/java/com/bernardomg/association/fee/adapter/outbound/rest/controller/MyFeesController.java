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

package com.bernardomg.association.fee.adapter.outbound.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeDtoMapper;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.usecase.service.MyFeesService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.web.WebSorting;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.permission.data.constant.Actions;
import com.bernardomg.ucronia.openapi.api.MyFeesApi;
import com.bernardomg.ucronia.openapi.model.FeePageDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

/**
 * My fees REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class MyFeesController implements MyFeesApi {

    /**
     * User fee service.
     */
    private final MyFeesService service;

    public MyFeesController(final MyFeesService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAccess(resource = "MY_FEES", action = Actions.READ)
    public FeePageDto getUserFees(@Min(0) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<String> sort) {
        final Pagination pagination;
        final Sorting    sorting;
        final Page<Fee>  fees;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        fees = service.getAllForUserInSession(pagination, sorting);

        return FeeDtoMapper.toDto(fees);
    }

}
