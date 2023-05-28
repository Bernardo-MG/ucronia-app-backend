
package com.bernardomg.security.permission.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.bernardomg.security.permission.model.ImmutablePermissionsSet;
import com.bernardomg.security.permission.model.PermissionsSet;
import com.bernardomg.security.permission.validation.PersistentUserValidPredicate;
import com.bernardomg.security.user.persistence.model.PersistentUserGrantedPermission;
import com.bernardomg.security.user.persistence.repository.UserGrantedPermissionRepository;
import com.bernardomg.security.user.persistence.repository.UserRepository;

import lombok.NonNull;

public final class DefaultPermissionService implements PermissionService {

    private final Predicate<String>               isValid;

    private final UserGrantedPermissionRepository userPermsRepository;

    public DefaultPermissionService(final UserGrantedPermissionRepository userPermsRepo,
            final UserRepository userRepository) {
        super();

        userPermsRepository = userPermsRepo;
        isValid = new PersistentUserValidPredicate(userRepository);
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
                Collectors.mapping(PersistentUserGrantedPermission::getAction, Collectors.toList())));
    }

}
