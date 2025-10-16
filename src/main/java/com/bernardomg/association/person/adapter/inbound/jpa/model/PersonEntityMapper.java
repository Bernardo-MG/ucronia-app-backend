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

package com.bernardomg.association.person.adapter.inbound.jpa.model;

import java.util.Collection;
import java.util.Optional;

import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.Person.Membership;
import com.bernardomg.association.person.domain.model.Person.PersonContact;
import com.bernardomg.association.person.domain.model.PersonName;

/**
 * Author repository mapper.
 */
public final class PersonEntityMapper {

    public static final ContactMethod toDomain(final ContactMethodEntity entity) {
        return new ContactMethod(entity.getNumber(), entity.getName());
    }

    public static final PersonContact toDomain(final PersonContactMethodEntity entity) {
        final ContactMethod method;

        method = toDomain(entity.getContactMethod());
        return new PersonContact(method, entity.getContact());
    }

    public static final Person toDomain(final PersonEntity entity) {
        final PersonName                name;
        final Optional<Membership>      membership;
        final Collection<PersonContact> contacts;

        name = new PersonName(entity.getFirstName(), entity.getLastName());
        if (!entity.getMember()) {
            membership = Optional.empty();
        } else {
            membership = Optional.of(new Membership(entity.getActive(), entity.getRenewMembership()));
        }

        contacts = entity.getContacts()
            .stream()
            .map(PersonEntityMapper::toDomain)
            .toList();

        return new Person(entity.getIdentifier(), entity.getNumber(), name, entity.getBirthDate(), membership,
            contacts);
    }

    private PersonEntityMapper() {
        super();
    }

}
