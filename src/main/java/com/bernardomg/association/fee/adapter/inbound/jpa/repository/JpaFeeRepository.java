
package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.Month;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeePaymentEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.MemberFee;
import com.bernardomg.association.fee.adapter.inbound.jpa.model.MemberFeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.specification.MemberFeeSpecifications;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.model.FeeMember;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.model.FeeTransaction;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.member.adapter.inbound.jpa.repository.MemberSpringRepository;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JpaFeeRepository implements FeeRepository {

    private final ActiveMemberSpringRepository activeMemberRepository;

    private final FeePaymentSpringRepository   feePaymentRepository;

    private final FeeSpringRepository          feeRepository;

    private final MemberFeeSpringRepository    memberFeeRepository;

    private final MemberSpringRepository       memberRepository;

    private final TransactionSpringRepository  transactionRepository;

    public JpaFeeRepository(final FeeSpringRepository feeRepo, final MemberFeeSpringRepository memberFeeRepo,
            final MemberSpringRepository memberRepo, final ActiveMemberSpringRepository activeMemberRepo,
            final FeePaymentSpringRepository feePaymentRepo, final TransactionSpringRepository transactionRepo) {
        super();

        feeRepository = feeRepo;
        memberFeeRepository = memberFeeRepo;
        memberRepository = memberRepo;
        activeMemberRepository = activeMemberRepo;
        feePaymentRepository = feePaymentRepo;
        transactionRepository = transactionRepo;
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
    public final boolean existsPaid(final Long memberNumber, final YearMonth date) {
        return memberFeeRepository.existsByMemberNumberAndDateAndPaid(memberNumber, date, true);
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
    public final Collection<Fee> findAll(final Long memberNumber, final Collection<YearMonth> feeDates) {
        return feeRepository.findAllByMemberNumberAndDateIn(memberNumber, feeDates)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Collection<Fee> findAllForActiveMembers(final int year, final Sort sort) {
        final Collection<Long> foundIds;
        final YearMonth        validStart;
        final YearMonth        validEnd;
        final YearMonth        start;
        final YearMonth        end;

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        foundIds = activeMemberRepository.findAllActiveIdsInRange(validStart, validEnd);

        return memberFeeRepository.findAllInRangeForMembersIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Collection<Fee> findAllForInactiveMembers(final int year, final Sort sort) {
        final Collection<Long> foundIds;
        final YearMonth        validStart;
        final YearMonth        validEnd;
        final YearMonth        start;
        final YearMonth        end;

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        foundIds = activeMemberRepository.findAllInactiveIds(validStart, validEnd);

        return memberFeeRepository.findAllInRangeForMembersIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Collection<Fee> findAllForPreviousMonth() {
        final YearMonth previousMonth;

        previousMonth = YearMonth.now()
            .minusMonths(1);

        return memberFeeRepository.findAllByDate(previousMonth)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public Collection<Fee> findAllInYear(final int year, final Sort sort) {
        final YearMonth start;
        final YearMonth end;

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);

        return memberFeeRepository.findAllInRange(start, end, sort)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public final Optional<Fee> findOne(final Long memberNumber, final YearMonth date) {
        final Optional<MemberFeeEntity> read;

        read = memberFeeRepository.findOneByMemberNumberAndDate(memberNumber, date);

        return read.map(this::toDomain);
    }

    @Override
    public final FeeCalendarYearsRange findRange() {
        final Collection<Integer> years;

        years = memberFeeRepository.findYears();
        return FeeCalendarYearsRange.builder()
            .withYears(years)
            .build();
    }

    @Override
    public final void pay(final Member member, final Collection<Fee> fees, final Transaction transaction) {
        final TransactionEntity           transactionEntity;
        final Iterable<FeePaymentEntity>  payments;
        final Collection<MemberFeeEntity> read;
        final Collection<YearMonth>       feeDates;

        feeDates = fees.stream()
            .map(Fee::getDate)
            .toList();

        transactionEntity = transactionRepository.findOneByIndex(transaction.getIndex())
            .get();
        read = memberFeeRepository.findAllByMemberNumberAndDateIn(member.getNumber(), feeDates);

        // Register payments
        payments = read.stream()
            .map(MemberFeeEntity::getId)
            .map(id -> FeePaymentEntity.builder()
                .withFeeId(id)
                .withTransactionId(transactionEntity.getId())
                .build())
            .toList();
        feePaymentRepository.saveAll(payments);

        transactionRepository.flush();
        feePaymentRepository.flush();
        memberFeeRepository.flush();
    }

    @Override
    public final Collection<Fee> save(final Collection<Fee> fees) {
        final Collection<FeeEntity> entities;

        entities = fees.stream()
            .map(this::toEntity)
            .toList();
        entities.forEach(this::loadId);
        return feeRepository.saveAll(entities)
            .stream()
            .map(this::toDomain)
            .toList();
    }

    private final void loadId(final FeeEntity fee) {
        final Long                id;
        final Optional<FeeEntity> read;

        // TODO: optimize to use a single query
        read = feeRepository.findOneByMemberIdAndDate(fee.getMemberId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final Fee toDomain(final FeeEntity entity) {
        final FeeMember              feeMember;
        final FeeTransaction         feeTransaction;
        final Optional<MemberEntity> member;
        final String                 name;

        member = memberRepository.findById(entity.getMemberId());
        if (member.isEmpty()) {
            feeMember = FeeMember.builder()
                .build();
        } else {
            name = (member.get()
                .getName() + " "
                    + member.get()
                        .getSurname()).trim();
            feeMember = FeeMember.builder()
                .withFullName(name)
                .withNumber(member.get()
                    .getNumber())
                .build();
        }

        feeTransaction = FeeTransaction.builder()
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withMember(feeMember)
            .withTransaction(feeTransaction)
            .build();
    }

    private final Fee toDomain(final MemberFee entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(entity.getMemberName())
            .withNumber(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(entity.getTransactionIndex())
            .withDate(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withPaid(entity.getPaid())
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    private final Fee toDomain(final MemberFeeEntity entity) {
        final FeeMember      member;
        final FeeTransaction transaction;

        member = FeeMember.builder()
            .withFullName(entity.getFullName())
            .withNumber(entity.getMemberNumber())
            .build();
        transaction = FeeTransaction.builder()
            .withIndex(entity.getTransactionIndex())
            .withDate(entity.getPaymentDate())
            .build();
        return Fee.builder()
            .withDate(entity.getDate())
            .withPaid(entity.getPaid())
            .withMember(member)
            .withTransaction(transaction)
            .build();
    }

    private final FeeEntity toEntity(final Fee fee) {
        final long id;

        id = memberRepository.findByNumber(fee.getMember()
            .getNumber())
            .get()
            .getId();
        return FeeEntity.builder()
            .withMemberId(id)
            .withDate(fee.getDate())
            .build();
    }

}
