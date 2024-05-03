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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.config.data.annotation.DonorWithMember;
import com.bernardomg.association.inventory.test.config.data.annotation.DonorWithoutMember;
import com.bernardomg.association.inventory.test.config.factory.DonorConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("DonorRepository - exists name for another")
class ITDonorRepositoryExistsNameForAnother {

    @Autowired
    private DonorRepository repository;

    @Test
    @DisplayName("With a single existing donor, nothing exists")
    @DonorWithoutMember
    void testExistsNameForAnother() {
        final boolean exists;

        // WHEN
        exists = repository.existsNameForAnother(DonorConstants.NAME, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("With no donor, nothing exists")
    void testExistsNameForAnother_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsName(DonorConstants.NAME);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("With another number and a donor with member, the name exists")
    @DonorWithMember
    void testExistsNameForAnother_WithMember_AnotherNumber() {
        final boolean exists;

        // WHEN
        exists = repository.existsNameForAnother(DonorConstants.NAME, DonorConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With another number and a donor without member, the name exists")
    @DonorWithoutMember
    void testExistsNameForAnother_WithoutMember_AnotherNumber() {
        final boolean exists;

        // WHEN
        exists = repository.existsNameForAnother(DonorConstants.NAME, DonorConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("With an existing donor, but a not existing nane, nothing exists")
    @DonorWithoutMember
    void testExistsNameForAnother_WrongName() {
        final boolean exists;

        // WHEN
        exists = repository.existsName("abc");

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
