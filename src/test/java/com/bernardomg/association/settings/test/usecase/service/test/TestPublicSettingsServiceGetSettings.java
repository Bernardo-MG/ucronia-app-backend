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
import com.bernardomg.association.settings.usecase.service.AssociationSettingsKey;
import com.bernardomg.association.settings.usecase.service.DefaultPublicSettingsService;
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
    @DisplayName("When the calendar code exists, it is returned")
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
    @DisplayName("When the calendar code is missing, an empty string is returned")
    void testGetSettings_CalendarCode_Missing() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP)).willReturn(Optional.empty());
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
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the email exists, it is returned")
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
    @DisplayName("When the email is missing, an empty string is returned")
    void testGetSettings_Email_Missing() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(AssociationSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(AssociationSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.empty());
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(AssociationSettings.instagram()));

        // WHEN
        settings = service.getSettings();

        // THEN
        Assertions.assertThat(settings)
            .extracting(PublicSettings::email)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the Instagram URL exists, it is returned")
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
    @DisplayName("When the Instagram URL is missing, an empty string is returned")
    void testGetSettings_Instagram_Missing() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(AssociationSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(AssociationSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL))
            .willReturn(Optional.of(AssociationSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM)).willReturn(Optional.empty());

        // WHEN
        settings = service.getSettings();

        // THEN
        Assertions.assertThat(settings)
            .extracting(PublicSettings::instagram)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the map code exists, it is returned")
    void testGetSettings_Map() {
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

    @Test
    @DisplayName("When the map code is missing, an empty string is returned")
    void testGetSettings_Map_Missing() {
        final PublicSettings settings;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(AssociationSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS)).willReturn(Optional.empty());
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL))
            .willReturn(Optional.of(AssociationSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(AssociationSettings.instagram()));

        // WHEN
        settings = service.getSettings();

        // THEN
        Assertions.assertThat(settings)
            .extracting(PublicSettings::map)
            .isEqualTo("");
    }
}
