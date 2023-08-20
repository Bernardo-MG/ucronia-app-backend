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

package com.bernardomg.security.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bernardomg.security.email.sender.SecurityMessageSender;
import com.bernardomg.security.permission.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.token.store.TokenStore;
import com.bernardomg.security.user.model.mapper.RoleMapper;
import com.bernardomg.security.user.model.mapper.UserMapper;
import com.bernardomg.security.user.model.mapper.UserRoleMapper;
import com.bernardomg.security.user.persistence.repository.RoleRepository;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.service.DefaultRoleService;
import com.bernardomg.security.user.service.DefaultUserRoleService;
import com.bernardomg.security.user.service.DefaultUserService;
import com.bernardomg.security.user.service.RoleService;
import com.bernardomg.security.user.service.UserRoleService;
import com.bernardomg.security.user.service.UserService;

/**
 * Password handling configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class UserConfig {

    public UserConfig() {
        super();
    }

    @Bean("roleService")
    public RoleService getRoleService(final RoleRepository roleRepo, final RolePermissionRepository rolePermissionRepo,
            final UserRoleRepository userRoleRepo, final RoleMapper roleMapper) {
        return new DefaultRoleService(roleRepo, rolePermissionRepo, userRoleRepo, roleMapper);
    }

    @Bean("userRoleService")
    public UserRoleService getUserRoleService(final UserRepository userRepo, final RoleRepository roleRepo,
            final UserRoleRepository userRoleRepo, final UserRoleMapper roleMapper) {
        return new DefaultUserRoleService(userRepo, roleRepo, userRoleRepo, roleMapper);
    }

    @Bean("userService")
    public UserService getUserService(final UserRepository userRepo, final SecurityMessageSender mSender,
            final TokenStore tStore, final PasswordEncoder passEncoder, final UserMapper userMapper) {
        return new DefaultUserService(userRepo, mSender, tStore, passEncoder, userMapper, "user_registered");
    }

}
