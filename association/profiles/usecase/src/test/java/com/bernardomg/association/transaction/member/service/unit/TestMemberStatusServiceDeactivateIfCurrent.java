
package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.DefaultMemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultMemberStatusService - deactivateIfCurrent")
class TestMemberStatusServiceDeactivateIfCurrent {

    @Mock
    private MemberRepository           memberRepository;

    @InjectMocks
    private DefaultMemberStatusService service;

    @Test
    @DisplayName("When activating for the current month, then the member is deactivated")
    void testDeactivateIfCurrent_CurrentMonth() {
        final Instant date;
        final Long    number;

        // GIVEN
        given(memberRepository.findOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));
        date = YearMonth.now()
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        number = MemberConstants.NUMBER;

        // WHEN
        service.deactivateIfCurrent(date, number);

        // THEN
        verify(memberRepository).save(Members.inactiveNoRenew());
    }

    @Test
    @DisplayName("When activating for the next month, then the member is not deactivated")
    void testDeactivateIfCurrent_NextMonth() {
        final Instant date;
        final Long    number;

        // GIVEN
        date = ZonedDateTime.now()
            .plus(1, ChronoUnit.MONTHS)
            .toInstant();
        number = MemberConstants.NUMBER;

        // WHEN
        service.deactivateIfCurrent(date, number);

        // THEN
        verify(memberRepository, never()).save(any());
    }

    @Test
    @DisplayName("When activating for the previous month, then the member is not deactivated")
    void testDeactivateIfCurrent_PreviousMonth() {
        final Instant date;
        final Long    number;

        // GIVEN
        date = ZonedDateTime.now()
            .minus(1, ChronoUnit.MONTHS)
            .toInstant();
        number = MemberConstants.NUMBER;

        // WHEN
        service.deactivateIfCurrent(date, number);

        // THEN
        verify(memberRepository, never()).save(any());
    }

}
