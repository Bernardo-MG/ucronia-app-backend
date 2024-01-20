
package com.bernardomg.association.fee.infra.jpa.repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.infra.jpa.model.FeeEntity;
import com.bernardomg.association.fee.infra.jpa.model.MemberFeeEntity;
import com.bernardomg.association.fee.infra.jpa.specification.MemberFeeSpecifications;
import com.bernardomg.association.member.infra.jpa.model.MemberEntity;
import com.bernardomg.association.member.infra.jpa.repository.MemberSpringRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaFeeRepository implements FeeRepository {

    private final FeeSpringRepository       feeRepository;

    private final MemberFeeSpringRepository memberFeeRepository;

    private final MemberSpringRepository    memberRepository;

    public JpaFeeRepository(final FeeSpringRepository feeRepo, final MemberFeeSpringRepository memberFeeRepo,
            final MemberSpringRepository memberRepo) {
        super();
        feeRepository = feeRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;

    }

    @Override
    public final void delete(final Long memberNumber, final YearMonth date) {
        final Optional<MemberEntity> member;
        final Optional<FeeEntity>    fee;

        member = memberRepository.findByNumber(memberNumber);
        fee = feeRepository.findOneByMemberIdAndDate(member.get()
            .getId(), date);

        feeRepository.deleteById(fee.get()
            .getId());
    }

    @Override
    public final boolean exists(final Long memberNumber, final YearMonth date) {
        return memberFeeRepository.existsByMemberNumberAndDate(memberNumber, date);
    }

    @Override
    public final Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable) {
        final Page<MemberFeeEntity>                    page;
        final Optional<Specification<MemberFeeEntity>> spec;
        // TODO: Test reading with no name or surname

        log.debug("Reading fees with sample {} and pagination {}", query, pageable);

        spec = MemberFeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        return page.map(this::toDomain);
    }

    @Override
    public final Optional<Fee> findOne(final Long memberNumber, final YearMonth date) {
        final Optional<MemberFeeEntity> read;

        read = memberFeeRepository.findOneByMemberNumberAndDate(memberNumber, date);

        return read.map(this::toDomain);
    }

    @Override
    public final Collection<FeeEntity> save(final Long memberNumber, final Collection<YearMonth> feeDates) {
        final Collection<FeeEntity>          fees;
        final Function<YearMonth, FeeEntity> toPersistentFee;
        final Optional<MemberEntity>         member;
        final Collection<FeeEntity>          created;

        member = memberRepository.findByNumber(memberNumber);

        // Register fees
        toPersistentFee = (date) -> toEntity(member.get()
            .getId(), date);
        fees = feeDates.stream()
            .map(toPersistentFee)
            .toList();

        // Update fees on fees to update
        fees.stream()
            .forEach(this::loadId);

        created = feeRepository.saveAll(fees);

        // TODO: Why?
        feeRepository.flush();

        return created;
    }

    private final void loadId(final FeeEntity fee) {
        final Long                id;
        final Optional<FeeEntity> read;

        read = feeRepository.findOneByMemberIdAndDate(fee.getMemberId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final Fee toDomain(final MemberFeeEntity entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .fullName(entity.getFullName())
            .number(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .index(entity.getTransactionIndex())
            .date(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .date(entity.getDate())
            .paid(entity.getPaid())
            .member(member)
            .transaction(transaction)
            .build();
    }

    private final FeeEntity toEntity(final Long memberId, final YearMonth date) {
        final FeeEntity fee;

        fee = new FeeEntity();
        fee.setMemberId(memberId);
        fee.setDate(date);

        return fee;
    }

}
