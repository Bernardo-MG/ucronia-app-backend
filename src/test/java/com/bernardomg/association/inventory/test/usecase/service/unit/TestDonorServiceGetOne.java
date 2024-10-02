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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.inventory.domain.exception.MissingDonorException;
import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.configuration.factory.DonorConstants;
import com.bernardomg.association.inventory.test.configuration.factory.Donors;
import com.bernardomg.association.inventory.usecase.service.DefaultDonorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DonorService - get one")
class TestDonorServiceGetOne {

    @Mock
    private DonorRepository     donorRepository;

    @InjectMocks
    private DefaultDonorService service;

    public TestDonorServiceGetOne() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
    void testGetOne_CallsRepository() {
        final Optional<Donor> donor;

        // GIVEN
        given(donorRepository.findOne(DonorConstants.NUMBER)).willReturn(Optional.of(Donors.valid()));

        // WHEN
        donor = service.getOne(DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(donor)
            .as("donor")
            .contains(Donors.valid());
    }

    @Test
    @DisplayName("When the donor doesn't exist an exception is thrown")
    void testGetOne_NotExisting_NotRemovesEntity() {
        final ThrowingCallable execution;

        // GIVEN
        given(donorRepository.findOne(DonorConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(DonorConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingDonorException.class);
    }

}
