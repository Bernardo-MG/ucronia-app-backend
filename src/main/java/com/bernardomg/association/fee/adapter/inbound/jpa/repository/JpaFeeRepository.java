
package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.Instant;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.adapter.inbound.jpa.model.FeeEntity;
import com.bernardomg.association.fee.adapter.inbound.jpa.specification.FeeSpecifications;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.model.FeeQuery;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.person.adapter.inbound.jpa.model.PersonEntity;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.repository.TransactionSpringRepository;
import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.data.domain.Pagination;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringPagination;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaFeeRepository implements FeeRepository {

    /**
     * Logger for the class.
     */
    private static final Logger               log               = LoggerFactory.getLogger(JpaFeeRepository.class);

    private static final Collection<String>   PERSON_PROPERTIES = List.of("firstName", "lastName", "member", "number");

    private final FeeSpringRepository         feeSpringRepository;

    private final PersonSpringRepository      personSpringRepository;

    private final TransactionSpringRepository transactionSpringRepository;

    public JpaFeeRepository(final FeeSpringRepository feeSpringRepo, final PersonSpringRepository personSpringRepo,
            final TransactionSpringRepository transactionSpringRepo) {
        super();

        feeSpringRepository = feeSpringRepo;
        personSpringRepository = personSpringRepo;
        transactionSpringRepository = transactionSpringRepo;
    }

    @Override
    public final void delete(final Long number, final YearMonth date) {
        final Optional<PersonEntity> person;
        final Instant                dateParsed;

        log.debug("Deleting fee for member {} in date {}", number, date);

        person = personSpringRepository.findByNumber(number);
        if (person.isPresent()) {
            dateParsed = date.atDay(1)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant();
            feeSpringRepository.deleteByPersonIdAndDate(person.get()
                .getId(), dateParsed);

            log.debug("Deleted fee for member {} in date {}", number, date);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete fee for member {} in date {}, as the member doesn't exist", number, date);
        }
    }

    @Override
    public final boolean exists(final Long number, final YearMonth date) {
        final boolean exists;
        final Instant dateParsed;

        log.debug("checking a fee exists for member {} in date {}", number, date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        exists = feeSpringRepository.existsByPersonNumberAndDate(number, dateParsed);

        log.debug("Fee exists for member {} in date {}: {}", number, date, exists);

        return exists;
    }

    @Override
    public final boolean existsPaid(final Long number, final YearMonth date) {
        final boolean exists;
        final Instant dateParsed;

        log.debug("checking a paid fee exists for member {} in date {}", number, date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        exists = feeSpringRepository.existsByPersonNumberAndDateAndPaid(number, dateParsed);

        log.debug("Paid fee exists for member {} in date {}: {}", number, date, exists);

        return exists;
    }

    @Override
    public final Iterable<Fee> findAll(final FeeQuery query, final Pagination pagination, final Sorting sorting) {
        final Optional<Specification<FeeEntity>> spec;
        final Iterable<Fee>                      found;
        final Pageable                           pageable;
        final Sorting                            correctedSorting;
        // TODO: Test reading with no first or last name

        log.debug("Finding all fees with sample {}, pagination {} and sorting {}", query, pagination, sorting);

        spec = FeeSpecifications.fromQuery(query);

        if (spec.isEmpty()) {
            correctedSorting = new Sorting(sorting.properties()
                .stream()
                .map(this::correct)
                .toList());
            pageable = SpringPagination.toPageable(pagination, correctedSorting);
            found = feeSpringRepository.findAllWithPerson(pageable)
                .map(this::toDomain);
        } else {
            pageable = SpringPagination.toPageable(pagination, sorting);
            found = feeSpringRepository.findAll(spec.get(), pageable)
                .map(this::toDomain);
        }

        log.debug("Found all fees with sample {}, pagination {} and sorting {}: {}", query, pagination, sorting, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForActiveMembers(final Year year, final Sorting sorting) {
        final Collection<Long> foundIds;
        final Instant          start;
        final Instant          end;
        final Collection<Fee>  found;
        final Sort             sort;
        final Sorting          correctedSorting;

        log.debug("Finding all fees for active members in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        end = YearMonth.of(year.getValue(), Month.DECEMBER)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        foundIds = personSpringRepository.findAllActiveMemberIds();

        log.debug("Active members: {}", foundIds);

        correctedSorting = new Sorting(sorting.properties()
            .stream()
            .map(this::correct)
            .toList());
        sort = SpringSorting.toSort(correctedSorting);
        found = feeSpringRepository.findAllInRangeForPersonsIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for active members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForInactiveMembers(final Year year, final Sorting sorting) {
        final Collection<Long> foundIds;
        final Instant          start;
        final Instant          end;
        final Collection<Fee>  found;
        final Sort             sort;
        final Sorting          correctedSorting;

        log.debug("Finding all fees for inactive members in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        end = YearMonth.of(year.getValue(), Month.DECEMBER)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        foundIds = personSpringRepository.findAllInactiveMemberIds();

        log.debug("Inactive members: {}", foundIds);

        correctedSorting = new Sorting(sorting.properties()
            .stream()
            .map(this::correct)
            .toList());
        sort = SpringSorting.toSort(correctedSorting);
        found = feeSpringRepository.findAllInRangeForPersonsIn(start, end, foundIds, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for inactive members in year {}: {}", year, found);

        return found;
    }

    @Override
    public final Iterable<Fee> findAllForPerson(final Long number, final Pagination pagination, final Sorting sorting) {
        final Iterable<Fee> found;
        final Pageable      pageable;
        final Sorting       correctedSorting;

        log.debug("Finding all fees for person {} with pagination {} and sorting {}", number, pagination, sorting);

        correctedSorting = new Sorting(sorting.properties()
            .stream()
            .map(this::correct)
            .toList());
        pageable = SpringPagination.toPageable(pagination, correctedSorting);
        found = feeSpringRepository.findAllByPersonNumber(number, pageable)
            .map(this::toDomain);

        log.debug("Found all fees for person {} with pagination {} and sorting {}: {}", number, pagination, sorting,
            found);

        return found;
    }

    @Override
    public final Collection<Fee> findAllForPersonInDates(final Long number, final Collection<YearMonth> feeMonths) {
        final Collection<Fee>     fees;
        final Collection<Instant> feeMonthsParsed;

        log.debug("Finding all fees for person {} in dates {}", number, feeMonths);

        feeMonthsParsed = feeMonths.stream()
            .map(this::toInstant)
            .toList();
        fees = feeSpringRepository.findAllFeesByPersonNumberAndDateIn(number, feeMonthsParsed)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees for person {} in dates {}: {}", number, feeMonths, fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllInMonth(final YearMonth date) {
        final Collection<Fee> fees;
        final Instant         dateParsed;

        log.debug("Finding all fees in month {}", date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        fees = feeSpringRepository.findAllByDate(dateParsed)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees in month {}: {}", date, fees);

        return fees;
    }

    @Override
    public final Collection<Fee> findAllInYear(final Year year, final Sorting sorting) {
        final Instant         start;
        final Instant         end;
        final Collection<Fee> fees;
        final Sort            sort;
        final Sorting         correctedSorting;

        log.debug("Finding all fees in year {}", year);

        start = YearMonth.of(year.getValue(), Month.JANUARY)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        end = YearMonth.of(year.getValue(), Month.DECEMBER)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();

        correctedSorting = new Sorting(sorting.properties()
            .stream()
            .map(this::correct)
            .toList());
        sort = SpringSorting.toSort(correctedSorting);
        fees = feeSpringRepository.findAllInRange(start, end, sort)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found all fees in year {}: {}", year, fees);

        return fees;
    }

    @Override
    public final Optional<Fee> findOne(final Long number, final YearMonth date) {
        final Optional<Fee> found;
        final Instant       dateParsed;

        log.debug("Finding fee for member {} in date {}", number, date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        found = feeSpringRepository.findByPersonNumberAndDate(number, dateParsed)
            .map(this::toDomain);

        log.debug("Found fee for member {} in date {}: {}", number, date, found);

        return found;
    }

    @Override
    public final FeeCalendarYearsRange findRange() {
        final Collection<Year>      years;
        final FeeCalendarYearsRange range;

        log.debug("Finding fees range");

        years = feeSpringRepository.findYears()
            .stream()
            .map(Year::of)
            .toList();
        range = new FeeCalendarYearsRange(years);

        log.debug("Found fees range: {}", range);

        return range;
    }

    @Override
    public final Collection<Fee> pay(final Person person, final Collection<Fee> fees, final Transaction transaction) {
        final Optional<TransactionEntity> transactionEntity;
        final Collection<FeeEntity>       read;
        final Collection<Instant>         feeMonths;
        final Collection<Fee>             saved;

        log.debug("Paying fees for {}, using fees {} and transaction {}", person.number(), fees, transaction);

        feeMonths = fees.stream()
            .map(Fee::month)
            .map(this::toInstant)
            .toList();

        transactionEntity = transactionSpringRepository.findByIndex(transaction.index());
        if (transactionEntity.isEmpty()) {
            log.error("Missing transaction with index {}", transaction.index());
        }
        read = feeSpringRepository.findAllFeesByPersonNumberAndDateIn(person.number(), feeMonths);

        // Register payments
        for (final FeeEntity fee : read) {
            fee.setTransactionId(transactionEntity.get()
                .getId());
            fee.setTransaction(transactionEntity.get());
            fee.setPaid(true);
        }
        saved = feeSpringRepository.saveAll(read)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Paid fees for {}, using fees {} and transaction {}", person.number(), fees, transaction);

        // TODO: test returned values
        return saved;
    }

    @Override
    public final Collection<Fee> save(final Collection<Fee> fees) {
        final Collection<FeeEntity> entities;
        final Collection<Fee>       saved;

        log.debug("Saving fees {}", fees);

        entities = fees.stream()
            .map(this::toEntity)
            .toList();
        // TODO: use map
        entities.forEach(this::loadId);
        saved = feeSpringRepository.saveAll(entities)
            .stream()
            .map(this::toDomain)
            .toList();

        log.debug("Saved fees {}", fees);

        return saved;
    }

    @Override
    public final Fee save(final Fee fee) {
        final FeeEntity entity;
        final FeeEntity saved;

        log.debug("Saving fee {}", fee);

        entity = toEntity(fee);
        loadId(entity);
        saved = feeSpringRepository.save(entity);

        log.debug("Saved fee {}", fee);

        return toDomain(saved);
    }

    private final Sorting.Property correct(final Sorting.Property property) {
        final Sorting.Property corrected;

        if (PERSON_PROPERTIES.contains(property.name())) {
            corrected = new Sorting.Property("p." + property.name(), property.direction());
        } else {
            corrected = property;
        }

        return corrected;
    }

    private final void loadId(final FeeEntity fee) {
        final Long                id;
        final Optional<FeeEntity> read;

        // TODO: optimize to use a single query
        read = feeSpringRepository.findByPersonIdAndDate(fee.getPerson()
            .getId(), fee.getDate());
        if (read.isPresent()) {
            id = read.get()
                .getId();
            fee.setId(id);
        }
    }

    private final Fee toDomain(final FeeEntity entity) {
        final Fee.Person                person;
        final Optional<Fee.Transaction> transaction;
        final PersonName                name;
        final YearMonth                 date;

        name = new PersonName(entity.getPerson()
            .getFirstName(),
            entity.getPerson()
                .getLastName());
        person = new Fee.Person(entity.getPerson()
            .getNumber(), name);

        if (entity.getPaid()) {
            transaction = Optional.of(new Fee.Transaction(entity.getTransaction()
                .getDate(),
                entity.getTransaction()
                    .getIndex()));
        } else {
            transaction = Optional.empty();
        }
        date = YearMonth.from(entity.getDate()
            .atZone(ZoneOffset.UTC));
        return new Fee(date, entity.getPaid(), person, transaction);
    }

    private final FeeEntity toEntity(final Fee fee) {
        final Optional<PersonEntity>      person;
        final Optional<TransactionEntity> transaction;
        final boolean                     paid;
        final FeeEntity                   entity;
        final Instant                     date;

        person = personSpringRepository.findByNumber(fee.person()
            .number());
        if (!person.isPresent()) {
            log.warn("Person with number {} not found", fee.person()
                .number());
        }
        if (fee.payment()
            .isPresent()) {
            paid = true;
            transaction = transactionSpringRepository.findByIndex(fee.payment()
                .get()
                .index());
            if (transaction.isEmpty()) {
                log.warn("Transaction with index {} not found", fee.payment()
                    .get()
                    .index());
            } else {
                transaction.get()
                    .setDate(fee.payment()
                        .get()
                        .date());
            }
        } else {
            paid = false;
            transaction = Optional.empty();
        }

        entity = new FeeEntity();
        entity.setPerson(person.orElse(null));
        date = fee.month()
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        entity.setDate(date);
        entity.setPaid(paid);
        entity.setTransaction(transaction.orElse(null));

        return entity;
    }

    private final Instant toInstant(final YearMonth month) {
        return month.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
    }

}
