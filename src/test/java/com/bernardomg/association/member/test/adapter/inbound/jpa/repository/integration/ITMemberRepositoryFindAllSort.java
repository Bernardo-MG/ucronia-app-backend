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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.test.configuration.data.annotation.MultipleFees;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.person.test.configuration.data.annotation.MultipleInactiveMembershipPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - find all - sort")
@MultipleInactiveMembershipPerson
@MultipleFees
class ITMemberRepositoryFindAllSort {

    @Autowired
    private MemberRepository repository;

    public ITMemberRepositoryFindAllSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by first name it returns the ordered data")
    void testFindAll_FirstName_Asc() {
        final Iterable<Member> members;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.ASC)));

        // WHEN
        // FIXME: names should be sorted ignoring case
        members = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forNumber(1), Members.forNumber(2), Members.forNumber(3), Members.forNumber(4),
                Members.forNumber(5));
    }

    @Test
    @DisplayName("With descending order by first name it returns the ordered data")
    void testFindAll_FirstName_Desc() {
        final Iterable<Member> members;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("firstName", Sorting.Direction.DESC)));

        // WHEN
        members = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forNumber(5), Members.forNumber(4), Members.forNumber(3), Members.forNumber(2),
                Members.forNumber(1));
    }

    @Test
    @DisplayName("With ascending order by last name it returns the ordered data")
    void testFindAll_LastName_Asc() {
        final Iterable<Member> members;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("lastName", Sorting.Direction.ASC)));

        // WHEN
        members = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forNumber(1), Members.forNumber(2), Members.forNumber(3), Members.forNumber(4),
                Members.forNumber(5));
    }

    @Test
    @DisplayName("With descending order by last name it returns the ordered data")
    void testFindAll_LastName_Desc() {
        final Iterable<Member> members;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = new Sorting(List.of(new Sorting.Property("lastName", Sorting.Direction.DESC)));

        // WHEN
        members = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forNumber(5), Members.forNumber(4), Members.forNumber(3), Members.forNumber(2),
                Members.forNumber(1));
    }

}
