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

package com.bernardomg.security.user.test.user.integration.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.DtoUser;
import com.bernardomg.security.user.model.User;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.model.PersistentUser;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.service.UserService;
import com.bernardomg.security.user.test.util.assertion.UserAssertions;
import com.bernardomg.security.user.test.util.model.UsersUpdate;

@IntegrationTest
@DisplayName("Role service - update")
@Sql({ "/db/queries/security/resource/single.sql", "/db/queries/security/action/crud.sql",
        "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/role_permission.sql" })
class ITUserServiceUpdate {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService    service;

    public ITUserServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("Adds no entity when updating")
    void testUpdate_AddsNoEntity() {
        final UserUpdate user;

        user = UsersUpdate.emailChange();

        service.update(1L, user);

        Assertions.assertThat(repository.count())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Updates persisted data")
    void testUpdate_PersistedData() {
        final UserUpdate     user;
        final PersistentUser entity;

        user = UsersUpdate.emailChange();

        service.update(1L, user);
        entity = repository.findAll()
            .iterator()
            .next();

        UserAssertions.isEqualTo(entity, PersistentUser.builder()
            .username("admin")
            .name("Admin")
            .email("email2@somewhere.com")
            .password("$2a$04$gV.k/KKIqr3oPySzs..bx.8absYRTpNe8AbHmPP90.ErW0ICGOsVW")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build());
    }

    @Test
    @DisplayName("Updates persisted data, ignoring case")
    void testUpdate_PersistedData_Case() {
        final UserUpdate     user;
        final PersistentUser entity;

        user = UsersUpdate.emailChangeUpperCase();

        service.update(1L, user);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(entity.getEmail())
            .isEqualTo("email2@somewhere.com");
    }

    @Test
    @DisplayName("Returns the updated data")
    void testUpdate_ReturnedData() {
        final UserUpdate user;
        final User       result;

        user = UsersUpdate.emailChange();

        result = service.update(1L, user);

        UserAssertions.isEqualTo(result, DtoUser.builder()
            .username("admin")
            .name("Admin")
            .email("email2@somewhere.com")
            .credentialsExpired(false)
            .enabled(true)
            .expired(false)
            .locked(false)
            .build());
    }

    @Test
    @DisplayName("Returns the updated data, ignoring case")
    void testUpdate_ReturnedData_Case() {
        final UserUpdate user;
        final User       result;

        user = UsersUpdate.emailChangeUpperCase();

        result = service.update(1L, user);

        Assertions.assertThat(result.getEmail())
            .isEqualTo("email2@somewhere.com");
    }

}
