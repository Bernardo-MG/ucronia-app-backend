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

package com.bernardomg.association.person.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.person.domain.exception.MissingContactMethodException;
import com.bernardomg.association.person.domain.model.ContactMethod;
import com.bernardomg.association.person.domain.repository.ContactMethodRepository;
import com.bernardomg.association.person.usecase.validation.ContactMethodNameNotEmptyRule;
import com.bernardomg.association.person.usecase.validation.ContactMethodNameNotExistsForAnotherRule;
import com.bernardomg.association.person.usecase.validation.ContactMethodNameNotExistsRule;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

/**
 * Default implementation of the ContactMethod service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultContactMethodService implements ContactMethodService {

    /**
     * Logger for the class.
     */
    private static final Logger            log = LoggerFactory.getLogger(DefaultContactMethodService.class);

    private final ContactMethodRepository  contactMethodRepository;

    private final Validator<ContactMethod> createContactMethodValidator;

    private final Validator<ContactMethod> updateContactMethodValidator;

    public DefaultContactMethodService(final ContactMethodRepository contactMethodRepo) {
        super();

        contactMethodRepository = Objects.requireNonNull(contactMethodRepo);
        createContactMethodValidator = new FieldRuleValidator<>(new ContactMethodNameNotEmptyRule(),
            new ContactMethodNameNotExistsRule(contactMethodRepo));
        updateContactMethodValidator = new FieldRuleValidator<>(new ContactMethodNameNotEmptyRule(),
            new ContactMethodNameNotExistsForAnotherRule(contactMethodRepo));
    }

    @Override
    public final ContactMethod create(final ContactMethod contactMethod) {
        final ContactMethod toCreate;
        final Long          number;

        log.debug("Creating ContactMethod {}", contactMethod);

        // Set number
        number = contactMethodRepository.findNextNumber();

        toCreate = new ContactMethod(number, contactMethod.name());

        createContactMethodValidator.validate(toCreate);

        return contactMethodRepository.save(toCreate);
    }

    @Override
    public final ContactMethod delete(final long number) {
        final ContactMethod contactMethod;

        log.debug("Deleting ContactMethod {}", number);

        contactMethod = contactMethodRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing ContactMethod {}", number);
                throw new MissingContactMethodException(number);
            });

        contactMethodRepository.delete(number);

        return contactMethod;
    }

    @Override
    public final Page<ContactMethod> getAll(final Pagination pagination, final Sorting sorting) {
        log.debug("Reading ContactMethods with pagination {} and sorting {}", pagination, sorting);

        return contactMethodRepository.findAll(pagination, sorting);
    }

    @Override
    public final Optional<ContactMethod> getOne(final long number) {
        final Optional<ContactMethod> contactMethod;

        log.debug("Reading ContactMethod {}", number);

        contactMethod = contactMethodRepository.findOne(number);
        if (contactMethod.isEmpty()) {
            log.error("Missing ContactMethod {}", number);
            throw new MissingContactMethodException(number);
        }

        return contactMethod;
    }

    @Override
    public final ContactMethod update(final ContactMethod contactMethod) {
        log.debug("Updating ContactMethod {} using data {}", contactMethod.number(), contactMethod);

        if (!contactMethodRepository.exists(contactMethod.number())) {
            log.error("Missing ContactMethod {}", contactMethod.number());
            throw new MissingContactMethodException(contactMethod.number());
        }

        updateContactMethodValidator.validate(contactMethod);

        return contactMethodRepository.save(contactMethod);
    }

}
