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

package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.AccentInactiveMembers;
import com.bernardomg.association.member.test.configuration.data.annotation.MultipleInactiveMembers;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - find inactive")
class ITMemberRepositoryFindInactiveSort {

    @Autowired
    private MemberRepository repository;

    public ITMemberRepositoryFindInactiveSort() {
        super();
    }

    @Test
    @DisplayName("When there are accents in the name, returns the ordered data")
    @AccentInactiveMembers
    @MultipleFees
    @Disabled("Database dependant")
    void testFindInactive_Accent_FirstName_Asc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "person.firstName");

        // WHEN
        // FIXME: names should be sorted ignoring case
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .extracting(m -> m.name()
                .firstName())
            .containsExactly("Person a", "Person é", "Person i", "Person o", "Person u");
    }

    @Test
    @DisplayName("With ascending order by first name it returns the ordered data")
    @MultipleInactiveMembers
    @MultipleFees
    void testFindInactive_FirstName_Asc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "person.firstName");

        // WHEN
        // FIXME: names should be sorted ignoring case
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .extracting(m -> m.name()
                .firstName())
            .containsExactly("Person 1", "Person 2", "Person 3", "Person 4", "Person 5");
    }

    @Test
    @DisplayName("With descending order by first name it returns the ordered data")
    @MultipleInactiveMembers
    @MultipleFees
    void testFindInactive_FirstName_Desc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "person.firstName");

        // WHEN
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .extracting(m -> m.name()
                .firstName())
            .containsExactly("Person 5", "Person 4", "Person 3", "Person 2", "Person 1");
    }

    @Test
    @DisplayName("With ascending order by last name it returns the ordered data")
    @MultipleInactiveMembers
    @MultipleFees
    void testFindInactive_LastName_Asc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "person.lastName");

        // WHEN
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .extracting(m -> m.name()
                .lastName())
            .containsExactly("Last name 1", "Last name 2", "Last name 3", "Last name 4", "Last name 5");
    }

    @Test
    @DisplayName("With descending order by last name it returns the ordered data")
    @MultipleInactiveMembers
    @MultipleFees
    void testFindInactive_LastName_Desc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "person.lastName");

        // WHEN
        members = repository.findInactive(pageable);

        // THEN
        Assertions.assertThat(members)
            .extracting(m -> m.name()
                .lastName())
            .containsExactly("Last name 5", "Last name 4", "Last name 3", "Last name 2", "Last name 1");
    }

}
