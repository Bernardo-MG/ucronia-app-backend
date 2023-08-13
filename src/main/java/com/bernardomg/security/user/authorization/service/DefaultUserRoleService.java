
package com.bernardomg.security.user.authorization.service;

import java.util.Objects;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import com.bernardomg.security.user.authorization.persistence.model.PersistentUserRole;
import com.bernardomg.security.user.authorization.persistence.repository.RoleRepository;
import com.bernardomg.security.user.authorization.persistence.repository.UserRoleRepository;
import com.bernardomg.security.user.model.DtoUserRole;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.UserRole;
import com.bernardomg.security.user.model.mapper.UserRoleMapper;
import com.bernardomg.security.user.persistence.repository.UserRepository;
import com.bernardomg.security.user.validation.AddUserRoleValidator;
import com.bernardomg.validation.Validator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultUserRoleService implements UserRoleService {

    private static final String       PERMISSION_SET_CACHE_NAME = "security_permission_set";

    private static final String       ROLE_CACHE_NAME           = "security_user_role";

    private final RoleRepository      roleRepository;

    private final UserRoleMapper      userRoleMapper;

    private final UserRoleRepository  userRoleRepository;

    private final Validator<UserRole> validatorAddUserRole;

    private final Validator<UserRole> validatorRemoveUserRole;

    public DefaultUserRoleService(final UserRepository userRepo, final RoleRepository roleRepo,
            final UserRoleRepository userRoleRepo, final UserRoleMapper roleMapper) {
        super();

        userRoleRepository = Objects.requireNonNull(userRoleRepo);
        roleRepository = Objects.requireNonNull(roleRepo);
        userRoleMapper = Objects.requireNonNull(roleMapper);

        validatorAddUserRole = new AddUserRoleValidator(userRepo, roleRepo);
        validatorRemoveUserRole = new AddUserRoleValidator(userRepo, roleRepo);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:UPDATE')")
    @CacheEvict(cacheNames = { PERMISSION_SET_CACHE_NAME, ROLE_CACHE_NAME }, allEntries = true)
    public final UserRole addRole(final long userId, final long roleId) {
        final PersistentUserRole userRoleSample;
        final UserRole           userRole;
        final PersistentUserRole created;

        log.debug("Adding role {} to user {}", roleId, userId);

        userRole = DtoUserRole.builder()
            .userId(userId)
            .roleId(roleId)
            .build();
        validatorAddUserRole.validate(userRole);

        userRoleSample = getUserRoleSample(userId, roleId);

        // Persist relationship
        created = userRoleRepository.save(userRoleSample);

        return userRoleMapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:READ')")
    @Cacheable(cacheNames = ROLE_CACHE_NAME)
    public final Iterable<Role> getRoles(final long userId, final Pageable pageable) {

        log.debug("Getting roles for user {} and pagination {}", userId, pageable);

        return roleRepository.findForUser(userId, pageable);
    }

    @Override
    @PreAuthorize("hasAuthority('USER:UPDATE')")
    @CacheEvict(cacheNames = { PERMISSION_SET_CACHE_NAME, ROLE_CACHE_NAME }, allEntries = true)
    public final UserRole removeRole(final long userId, final long roleId) {
        final PersistentUserRole userRoleSample;
        final UserRole           userRole;

        log.debug("Removing role {} from user {}", roleId, userId);

        userRole = DtoUserRole.builder()
            .userId(userId)
            .roleId(roleId)
            .build();
        validatorRemoveUserRole.validate(userRole);

        userRoleSample = getUserRoleSample(userId, roleId);

        // Persist relationship
        userRoleRepository.delete(userRoleSample);

        return userRoleMapper.toDto(userRoleSample);
    }

    private final PersistentUserRole getUserRoleSample(final long user, final long role) {
        return PersistentUserRole.builder()
            .userId(user)
            .roleId(role)
            .build();
    }

}
