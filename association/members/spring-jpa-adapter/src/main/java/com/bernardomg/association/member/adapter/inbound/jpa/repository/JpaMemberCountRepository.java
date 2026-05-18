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

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.domain.model.MemberCount;
import com.bernardomg.association.member.domain.repository.MemberCountRepository;

@Transactional
public final class JpaMemberCountRepository implements MemberCountRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                log = LoggerFactory.getLogger(JpaMemberCountRepository.class);

    private final PublicMemberSpringRepository publicMemberSpringRepository;

    public JpaMemberCountRepository(final PublicMemberSpringRepository publicMemberSpringRepo) {
        super();

        publicMemberSpringRepository = Objects.requireNonNull(publicMemberSpringRepo);
    }

    @Override
    public final MemberCount findCurrent() {
        final long        active;
        final long        renew;
        final MemberCount count;

        log.debug("Finding current member count");

        active = publicMemberSpringRepository.countByActiveTrue();
        renew = publicMemberSpringRepository.countByActiveTrueAndRenewTrue();

        count = new MemberCount(active, renew);

        log.debug("Found current member count: {}", count);

        return count;
    }

}
