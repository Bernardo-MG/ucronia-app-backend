
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
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - existsByIdentifierForAnother")
class ITMemberRepositoryExistsByIdentifierForAnother {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("When another member profile is provided, then it exists")
    @PositiveFeeType
    @ActiveMember
    void testExists_AnotherUser() {
        final boolean exists;

        // WHEN
        exists = repository.existsByIdentifierForAnother(MemberConstants.ALTERNATIVE_NUMBER,
            MemberConstants.IDENTIFIER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is an existing identifier, then it exists")
    @PositiveFeeType
    @ActiveMember
    void testExists_Existing() {
        final boolean exists;

        // WHEN
        exists = repository.existsByIdentifierForAnother(MemberConstants.NUMBER, MemberConstants.IDENTIFIER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is no member profile, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByIdentifierForAnother(MemberConstants.NUMBER, MemberConstants.IDENTIFIER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is a not existing identifier, then it doesn't exist")
    @PositiveFeeType
    @ActiveMember
    void testExists_NotExisting() {
        final boolean exists;

        // WHEN
        exists = repository.existsByIdentifierForAnother(MemberConstants.NUMBER, "abc");

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
