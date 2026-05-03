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

package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.domain.repository.FeeMemberRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - find fee type")
class ITFeeMemberRepositoryGetFeeType {

    @Autowired
    private FeeMemberRepository repository;

    @Test
    @DisplayName("With a member, the fee type is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindOne() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findFeeType(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .contains(FeeTypes.positive());
    }

    @Test
    @DisplayName("With no member, nothing is returned")
    void testFindOne_NoData() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findFeeType(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .isEmpty();
    }

    @Test
    @DisplayName("With a member with no member role, it returns nothing")
    @ValidProfile
    void testFindOne_NoMembership() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findFeeType(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .isEmpty();
    }

}
