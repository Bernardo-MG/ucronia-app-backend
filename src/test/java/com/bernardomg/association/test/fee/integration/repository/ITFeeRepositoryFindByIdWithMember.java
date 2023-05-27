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

package com.bernardomg.association.test.fee.integration.repository;

import java.util.GregorianCalendar;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.fee.model.MemberFee;
import com.bernardomg.association.fee.repository.FeeRepository;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("RoleRepository - find one by id with member")
@Sql({ "/db/queries/member/single.sql", "/db/queries/fee/single.sql" })
public class ITFeeRepositoryFindByIdWithMember {

    @Autowired
    private FeeRepository repository;

    public ITFeeRepositoryFindByIdWithMember() {
        super();
    }

    @Test
    @DisplayName("When reading a single entity with a valid id, an entity is returned")
    public void testFindOneByIdWithMember_Existing() {
        final Optional<MemberFee> result;

        result = repository.findOneByIdWithMember(1L);

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("Returns the correct data when reading a single entity")
    public void testFindOneByIdWithMember_Existing_Data() {
        final MemberFee result;
        final Long      id;

        id = 1L;

        result = repository.findOneByIdWithMember(id)
            .get();

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals(id, result.getMemberId());
        Assertions.assertEquals("Member 1", result.getName());
        Assertions.assertEquals("Surname 1", result.getSurname());
        Assertions.assertEquals(new GregorianCalendar(2020, 1, 1).getTime(), result.getDate()
            .getTime());
        Assertions.assertTrue(result.getPaid());
    }

    @Test
    @DisplayName("When reading a single entity with an invalid id, no entity is returned")
    public void testFindOneByIdWithMember_NotExisting() {
        final Optional<MemberFee> result;

        result = repository.findOneByIdWithMember(-1L);

        Assertions.assertFalse(result.isPresent());
    }

}
