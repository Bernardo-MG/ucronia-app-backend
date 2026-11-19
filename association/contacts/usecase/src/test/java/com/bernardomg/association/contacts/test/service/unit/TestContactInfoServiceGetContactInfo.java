
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
@DisplayName("DefaultContactInfoService - getContactInfo")
class TestContactInfoServiceGetContactInfo {

    @InjectMocks
    private DefaultContactInfoService service;

    @Mock
    private SettingRepository         settingRepository;

    @Test
    @DisplayName("When the calendar code exists, then it is returned")
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
            .as("When the calendar code exists, then it is returned")
            .extracting(ContactInfo::calendar)
            .isEqualTo(ContactSettingsConstants.TEAMUP);
    }

    @Test
    @DisplayName("When the calendar code is missing, then an empty string is returned")
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
            .as("When the calendar code is missing, then an empty string is returned")
            .extracting(ContactInfo::calendar)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the email exists, then it is returned")
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
            .as("When the email exists, then it is returned")
            .extracting(ContactInfo::email)
            .isEqualTo(ContactSettingsConstants.EMAIL);
    }

    @Test
    @DisplayName("When the email is missing, then an empty string is returned")
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
            .as("When the email is missing, then an empty string is returned")
            .extracting(ContactInfo::email)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the Instagram URL exists, then it is returned")
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
            .as("When the Instagram URL exists, then it is returned")
            .extracting(ContactInfo::instagram)
            .isEqualTo(ContactSettingsConstants.INSTAGRAM);
    }

    @Test
    @DisplayName("When the Instagram URL is missing, then an empty string is returned")
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
            .as("When the Instagram URL is missing, then an empty string is returned")
            .extracting(ContactInfo::instagram)
            .isEqualTo("");
    }

    @Test
    @DisplayName("When the map code exists, then it is returned")
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
            .as("When the map code exists, then it is returned")
            .extracting(ContactInfo::map)
            .isEqualTo(ContactSettingsConstants.GOOGLE_MAPS);
    }

    @Test
    @DisplayName("When the map code is missing, then an empty string is returned")
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
            .as("When the map code is missing, then an empty string is returned")
            .extracting(ContactInfo::map)
            .isEqualTo("");
    }
}
