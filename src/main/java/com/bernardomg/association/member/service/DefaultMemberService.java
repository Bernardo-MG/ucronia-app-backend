
package com.bernardomg.association.member.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.mapper.MemberMapper;
import com.bernardomg.association.member.model.request.MemberCreate;
import com.bernardomg.association.member.model.request.MemberQuery;
import com.bernardomg.association.member.model.request.MemberUpdate;
import com.bernardomg.association.member.persistence.model.PersistentMember;
import com.bernardomg.association.member.persistence.repository.MemberRepository;
import com.bernardomg.exception.InvalidIdException;

import lombok.AllArgsConstructor;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
public final class DefaultMemberService implements MemberService {

    private static final String    CACHE_MULTIPLE = "members";

    private static final String    CACHE_SINGLE   = "member";

    private final MemberMapper     mapper;

    /**
     * Member repository.
     */
    private final MemberRepository repository;

    @Override
    @PreAuthorize("hasAuthority('MEMBER:CREATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Member create(final MemberCreate member) {
        final PersistentMember entity;
        final PersistentMember created;

        // TODO: Return error messages for duplicate data
        // TODO: Phone and identifier should be unique or empty

        entity = mapper.toEntity(member);

        created = repository.save(entity);

        return mapper.toDto(created);
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:DELETE')")
    @Caching(evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true),
            @CacheEvict(cacheNames = CACHE_SINGLE, key = "#id") })
    public final void delete(final long id) {
        if (!repository.existsById(id)) {
            throw new InvalidIdException(String.format("Failed delete. No member with id %s", id));
        }

        // TODO: Forbid deleting when there are relationships

        repository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:READ')")
    @Cacheable(cacheNames = CACHE_MULTIPLE)
    public final Iterable<Member> getAll(final MemberQuery sample, final Pageable pageable) {
        final PersistentMember entity;

        entity = mapper.toEntity(sample);

        return repository.findAll(Example.of(entity), pageable)
            .map(mapper::toDto);
    }

    @Override
    @PreAuthorize("hasAuthority('MEMBER:READ')")
    @Cacheable(cacheNames = CACHE_SINGLE, key = "#id")
    public final Optional<Member> getOne(final long id) {
        final Optional<PersistentMember> found;
        final Optional<Member>           result;
        final Member                     data;

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
    @PreAuthorize("hasAuthority('MEMBER:UPDATE')")
    @Caching(put = { @CachePut(cacheNames = CACHE_SINGLE, key = "#result.id") },
            evict = { @CacheEvict(cacheNames = CACHE_MULTIPLE, allEntries = true) })
    public final Member update(final long id, final MemberUpdate member) {
        final PersistentMember entity;
        final PersistentMember updated;

        if (!repository.existsById(id)) {
            throw new InvalidIdException(String.format("Failed update. No member with id %s", id));
        }

        entity = mapper.toEntity(member);
        entity.setId(id);

        updated = repository.save(entity);
        return mapper.toDto(updated);
    }

}
