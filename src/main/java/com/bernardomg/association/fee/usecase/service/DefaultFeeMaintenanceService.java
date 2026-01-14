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

package com.bernardomg.association.fee.usecase.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.domain.repository.FeeTypeRepository;
import com.bernardomg.association.member.domain.model.MemberProfile;
import com.bernardomg.association.member.domain.repository.MemberProfileRepository;

/**
 * Default implementation of the fee maintenance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class DefaultFeeMaintenanceService implements FeeMaintenanceService {

    /**
     * Logger for the class.
     */
    private static final Logger           log = LoggerFactory.getLogger(DefaultFeeMaintenanceService.class);

    private final FeeRepository           feeRepository;

    private final FeeTypeRepository       feeTypeRepository;

    private final MemberProfileRepository memberProfileRepository;

    public DefaultFeeMaintenanceService(final FeeRepository feeRepo, final FeeTypeRepository feeTypeRepo,
            final MemberProfileRepository memberProfileRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        feeTypeRepository = Objects.requireNonNull(feeTypeRepo);
        memberProfileRepository = Objects.requireNonNull(memberProfileRepo);
    }

    @Override
    public final void registerMonthFees() {
        final Collection<Fee>           feesToCreate;
        final Collection<MemberProfile> toRenew;
        final Collection<Long>          feeTypeIds;
        final Map<Long, FeeType>        feeTypes;

        log.info("Registering fees for this month");

        // Find fees to extend into the current month
        toRenew = memberProfileRepository.findAllToRenew();

        feeTypeIds = toRenew.stream()
            .map(MemberProfile::feeType)
            .map(MemberProfile.FeeType::number)
            .distinct()
            .toList();
        // TODO: verify the fee types exist
        feeTypes = feeTypeIds.stream()
            .map(id -> feeTypeRepository.findOne(id))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(FeeType::number, Function.identity()));

        feesToCreate = toRenew.stream()
            // Prepare for the current month
            .map(member -> toFeeThisMonth(member, feeTypes))
            // Make sure it doesn't exist
            .filter(this::notExists)
            .toList();

        feeRepository.save(feesToCreate);

        log.debug("Registered {} fees for this month", feesToCreate.size());
    }

    private final boolean notExists(final Fee fee) {
        return !feeRepository.exists(fee.member()
            .number(), fee.month());
    }

    private final Fee toFeeThisMonth(final MemberProfile member, final Map<Long, FeeType> feeTypes) {
        final Fee.FeeType feeFeeType;
        final FeeType     feeType;
        final Fee         fee;

        feeType = feeTypes.get(member.feeType()
            .number());
        feeFeeType = new Fee.FeeType(member.feeType()
            .number(),
            member.feeType()
                .name(),
            member.feeType()
                .amount());

        if (feeType.amount() == 0) {
            // No amount
            // Set to paid automatically
            fee = Fee.paid(YearMonth.now(), member.number(), member.name(), feeFeeType);
        } else {
            fee = Fee.unpaid(YearMonth.now(), member.number(), member.name(), feeFeeType);
        }

        return fee;
    }

}
