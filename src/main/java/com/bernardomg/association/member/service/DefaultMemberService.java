
package com.bernardomg.association.member.service;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.Member;
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
    public Iterable<? extends Member> getAllMembers() {
        return repository.findAll()
            .stream()
            .map(this::toMember)
            .collect(Collectors.toList());
    }

    private final Member toMember(final PersistentMember entity) {
        final DtoMember member;

        member = new DtoMember();
        member.setId(entity.getId());
        member.setName(entity.getName());

        return member;
    }

}
