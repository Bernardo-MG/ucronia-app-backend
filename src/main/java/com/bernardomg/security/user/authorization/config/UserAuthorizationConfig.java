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

package com.bernardomg.security.user.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.security.permission.model.mapper.PermissionMapper;
import com.bernardomg.security.permission.persistence.repository.RoleGrantedPermissionRepository;
import com.bernardomg.security.user.authorization.persistence.repository.ActionRepository;
import com.bernardomg.security.user.authorization.persistence.repository.ResourceRepository;
import com.bernardomg.security.user.authorization.persistence.repository.RolePermissionRepository;
import com.bernardomg.security.user.authorization.persistence.repository.RoleRepository;
import com.bernardomg.security.user.authorization.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.authorization.service.ActionService;
import com.bernardomg.security.user.authorization.service.DefaultActionService;
import com.bernardomg.security.user.authorization.service.DefaultResourceService;
import com.bernardomg.security.user.authorization.service.DefaultRolePermissionService;
import com.bernardomg.security.user.authorization.service.DefaultRoleService;
import com.bernardomg.security.user.authorization.service.DefaultUserRoleService;
import com.bernardomg.security.user.authorization.service.ResourceService;
import com.bernardomg.security.user.authorization.service.RolePermissionService;
import com.bernardomg.security.user.authorization.service.RoleService;
import com.bernardomg.security.user.authorization.service.UserRoleService;
import com.bernardomg.security.user.model.mapper.ActionMapper;
import com.bernardomg.security.user.model.mapper.ResourceMapper;
import com.bernardomg.security.user.model.mapper.RoleMapper;
import com.bernardomg.security.user.model.mapper.RolePermissionMapper;
import com.bernardomg.security.user.model.mapper.UserRoleMapper;
import com.bernardomg.security.user.persistence.repository.UserRepository;

/**
 * Password handling configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class UserAuthorizationConfig {

    public UserAuthorizationConfig() {
        super();
    }

    @Bean("actionService")
    public ActionService getActionService(final ActionRepository repository, final ActionMapper mapper) {
        return new DefaultActionService(repository, mapper);
    }

    @Bean("resourceService")
    public ResourceService getResourceService(final ResourceMapper mapper, final ResourceRepository repository) {
        return new DefaultResourceService(mapper, repository);
    }

    @Bean("rolePermissionService")
    public RolePermissionService getRolePermissionService(final RoleRepository roleRepo,
            final ResourceRepository resourceRepo, final ActionRepository actionRepo,
            final RolePermissionRepository roleActionsRepo,
            final RoleGrantedPermissionRepository roleGrantedPermissionRepo, final RolePermissionMapper rolePermMapper,
            final PermissionMapper permMapper) {
        return new DefaultRolePermissionService(roleRepo, resourceRepo, actionRepo, roleActionsRepo,
            roleGrantedPermissionRepo, rolePermMapper, permMapper);
    }

    @Bean("roleService")
    public RoleService getRoleService(final RoleRepository roleRepo, final RolePermissionRepository roleActionsRepo,
            final UserRoleRepository userRoleRepo, final RoleMapper roleMapper) {
        return new DefaultRoleService(roleRepo, roleActionsRepo, userRoleRepo, roleMapper);
    }

    @Bean("userRoleService")
    public UserRoleService getUserRoleService(final UserRepository userRepo, final RoleRepository roleRepo,
            final UserRoleRepository userRoleRepo, final UserRoleMapper roleMapper) {
        return new DefaultUserRoleService(userRepo, roleRepo, userRoleRepo, roleMapper);
    }

}
