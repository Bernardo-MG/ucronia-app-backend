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

package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.calendar.game.adapter.outbound.rest.dto.ScheduledGameResponseDto;
import com.bernardomg.association.calendar.game.adapter.outbound.rest.model.ScheduledGameDtoMapper;
import com.bernardomg.association.calendar.game.domain.model.ScheduledGame;
import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.domain.permission.constant.Actions;

/**
 * Scheduled game publishing REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ScheduledGamePublishController implements ScheduledGamePublishApi {

    /**
     * Scheduled game service.
     */
    private final ScheduledGameService service;

    public ScheduledGamePublishController(final ScheduledGameService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "SCHEDULED_GAME", action = Actions.UPDATE)
    public ScheduledGameResponseDto publishScheduledGame(final Long number) {
        final ScheduledGame published;

        published = service.publish(number);
        return ScheduledGameDtoMapper.toResponseDto(published);
    }

}
