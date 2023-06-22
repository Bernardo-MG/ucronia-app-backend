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

package com.bernardomg.security.user.test.role.integration.service;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.test.config.annotation.IntegrationTest;
import com.bernardomg.security.user.model.request.DtoRoleUpdateRequest;
import com.bernardomg.security.user.model.request.RoleUpdateRequest;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

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
        final ThrowingCallable  executable;
        final FieldFailure      failure;
        final RoleUpdateRequest data;

        data = getRoleWithNoActions();

        executable = () -> service.update(data);

        failure = FieldFailure.of("id.notExisting", "id", "notExisting", 1L);

        ValidationAssertions.assertThatFieldFails(executable, failure);
    }

    private final RoleUpdateRequest getRoleWithNoActions() {
        return DtoRoleUpdateRequest.builder()
            .id(1L)
            .name("Role")
            .build();
    }

}
