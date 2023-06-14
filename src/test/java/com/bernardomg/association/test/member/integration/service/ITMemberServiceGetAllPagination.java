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

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.request.DtoMemberQueryRequest;
import com.bernardomg.association.member.model.request.MemberQueryRequest;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - get all - pagination")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITMemberServiceGetAllPagination {

    @Autowired
    private MemberService service;

    public ITMemberServiceGetAllPagination() {
        super();
    }

    @Test
    @DisplayName("Returns a page")
    public void testGetAll_Page_Container() {
        final Iterable<Member>   result;
        final MemberQueryRequest sample;
        final Pageable           pageable;

        pageable = Pageable.ofSize(10);

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

    @Test
    @DisplayName("Returns all the data for the first page")
    public void testGetAll_Page1_Data() {
        final MemberQueryRequest sample;
        final Iterator<Member>   data;
        final Member             result;
        final Pageable           pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoMemberQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 1");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 1");
        Assertions.assertThat(result.getPhone())
            .isEqualTo("12345");
        Assertions.assertThat(result.getIdentifier())
            .isEqualTo("6789");
        Assertions.assertThat(result.getActive())
            .isTrue();
    }

    @Test
    @DisplayName("Returns all the data for the second page")
    public void testGetAll_Page2_Data() {
        final MemberQueryRequest sample;
        final Iterator<Member>   data;
        final Member             result;
        final Pageable           pageable;

        pageable = PageRequest.of(1, 1);

        sample = new DtoMemberQueryRequest();

        data = service.getAll(sample, pageable)
            .iterator();

        result = data.next();
        Assertions.assertThat(result.getId())
            .isNotNull();
        Assertions.assertThat(result.getName())
            .isEqualTo("Member 2");
        Assertions.assertThat(result.getSurname())
            .isEqualTo("Surname 2");
        Assertions.assertThat(result.getPhone())
            .isEqualTo("12346");
        Assertions.assertThat(result.getIdentifier())
            .isEqualTo("6780");
        Assertions.assertThat(result.getActive())
            .isTrue();
    }

    @Test
    @DisplayName("Returns the page entities")
    public void testGetAll_Paged_Count() {
        final Iterable<Member>      result;
        final DtoMemberQueryRequest sample;
        final Pageable              pageable;

        pageable = PageRequest.of(0, 1);

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(IterableUtils.size(result))
            .isOne();
    }

    @Test
    @DisplayName("Returns a page when the pagination is disabled")
    public void testGetAll_Unpaged_Container() {
        final Iterable<Member>   result;
        final MemberQueryRequest sample;
        final Pageable           pageable;

        pageable = Pageable.unpaged();

        sample = new DtoMemberQueryRequest();

        result = service.getAll(sample, pageable);

        Assertions.assertThat(result)
            .isInstanceOf(Page.class);
    }

}
