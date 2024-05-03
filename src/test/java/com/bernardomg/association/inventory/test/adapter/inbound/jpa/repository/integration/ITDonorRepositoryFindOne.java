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

package com.bernardomg.association.inventory.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.config.data.annotation.DonorWithMember;
import com.bernardomg.association.inventory.test.config.data.annotation.DonorWithoutMember;
import com.bernardomg.association.inventory.test.config.factory.DonorConstants;
import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DonorRepository - find one")
class ITDonorRepositoryFindOne {

    @Autowired
    private DonorRepository repository;

    @Test
    @DisplayName("With no donor, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Donor> donor;

        // WHEN
        donor = repository.findOne(DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(donor)
            .isEmpty();
    }

    @Test
    @DisplayName("With an existing donor with a member, it is returned")
    @DonorWithMember
    void testFindOne_WithMember() {
        final Optional<Donor> donor;

        // WHEN
        donor = repository.findOne(DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(donor)
            .contains(Donors.withMember());
    }

    @Test
    @DisplayName("With an existing donor without member, it is returned")
    @DonorWithoutMember
    void testFindOne_WithoutMember() {
        final Optional<Donor> donor;

        // WHEN
        donor = repository.findOne(DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(donor)
            .contains(Donors.withoutMember());
    }

}
