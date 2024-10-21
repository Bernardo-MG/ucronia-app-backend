/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.person.adapter.inbound.event;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.bernardomg.association.event.domain.MonthStartEvent;
import com.bernardomg.association.person.usecase.service.MemberStatusService;
import com.bernardomg.event.listener.EventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * Listens for the month start event and deactivates members which are not renewed.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Slf4j
@Component
public final class DeactivateNotRenewedOnMonthStartEventListener implements EventListener<MonthStartEvent> {

    private final MemberStatusService service;

    public DeactivateNotRenewedOnMonthStartEventListener(final MemberStatusService serv) {
        super();

        service = Objects.requireNonNull(serv);
    }

    @Override
    public final Class<MonthStartEvent> getEventType() {
        return MonthStartEvent.class;
    }

    @Override
    public final void handle(final MonthStartEvent event) {
        log.debug("Deactivating members not renewing at the start of {}", event.getMonth());
        service.deactivateNotRenewed();
        log.debug("Deactivated members not renewing at the start of {}", event.getMonth());
    }

}
