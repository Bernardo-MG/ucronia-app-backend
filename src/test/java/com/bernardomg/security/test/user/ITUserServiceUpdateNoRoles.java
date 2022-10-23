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

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.DtoUser;
import com.bernardomg.security.model.Privilege;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.model.User;
import com.bernardomg.security.persistence.model.PersistentUser;
import com.bernardomg.security.persistence.repository.UserRepository;
import com.bernardomg.security.service.UserService;

@IntegrationTest
@DisplayName("Role service - update with no roles")
@Sql({ "/db/queries/security/privilege/multiple.sql", "/db/queries/security/role/single.sql",
        "/db/queries/security/user/single.sql", "/db/queries/security/relationship/role_privilege.sql" })
public class ITUserServiceUpdateNoRoles {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService    service;

    public ITUserServiceUpdateNoRoles() {
        super();
    }

    @Test
    @DisplayName("Adds no entity when updating")
    public void testUpdate_AddsNoEntity() {
        final User data;

        data = getUser();

        service.update(1L, data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("When updating a not existing entity a new one is added")
    public void testUpdate_NotExisting_AddsEntity() {
        final User data;

        data = getUser();

        service.update(10L, data);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final User           data;
        final PersistentUser entity;

        data = getUser();

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
        final User data;
        final User result;

        data = getUser();

        result = service.update(1L, data);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("New name", result.getUsername());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());

        Assertions.assertEquals(0, IterableUtils.size(result.getRoles()));
    }

    @Test
    @DisplayName("Reading the updated data returns the role and privileges")
    public void testUpdate_WithRole_ReadBack() {
        final User               data;
        final User               result;
        final Role               roleResult;
        final User               read;
        final Collection<String> privileges;

        data = getUserWithRole();

        result = service.update(1L, data);
        read = service.getOne(result.getId())
            .get();

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("New name", read.getUsername());
        Assertions.assertEquals("email", read.getEmail());
        Assertions.assertEquals(false, read.getCredentialsExpired());
        Assertions.assertEquals(true, read.getEnabled());
        Assertions.assertEquals(false, read.getExpired());
        Assertions.assertEquals(false, read.getLocked());

        Assertions.assertEquals(1, IterableUtils.size(read.getRoles()));

        roleResult = read.getRoles()
            .iterator()
            .next();

        Assertions.assertNotNull(roleResult.getId());
        Assertions.assertEquals("ADMIN", roleResult.getName());

        Assertions.assertEquals(4, IterableUtils.size(roleResult.getPrivileges()));

        privileges = StreamSupport.stream(roleResult.getPrivileges()
            .spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privileges.contains("CREATE_DATA"));
        Assertions.assertTrue(privileges.contains("READ_DATA"));
        Assertions.assertTrue(privileges.contains("UPDATE_DATA"));
        Assertions.assertTrue(privileges.contains("DELETE_DATA"));
    }

    @Test
    @DisplayName("Returns the updated data when updating with a role")
    public void testUpdate_WithRole_ReturnedData() {
        final User               data;
        final User               result;
        final Role               roleResult;
        final Collection<String> privileges;

        data = getUserWithRole();

        result = service.update(1L, data);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("New name", result.getUsername());
        Assertions.assertEquals("email", result.getEmail());
        Assertions.assertEquals(false, result.getCredentialsExpired());
        Assertions.assertEquals(true, result.getEnabled());
        Assertions.assertEquals(false, result.getExpired());
        Assertions.assertEquals(false, result.getLocked());

        Assertions.assertEquals(1, IterableUtils.size(result.getRoles()));

        roleResult = result.getRoles()
            .iterator()
            .next();

        Assertions.assertNotNull(roleResult.getId());
        Assertions.assertEquals("ADMIN", roleResult.getName());

        Assertions.assertEquals(4, IterableUtils.size(roleResult.getPrivileges()));

        privileges = StreamSupport.stream(roleResult.getPrivileges()
            .spliterator(), false)
            .map(Privilege::getName)
            .collect(Collectors.toList());

        Assertions.assertTrue(privileges.contains("CREATE_DATA"));
        Assertions.assertTrue(privileges.contains("READ_DATA"));
        Assertions.assertTrue(privileges.contains("UPDATE_DATA"));
        Assertions.assertTrue(privileges.contains("DELETE_DATA"));
    }

    private final User getUser() {
        final DtoUser user;

        user = new DtoUser();
        user.setUsername("New name");
        user.setEmail("email");
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        return user;
    }

    private final User getUserWithRole() {
        final DtoUser          user;
        final DtoRole          role;
        final Collection<Role> roles;

        role = new DtoRole();
        role.setId(1L);

        roles = new ArrayList<>();
        roles.add(role);

        user = new DtoUser();
        user.setUsername("User");
        user.setEmail("email");
        user.setCredentialsExpired(false);
        user.setEnabled(true);
        user.setExpired(false);
        user.setLocked(false);

        user.setRoles(roles);

        return user;
    }

}
