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

package com.bernardomg.association.security.account.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.security.account.domain.model.ProfileAccount.Profile;
import com.bernardomg.association.security.account.domain.repository.AccountProfileRepository;
import com.bernardomg.association.security.account.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.security.account.test.configuration.factory.Profiles;
import com.bernardomg.association.security.user.test.TestApplication;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AccountProfileRepository - find one")
class ITAccountProfileRepositoryFindOne {

    @Autowired
    private AccountProfileRepository repository;

    @Test
    @DisplayName("With a profile, it is returned")
    @ValidProfile
    void testFindOne() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .contains(Profiles.valid());
    }

    @Test
    @DisplayName("With no profile, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .isEmpty();
    }

}
