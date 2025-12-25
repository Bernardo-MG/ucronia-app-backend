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

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.QueryMemberContactSpringRepository;
import com.bernardomg.association.member.domain.repository.MemberContactRepository;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberContactRepository - delete")
class ITMemberContactRepositoryDelete {

    @Autowired
    private ContactSpringRepository            contactSpringRepository;

    @Autowired
    private MemberContactRepository            repository;

    @Autowired
    private QueryMemberContactSpringRepository springRepository;

    public ITMemberContactRepositoryDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting a guest, it is deleted")
    @ValidContact
    void testDelete_Active() {
        // WHEN
        repository.delete(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("When deleting a guest, the contact is deleted")
    @ValidContact
    void testDelete_Active_Contact() {
        // WHEN
        repository.delete(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(contactSpringRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

}
