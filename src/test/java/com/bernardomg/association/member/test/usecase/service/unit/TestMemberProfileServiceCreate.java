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

package com.bernardomg.association.member.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberContacts;
import com.bernardomg.association.member.usecase.service.DefaultMemberProfileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberProfileService - create")
class TestMemberProfileServiceCreate {

    @Mock
    private MemberProfileRepository     memberProfileRepository;

    @InjectMocks
    private DefaultMemberProfileService service;

    public TestMemberProfileServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a guest having padding whitespaces in first and last name, these whitespaces are removed and the profile is persisted")
    void testCreate_Padded_PersistedData() {
        final MemberProfile guest;

        // GIVEN
        guest = MemberContacts.padded();

        // WHEN
        service.create(guest);

        // THEN
        verify(memberProfileRepository).save(MemberContacts.toCreate());
    }

    @Test
    @DisplayName("With a valid guest, the profile is persisted")
    void testCreate_PersistedData() {
        final MemberProfile guest;

        // GIVEN
        guest = MemberContacts.toCreate();

        // WHEN
        service.create(guest);

        // THEN
        verify(memberProfileRepository).save(MemberContacts.toCreate());
    }

    @Test
    @DisplayName("With a valid guest, the created profile is returned")
    void testCreate_ReturnedData() {
        final MemberProfile guest;
        final MemberProfile created;

        // GIVEN
        guest = MemberContacts.toCreate();

        given(memberProfileRepository.save(guest)).willReturn(MemberContacts.active());

        // WHEN
        created = service.create(guest);

        // THEN
        Assertions.assertThat(created)
            .as("profile")
            .isEqualTo(MemberContacts.active());
    }

}
