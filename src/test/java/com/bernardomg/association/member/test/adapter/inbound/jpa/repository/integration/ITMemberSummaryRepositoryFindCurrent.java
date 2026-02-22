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

package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.model.MemberSummary;
import com.bernardomg.association.member.domain.repository.MemberSummaryRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveToNotRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("MemberRepository - find all")
class ITMemberSummaryRepositoryFindCurrent {

    @Autowired
    private MemberSummaryRepository repository;

    @Test
    @DisplayName("With an active member to renew, it is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll() {
        final MemberSummary summary;

        // WHEN
        summary = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.active())
                .isEqualTo(1L);
            softly.assertThat(summary.renew())
                .isEqualTo(1L);
        });
    }

    @Test
    @DisplayName("With an inactive member, nothing is returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_Inactive() {
        final MemberSummary summary;

        // WHEN
        summary = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.active())
                .isEqualTo(0L);
            softly.assertThat(summary.renew())
                .isEqualTo(0L);
        });
    }

    @Test
    @DisplayName("With no member, nothing is returned")
    void testFindAll_NoData() {
        final MemberSummary summary;

        // WHEN
        summary = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.active())
                .isEqualTo(0L);
            softly.assertThat(summary.renew())
                .isEqualTo(0L);
        });
    }

    @Test
    @DisplayName("With an active member to not renew, it is returned")
    @PositiveFeeType
    @ActiveToNotRenewMember
    void testFindAll_NotRenews() {
        final MemberSummary summary;

        // WHEN
        summary = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.active())
                .isEqualTo(1L);
            softly.assertThat(summary.renew())
                .isEqualTo(0L);
        });
    }

    @Test
    @DisplayName("With a profile with no member role, nothing is returned")
    @ValidProfile
    void testFindAll_WithoutMembership() {
        final MemberSummary summary;

        // WHEN
        summary = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(summary.active())
                .isEqualTo(0L);
            softly.assertThat(summary.renew())
                .isEqualTo(0L);
        });
    }

}
