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

package com.bernardomg.association.contacts.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.contacts.domain.model.ContactInfo;
import com.bernardomg.association.contacts.test.factory.ContactSettings;
import com.bernardomg.association.contacts.test.factory.ContactSettingsConstants;
import com.bernardomg.association.contacts.usecase.constants.AssociationSettingsKey;
import com.bernardomg.association.contacts.usecase.service.DefaultContactInfoService;
import com.bernardomg.settings.domain.repository.SettingRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContactInfoService - getContactInfo")
class TestContactInfoServiceGetContactInfo {

    @InjectMocks
    private DefaultContactInfoService service;

    @Mock
    private SettingRepository         settingRepository;

    public TestContactInfoServiceGetContactInfo() {
        super();
    }

    @Test
    @DisplayName("When the calendar code exists, it is returned")
    void testGetContactInfo_CalendarCode() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(ContactSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(ContactSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.of(ContactSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(ContactSettings.instagram()));

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::calendar)
            .isEqualTo(ContactSettingsConstants.TEAMUP);
    }

    @Test
    @DisplayName("When the calendar code is missing, an empty string is returned")
    void testGetContactInfo_CalendarCode_Missing() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP)).willReturn(Optional.empty());
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(ContactSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.of(ContactSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(ContactSettings.instagram()));

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::calendar)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the email exists, it is returned")
    void testGetContactInfo_Email() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(ContactSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(ContactSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.of(ContactSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(ContactSettings.instagram()));

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::email)
            .isEqualTo(ContactSettingsConstants.EMAIL);
    }

    @Test
    @DisplayName("When the email is missing, an empty string is returned")
    void testGetContactInfo_Email_Missing() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(ContactSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(ContactSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.empty());
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(ContactSettings.instagram()));

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::email)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the Instagram URL exists, it is returned")
    void testGetContactInfo_Instagram() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(ContactSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(ContactSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.of(ContactSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(ContactSettings.instagram()));

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::instagram)
            .isEqualTo(ContactSettingsConstants.INSTAGRAM);
    }

    @Test
    @DisplayName("When the Instagram URL is missing, an empty string is returned")
    void testGetContactInfo_Instagram_Missing() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(ContactSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(ContactSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.of(ContactSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM)).willReturn(Optional.empty());

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::instagram)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the map code exists, it is returned")
    void testGetContactInfo_Map() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(ContactSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS))
            .willReturn(Optional.of(ContactSettings.googleMaps()));
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.of(ContactSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(ContactSettings.instagram()));

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::map)
            .isEqualTo(ContactSettingsConstants.GOOGLE_MAPS);
    }

    @Test
    @DisplayName("When the map code is missing, an empty string is returned")
    void testGetContactInfo_Map_Missing() {
        final ContactInfo contactInfo;

        // GIVEN
        given(settingRepository.findOne(AssociationSettingsKey.TEAMUP))
            .willReturn(Optional.of(ContactSettings.teamUp()));
        given(settingRepository.findOne(AssociationSettingsKey.GOOGLE_MAPS)).willReturn(Optional.empty());
        given(settingRepository.findOne(AssociationSettingsKey.EMAIL)).willReturn(Optional.of(ContactSettings.email()));
        given(settingRepository.findOne(AssociationSettingsKey.INSTAGRAM))
            .willReturn(Optional.of(ContactSettings.instagram()));

        // WHEN
        contactInfo = service.getContactInfo();

        // THEN
        Assertions.assertThat(contactInfo)
            .extracting(ContactInfo::map)
            .isEqualTo("");
    }
}
