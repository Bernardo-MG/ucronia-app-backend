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

package com.bernardomg.security.test.role;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.persistence.model.PersistentRole;
import com.bernardomg.security.persistence.repository.RoleRepository;
import com.bernardomg.security.service.RoleService;

@IntegrationTest
@DisplayName("Role service - update")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITRoleServiceUpdate {

    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleService    service;

    public ITRoleServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("Adds no entity when updating")
    public void testUpdate_AddsNoEntity() {
        final DtoRole data;

        data = new DtoRole();
        data.setName("Role");

        service.update(1L, data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("When updating a not existing entity a new one is added")
    public void testUpdate_NotExisting_AddsEntity() {
        final DtoRole data;

        data = new DtoRole();
        data.setName("Role");

        service.update(10L, data);

        Assertions.assertEquals(2L, repository.count());
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final DtoRole        data;
        final PersistentRole entity;

        data = new DtoRole();
        data.setName("Role");

        service.update(1L, data);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Role", entity.getName());
    }

    @Test
    @DisplayName("Returns the updated data")
    public void testUpdate_ReturnedData() {
        final DtoRole data;
        final Role    result;

        data = new DtoRole();
        data.setName("Role");

        result = service.update(1L, data);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Role", result.getName());
    }

}
