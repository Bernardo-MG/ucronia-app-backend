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

package com.bernardomg.security.test.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.User;
import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("Role service - update")
@Sql({ "/db/queries/security/role/single.sql" })
public class ITUserServiceUpdate {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService    service;

    public ITUserServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("Adds no entity when updating")
    public void testUpdate_AddsNoEntity() {
        final DtoUser data;

        data = new DtoUser();
        data.setUsername("New name");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        service.update(1L, data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("When updating a not existing entity a new one is added")
    public void testUpdate_NotExisting_AddsEntity() {
        final DtoUser data;

        data = new DtoUser();
        data.setUsername("New name");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        service.update(10L, data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final DtoUser        data;
        final PersistentUser entity;

        data = new DtoUser();
        data.setUsername("New name");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        service.update(1L, data);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("New name", entity.getUsername());
        Assertions.assertEquals("email", entity.getEmail());
        Assertions.assertEquals(false, entity.getCredentialsExpired());
        Assertions.assertEquals(true, entity.getEnabled());
        Assertions.assertEquals(false, entity.getExpired());
        Assertions.assertEquals(false, entity.getLocked());
    }

    @Test
    @DisplayName("Returns the updated data")
    public void testUpdate_ReturnedData() {
        final DtoUser data;
        final User    result;

        data = new DtoUser();
        data.setUsername("New name");
        data.setEmail("email");
        data.setCredentialsExpired(false);
        data.setEnabled(true);
        data.setExpired(false);
        data.setLocked(false);

        result = service.update(1L, data);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("New name", result.getUsername());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());
    }

}
