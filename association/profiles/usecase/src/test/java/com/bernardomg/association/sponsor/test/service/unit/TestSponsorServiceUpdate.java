
package com.bernardomg.association.sponsor.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ContactMethodConstants;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.domain.exception.MissingSponsorException;
import com.bernardomg.association.sponsor.domain.model.Sponsor;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.association.sponsor.usecase.service.DefaultSponsorService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultSponsorService - update")
class TestSponsorServiceUpdate {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @InjectMocks
    private DefaultSponsorService   service;

    @Mock
    private SponsorRepository       sponsorRepository;

    @Test
    @DisplayName("When there is a sponsor with an existing identifier, then an exception is thrown")
    void testUpdate_IdentifierExists() {
        final ThrowingCallable action;
        final Sponsor          sponsor;

        // GIVEN
        sponsor = Sponsors.valid();

        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(sponsorRepository.existsByIdentifierForAnother(ProfileConstants.NUMBER, ProfileConstants.IDENTIFIER))
            .willReturn(true);

        // WHEN
        action = () -> service.update(sponsor);

        // THEN
        ValidationAssertions.assertThatFieldFails(action,
            new FieldFailure("existing", "identifier", ProfileConstants.IDENTIFIER));
    }

    @Test
    @DisplayName("When there is a not existing sponsor, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Sponsor          sponsor;
        final ThrowingCallable action;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(sponsor);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing sponsor, then an exception is thrown")
            .isInstanceOf(MissingSponsorException.class);
    }

    @Test
    @DisplayName("When there is a sponsor having padding whitespaces in first and last name, then these whitespaces are removed")
    void testUpdate_Padded_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.padded();

        given(sponsorRepository.exists(ContactMethodConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.valid());
    }

    @Test
    @DisplayName("When updating a sponsor, then the change is persisted")
    void testUpdate_PersistedData() {
        final Sponsor sponsor;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(sponsorRepository.save(Sponsors.nameChange())).willReturn(Sponsors.nameChange());

        // WHEN
        service.update(sponsor);

        // THEN
        verify(sponsorRepository).save(Sponsors.nameChange());
    }

    @Test
    @DisplayName("When updating a sponsor, then the change is returned")
    void testUpdate_ReturnedData() {
        final Sponsor sponsor;
        final Sponsor updated;

        // GIVEN
        sponsor = Sponsors.nameChange();

        given(sponsorRepository.exists(ProfileConstants.NUMBER)).willReturn(true);
        given(sponsorRepository.save(Sponsors.nameChange())).willReturn(Sponsors.nameChange());

        // WHEN
        updated = service.update(sponsor);

        // THEN
        Assertions.assertThat(updated)
            .as("sponsor")
            .isEqualTo(Sponsors.nameChange());
    }

}
