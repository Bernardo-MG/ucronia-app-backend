
package com.bernardomg.association.member.adapter.inbound.event;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.event.FeeDeletedEvent;
import com.bernardomg.association.fee.test.configuration.factory.FeeDeletedEvents;
import com.bernardomg.association.member.usecase.service.MemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeactivateMemberOnFeeDeletedEventListener - handle")
class TestDeactivateMemberOnFeeDeletedEventListener {

    @InjectMocks
    private DeactivateMemberOnFeeDeletedEventListener listener;

    @Mock
    private MemberStatusService                       service;

    @Test
    @DisplayName("When a paid fee-deleted event is handled, then the member is deactivated if current")
    void testHandle() {
        final FeeDeletedEvent event;

        // GIVEN
        event = FeeDeletedEvents.paid();

        // WHEN
        listener.handle(event);

        // THEN
        verify(service).deactivateIfCurrent(Instant.parse("2026-10-01T00:00:00Z"), 1L);
    }

    @Test
    @DisplayName("When an unpaid fee-deleted event is handled, then member status is unchanged")
    void testHandleUnpaidFee() {
        final FeeDeletedEvent event;

        // GIVEN
        event = FeeDeletedEvents.notPaid();

        // WHEN
        listener.handle(event);

        // THEN
        verifyNoInteractions(service);
    }

}
