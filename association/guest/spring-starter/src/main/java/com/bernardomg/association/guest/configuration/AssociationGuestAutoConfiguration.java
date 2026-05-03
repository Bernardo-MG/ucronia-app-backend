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

package com.bernardomg.association.guest.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bernardomg.association.guest.adapter.inbound.jpa.repository.GuestContactMethodSpringRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.GuestInnerProfileSpringRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.GuestSpringRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.JpaGuestContactMethodRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.JpaGuestProfileRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.JpaGuestRepository;
import com.bernardomg.association.guest.adapter.inbound.jpa.repository.ReadGuestSpringRepository;
import com.bernardomg.association.guest.domain.repository.GuestContactMethodRepository;
import com.bernardomg.association.guest.domain.repository.GuestProfileRepository;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.usecase.service.DefaultGuestService;
import com.bernardomg.association.guest.usecase.service.DefaultProfileGuestService;
import com.bernardomg.association.guest.usecase.service.GuestService;
import com.bernardomg.association.guest.usecase.service.ProfileGuestService;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.association.guest.adapter.outbound.rest.controller",
        "com.bernardomg.association.guest.adapter.inbound.jpa" })
public class AssociationGuestAutoConfiguration {

    @Bean("guestContactMethodRepository")
    public GuestContactMethodRepository
            getGuestContactMethodRepository(final GuestContactMethodSpringRepository contactMethodSpringRepository) {
        return new JpaGuestContactMethodRepository(contactMethodSpringRepository);
    }

    @Bean("guestProfileRepository")
    public GuestProfileRepository
            getGuestProfileRepository(final GuestInnerProfileSpringRepository guestProfileSpringRepository) {
        return new JpaGuestProfileRepository(guestProfileSpringRepository);
    }

    @Bean("guestRepository")
    public GuestRepository getGuestRepository(final GuestSpringRepository guestSpringRepository,
            final ReadGuestSpringRepository readGuestSpringRepository,
            final GuestInnerProfileSpringRepository guestInnerProfileSpringRepository,
            final GuestContactMethodSpringRepository contactMethodSpringRepository) {
        return new JpaGuestRepository(guestSpringRepository, readGuestSpringRepository,
            guestInnerProfileSpringRepository, contactMethodSpringRepository);
    }

    @Bean("guestService")
    public GuestService getGuestService(final GuestRepository guestRepository,
            final GuestContactMethodRepository contactMethodRepository) {
        return new DefaultGuestService(guestRepository, contactMethodRepository);
    }

    @Bean("profileGuestService")
    public ProfileGuestService getProfileGuestService(final GuestRepository guestRepository,
            final GuestProfileRepository profileRepository) {
        return new DefaultProfileGuestService(guestRepository, profileRepository);
    }

}
