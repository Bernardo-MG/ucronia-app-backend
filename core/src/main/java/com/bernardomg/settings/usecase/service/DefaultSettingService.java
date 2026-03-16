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

package com.bernardomg.settings.usecase.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.settings.domain.exception.MissingSettingException;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

@Transactional
public final class DefaultSettingService implements SettingService {

    /**
     * Logger for the class.
     */
    private static final Logger     log = LoggerFactory.getLogger(DefaultSettingService.class);

    private final SettingRepository settingRepository;

    public DefaultSettingService(final SettingRepository settingRepo) {
        super();

        settingRepository = Objects.requireNonNull(settingRepo);
    }

    @Override
    public final Collection<Setting> getAll() {
        final Collection<Setting> settings;

        log.debug("Reading settings");

        settings = settingRepository.findAll();

        log.debug("Read settings: {}", settings);

        return settings;
    }

    @Override
    public final Optional<Setting> getOne(final String code) {
        final Optional<Setting> setting;

        log.debug("Reading setting {}", code);

        setting = settingRepository.findOne(code);
        if (setting.isEmpty()) {
            log.error("Missing setting {}", code);
            throw new MissingSettingException(code);
        }

        log.debug("Read setting {}", setting);

        return setting;
    }

    @Override
    public final Setting update(final String code, final String value) {
        final Setting toSave;
        final Setting existing;
        final Setting saved;

        log.debug("Updating code {} with value {}", code, value);

        existing = settingRepository.findOne(code.trim())
            .orElseThrow(() -> {
                log.error("Missing configuration {}", code.trim());
                throw new MissingSettingException(code.trim());
            });

        toSave = new Setting(existing.type(), code, value);

        saved = settingRepository.save(toSave);

        log.debug("Updated code {}: {}", code, saved);

        return saved;
    }

}
