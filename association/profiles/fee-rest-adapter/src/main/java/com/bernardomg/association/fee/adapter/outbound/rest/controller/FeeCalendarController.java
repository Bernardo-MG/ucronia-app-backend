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

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.fee.adapter.outbound.rest.dto.YearsRangeResponseDto;
import com.bernardomg.association.fee.adapter.outbound.rest.model.FeeDtoMapper;
import com.bernardomg.association.fee.usecase.service.FeeService;
import com.bernardomg.association.member.domain.model.YearsRange;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.domain.permission.constant.Actions;

/**
 * Fee calendar REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class FeeCalendarController implements FeeCalendarApi {

    /**
     * Fee service.
     */
    private final FeeService service;

    public FeeCalendarController(final FeeService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "FEE", action = Actions.READ)
    public YearsRangeResponseDto getFeesCalendarRange() {
        final YearsRange range;

        range = service.getRange();

        return FeeDtoMapper.toResponseDto(range);
    }

}
