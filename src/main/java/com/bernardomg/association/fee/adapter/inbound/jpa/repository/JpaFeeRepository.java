
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

        log.debug("Deleting fee for member {} in date {}", memberNumber, date);

        member = memberRepository.findByNumber(memberNumber);
        fee = feeRepository.findOneByMemberIdAndDate(member.get()
            .getId(), date);

        feeRepository.deleteById(fee.get()
            .getId());

        log.debug("Deleted fee for member {} in date {}", memberNumber, date);
    }

    @Override
    public final boolean exists(final Long memberNumber, final YearMonth date) {
        final boolean exists;

        log.debug("checking a fee exists for member {} in date {}", memberNumber, date);

        exists = memberFeeRepository.existsByMemberNumberAndDate(memberNumber, date);

        log.debug("Fee exists for member {} in date {}: {}", memberNumber, date, exists);

        return exists;
    }

    @Override
    public final boolean existsPaid(final Long memberNumber, final YearMonth date) {
        final boolean exists;

        log.debug("checking a paid fee exists for member {} in date {}", memberNumber, date);

        // TODO: the boolean is not needed
        exists = memberFeeRepository.existsByMemberNumberAndDateAndPaid(memberNumber, date, true);

        log.debug("Paid fee exists for member {} in date {}: {}", memberNumber, date, exists);

        return exists;
    }

    @Override
    public final Iterable<Fee> findAll(final FeeQuery query, final Pageable pageable) {
        final Page<MemberFeeEntity>                    page;
        final Optional<Specification<MemberFeeEntity>> spec;
        final Iterable<Fee>                            found;
        // TODO: Test reading with no name or surname

        log.debug("Finding all fees with sample {} and pagination {}", query, pageable);

        spec = MemberFeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            page = memberFeeRepository.findAll(pageable);
        } else {
            page = memberFeeRepository.findAll(spec.get(), pageable);
        }

        found = page.map(this::toDomain);

        log.debug("Found all fees with sample {} and pagination {}: {}", query, pageable, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForActiveMembers(final int year, final Sort sort) {
        final Collection<Long> foundIds;
        final YearMonth        validStart;
        final YearMonth        validEnd;
        final YearMonth        start;
        final YearMonth        end;
        final Collection<Fee>  found;

        log.debug("Finding all fees for active members in year {}", year);

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        foundIds = activeMemberRepository.findAllActiveIdsInRange(validStart, validEnd);

        found = memberFeeRepository.findAllInRangeForMembersIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for active members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForInactiveMembers(final int year, final Sort sort) {
        final Collection<Long> foundIds;
        final YearMonth        validStart;
        final YearMonth        validEnd;
        final YearMonth        start;
        final YearMonth        end;
        final Collection<Fee>  found;

        log.debug("Finding all fees for inactive members in year {}", year);

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        foundIds = activeMemberRepository.findAllInactiveIds(validStart, validEnd);

        found = memberFeeRepository.findAllInRangeForMembersIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for inactive members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForMemberInDates(final Long memberNumber,
            final Collection<YearMonth> feeDates) {
        final Collection<Fee> fees;

        log.debug("Finding all fees for member {} in dates {}", memberNumber, feeDates);

        fees = feeRepository.findAllByMemberNumberAndDateIn(memberNumber, feeDates)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for member {} in dates {}: {}", memberNumber, feeDates, fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllForPreviousMonth() {
        final YearMonth       previousMonth;
        final Collection<Fee> fees;

        log.debug("Finding all fees for the previous month");

        previousMonth = YearMonth.now()
            .minusMonths(1);

        fees = memberFeeRepository.findAllByDate(previousMonth)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for the previous month: {}", fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllInYear(final int year, final Sort sort) {
        final YearMonth       start;
        final YearMonth       end;
        final Collection<Fee> fees;

        log.debug("Finding all fees in year {}", year);

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);

        fees = memberFeeRepository.findAllInRange(start, end, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees in year {}: {}", year, fees);

        return fees;
    }

    @Override
    public final Optional<Fee> findOne(final Long memberNumber, final YearMonth date) {
        final Optional<MemberFeeEntity> read;
        final Optional<Fee>             found;

        log.debug("Finding fee for member {} in date {}", memberNumber, date);

        read = memberFeeRepository.findOneByMemberNumberAndDate(memberNumber, date);

        found = read.map(this::toDomain);

        log.debug("Found fee for member {} in date {}: {}", memberNumber, date, found);

        return found;
    }

    @Override
    public final FeeCalendarYearsRange findRange() {
        final Collection<Integer>   years;
        final FeeCalendarYearsRange range;

        log.debug("Finding fees range");

        years = memberFeeRepository.findYears();
        range = FeeCalendarYearsRange.builder()
            .withYears(years)
            .build();

        log.debug("Found fees range: {}", range);

        return range;
    }

    @Override
    public final void pay(final Member member, final Collection<Fee> fees, final Transaction transaction) {
        final TransactionEntity           transactionEntity;
        final Iterable<FeePaymentEntity>  payments;
        final Collection<MemberFeeEntity> read;
        final Collection<YearMonth>       feeDates;

        log.debug("Paying fees for {}, using fees {} and transaction {}", member.getNumber(), fees, transaction);

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

        log.debug("Paid fees for {}, using fees {} and transaction {}", member.getNumber(), fees, transaction);
    }

    @Override
    public final Collection<Fee> save(final Collection<Fee> fees) {
        final Collection<FeeEntity> entities;
        final Collection<Fee>       saved;

        log.debug("Saving fees {}", fees);

        entities = fees.stream()
            .map(this::toEntity)
            .toList();
        entities.forEach(this::loadId);
        saved = feeRepository.saveAll(entities)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Saved fees {}", fees);

        return saved;
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
