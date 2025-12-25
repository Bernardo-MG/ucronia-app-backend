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

package com.bernardomg.association.sponsor.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.test.configuration.data.annotation.ValidContact;
import com.bernardomg.association.contact.test.configuration.factory.ContactConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.QuerySponsorSpringRepository;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.data.annotation.ValidSponsor;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SponsorRepository - delete")
class ITSponsorRepositoryDelete {

    @Autowired
    private ContactSpringRepository      contactSpringRepository;

    @Autowired
    private SponsorRepository            repository;

    @Autowired
    private QuerySponsorSpringRepository springRepository;

    public ITSponsorRepositoryDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting an sponsor, it is deleted")
    @ValidSponsor
    void testDelete_Active() {
        // WHEN
        repository.delete(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

    @Test
    @DisplayName("When deleting an sponsor, the contact is deleted")
    @ValidSponsor
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

    @Test
    @DisplayName("With a contact with no sponsor role, nothing is deleted")
    @ValidContact
    void testDelete_NoSponsor() {
        // WHEN
        repository.delete(ContactConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
    }

}
