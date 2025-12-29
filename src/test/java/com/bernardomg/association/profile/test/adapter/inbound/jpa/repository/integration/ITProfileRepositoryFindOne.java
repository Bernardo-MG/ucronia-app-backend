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

package com.bernardomg.association.profile.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.profile.domain.model.Profile;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.EmailContactMethod;
import com.bernardomg.association.profile.test.configuration.data.annotation.ProfileWithType;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.WithContactChannel;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.profile.test.configuration.factory.Profiles;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ProfileRepository - find one")
class ITProfileRepositoryFindOne {

    @Autowired
    private ProfileRepository repository;

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

    @Test
    @DisplayName("With a profile having a profile, it is returned")
    @EmailContactMethod
    @WithContactChannel
    void testFindOne_WithContact() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .contains(Profiles.withEmail());
    }

    @Test
    @DisplayName("With a profile with type, it is returned")
    @ProfileWithType
    void testFindOne_WithType() {
        final Optional<Profile> profile;

        // WHEN
        profile = repository.findOne(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(profile)
            .contains(Profiles.withType(ProfileConstants.TYPE_MEMBER));
    }

}
