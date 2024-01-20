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

package com.bernardomg.association.fee.test.domain.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.bernardomg.association.fee.infra.jpa.repository.AssignedFeeActiveMemberRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.test.config.data.annotation.MultipleMembers;
import com.bernardomg.association.member.test.config.factory.Members;
import com.bernardomg.association.test.data.fee.annotation.MultipleFees;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("AssignedFeeActiveMemberDomainService - find all - sort")
@MultipleMembers
@MultipleFees
class ITAssignedFeeActiveMemberDomainServiceFindAllSort {

    @Autowired
    private AssignedFeeActiveMemberRepository service;

    public ITAssignedFeeActiveMemberDomainServiceFindAllSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    void testFindAll_Name_Asc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "name");

        // WHEN
        // FIXME: names should be sorted ignoring case
        members = service.findAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forIndex(1, false), Members.forIndex(2, false), Members.forIndex(3, false),
                Members.forIndex(4, false), Members.forIndex(5, false));
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    void testFindAll_Name_Desc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "name");

        // WHEN
        members = service.findAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forIndex(5, false), Members.forIndex(4, false), Members.forIndex(3, false),
                Members.forIndex(2, false), Members.forIndex(1, false));
    }

    @Test
    @DisplayName("With ascending order by surname it returns the ordered data")
    void testFindAll_Surname_Asc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.ASC, "surname");

        // WHEN
        members = service.findAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forIndex(1, false), Members.forIndex(2, false), Members.forIndex(3, false),
                Members.forIndex(4, false), Members.forIndex(5, false));
    }

    @Test
    @DisplayName("With descending order by surname it returns the ordered data")
    void testFindAll_Surname_Desc() {
        final Iterable<Member> members;
        final Pageable         pageable;

        // GIVEN
        pageable = PageRequest.of(0, 10, Direction.DESC, "surname");

        // WHEN
        members = service.findAll(pageable);

        // THEN
        Assertions.assertThat(members)
            .containsExactly(Members.forIndex(5, false), Members.forIndex(4, false), Members.forIndex(3, false),
                Members.forIndex(2, false), Members.forIndex(1, false));
    }

}
