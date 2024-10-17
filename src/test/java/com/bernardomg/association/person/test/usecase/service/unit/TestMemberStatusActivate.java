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

package com.bernardomg.association.person.test.usecase.service.unit;

import static org.mockito.Mockito.verify;

import java.time.YearMonth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.association.person.usecase.service.DefaultMemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member status service - activate")
class TestMemberStatusActivate {

    @Mock
    private PersonRepository           personRepository;

    @InjectMocks
    private DefaultMemberStatusService service;

    public TestMemberStatusActivate() {
        super();
    }

    @Test
    @DisplayName("When activating for the current month, the member is activated")
    void testActivate_CurrentMonth() {
        final YearMonth date;
        final Long      number;

        // GIVEN
        date = YearMonth.now();
        number = PersonConstants.NUMBER;

        // WHEN
        service.activate(date, number);

        // THEN
        verify(personRepository).activate(number);
    }

    @Test
    @DisplayName("When activating for the previous month, the member is not activated")
    void testActivate_PreviousMonth() {
        final YearMonth date;
        final Long      number;

        // GIVEN
        date = YearMonth.now()
            .minusMonths(1);
        number = PersonConstants.NUMBER;

        // WHEN
        service.activate(date, number);

        // THEN
        verify(personRepository, Mockito.never()).activate(number);
    }

}
