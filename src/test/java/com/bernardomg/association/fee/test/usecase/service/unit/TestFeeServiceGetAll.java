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

package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.test.config.factory.FeesQuery;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - get all")
class TestFeeServiceGetAll {

    @Mock
    private FeeRepository             feeRepository;

    @Mock
    private MemberRepository          memberRepository;

    @Mock
    private MessageSource             messageSource;

    @Mock
    private PersonRepository          personRepository;

    @InjectMocks
    private DefaultFeeService         service;

    @Mock
    private AssociationSettingsSource settingsSource;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("When there is data it is returned")
    void testGetAll() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        given(feeRepository.findAll(feeQuery, pageable)).willReturn(List.of(Fees.paid()));

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetAll_NoData() {
        final Iterable<Fee> fees;
        final FeeQuery      feeQuery;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        feeQuery = FeesQuery.empty();

        given(feeRepository.findAll(feeQuery, pageable)).willReturn(List.of());

        // WHEN
        fees = service.getAll(feeQuery, pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
