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

package com.bernardomg.security.initializer.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.bernardomg.security.initializer.TestRolesInitializer;
import com.bernardomg.security.initializer.TestUsersInitializer;
import com.bernardomg.security.user.persistence.repository.ActionRepository;
import com.bernardomg.security.user.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.persistence.repository.UserRoleRepository;

/**
 * Security configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class SecurityInitializerConfig {

    public SecurityInitializerConfig() {
        super();
    }

    @Bean("testRolesInitializer")
    @ConditionalOnProperty(prefix = "initialize.test", name = "user", havingValue = "true")
    public TestRolesInitializer getTestRolesInitializer(final ActionRepository actionRepository,
            final ResourceRepository resourceRepository, final RoleRepository roleRepository,
            final RolePermissionRepository rolePermissionRepository) {
        return new TestRolesInitializer(actionRepository, resourceRepository, roleRepository, rolePermissionRepository);
    }

    @Bean("testUsersInitializer")
    @DependsOn("testRolesInitializer")
    @ConditionalOnProperty(prefix = "initialize.test", name = "user", havingValue = "true")
    public TestUsersInitializer getTestUsersInitializer(final UserRepository userRepository,
            final UserRoleRepository userRoleRepository) {
        return new TestUsersInitializer(userRepository, userRoleRepository);
    }

}
