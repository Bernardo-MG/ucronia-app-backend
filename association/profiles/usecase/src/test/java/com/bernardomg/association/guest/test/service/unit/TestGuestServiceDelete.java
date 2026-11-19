
package com.bernardomg.association.guest.test.service.unit;

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

import com.bernardomg.association.guest.domain.exception.MissingGuestException;
import com.bernardomg.association.guest.domain.repository.GuestRepository;
import com.bernardomg.association.guest.test.configuration.factory.Guests;
import com.bernardomg.association.guest.usecase.service.DefaultGuestService;
import com.bernardomg.association.profile.domain.repository.ContactMethodRepository;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGuestService - delete")
class TestGuestServiceDelete {

    @Mock
    private ContactMethodRepository contactMethodRepository;

    @Mock
    private GuestRepository         guestRepository;

    @InjectMocks
    private DefaultGuestService     service;

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.of(Guests.noGames()));

        // WHEN
        service.delete(ProfileConstants.NUMBER);

        // THEN
        verify(guestRepository).delete(ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When the guest doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_NotRemovesEntity() {
        final ThrowingCallable action;

        // GIVEN
        given(guestRepository.findOne(ProfileConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the guest doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingGuestException.class);
    }

}
