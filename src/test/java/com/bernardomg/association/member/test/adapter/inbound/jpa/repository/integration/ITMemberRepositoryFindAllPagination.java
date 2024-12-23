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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.person.test.configuration.data.annotation.MultipleInactiveMembershipPerson;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.test.configuration.annotation.IntegrationTest;
import com.bernardomg.test.pagination.AbstractPaginationIT;

@IntegrationTest
@DisplayName("MemberRepository - find all - pagination")
@MultipleInactiveMembershipPerson
class ITMemberRepositoryFindAllPagination extends AbstractPaginationIT<Member> {

    @Autowired
    private MemberRepository repository;

    public ITMemberRepositoryFindAllPagination() {
        super(5);
    }

    @Override
    protected final Iterable<Member> read(final Pagination pagination, final Sorting sorting) {
        return repository.findAll(pagination, sorting);
    }

    @Test
    @DisplayName("With pagination for the first page, it returns the first page")
    void testFindAll_Page1() {
        final Iterable<Member> members;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(0, 1);
        sorting = Sorting.unsorted();

        // WHEN
        members = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forNumber(1));
    }

    @Test
    @DisplayName("With pagination for the second page, it returns the second page")
    void testFindAll_Page2() {
        final Iterable<Member> members;
        final Pagination       pagination;
        final Sorting          sorting;

        // GIVEN
        pagination = new Pagination(1, 1);
        sorting = Sorting.unsorted();

        // WHEN
        members = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forNumber(2));
    }

}
