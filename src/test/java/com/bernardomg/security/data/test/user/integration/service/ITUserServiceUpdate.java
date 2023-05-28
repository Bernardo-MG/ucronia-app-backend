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

package com.bernardomg.security.data.test.user.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.data.model.ImmutableUser;
import com.bernardomg.security.data.model.User;
import com.bernardomg.security.data.persistence.model.PersistentUser;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("Role service - update with no roles")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql" })
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
        final User user;

        user = getUser();

        service.update(user);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final User           user;
        final PersistentUser entity;

        user = getUser();

        service.update(user);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("admin", entity.getUsername());
        Assertions.assertEquals("email2@somewhere.com", entity.getEmail());
        Assertions.assertEquals("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW", entity.getPassword());
        Assertions.assertEquals(false, entity.getCredentialsExpired());
        Assertions.assertEquals(true, entity.getEnabled());
        Assertions.assertEquals(false, entity.getExpired());
        Assertions.assertEquals(false, entity.getLocked());
    }

    @Test
    @DisplayName("Updates persisted data, ignoring case")
    public void testUpdate_PersistedData_Case() {
        final ImmutableUser  user;
        final PersistentUser entity;

        user = getUser("EMAIL@SOMEWHERE.COM");

        service.update(user);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertEquals("email@somewhere.com", entity.getEmail());
    }

    @Test
    @DisplayName("Returns the updated data")
    public void testUpdate_ReturnedData() {
        final User user;
        final User result;

        user = getUser();

        result = service.update(user);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("admin", result.getUsername());
        Assertions.assertEquals("email2@somewhere.com", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());
    }

    @Test
    @DisplayName("Returns the updated data, ignoring case")
    public void testUpdate_ReturnedData_Case() {
        final ImmutableUser user;
        final User          result;

        user = getUser("EMAIL2@SOMEWHERE.COM");

        result = service.update(user);

        Assertions.assertEquals("email2@somewhere.com", result.getEmail());
    }

    private final ImmutableUser getUser() {
        return ImmutableUser.builder()
            .id(1L)
            .username("admin")
            .name("Admin")
            .email("email2@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

    private final ImmutableUser getUser(final String email) {
        return ImmutableUser.builder()
            .id(1L)
            .username("admin")
            .name("Admin")
            .email(email)
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build();
    }

}
