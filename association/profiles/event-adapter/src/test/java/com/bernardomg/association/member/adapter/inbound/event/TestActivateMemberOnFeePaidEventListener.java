
package com.bernardomg.association.member.adapter.inbound.event;

import static org.mockito.Mockito.verify;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.fee.domain.event.FeePaidEvent;
import com.bernardomg.association.fee.test.configuration.factory.FeePaidEvents;
import com.bernardomg.association.member.usecase.service.MemberStatusService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivateMemberOnFeePaidEventListener - handle")
class TestActivateMemberOnFeePaidEventListener {

    @InjectMocks
    private ActivateMemberOnFeePaidEventListener listener;

    @Mock
    private MemberStatusService                  service;

    @Test
    @DisplayName("When a fee-paid event is handled, then the member is activated if current")
    void testHandle() {
        final FeePaidEvent event;

        // GIVEN
        event = FeePaidEvents.valid();

        // WHEN
        listener.handle(event);

        // THEN
        verify(service).activateIfCurrent(Instant.parse("2026-10-01T00:00:00Z"), 1L);
    }

}
