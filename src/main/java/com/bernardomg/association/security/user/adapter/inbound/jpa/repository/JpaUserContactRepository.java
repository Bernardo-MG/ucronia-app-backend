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

package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntity;
import com.bernardomg.association.contact.adapter.inbound.jpa.model.ContactEntityMapper;
import com.bernardomg.association.contact.adapter.inbound.jpa.repository.ContactSpringRepository;
import com.bernardomg.association.contact.domain.model.Contact;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserContactEntity;
import com.bernardomg.association.security.user.domain.repository.UserContactRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.security.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.user.adapter.inbound.jpa.repository.UserSpringRepository;

@Repository
@Transactional
public final class JpaUserContactRepository implements UserContactRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log = LoggerFactory.getLogger(JpaUserContactRepository.class);

    private final ContactSpringRepository     contactSpringRepository;

    private final UserContactSpringRepository userContactSpringRepository;

    private final UserSpringRepository        userSpringRepository;

    public JpaUserContactRepository(final UserContactSpringRepository userContactSpringRepo,
            final UserSpringRepository userSpringRepo, final ContactSpringRepository contactSpringRepo) {
        super();

        userContactSpringRepository = Objects.requireNonNull(userContactSpringRepo);
        userSpringRepository = Objects.requireNonNull(userSpringRepo);
        contactSpringRepository = Objects.requireNonNull(contactSpringRepo);
    }

    @Override
    public final Contact assignContact(final String username, final long number) {
        final UserContactEntity       userMember;
        final Optional<UserEntity>    user;
        final Optional<ContactEntity> contact;
        final Contact                 result;

        log.trace("Assigning contact {} to username {}", number, username);

        user = userSpringRepository.findByUsername(username);
        contact = contactSpringRepository.findByNumber(number);
        if ((user.isPresent()) && (contact.isPresent())) {
            userMember = new UserContactEntity();
            userMember.setUserId(user.get()
                .getId());
            userMember.setContact(contact.get());
            userMember.setUser(user.get());

            userContactSpringRepository.save(userMember);
            result = ContactEntityMapper.toDomain(contact.get());

            log.trace("Assigned contact {} to username {}", number, username);
        } else {
            log.warn("Failed to assign contact {} to username {}", number, username);

            result = null;
        }

        return result;
    }

    @Override
    public final boolean existsByContactForAnotherUser(final String username, final long number) {
        final boolean exists;

        log.trace("Checking if username {} exists for a user with a number distinct from {}", username, number);

        exists = userContactSpringRepository.existsByNotUsernameAndMemberNumber(username, number);

        log.trace("Username {} exists for a user with a number distinct from {}: {}", username, number, exists);

        return exists;
    }

    @Override
    public final Page<Contact> findAllNotAssigned(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Contact> read;
        final Pageable                                      pageable;

        log.trace("Finding all the people with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        read = userContactSpringRepository.findAllNotAssigned(pageable)
            .map(ContactEntityMapper::toDomain);

        log.trace("Found all the people: {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Contact> findByUsername(final String username) {
        final Optional<UserEntity>        user;
        final Optional<UserContactEntity> userMember;
        final Optional<Contact>           contact;

        log.trace("Finding contact for username {}", username);

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            // TODO: Simplify this, use JPA relationships
            userMember = userContactSpringRepository.findByUserId(user.get()
                .getId());
            if ((userMember.isPresent()) && (userMember.get()
                .getContact() != null)) {
                contact = Optional.of(ContactEntityMapper.toDomain(userMember.get()
                    .getContact()));
            } else {
                contact = Optional.empty();
            }
        } else {
            contact = Optional.empty();
        }

        log.trace("Found contact for username {}: {}", username, contact);

        return contact;
    }

    @Override
    public final Contact unassignContact(final String username) {
        final Optional<UserEntity> user;
        final Contact              contact;

        log.debug("Deleting user {}", username);

        user = userSpringRepository.findByUsername(username);
        if (user.isPresent()) {
            contact = findByUsername(username).orElse(null);

            // TODO: handle relationships
            // TODO: why not delete by username?
            userContactSpringRepository.deleteByUserId(user.get()
                .getId());

            log.debug("Deleted user {}", username);
        } else {
            contact = null;
        }

        return contact;
    }

}
