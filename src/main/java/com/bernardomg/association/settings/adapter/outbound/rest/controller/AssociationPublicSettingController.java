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

package com.bernardomg.association.settings.adapter.outbound.rest.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.settings.adapter.outbound.cache.SettingsCaches;
import com.bernardomg.association.settings.adapter.outbound.rest.model.PublicSetting;
import com.bernardomg.security.access.Unsecured;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.usecase.service.SettingService;

import lombok.AllArgsConstructor;

/**
 * Settings REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/settings/public")
@AllArgsConstructor
public class AssociationPublicSettingController {

    private final SettingService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Unsecured
    @Cacheable(cacheNames = SettingsCaches.PUBLIC)
    public PublicSetting readOnePublic() {
        final String calendarId;
        final String mapId;

        calendarId = service.getOne("social.teamup.id")
            .map(Setting::getValue)
            .orElse(null);
        mapId = service.getOne("social.googleMap.id")
            .map(Setting::getValue)
            .orElse(null);
        return new PublicSetting(mapId, calendarId);
    }

}
