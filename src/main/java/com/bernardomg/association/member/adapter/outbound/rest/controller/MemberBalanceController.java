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

package com.bernardomg.association.member.adapter.outbound.rest.controller;

import java.time.Instant;
import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.member.adapter.outbound.cache.MembersCaches;
import com.bernardomg.association.member.adapter.outbound.rest.model.MemberBalanceDtoMapper;
import com.bernardomg.association.member.domain.filter.MemberBalanceQuery;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.usecase.service.MemberBalanceService;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;
import com.bernardomg.ucronia.openapi.api.MemberBalanceApi;
import com.bernardomg.ucronia.openapi.model.MonthlyMemberBalancesResponseDto;

import jakarta.validation.Valid;

/**
 * Member balance REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class MemberBalanceController implements MemberBalanceApi {

    /**
     * Member balance service.
     */
    private final MemberBalanceService service;

    public MemberBalanceController(final MemberBalanceService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER", action = Actions.READ)
    @Cacheable(cacheNames = MembersCaches.MONTHLY_BALANCE)
    public MonthlyMemberBalancesResponseDto getMonthlyMemberBalance(@Valid final Instant from, @Valid final Instant to,
            @Valid final Long memberNumber) {
        final Collection<MonthlyMemberBalance> balances;
        final MemberBalanceQuery               query;

        query = new MemberBalanceQuery(from, to);
        balances = service.getMonthlyBalance(query);

        return MemberBalanceDtoMapper.toResponseDto(balances);
    }

}
