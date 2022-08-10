
package com.bernardomg.association.member.service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.DtoMemberDetail;
import com.bernardomg.association.member.model.Member;
import com.bernardomg.association.member.model.MemberDetail;
import com.bernardomg.association.member.model.PersistentMember;
import com.bernardomg.association.member.repository.MemberRepository;

@Service
public final class DefaultMemberService implements MemberService {

    private final MemberRepository repository;

    public DefaultMemberService(final MemberRepository repo) {
        super();

        repository = Objects.requireNonNull(repo, "Received a null pointer as repository");
    }

    @Override
    public final Member create(final Member member) {
        final PersistentMember entity;
        final PersistentMember created;

        entity = toPersistentMember(member);
        created = repository.save(entity);
        return toMember(created);
    }

    @Override
    public final Boolean delete(final Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public final Iterable<? extends Member> getAll() {
        return repository.findAll()
            .stream()
            .map(this::toMember)
            .collect(Collectors.toList());
    }

    @Override
    public final Optional<? extends MemberDetail> getOne(final Long id) {
        final Optional<PersistentMember>       found;
        final Optional<? extends MemberDetail> result;
        final MemberDetail                     member;

        found = repository.findById(id);

        if (found.isPresent()) {
            member = toMemberDetail(found.get());
            // TODO: add relationships

            result = Optional.of(member);
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final Member update(final Member member) {
        final PersistentMember entity;
        final PersistentMember updated;

        entity = toPersistentMember(member);
        updated = repository.save(entity);
        return toMember(updated);
    }

    private final Member toMember(final PersistentMember entity) {
        final DtoMember data;

        data = new DtoMember();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final DtoMemberDetail toMemberDetail(final PersistentMember entity) {
        final DtoMemberDetail data;

        data = new DtoMemberDetail();
        data.setId(entity.getId());
        data.setName(entity.getName());

        return data;
    }

    private final PersistentMember toPersistentMember(final Member data) {
        final PersistentMember entity;

        entity = new PersistentMember();
        entity.setId(data.getId());
        entity.setName(data.getName());

        return entity;
    }

}
