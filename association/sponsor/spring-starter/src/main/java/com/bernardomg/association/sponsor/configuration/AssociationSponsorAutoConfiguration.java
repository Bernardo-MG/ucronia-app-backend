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

package com.bernardomg.association.sponsor.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.JpaSponsorRepository;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.repository.SponsorSpringRepository;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.usecase.service.DefaultProfileSponsorshipService;
import com.bernardomg.association.sponsor.usecase.service.DefaultSponsorService;
import com.bernardomg.association.sponsor.usecase.service.ProfileSponsorshipService;
import com.bernardomg.association.sponsor.usecase.service.SponsorService;

@AutoConfiguration
@ComponentScan({ "com.bernardomg.association.sponsor.adapter.outbound.rest.controller",
        "com.bernardomg.association.sponsor.adapter.inbound.jpa" })
public class AssociationSponsorAutoConfiguration {

    @Bean("profileSponsorshipService")
    public ProfileSponsorshipService getProfileSponsorshipService(final SponsorRepository sponsorRepository,
            final ProfileRepository profileRepository) {
        return new DefaultProfileSponsorshipService(sponsorRepository, profileRepository);
    }

    @Bean("sponsorRepository")
    public SponsorRepository getSponsorRepository(final SponsorSpringRepository sponsorSpringRepository,
            final ContactMethodSpringRepository contactMethodSpringRepository,
            final ProfileSpringRepository profileSpringRepository) {
        return new JpaSponsorRepository(sponsorSpringRepository, contactMethodSpringRepository,
            profileSpringRepository);
    }

    @Bean("sponsorService")
    public SponsorService getSponsorService(final SponsorRepository sponsorRepository,
            final ContactMethodRepository contactMethodRepository) {
        return new DefaultSponsorService(sponsorRepository, contactMethodRepository);
    }

}
