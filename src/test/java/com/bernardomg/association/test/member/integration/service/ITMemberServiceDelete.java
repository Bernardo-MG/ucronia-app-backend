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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.association.member.service.MemberService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Member service - delete")
@Sql({ "/db/queries/member/single.sql" })
public class ITMemberServiceDelete {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private MemberService    service;

    public ITMemberServiceDelete() {
        super();
    }

    @Test
    @DisplayName("With an invalid id it removes no entity")
    public void testDelete_NotExisting_NotRemovesEntity() {
        service.delete(-1L);

        Assertions.assertThat(repository.count())
            .isOne();
    }

    @Test
    @DisplayName("With an invalid id it returns a true flag")
    public void testDelete_NotExisting_ReturnsFalse() {
        final Boolean deleted;

        // TODO: Shouldn't this be an error?
        deleted = service.delete(-1L);

        Assertions.assertThat(deleted)
            .isTrue();
    }

    @Test
    @DisplayName("With a valid id it removes the entity")
    public void testDelete_RemovesEntity() {
        service.delete(1L);

        Assertions.assertThat(repository.count())
            .isZero();
    }

    @Test
    @DisplayName("With a valid id it returns a true flag")
    public void testDelete_ReturnsTrue() {
        final Boolean deleted;

        deleted = service.delete(1L);

        Assertions.assertThat(deleted)
            .isTrue();
    }

}
