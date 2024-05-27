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

package com.bernardomg.association.fee.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.config.factory.Fees;
import com.bernardomg.association.fee.usecase.service.DefaultMyFeesService;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.association.person.test.config.factory.Persons;
import com.bernardomg.association.security.user.domain.repository.UserPersonRepository;
import com.bernardomg.association.security.user.test.config.factory.UserConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("My fees service - get all for user in session")
class TestMyFeesServiceGetAllForUserInSession {

    @Mock
    private Authentication       authentication;

    @Mock
    private FeeRepository        feeRepository;

    @InjectMocks
    private DefaultMyFeesService myFeesService;

    @Mock
    private UserDetails          userDetails;

    @Mock
    private UserPersonRepository userPersonRepository;

    @Test
    @DisplayName("When there is data it is returned")
    void testGetAllForUserInSession() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        given(userDetails.getUsername()).willReturn(UserConstants.USERNAME);
        given(authentication.getPrincipal()).willReturn(userDetails);

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        given(userPersonRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.findAllForMember(PersonConstants.NUMBER, pageable)).willReturn(List.of(Fees.paid()));

        // WHEN
        fees = myFeesService.getAllForUserInSession(pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .containsExactly(Fees.paid());
    }

    @Test
    @DisplayName("When the user is anonymous, nothing is returned")
    void testGetAllForUserInSession_Anonymous() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        given(authentication.getPrincipal()).willReturn(
            new AnonymousAuthenticationToken("key", "principal", List.of(new SimpleGrantedAuthority("role"))));

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        // WHEN
        fees = myFeesService.getAllForUserInSession(pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetAllForUserInSession_NoData() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        given(userDetails.getUsername()).willReturn(UserConstants.USERNAME);
        given(authentication.getPrincipal()).willReturn(userDetails);

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        given(userPersonRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.of(Persons.valid()));
        given(feeRepository.findAllForMember(PersonConstants.NUMBER, pageable)).willReturn(List.of());

        // WHEN
        fees = myFeesService.getAllForUserInSession(pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

    @Test
    @DisplayName("When the user has no member, nothing is returned")
    void testGetAllForUserInSession_NoMember() {
        final Iterable<Fee> fees;
        final Pageable      pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        given(userDetails.getUsername()).willReturn(UserConstants.USERNAME);
        given(authentication.getPrincipal()).willReturn(userDetails);

        SecurityContextHolder.getContext()
            .setAuthentication(authentication);

        given(userPersonRepository.findByUsername(UserConstants.USERNAME)).willReturn(Optional.empty());

        // WHEN
        fees = myFeesService.getAllForUserInSession(pageable);

        // THEN
        Assertions.assertThat(fees)
            .as("fees")
            .isEmpty();
    }

}
