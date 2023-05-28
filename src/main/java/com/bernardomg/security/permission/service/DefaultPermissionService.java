
package com.bernardomg.security.permission.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.bernardomg.security.permission.model.ImmutablePermissionsSet;
import com.bernardomg.security.permission.model.PermissionsSet;
import com.bernardomg.security.user.persistence.model.PersistentUserPermission;
import com.bernardomg.security.user.persistence.repository.UserPermissionRepository;

import lombok.NonNull;

public final class DefaultPermissionService implements PermissionService {

    private final Predicate<String>        isValid;

    private final UserPermissionRepository userPermsRepository;

    public DefaultPermissionService(final UserPermissionRepository userPermsRepo, final Predicate<String> valid) {
        super();

        userPermsRepository = userPermsRepo;
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
        // Transform into a map, with the resource as key, and the list of actions as value
        return userPermsRepository.findAllByUsername(username)
            .stream()
            .collect(Collectors.groupingBy(d -> d.getResource(),
                Collectors.mapping(PersistentUserPermission::getAction, Collectors.toList())));
    }

}
