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

package com.bernardomg.association.sponsor.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.contact.domain.model.ContactName;
import com.bernardomg.association.sponsor.domain.exception.MissingSponsorException;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the sponsor service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@Transactional
public final class DefaultSponsorService implements SponsorService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultSponsorService.class);

    private final SponsorRepository sponsorRepository;

    public DefaultSponsorService(final SponsorRepository sponsorRepo) {
        super();

        sponsorRepository = Objects.requireNonNull(sponsorRepo);
    }

    @Override
    public final Sponsor create(final Sponsor sponsor) {
        final Sponsor toCreate;
        final Sponsor created;

        log.debug("Creating sponsor {}", sponsor);

        toCreate = new Sponsor(sponsor.identifier(), 0L, sponsor.name(), sponsor.birthDate(), sponsor.contactChannels(),
            sponsor.years(), sponsor.comments(),sponsor.types());

        created = sponsorRepository.save(toCreate);

        log.debug("Created sponsor {}", created);

        return created;
    }

    @Override
    public final Sponsor delete(final long number) {
        final Sponsor existing;

        log.debug("Deleting sponsor {}", number);

        existing = sponsorRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing sponsor {}", number);
                throw new MissingSponsorException(number);
            });

        sponsorRepository.delete(number);

        log.debug("Deleted sponsor {}", number);

        return existing;
    }

    @Override
    public final Page<Sponsor> getAll(final SponsorFilter filter, final Pagination pagination, final Sorting sorting) {
        final Page<Sponsor> sponsors;

        log.debug("Reading sponsors with filter {}, pagination {} and sorting {}", filter, pagination, sorting);

        sponsors = sponsorRepository.findAll(filter, pagination, sorting);

        log.debug("Read sponsors with filter {}, pagination {} and sorting {}: {}", filter, pagination, sorting,
            sponsors);

        return sponsors;
    }

    @Override
    public final Optional<Sponsor> getOne(final long number) {
        final Optional<Sponsor> sponsor;

        log.debug("Reading sponsor {}", number);

        sponsor = sponsorRepository.findOne(number);
        if (sponsor.isEmpty()) {
            log.error("Missing sponsor {}", number);
            throw new MissingSponsorException(number);
        }

        log.debug("Read sponsor {}: {}", number, sponsor);

        return sponsor;
    }

    @Override
    public final Sponsor patch(final Sponsor sponsor) {
        final Sponsor existing;
        final Sponsor toSave;
        final Sponsor saved;

        log.debug("Patching sponsor {} using data {}", sponsor.number(), sponsor);

        existing = sponsorRepository.findOne(sponsor.number())
            .orElseThrow(() -> {
                log.error("Missing sponsor {}", sponsor.number());
                throw new MissingSponsorException(sponsor.number());
            });

        toSave = copy(existing, sponsor);

        saved = sponsorRepository.save(toSave);

        log.debug("Patched sponsor {}: {}", sponsor.number(), saved);

        return saved;
    }

    @Override
    public final Sponsor update(final Sponsor sponsor) {
        final Sponsor saved;

        log.debug("Updating sponsor {} using data {}", sponsor.number(), sponsor);

        if (!sponsorRepository.exists(sponsor.number())) {
            log.error("Missing sponsor {}", sponsor.number());
            throw new MissingSponsorException(sponsor.number());
        }

        saved = sponsorRepository.save(sponsor);

        log.debug("Updated sponsor {}: {}", sponsor.number(), saved);

        return saved;
    }

    private final Sponsor copy(final Sponsor existing, final Sponsor updated) {
        final ContactName name;

        if (updated.name() == null) {
            name = existing.name();
        } else {
            name = new ContactName(Optional.ofNullable(updated.name()
                .firstName())
                .orElse(existing.name()
                    .firstName()),
                Optional.ofNullable(updated.name()
                    .lastName())
                    .orElse(existing.name()
                        .lastName()));
        }
        return new Sponsor(Optional.ofNullable(updated.identifier())
            .orElse(existing.identifier()),
            Optional.ofNullable(updated.number())
                .orElse(existing.number()),
            name, Optional.ofNullable(updated.birthDate())
                .orElse(existing.birthDate()),
            Optional.ofNullable(updated.contactChannels())
                .orElse(existing.contactChannels()),
            Optional.ofNullable(updated.years())
                .orElse(existing.years()),
            Optional.ofNullable(updated.comments())
                .orElse(existing.comments()),
            Optional.ofNullable(updated.types())
                .orElse(existing.types()));
    }

}
