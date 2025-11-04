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

package com.bernardomg.association.contact.test.usecase.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.YearMonth;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.association.contact.usecase.service.DefaultMemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member status service - deactivate")
class TestMemberStatusDeactivate {

    @Mock
    private ContactRepository          contactRepository;

    @InjectMocks
    private DefaultMemberStatusService service;

    public TestMemberStatusDeactivate() {
        super();
    }

    @Test
    @DisplayName("When activating for the current month, the member is deactivated")
    void testDeactivate_CurrentMonth() {
        final YearMonth date;
        final Long      number;

        // GIVEN
        given(contactRepository.findOne(ContactConstants.NUMBER)).willReturn(Optional.of(Contacts.membershipActive()));
        date = YearMonth.now();
        number = ContactConstants.NUMBER;

        // WHEN
        service.deactivate(date, number);

        // THEN
        verify(contactRepository).save(Contacts.membershipInactiveNoRenew());
    }

    @Test
    @DisplayName("When activating for the previous month, the member is not deactivated")
    void testDeactivate_PreviousMonth() {
        final YearMonth date;
        final Long      number;

        // GIVEN
        date = YearMonth.now()
            .minusMonths(1);
        number = ContactConstants.NUMBER;

        // WHEN
        service.deactivate(date, number);

        // THEN
        verify(contactRepository, never()).save(any());
    }

}
