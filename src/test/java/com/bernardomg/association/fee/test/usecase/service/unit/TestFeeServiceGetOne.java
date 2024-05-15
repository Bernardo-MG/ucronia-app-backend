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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.fee.domain.exception.MissingFeeException;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.FeeConstants;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.person.domain.exception.MissingPersonException;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - get one")
class TestFeeServiceGetOne {

    @Mock
    private AssociationConfigurationSource configurationSource;

    @Mock
    private FeeRepository                  feeRepository;

    @Mock
    private MemberRepository               memberRepository;

    @Mock
    private MessageSource                  messageSource;

    @Mock
    private PersonRepository               personRepository;

    @InjectMocks
    private DefaultFeeService              service;

    @Mock
    private TransactionRepository          transactionRepository;

    @Test
    @DisplayName("When there is data it is returned")
    void testGetOne() {
        final Optional<Fee> fee;

        // GIVEN
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(feeRepository.exists(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(true);
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.of(Fees.paid()));

        // WHEN
        fee = service.getOne(PersonConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .contains(Fees.paid());
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetOne_NoData() {
        final Optional<Fee> fee;

        // GIVEN
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(feeRepository.exists(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(true);
        given(feeRepository.findOne(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(Optional.empty());

        // WHEN
        fee = service.getOne(PersonConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .isEmpty();
    }

    @Test
    @DisplayName("With a not existing fee, an exception is thrown")
    void testGetOne_NotExistingFee() {
        final ThrowingCallable execution;

        // GIVEN
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(true);
        given(feeRepository.exists(PersonConstants.NUMBER, FeeConstants.DATE)).willReturn(false);

        // WHEN
        execution = () -> service.getOne(PersonConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingFeeException.class);
    }

    @Test
    @DisplayName("With a not existing person, an exception is thrown")
    void testGetOne_NotExistingPerson() {
        final ThrowingCallable execution;

        // GIVEN
        given(personRepository.exists(PersonConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.getOne(PersonConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingPersonException.class);
    }

}
