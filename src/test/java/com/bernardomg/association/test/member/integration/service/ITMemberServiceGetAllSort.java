/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.member.integration.service;

import java.util.Iterator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.DtoMemberQueryRequest;
import com.bernardomg.association.member.model.request.MemberQueryRequest;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all - sort")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITMemberServiceGetAllSort {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllSort() {
        super();
    }

    @Test
    @DisplayName("With ascending order by active flag it returns the ordered data")
    public void testGetAll_Active_Asc() {
        final Iterator<Member>   result;
        final MemberQueryRequest sample;
        Member                   data;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "active");

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 5");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 5");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12349");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6783");
        Assertions.assertThat(data.getActive())
            .isFalse();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 3");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 3");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12347");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6781");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 4");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 4");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12348");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6782");
        Assertions.assertThat(data.getActive())
            .isTrue();
    }

    @Test
    @DisplayName("With descending order by active flag it returns the ordered data")
    public void testGetAll_Active_Desc() {
        final Iterator<Member>   result;
        final MemberQueryRequest sample;
        Member                   data;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "active");

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 3");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 3");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12347");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6781");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 4");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 4");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12348");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6782");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 5");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 5");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12349");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6783");
        Assertions.assertThat(data.getActive())
            .isFalse();
    }

    @Test
    @DisplayName("With ascending order by name it returns the ordered data")
    public void testGetAll_Name_Asc() {
        final Iterator<Member>   result;
        final MemberQueryRequest sample;
        Member                   data;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "name");

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 3");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 3");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12347");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6781");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 4");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 4");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12348");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6782");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 5");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 5");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12349");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6783");
        Assertions.assertThat(data.getActive())
            .isFalse();
    }

    @Test
    @DisplayName("With descending order by name it returns the ordered data")
    public void testGetAll_Name_Desc() {
        final Iterator<Member>   result;
        final MemberQueryRequest sample;
        Member                   data;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "name");

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 5");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 5");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12349");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6783");
        Assertions.assertThat(data.getActive())
            .isFalse();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 4");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 4");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12348");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6782");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 3");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 3");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12347");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6781");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(data.getActive())
            .isTrue();
    }

    @Test
    @DisplayName("With ascending order by surname it returns the ordered data")
    public void testGetAll_Surname_Asc() {
        final Iterator<Member>   result;
        final MemberQueryRequest sample;
        Member                   data;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 10, Direction.ASC, "surname");

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 3");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 3");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12347");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6781");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 4");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 4");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12348");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6782");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 5");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 5");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12349");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6783");
        Assertions.assertThat(data.getActive())
            .isFalse();
    }

    @Test
    @DisplayName("With descending order by surname it returns the ordered data")
    public void testGetAll_Surname_Desc() {
        final Iterator<Member>   result;
        final MemberQueryRequest sample;
        Member                   data;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 10, Direction.DESC, "surname");

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable)
            .iterator();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 5");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 5");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12349");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6783");
        Assertions.assertThat(data.getActive())
            .isFalse();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 4");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 4");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12348");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6782");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 3");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 3");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12347");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6781");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(data.getActive())
            .isTrue();

        data = result.next();
        Assertions.assertThat(data.getId())
            .isNotNull();
        Assertions.assertThat(data.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(data.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(data.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(data.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(data.getActive())
            .isTrue();
    }

}
