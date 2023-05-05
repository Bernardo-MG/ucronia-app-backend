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
import com.bernardomg.security.data.persistence.repository.PrivilegeRepository;
import com.bernardomg.security.data.persistence.repository.RoleRepository;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.data.service.UserService;

@IntegrationTest
@DisplayName("User service - delete with role and privileges")
@Sql({ "/db/queries/security/role/single.sql", "/db/queries/security/user/single.sql",
        "/db/queries/security/relationship/user_role.sql" })
public class ITUserServiceDeleteWithRoleNoPrivileges {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserRepository      repository;

    @Autowired
    private RoleRepository      roleRepository;

    @Autowired
    private UserService         service;

    public ITUserServiceDeleteWithRoleNoPrivileges() {
        super();
    }

    @Test
    @DisplayName("Does not remove roles or privileges when deleting")
    public void testDelete_DoesNotRemoveRelations() {
        service.delete(1L);

        Assertions.assertEquals(0L, repository.count());
        Assertions.assertEquals(1L, roleRepository.count());
        Assertions.assertEquals(0L, privilegeRepository.count());
    }

}
