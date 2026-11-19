
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - isActive")
class ITMemberRepositoryIsActive {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("When the member is active, then is is indicated as so")
    @PositiveFeeType
    @ActiveMember
    void testIsActive_Active() {
        final boolean active;

        // WHEN
        active = repository.isActive(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(active)
            .as("active")
            .isTrue();
    }

    @Test
    @DisplayName("When the member is inactive, then is is indicated as so")
    @PositiveFeeType
    @InactiveMember
    void testIsActive_Inactive() {
        final boolean active;

        // WHEN
        active = repository.isActive(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(active)
            .as("active")
            .isFalse();
    }

    @Test
    @DisplayName("When the member doesn't exist, then it is indicated as so")
    void testIsActive_NoData() {
        final boolean active;

        // WHEN
        active = repository.isActive(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(active)
            .as("active")
            .isFalse();
    }

    @Test
    @DisplayName("When there is a profile with no member role, then it is indicated as so")
    @ValidProfile
    void testIsActive_NoMembership() {
        final boolean active;

        // WHEN
        active = repository.isActive(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(active)
            .as("active")
            .isFalse();
    }

}
