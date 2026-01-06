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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleActiveMemberAccents;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in year for active - sort")
class ITFeeRepositoryFindAllInYearForActiveMembersSort {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With ascending order by name with accents it returns the ordered data")
    @MultipleActiveMemberAccents
    @PositiveFeeType
    @MultipleFees
    @Disabled("Database dependant")
    void testFindAllInYearForActiveMembers_Accents_Name_Asc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.member()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Profile a Last name 1", "Profile é Last name 2", "Profile i Last name 3",
                "Profile o Last name 4", "Profile u Last name 5");
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @MultipleActiveMember
    @PositiveFeeType
    @MultipleFees
    void testFindAllInYearForActiveMembers_Name_Asc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.member()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Profile 1 Last name 1", "Profile 2 Last name 2", "Profile 3 Last name 3",
                "Profile 4 Last name 4", "Profile 5 Last name 5");
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @MultipleActiveMember
    @PositiveFeeType
    @MultipleFees
    void testFindAllInYearForActiveMembers_Name_Desc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.DESC)));

        // WHEN
        fees = repository.findAllInYearForActiveMembers(FeeConstants.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .extracting(fee -> fee.member()
                .name()
                .fullName())
            .as("fee full names")
            .containsExactly("Profile 5 Last name 5", "Profile 4 Last name 4", "Profile 3 Last name 3",
                "Profile 2 Last name 2", "Profile 1 Last name 1");
    }

}
