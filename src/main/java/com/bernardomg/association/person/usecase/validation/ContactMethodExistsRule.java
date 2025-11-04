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

package com.bernardomg.association.person.usecase.validation;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.person.domain.model.Contact;
import com.bernardomg.association.person.domain.model.Contact.PersonContact;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the contact method exists.
 */
public final class ContactMethodExistsRule implements FieldRule<Contact> {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(ContactMethodExistsRule.class);

    private final ContactMethodRepository contactMethodRepository;

    public ContactMethodExistsRule(final ContactMethodRepository contactMethodRepo) {
        super();

        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
    }

    @Override
    public final Optional<FieldFailure> check(final Contact contact) {
        // TODO: what about multiple failues?
        return contact.contacts()
            .stream()
            .map(PersonContact::method)
            .map(this::check)
            .filter(Optional::isPresent)
            .map(o -> o.orElse(null))
            .findFirst();
    }

    private final Optional<FieldFailure> check(final ContactMethod contactMethod) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Existing contact method name {}", contactMethod.name());
            fieldFailure = new FieldFailure("notExisting", "contact", contactMethod.number());
            failure = Optional.of(fieldFailure);
        } else {
            failure = Optional.empty();
        }

        return failure;
    }

}
