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

package com.bernardomg.association.sponsor.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.profile.adapter.inbound.jpa.model.ContactMethodEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.model.ProfileEntity;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ContactMethodSpringRepository;
import com.bernardomg.association.profile.adapter.inbound.jpa.repository.ProfileSpringRepository;
import com.bernardomg.association.profile.domain.model.ContactMethod;
import com.bernardomg.association.profile.domain.model.Profile.ContactChannel;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntity;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityConstants;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.model.SponsorEntityMapper;
import com.bernardomg.association.sponsor.adapter.inbound.jpa.specification.SponsorSpecifications;
import com.bernardomg.association.sponsor.domain.filter.SponsorFilter;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.data.domain.Page;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.domain.Sorting.Property;
import com.bernardomg.data.springframework.SpringPagination;

@Repository
@Transactional
public final class JpaSponsorRepository implements SponsorRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                 log                = LoggerFactory
        .getLogger(JpaSponsorRepository.class);

    private static final Collection<String>     PROFILE_PROPERTIES = List.of("firstName", "lastName");

    private final ContactMethodSpringRepository contactMethodSpringRepository;

    private final ProfileSpringRepository       profileSpringRepository;

    private final SponsorSpringRepository       sponsorSpringRepository;

    public JpaSponsorRepository(final SponsorSpringRepository SponsorSpringRepo,
            final ContactMethodSpringRepository contactMethodSpringRepo,
            final ProfileSpringRepository profileSpringRepo) {
        super();

        sponsorSpringRepository = Objects.requireNonNull(SponsorSpringRepo);
        contactMethodSpringRepository = Objects.requireNonNull(contactMethodSpringRepo);
        // TODO: remove profile repository
        profileSpringRepository = Objects.requireNonNull(profileSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        log.debug("Deleting sponsor {}", number);

        // TODO: delete on cascade from the contact
        sponsorSpringRepository.deleteByNumber(number);
        profileSpringRepository.deleteByNumber(number);

        log.debug("Deleted sponsor {}", number);
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if sponsor {} exists", number);

        exists = sponsorSpringRepository.existsByNumber(number);

        log.debug("Sponsor {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Sponsor> findAll(final SponsorFilter filter, final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<Sponsor> read;
        final Pageable                                      pageable;
        final Optional<Specification<SponsorEntity>>        spec;
        final Sorting                                       fixedSorting;

        fixedSorting = fixSorting(sorting);
        log.debug("Finding all the sponsors with filter {}, pagination {} and sorting {}", filter, pagination,
            fixedSorting);

        pageable = SpringPagination.toPageable(pagination, fixedSorting);
        spec = SponsorSpecifications.query(filter);
        if (spec.isEmpty()) {
            read = sponsorSpringRepository.findAll(pageable)
                .map(SponsorEntityMapper::toDomain);
        } else {
            read = sponsorSpringRepository.findAll(spec.get(), pageable)
                .map(SponsorEntityMapper::toDomain);
        }

        log.debug("Found all the sponsors with filter {}, pagination {} and sorting {}: {}", filter, pagination,
            sorting, read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Sponsor> findOne(final Long number) {
        final Optional<Sponsor> sponsor;

        log.trace("Finding sponsor with number {}", number);

        sponsor = sponsorSpringRepository.findByNumber(number)
            .map(SponsorEntityMapper::toDomain);

        log.trace("Found sponsor with number {}: {}", number, sponsor);

        return sponsor;
    }

    @Override
    public final Sponsor save(final Sponsor sponsor) {
        final Optional<SponsorEntity>   existing;
        final SponsorEntity             entity;
        final Sponsor                   created;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;
        final Long                      number;

        log.debug("Saving sponsor {}", sponsor);

        existing = sponsorSpringRepository.findByNumber(sponsor.number());
        if (existing.isPresent()) {
            entity = SponsorEntityMapper.toEntity(existing.get(), sponsor);
        } else {
            contactMethodNumbers = sponsor.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = SponsorEntityMapper.toEntity(sponsor, contactMethods);
            number = sponsorSpringRepository.findNextNumber();
            entity.getProfile()
                .setNumber(number);
        }

        setType(entity.getProfile());

        created = SponsorEntityMapper.toDomain(sponsorSpringRepository.save(entity));

        log.debug("Saved sponsor {}", created);

        return created;
    }

    @Override
    public final Sponsor save(final Sponsor sponsor, final long number) {
        final SponsorEntity             entity;
        final Sponsor                   created;
        final Optional<ProfileEntity>   profile;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        log.debug("Saving sponsor {} with number {}", sponsor, number);

        contactMethodNumbers = sponsor.contactChannels()
            .stream()
            .map(ContactChannel::contactMethod)
            .map(ContactMethod::number)
            .toList();
        contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
        entity = SponsorEntityMapper.toEntity(sponsor, contactMethods);

        profile = profileSpringRepository.findByNumber(number);
        if (profile.isPresent()) {
            entity.setProfile(profile.get());
        }

        setType(entity.getProfile());

        created = SponsorEntityMapper.toDomain(sponsorSpringRepository.save(entity));

        log.debug("Saved sponsor {} with number {}", created, number);

        return created;
    }

    @Override
    public final Collection<Sponsor> saveAll(final Collection<Sponsor> sponsors) {
        final List<SponsorEntity> entities;
        final List<Sponsor>       saved;
        final AtomicLong          number;

        log.debug("Saving sponsors {}", sponsors);

        number = new AtomicLong(sponsorSpringRepository.findNextNumber());
        entities = sponsors.stream()
            .map(m -> convert(m, number))
            .toList();

        entities.stream()
            .forEach(m -> setType(m.getProfile()));

        saved = sponsorSpringRepository.saveAll(entities)
            .stream()
            .map(SponsorEntityMapper::toDomain)
            .toList();

        log.debug("Saved sponsors {}", saved);

        return saved;
    }

    private final SponsorEntity convert(final Sponsor sponsor, final AtomicLong number) {
        final Optional<SponsorEntity>   existing;
        final SponsorEntity             entity;
        final List<Long>                contactMethodNumbers;
        final List<ContactMethodEntity> contactMethods;

        existing = sponsorSpringRepository.findByNumber(sponsor.number());
        if (existing.isPresent()) {
            entity = SponsorEntityMapper.toEntity(existing.get(), sponsor);
        } else {
            contactMethodNumbers = sponsor.contactChannels()
                .stream()
                .map(ContactChannel::contactMethod)
                .map(ContactMethod::number)
                .toList();
            contactMethods = contactMethodSpringRepository.findAllByNumberIn(contactMethodNumbers);
            entity = SponsorEntityMapper.toEntity(sponsor, contactMethods);
            entity.getProfile()
                .setNumber(number.getAndIncrement());
        }

        return entity;
    }

    private final Sorting fixSorting(final Sorting sorting) {
        final Collection<Property> properties;

        properties = sorting.properties()
            .stream()
            .map(prop -> {
                if (PROFILE_PROPERTIES.contains(prop.name())) {
                    return new Property("profile." + prop.name(), prop.direction());
                }
                return prop;
            })
            .toList();

        return new Sorting(properties);
    }

    private final void setType(final ProfileEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(Set.of(SponsorEntityConstants.PROFILE_TYPE));
        } else {
            entity.setTypes(new HashSet<>(entity.getTypes()));
            entity.getTypes()
                .add(SponsorEntityConstants.PROFILE_TYPE);
        }
    }

}
