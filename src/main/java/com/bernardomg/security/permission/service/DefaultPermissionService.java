
package com.bernardomg.security.permission.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.bernardomg.security.permission.model.ImmutablePermissionsSet;
import com.bernardomg.security.permission.model.PermissionsSet;
import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.NonNull;

public final class DefaultPermissionService implements PermissionService {

    private final Predicate<String> isValid;

    private final UserRepository    userRepository;

    public DefaultPermissionService(final UserRepository userRepo, final Predicate<String> valid) {
        super();

        userRepository = userRepo;
        isValid = valid;
    }

    @Override
    public PermissionsSet getPermissions(@NonNull final String username) {
        final Map<String, List<String>> permissions;

        if (isValid.test(username)) {
            permissions = getPermissionsMap(username);
        } else {
            permissions = Collections.emptyMap();
        }

        return new ImmutablePermissionsSet(username, permissions);
    }

    private final Map<String, List<String>> getPermissionsMap(final String username) {
        final Collection<Permission> permissions;

        permissions = userRepository.findPermissionsByUsername(username);

        // Transform into a map, with the resource as key, and the list of actions as value
        return permissions.stream()
            .collect(Collectors.groupingBy(d -> d.getResource(),
                Collectors.mapping(Permission::getAction, Collectors.toList())));
    }

}
