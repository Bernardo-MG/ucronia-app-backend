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

package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.domain.model.MemberContact;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberContactRepository - find one")
class ITMemberContactRepositoryFindOne {

    @Autowired
    private MemberContactRepository repository;

    @Test
    @DisplayName("With an guest, it is returned")
    @ValidContact
    void testFindOne() {
        final Optional<MemberContact> guest;

        // WHEN
        guest = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .contains(MemberContacts.active());
    }

    @Test
    @DisplayName("With no guest, nothing is returned")
    void testFindOne_NoData() {
        final Optional<MemberContact> guest;

        // WHEN
        guest = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .isEmpty();
    }

    @Test
    @DisplayName("With a guest with no guest role, it returns nothing")
    @ValidContact
    void testFindOne_NoMemberContactship() {
        final Optional<MemberContact> guest;

        // WHEN
        guest = repository.findOne(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(guest)
            .isEmpty();
    }

}
