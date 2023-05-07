
package com.bernardomg.security.login.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bernardomg.security.data.model.Permission;
import com.bernardomg.security.data.persistence.repository.UserRepository;
import com.bernardomg.security.login.model.ImmutableFullLoginStatus;
import com.bernardomg.security.login.model.ImmutableLoginStatus;
import com.bernardomg.security.login.model.LoginStatus;
import com.bernardomg.security.token.provider.TokenProvider;

public final class FullLoginStatusProvider implements LoginStatusProvider {

    /**
     * Token provider, creates authentication tokens.
     */
    private final TokenProvider  tokenProvider;

    private final UserRepository userRepository;

    public FullLoginStatusProvider(final TokenProvider tokenProv, final UserRepository userRepo) {
        super();

        tokenProvider = tokenProv;
        userRepository = userRepo;
    }

    @Override
    public final LoginStatus getStatus(final String username, final Boolean logged) {
        final LoginStatus               status;
        final String                    token;
        final Map<String, List<String>> permissions;

        if (logged) {
            token = tokenProvider.generateToken(username);
            permissions = getPermissions(username);

            status = new ImmutableFullLoginStatus(username, logged, token, permissions);
        } else {
            status = new ImmutableLoginStatus(username, logged);
        }

        return status;
    }

    private final Map<String, List<String>> getPermissions(final String username) {
        final Collection<Permission> permissions;

        permissions = userRepository.findPermissionsByUsername(username);

        // Transform into a map, with the resource as key, and the list of actions as value
        return permissions.stream()
            .collect(Collectors.groupingBy(d -> d.getResource(),
                Collectors.mapping(Permission::getAction, Collectors.toList())));
    }

}
