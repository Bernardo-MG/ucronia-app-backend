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

package com.bernardomg.settings.adapter.inbound.jpa.repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;
import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntityMapper;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;

@Transactional
public final class JpaSettingsRepository implements SettingRepository {

    /**
     * Logger for the class.
     */
    private static final Logger            log = LoggerFactory.getLogger(JpaSettingsRepository.class);

    private final SettingsSpringRepository settingSpringRepository;

    public JpaSettingsRepository(final SettingsSpringRepository settingSpringRepo) {
        super();

        settingSpringRepository = Objects.requireNonNull(settingSpringRepo);
    }

    @Override
    public final Collection<Setting> findAll() {
        final Collection<Setting> settings;
        final Sort                sort;

        log.debug("Finding all settings");

        sort = Sort.by("code");

        settings = settingSpringRepository.findAll(sort)
            .stream()
            .map(SettingsEntityMapper::toDomain)
            .toList();

        log.trace("Found all the settings: {}", settings);

        return settings;
    }

    @Override
    public final Optional<Setting> findOne(final String code) {
        final Optional<Setting> setting;

        log.trace("Finding setting with code {}", code);

        setting = settingSpringRepository.findByCode(code)
            .map(SettingsEntityMapper::toDomain);

        log.trace("Found setting with code {}: {}", code, setting);

        return setting;
    }

    @Override
    public final Float getFloat(final String code) {
        final Optional<Setting> read;
        final String            text;
        final Float             value;

        log.trace("Finding float setting value with code {}", code);

        read = settingSpringRepository.findByCode(code)
            .map(SettingsEntityMapper::toDomain);
        if (read.isPresent()) {
            text = read.get()
                .value();
            value = Float.valueOf(text);
        } else {
            value = 0f;
        }

        log.trace("Found float setting value with code {}: {}", code, value);

        return value;
    }

    @Override
    public final Setting save(final Setting setting) {
        final SettingsEntity           entity;
        final SettingsEntity           saved;
        final Optional<SettingsEntity> existing;

        log.trace("Saving setting {}", setting);

        entity = SettingsEntityMapper.toEntity(setting);

        existing = settingSpringRepository.findByCode(setting.code());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        saved = settingSpringRepository.save(entity);

        log.trace("Saved setting {}", saved);

        return SettingsEntityMapper.toDomain(saved);
    }

}
