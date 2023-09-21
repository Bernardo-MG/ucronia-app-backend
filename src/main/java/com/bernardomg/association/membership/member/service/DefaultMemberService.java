
package com.bernardomg.association.membership.member.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.mapper.MemberMapper;
import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.model.request.MemberUpdate;
import com.bernardomg.association.membership.member.persistence.model.PersistentMember;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;
import com.bernardomg.exception.InvalidIdException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
@Slf4j
public final class DefaultMemberService implements MemberService {

    private static final String    CACHE_MULTIPLE = "members";

    private static final String    CACHE_SINGLE   = "member";

    private final MemberMapper     mapper;

    /**
     * Member repository.
     */
    private final MemberRepository repository;

    @Override
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Member create(final MemberCreate member) {
        final PersistentMember entity;
        final PersistentMember created;

        log.debug("Creating member {}", member);

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = mapper.toEntity(member);

        // Active by default
        entity.setActive(true);

        // Trim strings
        entity.setName(entity.getName()
            .trim());
        entity.setSurname(entity.getSurname()
            .trim());

        created = repository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {

        log.debug("Deleting member {}", id);

        if (!repository.existsById(id)) {
            throw new InvalidIdException("member", id);
        }

        // TODO: Forbid deleting when there are relationships

        repository.deleteById(id);
    }

    @Override
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Member> getAll(final MemberQuery sample, final Pageable pageable) {
        final PersistentMember entity;

        log.debug("Reading members with sample {} and pagination {}", sample, pageable);

        entity = mapper.toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(mapper::toDto);
    }

    @Override
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Member> getOne(final long id) {
        final Optional<PersistentMember> found;
        final Optional<Member>           result;
        final Member                     data;

        log.debug("Reading member with id {}", id);

        if (!repository.existsById(id)) {
            throw new InvalidIdException("member", id);
        }

        found = repository.findById(id);

        if (found.isPresent()) {
            data = mapper.toDto(found.get());
            result = Optional.of(data);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Member update(final long id, final MemberUpdate member) {
        final PersistentMember entity;
        final PersistentMember updated;

        log.debug("Updating member with id {} using data {}", id, member);

        if (!repository.existsById(id)) {
            throw new InvalidIdException("member", id);
        }

        entity = mapper.toEntity(member);

        // Set id
        entity.setId(id);

        // Trim strings
        entity.setName(entity.getName()
            .trim());
        entity.setSurname(entity.getSurname()
            .trim());

        updated = repository.save(entity);
        return mapper.toDto(updated);
    }

}
