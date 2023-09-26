
package com.bernardomg.security.permission.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.bernardomg.security.permission.model.ImmutablePermissionsSet;
import com.bernardomg.security.permission.model.PermissionsSet;
import com.bernardomg.security.permission.persistence.model.PersistentUserGrantedPermission;
import com.bernardomg.security.permission.persistence.repository.UserGrantedPermissionRepository;

import lombok.NonNull;

/**
 * {@link PermissionService} based on the permissions granted to a user. This is actually a view, which shows all the
 * permissions which a user has, based on the roles configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class UserGrantedPermissionService implements PermissionService {

    private final Predicate<String>               isValid;

    private final UserGrantedPermissionRepository repository;

    public UserGrantedPermissionService(final UserGrantedPermissionRepository userPermsRepo,
            final Predicate<String> usernameValid) {
        super();

        repository = userPermsRepo;
        isValid = usernameValid;
    }

    @Override
    public final PermissionsSet getPermissions(@NonNull final String username) {
        final Map<String, List<String>> permissions;

        // TODO: Move cache to controller

        if (isValid.test(username)) {
            permissions = getPermissionsMap(username);
        } else {
            permissions = Collections.emptyMap();
        }

        return ImmutablePermissionsSet.builder()
            .username(username)
            .permissions(permissions)
            .build();
    }

    private final Map<String, List<String>> getPermissionsMap(final String username) {
        Function<PersistentUserGrantedPermission, String> resourceMapper;
        Function<PersistentUserGrantedPermission, String> actionMapper;

        // Resource name in lower case
        resourceMapper = PersistentUserGrantedPermission::getResource;
        resourceMapper = resourceMapper.andThen(String::toLowerCase);

        // Action name in lower case
        actionMapper = PersistentUserGrantedPermission::getAction;
        actionMapper = actionMapper.andThen(String::toLowerCase);

        // Transform into a map, with the resource as key, and the list of actions as value
        return repository.findAllByUsername(username)
            .stream()
            .collect(Collectors.groupingBy(resourceMapper, Collectors.mapping(actionMapper, Collectors.toList())));
    }

}
