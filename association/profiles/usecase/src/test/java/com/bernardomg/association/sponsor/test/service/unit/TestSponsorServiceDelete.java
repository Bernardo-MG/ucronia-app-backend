
package com.bernardomg.association.sponsor.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.association.sponsor.domain.exception.MissingSponsorException;
import com.bernardomg.association.sponsor.domain.repository.SponsorRepository;
import com.bernardomg.association.sponsor.test.configuration.factory.Sponsors;
import com.bernardomg.association.sponsor.usecase.service.DefaultSponsorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultSponsorService - delete")
class TestSponsorServiceDelete {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @InjectMocks
    private DefaultSponsorService   service;

    @Mock
    private SponsorRepository       sponsorRepository;

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(sponsorRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Sponsors.valid()));

        // WHEN
        service.delete(ProfileConstants.NUMBER);

        // THEN
        verify(sponsorRepository).delete(ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When the sponsor doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_NotRemovesEntity() {
        final ThrowingCallable action;

        // GIVEN
        given(sponsorRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the sponsor doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingSponsorException.class);
    }

}
