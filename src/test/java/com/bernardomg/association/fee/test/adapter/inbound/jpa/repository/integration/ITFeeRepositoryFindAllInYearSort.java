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

import java.time.Month;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.AlternativeFeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.member.test.configuration.factory.MemberCalendars;
import com.bernardomg.association.person.test.configuration.data.annotation.AccentActiveMembershipPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.AlternativeActiveMembershipPerson;
import com.bernardomg.association.person.test.configuration.data.annotation.MembershipActivePerson;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("FeeRepository - find all in year - sort")
class ITFeeRepositoryFindAllInYearSort {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @AccentActiveMembershipPerson
    @MultipleFees
    @Disabled("Database dependant")
    void testFindAllInYear_Accents_Name_Asc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR, sorting);

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
    @DisplayName("With an invalid field ordering throws an exception")
    void testFindAllInYear_NoData() {
        final ThrowingCallable execution;
        final Sorting          sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("abc", Sorting.Direction.ASC)));

        // WHEN
        execution = () -> repository.findAllInYear(MemberCalendars.YEAR, sorting)
            .iterator();

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    @MembershipActivePerson
    @AlternativeActiveMembershipPerson
    @FeeFullYear
    @AlternativeFeeFullYear
    void testFindAllInYear_TwoMembers_Name_Asc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAt(Month.JANUARY.getValue()), Fees.paidAt(Month.FEBRUARY.getValue()),
                Fees.paidAt(Month.MARCH.getValue()), Fees.paidAt(Month.APRIL.getValue()),
                Fees.paidAt(Month.MAY.getValue()), Fees.paidAt(Month.JUNE.getValue()),
                Fees.paidAt(Month.JULY.getValue()), Fees.paidAt(Month.AUGUST.getValue()),
                Fees.paidAt(Month.SEPTEMBER.getValue()), Fees.paidAt(Month.OCTOBER.getValue()),
                Fees.paidAt(Month.NOVEMBER.getValue()), Fees.paidAt(Month.DECEMBER.getValue()),
                Fees.paidAtAlternative(Month.JANUARY.getValue()), Fees.paidAtAlternative(Month.FEBRUARY.getValue()),
                Fees.paidAtAlternative(Month.MARCH.getValue()), Fees.paidAtAlternative(Month.APRIL.getValue()),
                Fees.paidAtAlternative(Month.MAY.getValue()), Fees.paidAtAlternative(Month.JUNE.getValue()),
                Fees.paidAtAlternative(Month.JULY.getValue()), Fees.paidAtAlternative(Month.AUGUST.getValue()),
                Fees.paidAtAlternative(Month.SEPTEMBER.getValue()), Fees.paidAtAlternative(Month.OCTOBER.getValue()),
                Fees.paidAtAlternative(Month.NOVEMBER.getValue()), Fees.paidAtAlternative(Month.DECEMBER.getValue()));
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    @MembershipActivePerson
    @AlternativeActiveMembershipPerson
    @FeeFullYear
    @AlternativeFeeFullYear
    void testFindAllInYear_TwoMembers_Name_Desc() {
        final Iterable<Fee> fees;
        final Sorting       sorting;

        // GIVEN
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.DESC),
            new Sorting.Property("date", Sorting.Direction.ASC)));

        // WHEN
        fees = repository.findAllInYear(MemberCalendars.YEAR, sorting);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paidAtAlternative(Month.JANUARY.getValue()),
                Fees.paidAtAlternative(Month.FEBRUARY.getValue()), Fees.paidAtAlternative(Month.MARCH.getValue()),
                Fees.paidAtAlternative(Month.APRIL.getValue()), Fees.paidAtAlternative(Month.MAY.getValue()),
                Fees.paidAtAlternative(Month.JUNE.getValue()), Fees.paidAtAlternative(Month.JULY.getValue()),
                Fees.paidAtAlternative(Month.AUGUST.getValue()), Fees.paidAtAlternative(Month.SEPTEMBER.getValue()),
                Fees.paidAtAlternative(Month.OCTOBER.getValue()), Fees.paidAtAlternative(Month.NOVEMBER.getValue()),
                Fees.paidAtAlternative(Month.DECEMBER.getValue()), Fees.paidAt(Month.JANUARY.getValue()),
                Fees.paidAt(Month.FEBRUARY.getValue()), Fees.paidAt(Month.MARCH.getValue()),
                Fees.paidAt(Month.APRIL.getValue()), Fees.paidAt(Month.MAY.getValue()),
                Fees.paidAt(Month.JUNE.getValue()), Fees.paidAt(Month.JULY.getValue()),
                Fees.paidAt(Month.AUGUST.getValue()), Fees.paidAt(Month.SEPTEMBER.getValue()),
                Fees.paidAt(Month.OCTOBER.getValue()), Fees.paidAt(Month.NOVEMBER.getValue()),
                Fees.paidAt(Month.DECEMBER.getValue()));
    }

}
