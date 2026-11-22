
package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.model.MemberCount;
import com.bernardomg.association.member.domain.repository.MemberCountRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberCounts;
import com.bernardomg.association.member.usecase.service.DefaultMemberCountService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberCountService - getCount")
class TestMemberCountServiceGetCount {

    @Mock
    private MemberCountRepository     memberCountRepository;

    @InjectMocks
    private DefaultMemberCountService service;

    @Test
    @DisplayName("When the component is queried, then the count is returned")
    void testGetCount_NoData() {
        final MemberCount count;
        final MemberCount existing;

        // GIVEN
        existing = MemberCounts.valid();
        given(memberCountRepository.findCurrent()).willReturn(existing);

        // WHEN
        count = service.getCount();

        // THEN
        Assertions.assertThat(count)
            .as("It returns the count")
            .isEqualTo(existing);
    }

}
