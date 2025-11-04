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

package com.bernardomg.association.security.user.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.contact.test.configuration.factory.Contacts;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.association.security.user.usecase.service.DefaultUserContactService;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.security.user.domain.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("User person service - get person")
class TestUserContactServiceGetAvailableMembers {

    @Mock
    private ContactRepository         personRepository;

    @InjectMocks
    private DefaultUserContactService service;

    @Mock
    private UserContactRepository     userContactRepository;

    @Mock
    private UserRepository            userRepository;

    @Test
    @DisplayName("When there are not assigned persons, these are returned")
    void testGetContact() {
        final Page<Contact> contacts;
        final Page<Contact> existing;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(Contacts.noMembership()), 0, 0, 0, 0, 0, false, false, sorting);
        given(userContactRepository.findAllNotAssigned(pagination, sorting)).willReturn(existing);

        // WHEN
        contacts = service.getAvailableContact(pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Contacts.noMembership());
    }

    @Test
    @DisplayName("When there are no not assigned persons, nothing is returned")
    void testGetContact_NoContact() {
        final Page<Contact> contacts;
        final Page<Contact> existing;
        final Pagination    pagination;
        final Sorting       sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(userContactRepository.findAllNotAssigned(pagination, sorting)).willReturn(existing);

        // WHEN
        contacts = service.getAvailableContact(pagination, sorting);

        // THEN
        Assertions.assertThat(contacts)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
