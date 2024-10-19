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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.person.test.configuration.data.annotation.AccentActiveMembershipPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MultipleActiveMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all - sort")
class ITFeeRepositoryFindAllForActiveMembersSort {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With ascending order by name with accents it returns the ordered data")
    @AccentActiveMembershipPerson
    @MultipleFees
    @Disabled("Database dependant")
    void testFindAllForActiveMembers_Accents_Name_Asc() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.by(Direction.ASC, "firstName");

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.person()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Person a Last name 1", "Person é Last name 2", "Person i Last name 3",
                "Person o Last name 4", "Person u Last name 5");
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @MultipleActiveMembershipPerson
    @MultipleFees
    void testFindAllForActiveMembers_Name_Asc() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.by(Direction.ASC, "firstName");

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.person()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Person 1 Last name 1", "Person 2 Last name 2", "Person 3 Last name 3",
                "Person 4 Last name 4", "Person 5 Last name 5");
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @MultipleActiveMembershipPerson
    @MultipleFees
    void testFindAllForActiveMembers_Name_Desc() {
        final Iterable<Fee> fees;
        final Sort          sort;

        // GIVEN
        sort = Sort.by(Direction.DESC, "firstName");

        // WHEN
        fees = repository.findAllForActiveMembers(FeeConstants.YEAR, sort);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.person()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Person 5 Last name 5", "Person 4 Last name 4", "Person 3 Last name 3",
                "Person 2 Last name 2", "Person 1 Last name 1");
    }

}
