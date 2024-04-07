
package com.bernardomg.association.security.user.adapter.inbound.jpa.repository;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.MemberName;
import com.bernardomg.association.security.user.adapter.inbound.jpa.model.UserMemberEntity;
import com.bernardomg.association.security.user.domain.model.UserMember;
import com.bernardomg.association.security.user.domain.model.UserMemberName;
import com.bernardomg.association.security.user.domain.repository.UserMemberRepository;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.model.UserEntity;
import com.bernardomg.security.authentication.user.adapter.inbound.jpa.repository.UserSpringRepository;

import io.jsonwebtoken.lang.Strings;

@Transactional
public final class JpaUserMemberRepository implements UserMemberRepository {

    public final MemberSpringRepository     memberSpringRepository;

    public final UserMemberSpringRepository userMemberJpaRepository;

    public final UserSpringRepository       userSpringRepository;

    public JpaUserMemberRepository(final UserMemberSpringRepository userMemberJpaRepo,
            final UserSpringRepository userSpringRepo, final MemberSpringRepository memberSpringRepo) {
        super();

        userMemberJpaRepository = userMemberJpaRepo;
        userSpringRepository = userSpringRepo;
        memberSpringRepository = memberSpringRepo;
    }

    @Override
    public final void delete(final String username) {
        final Optional<UserEntity>       user;
        final Optional<UserMemberEntity> userMember;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            userMember = userMemberJpaRepository.findByUserId(user.get()
                .getId());
            userMemberJpaRepository.deleteById(userMember.get()
                .getUserId());
        }
    }

    @Override
    public final Optional<Member> findByUsername(final String username) {
        final Optional<UserEntity>       user;
        final Optional<MemberEntity>     member;
        final Optional<UserMemberEntity> userMember;
        final Optional<Member>           result;

        user = userSpringRepository.findOneByUsername(username);
        if (user.isPresent()) {
            // TODO: Simplify this, use JPA relationships
            userMember = userMemberJpaRepository.findByUserId(user.get()
                .getId());
            if (userMember.isPresent()) {
                member = memberSpringRepository.findById(userMember.get()
                    .getMemberId());
                if (member.isPresent()) {
                    result = Optional.of(toDomain(member.get()));
                } else {
                    result = Optional.empty();
                }
            } else {
                result = Optional.empty();
            }
        } else {
            result = Optional.empty();
        }

        return result;
    }

    @Override
    public final UserMember save(final String username, final Long number) {
        final UserMemberEntity       userMember;
        final Optional<UserEntity>   user;
        final Optional<MemberEntity> member;

        user = userSpringRepository.findOneByUsername(username);
        member = memberSpringRepository.findByNumber(number);
        userMember = UserMemberEntity.builder()
            .withMemberId(member.get()
                .getId())
            .withUserId(user.get()
                .getId())
            .build();
        userMemberJpaRepository.save(userMember);

        return toDto(user.get(), member.get());
    }

    private final Member toDomain(final MemberEntity entity) {
        final MemberName memberName;
        final String     fullName;

        // TODO: the model should return this automatically
        fullName = Strings.trimWhitespace(entity.getName() + " " + entity.getSurname());
        memberName = MemberName.builder()
            .withFirstName(entity.getName())
            .withLastName(entity.getSurname())
            .withFullName(fullName)
            .build();
        return Member.builder()
            .withNumber(entity.getNumber())
            .withIdentifier(entity.getIdentifier())
            .withName(memberName)
            .withPhone(entity.getPhone())
            .build();
    }

    private final UserMember toDto(final UserEntity user, final MemberEntity member) {
        final String         fullName;
        final UserMemberName name;

        // TODO: change model
        fullName = Strings.trimWhitespace(member.getName() + " " + member.getSurname());
        name = UserMemberName.builder()
            .withFirstName(member.getName())
            .withLastName(member.getSurname())
            .withFullName(fullName)
            .build();
        return UserMember.builder()
            .withUsername(user.getUsername())
            .withName(name)
            .withNumber(member.getNumber())
            .build();
    }

}
