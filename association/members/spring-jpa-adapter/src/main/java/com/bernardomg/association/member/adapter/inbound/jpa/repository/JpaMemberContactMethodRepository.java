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

package com.bernardomg.association.member.adapter.inbound.jpa.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.repository.MemberContactMethodRepository;

@Transactional
public final class JpaMemberContactMethodRepository implements MemberContactMethodRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                       log = LoggerFactory
        .getLogger(JpaMemberContactMethodRepository.class);

    private final MemberContactMethodSpringRepository contactMethodSpringRepository;

    public JpaMemberContactMethodRepository(final MemberContactMethodSpringRepository ContactMethodSpringRepository) {
        super();

        contactMethodSpringRepository = ContactMethodSpringRepository;
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if contact method {} exists", number);

        exists = contactMethodSpringRepository.existsByNumber(number);

        log.debug("Contact method {} exists: {}", number, exists);

        return exists;
    }

}
