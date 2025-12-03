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

import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultFeeService;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.settings.usecase.source.AssociationSettingsSource;
import com.bernardomg.association.transaction.domain.repository.TransactionRepository;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fee service - create unpaid fee")
class TestFeeServiceCreateUnpaidFee {

    @Mock
    private EventEmitter              eventEmitter;

    @Mock
    private FeeRepository             feeRepository;

    @Mock
    private MemberContactRepository   memberContactRepository;

    @Mock
    private MessageSource             messageSource;

    @InjectMocks
    private DefaultFeeService         service;

    @Mock
    private AssociationSettingsSource settingsSource;

    @Mock
    private TransactionRepository     transactionRepository;

    @Test
    @DisplayName("Can create unpaid fee")
    void testCreateUnpaidFee() {
        final Fee fee;

        // GIVEN
        given(memberContactRepository.findOne(ContactConstants.NUMBER))
            .willReturn(Optional.of(MemberContacts.active()));
        given(feeRepository.save(Fees.notPaid())).willReturn(Fees.notPaid());
        given(feeRepository.exists(ContactConstants.NUMBER, FeeConstants.DATE)).willReturn(false);

        // WHEN
        fee = service.createUnpaidFee(FeeConstants.DATE, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .isEqualTo(Fees.notPaid());
    }

    @Test
    @DisplayName("With the fee date already exists, it throws an exception")
    void testPayFees_Existing() {
        final ThrowingCallable execution;
        final FieldFailure     failure;

        // GIVEN
        given(memberContactRepository.findOne(ContactConstants.NUMBER))
            .willReturn(Optional.of(MemberContacts.active()));
        given(feeRepository.exists(ContactConstants.NUMBER, FeeConstants.DATE)).willReturn(true);

        // WHEN
        execution = () -> service.createUnpaidFee(FeeConstants.DATE, ContactConstants.NUMBER);

        // THEN
        failure = new FieldFailure("existing", "month", "month.existing", FeeConstants.DATE);

        ValidationAssertions.assertThatFieldFails(execution, failure);
    }

    @Test
    @DisplayName("When the member doesn't exist it throws an exception")
    void testPayFees_NotExistingContact() {
        final ThrowingCallable execution;

        // GIVEN
        given(memberContactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.createUnpaidFee(FeeConstants.DATE, ContactConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

}
