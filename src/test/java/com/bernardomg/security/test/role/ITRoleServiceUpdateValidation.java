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
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.model.DtoRole;
import com.bernardomg.security.model.Role;
import com.bernardomg.security.service.RoleService;
import com.bernardomg.validation.exception.ValidationException;

@IntegrationTest
@DisplayName("Role service - update validation")
public class ITRoleServiceUpdateValidation {

    @Autowired
    private RoleService service;

    public ITRoleServiceUpdateValidation() {
        super();
    }

    @Test
    @DisplayName("Throws an exception when the role doesn't exist")
    public void testUpdate_NotExistingRole() {
        final Executable executable;
        final Exception  exception;
        final Role       data;

        data = getRoleWithNoPrivileges();

        executable = () -> service.update(data);

        exception = Assertions.assertThrows(ValidationException.class, executable);

        Assertions.assertEquals("error.id.notExisting", exception.getMessage());
    }

    private final Role getRoleWithNoPrivileges() {
        final DtoRole role;

        role = new DtoRole();
        role.setId(1L);
        role.setName("Role");

        return role;
    }

}
