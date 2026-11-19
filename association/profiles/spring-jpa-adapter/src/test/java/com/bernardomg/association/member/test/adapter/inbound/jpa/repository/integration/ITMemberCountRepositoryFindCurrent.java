
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.model.MemberCount;
import com.bernardomg.association.member.domain.repository.MemberCountRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveToNotRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberCountRepository - findCurrent")
class ITMemberCountRepositoryFindCurrent {

    @Autowired
    private MemberCountRepository repository;

    @Test
    @DisplayName("When there is an active member to renew, then it is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindAll() {
        final MemberCount count;

        // WHEN
        count = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(count.active())
                .as("When there is an active member to renew, then it is returned")
                .isEqualTo(1L);
            softly.assertThat(count.renew())
                .as("When there is an active member to renew, then it is returned")
                .isEqualTo(1L);
        });
    }

    @Test
    @DisplayName("When there is an inactive member, then nothing is returned")
    @PositiveFeeType
    @InactiveMember
    void testFindAll_Inactive() {
        final MemberCount count;

        // WHEN
        count = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(count.active())
                .as("When there is an inactive member, then nothing is returned")
                .isEqualTo(0L);
            softly.assertThat(count.renew())
                .as("When there is an inactive member, then nothing is returned")
                .isEqualTo(0L);
        });
    }

    @Test
    @DisplayName("When there is no member, then nothing is returned")
    void testFindAll_NoData() {
        final MemberCount count;

        // WHEN
        count = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(count.active())
                .as("When there is no member, then nothing is returned")
                .isEqualTo(0L);
            softly.assertThat(count.renew())
                .as("When there is no member, then nothing is returned")
                .isEqualTo(0L);
        });
    }

    @Test
    @DisplayName("When there is an active member to not renew, then it is returned")
    @PositiveFeeType
    @ActiveToNotRenewMember
    void testFindAll_NotRenews() {
        final MemberCount count;

        // WHEN
        count = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(count.active())
                .as("When there is an active member to not renew, then it is returned")
                .isEqualTo(1L);
            softly.assertThat(count.renew())
                .as("When there is an active member to not renew, then it is returned")
                .isEqualTo(0L);
        });
    }

    @Test
    @DisplayName("When there is a profile with no member role, then nothing is returned")
    @ValidProfile
    void testFindAll_WithoutMembership() {
        final MemberCount count;

        // WHEN
        count = repository.findCurrent();

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(count.active())
                .as("When there is a profile with no member role, then nothing is returned")
                .isEqualTo(0L);
            softly.assertThat(count.renew())
                .as("When there is a profile with no member role, then nothing is returned")
                .isEqualTo(0L);
        });
    }

}
