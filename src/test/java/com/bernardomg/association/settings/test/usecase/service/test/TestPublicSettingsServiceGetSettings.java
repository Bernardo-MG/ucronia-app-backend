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

package com.bernardomg.association.settings.test.usecase.service.test;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.settings.domain.PublicSettings;
import com.bernardomg.association.settings.test.configuration.factory.AssociationSettings;
import com.bernardomg.association.settings.test.configuration.factory.AssociationSettingsConstants;
import com.bernardomg.association.settings.usecase.AssociationSettingsKey;
import com.bernardomg.association.settings.usecase.DefaultPublicSettingsService;
import com.bernardomg.settings.domain.repository.SettingRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicSettingsService - getSettings")
class TestPublicSettingsServiceGetSettings {

    @InjectMocks
    private DefaultPublicSettingsService service;

    @Mock
    private SettingRepository            settingRepository;

    public TestPublicSettingsServiceGetSettings() {
        super();
    }

    @Test
    @DisplayName("Returns the calendar code")
    void testGetSettings_CalendarCode() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(AssociationSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(AssociationSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL))
            .willReturn(Optional.of(AssociationSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(AssociationSettings.instagram()));

        // WHEN
        settings = service.getSettings();

        // THEN
        Assertions.assertThat(settings)
            .extracting(PublicSettings::calendar)
            .isEqualTo(AssociationSettingsConstants.TEAMUP);
    }

    @Test
    @DisplayName("Returns the email")
    void testGetSettings_Email() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(AssociationSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(AssociationSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL))
            .willReturn(Optional.of(AssociationSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(AssociationSettings.instagram()));

        // WHEN
        settings = service.getSettings();

        // THEN
        Assertions.assertThat(settings)
            .extracting(PublicSettings::email)
            .isEqualTo(AssociationSettingsConstants.EMAIL);
    }

    @Test
    @DisplayName("Returns the instagram URL")
    void testGetSettings_Instagram() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(AssociationSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(AssociationSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL))
            .willReturn(Optional.of(AssociationSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(AssociationSettings.instagram()));

        // WHEN
        settings = service.getSettings();

        // THEN
        Assertions.assertThat(settings)
            .extracting(PublicSettings::instagram)
            .isEqualTo(AssociationSettingsConstants.INSTAGRAM);
    }

    @Test
    @DisplayName("Returns the map code")
    void testGetSettings_MapCode() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(AssociationSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(AssociationSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL))
            .willReturn(Optional.of(AssociationSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(AssociationSettings.instagram()));

        // WHEN
        settings = service.getSettings();

        // THEN
        Assertions.assertThat(settings)
            .extracting(PublicSettings::map)
            .isEqualTo(AssociationSettingsConstants.GOOGLE_MAPS);
    }

}
