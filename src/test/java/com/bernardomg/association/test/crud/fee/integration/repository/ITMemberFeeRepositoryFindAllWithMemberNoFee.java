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

package com.bernardomg.association.test.crud.fee.integration.repository;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.domain.fee.model.MemberFee;
import com.bernardomg.association.domain.fee.model.PersistentFee;
import com.bernardomg.association.domain.fee.repository.MemberFeeRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Fee repository - find all with member - no fees")
@Sql({ "/db/queries/member/multiple.sql" })
public class ITMemberFeeRepositoryFindAllWithMemberNoFee {

    @Autowired
    private MemberFeeRepository repository;

    public ITMemberFeeRepositoryFindAllWithMemberNoFee() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testFindAllWithMember_Count() {
        final Iterable<? extends MemberFee> result;
        final Example<PersistentFee>        example;
        final Pageable                      pageable;

        pageable = Pageable.unpaged();

        example = Example.of(new PersistentFee());

        result = repository.findAllWithMember(example, pageable);

        Assertions.assertEquals(0, IterableUtils.size(result));
    }

}
