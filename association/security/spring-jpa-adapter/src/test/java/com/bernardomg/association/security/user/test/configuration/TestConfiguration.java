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

package com.bernardomg.association.security.user.test.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.JpaUserProfileRepository;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserInnerProfileSpringRepository;
import com.bernardomg.association.security.user.adapter.inbound.jpa.repository.UserProfileSpringRepository;
import com.bernardomg.association.security.user.domain.repository.UserProfileRepository;
import com.bernardomg.security.user.adapter.inbound.jpa.repository.UserSpringRepository;

@Configuration
@EnableJpaRepositories(basePackages = { "com.bernardomg.association.security.user.adapter.inbound.jpa",
        "com.bernardomg.security.user.adapter.inbound.jpa", "com.bernardomg.security.role.adapter.inbound.jpa",
        "com.bernardomg.security.permission.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa" })
@EntityScan(basePackages = { "com.bernardomg.association.security.user.adapter.inbound.jpa",
        "com.bernardomg.security.user.adapter.inbound.jpa", "com.bernardomg.security.role.adapter.inbound.jpa",
        "com.bernardomg.security.permission.adapter.inbound.jpa",
        "com.bernardomg.association.profile.adapter.inbound.jpa" })
public class TestConfiguration {

    @Bean("userProfileRepository")
    public UserProfileRepository getUserProfileRepository(final UserProfileSpringRepository userProfileSpringRepository,
            final UserSpringRepository userSpringRepository,
            final UserInnerProfileSpringRepository profileSpringRepository) {
        return new JpaUserProfileRepository(userProfileSpringRepository, userSpringRepository, profileSpringRepository);
    }

}
