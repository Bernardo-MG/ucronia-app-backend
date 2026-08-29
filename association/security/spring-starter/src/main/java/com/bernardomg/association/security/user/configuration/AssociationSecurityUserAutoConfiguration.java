/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.security.user.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationTrustResolver;

import com.bernardomg.association.security.account.usecase.AccountProfileService;
import com.bernardomg.association.security.account.usecase.DefaultAccountProfileService;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.AssociationUserSpringRepository;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.JpaUserProfileRepository;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserAssignedProfileSpringRepository;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserProfileSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.association.security.user.usecase.service.DefaultUserProfileService;
import com.bernardomg.association.security.user.usecase.service.UserProfileService;
import com.bernardomg.security.domain.user.repository.UserRepository;
import com.bernardomg.security.springframework.session.SecurityContextHolderUsernameInSessionProvider;
import com.bernardomg.security.usecase.session.UsernameInSessionProvider;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.association.security.user.adapter.outbound.rest.controller",
        "com.bernardomg.association.security.user.adapter.inbound.jpa" })
public class AssociationSecurityUserAutoConfiguration {

    @Bean("accountProfileService")
    public AccountProfileService getAccountProfileService(final UserProfileRepository userProfileRepository,
            final AuthenticationTrustResolver trustResolver) {
        final UsernameInSessionProvider usernameProvider;

        // TODO: maybe it should be injected
        usernameProvider = new SecurityContextHolderUsernameInSessionProvider(trustResolver);
        return new DefaultAccountProfileService(userProfileRepository, usernameProvider);
    }

    @Bean("userProfileRepository")
    public UserProfileRepository getUserProfileRepository(
            final UserAssignedProfileSpringRepository userProfileSpringRepository,
            final AssociationUserSpringRepository userSpringRepository,
            final UserProfileSpringRepository profileSpringRepository) {
        return new JpaUserProfileRepository(userProfileSpringRepository, userSpringRepository, profileSpringRepository);
    }

    @Bean("userProfileService")
    public UserProfileService getUserProfileService(final UserRepository userRepository,
            final UserProfileRepository userProfileRepository) {
        return new DefaultUserProfileService(userRepository, userProfileRepository);
    }

}
