
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
        final Collection<Permission>    permissions;
        final Map<String, List<String>> groupedPerms;

        if (logged) {
            token = tokenProvider.generateToken(username);
            permissions = userRepository.findPermissionsByUsername(username);

            groupedPerms = permissions.stream()
                .collect(Collectors.groupingBy(d -> d.getResource(),
                    Collectors.mapping(Permission::getAction, Collectors.toList())));

            status = new ImmutableFullLoginStatus(username, logged, token, groupedPerms);
        } else {
            status = new ImmutableLoginStatus(username, logged);
        }

        return status;
    }

}
