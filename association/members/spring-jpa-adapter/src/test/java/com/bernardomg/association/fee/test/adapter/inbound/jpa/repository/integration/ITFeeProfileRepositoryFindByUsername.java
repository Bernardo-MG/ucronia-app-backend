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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.FeeProfile;
import com.bernardomg.association.fee.domain.repository.FeeProfileRepository;
import com.bernardomg.association.fee.test.configuration.factory.FeeProfiles;
import com.bernardomg.association.fee.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUser;
import com.bernardomg.association.security.user.test.configuration.data.annotation.ValidUserWithProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AccountProfileRepository - find by username")
class ITFeeProfileRepositoryFindByUsername {

    @Autowired
    private FeeProfileRepository repository;

    @Test
    @DisplayName("When the user exists it is returned")
    @ValidUserWithProfile
    void testFindByUsername() {
        final Optional<FeeProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .contains(FeeProfiles.valid());
    }

    @Test
    @DisplayName("When no data exists nothing is returned")
    void testFindByUsername_NoData() {
        final Optional<FeeProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .isEmpty();
    }

    @Test
    @DisplayName("When the profile doesn't exist nothing is returned")
    @ValidUser
    void testFindByUsername_NoMember() {
        final Optional<FeeProfile> profile;

        // WHEN
        profile = repository.findByUsername(UserConstants.USERNAME);

        // THEN
        Assertions.assertThat(profile)
            .isEmpty();
    }

}
