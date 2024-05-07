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

package com.bernardomg.association.inventory.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.config.factory.DonorConstants;
import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.association.inventory.usecase.service.DefaultDonorService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("DonorService - create")
class TestDonorServiceCreate {

    @Mock
    private DonorRepository     donorRepository;

    @InjectMocks
    private DefaultDonorService service;

    public TestDonorServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a donor with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Donor            author;

        // GIVEN
        author = Donors.emptyName();

        // WHEN
        execution = () -> service.create(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", ""));
    }

    @Test
    @DisplayName("With a valid donor, the donor is persisted")
    void testCreate_PersistedData() {
        final Donor author;

        // GIVEN
        author = Donors.valid();

        given(donorRepository.findNextNumber()).willReturn(DonorConstants.NUMBER);

        // WHEN
        service.create(author);

        // THEN
        verify(donorRepository).save(Donors.valid());
    }

    @Test
    @DisplayName("With a valid donor, the created donor is returned")
    void testCreate_ReturnedData() {
        final Donor author;
        final Donor created;

        // GIVEN
        author = Donors.valid();

        given(donorRepository.findNextNumber()).willReturn(DonorConstants.NUMBER);
        given(donorRepository.save(Donors.valid())).willReturn(Donors.valid());

        // WHEN
        created = service.create(author);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Donors.valid());
    }

}
