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

package com.bernardomg.association.guest.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.guest.TestApplication;
import com.bernardomg.association.guest.domain.model.GuestProfile;
import com.bernardomg.association.guest.domain.repository.GuestProfileRepository;
import com.bernardomg.association.guest.test.configuration.data.annotation.GuestWithEmail;
import com.bernardomg.association.guest.test.configuration.data.annotation.ValidGuest;
import com.bernardomg.association.guest.test.configuration.factory.GuestConstants;
import com.bernardomg.association.guest.test.configuration.factory.GuestProfiles;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GuestProfileRepository - find one")
class ITGuestProfileRepositoryFindOne {

    @Autowired
    private GuestProfileRepository repository;

    @Test
    @DisplayName("With a guest, it is returned")
    @ValidGuest
    void testFindOne() {
        final Optional<GuestProfile> guest;

        // WHEN
        guest = repository.findOne(GuestConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .contains(GuestProfiles.valid());
    }

    @Test
    @DisplayName("With no guest, nothing is returned")
    void testFindOne_NoData() {
        final Optional<GuestProfile> guest;

        // WHEN
        guest = repository.findOne(GuestConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .isEmpty();
    }

    @Test
    @DisplayName("With a guest with contact method, it is returned")
    @GuestWithEmail
    void testFindOne_WithContactMethod() {
        final Optional<GuestProfile> guest;

        // WHEN
        guest = repository.findOne(GuestConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .contains(GuestProfiles.withEmail());
    }

}
