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

package com.bernardomg.association.inventory.test.usecase.service.unit;

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

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.config.factory.DonorConstants;
import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.association.inventory.usecase.service.DefaultDonorService;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.config.factory.MemberConstants;
import com.bernardomg.test.assertion.ValidationAssertions;
import com.bernardomg.validation.failure.FieldFailure;

@ExtendWith(MockitoExtension.class)
@DisplayName("DonorService - create")
class TestDonorServiceCreate {

    @Mock
    private DonorRepository     donorRepository;

    @Mock
    private MemberRepository    memberRepository;

    @InjectMocks
    private DefaultDonorService service;

    public TestDonorServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a donor with an empty name, an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable execution;
        final Donor            author;

        // GIVEN
        author = Donors.emptyName();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.create(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", " "));
    }

    @Test
    @DisplayName("With a donor with an existing member, an exception is thrown")
    void testCreate_ExistingMember() {
        final ThrowingCallable execution;
        final Donor            author;

        // GIVEN
        author = Donors.withMember();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(donorRepository.existsByMember(MemberConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.create(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            FieldFailure.of("member", "existing", MemberConstants.NUMBER));
    }

    @Test
    @DisplayName("With a donor with an existing name, an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable execution;
        final Donor            author;

        // GIVEN
        author = Donors.withoutMember();

        given(donorRepository.existsByName(DonorConstants.NAME)).willReturn(true);

        // WHEN
        execution = () -> service.create(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "existing", DonorConstants.NAME));
    }

    @Test
    @DisplayName("With a not existing member, an exception is thrown")
    void testCreate_NotExistingMember_Exception() {
        final ThrowingCallable execution;
        final Donor            donor;

        // GIVEN
        donor = Donors.withMember();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.create(donor);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingMemberException.class);
    }

    @Test
    @DisplayName("With a valid donor with member, the donor is persisted")
    void testCreate_WithMember_PersistedData() {
        final Donor author;

        // GIVEN
        author = Donors.withMember();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(donorRepository.findNextNumber()).willReturn(DonorConstants.NUMBER);

        // WHEN
        service.create(author);

        // THEN
        verify(donorRepository).save(Donors.withMember());
    }

    @Test
    @DisplayName("With a valid donor with member, the created donor is returned")
    void testCreate_WithMember_ReturnedData() {
        final Donor author;
        final Donor created;

        // GIVEN
        author = Donors.withMember();

        given(memberRepository.exists(MemberConstants.NUMBER)).willReturn(true);
        given(donorRepository.findNextNumber()).willReturn(DonorConstants.NUMBER);
        given(donorRepository.save(Donors.withMember())).willReturn(Donors.withMember());

        // WHEN
        created = service.create(author);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Donors.withMember());
    }

    @Test
    @DisplayName("With a valid donor without member, the donor is persisted")
    void testCreate_WithoutMember_PersistedData() {
        final Donor author;

        // GIVEN
        author = Donors.withoutMember();

        given(donorRepository.findNextNumber()).willReturn(DonorConstants.NUMBER);

        // WHEN
        service.create(author);

        // THEN
        verify(donorRepository).save(Donors.withoutMember());
    }

    @Test
    @DisplayName("With a valid donor without member, the created donor is returned")
    void testCreate_WithoutMember_ReturnedData() {
        final Donor author;
        final Donor created;

        // GIVEN
        author = Donors.withoutMember();

        given(donorRepository.save(Donors.withoutMember())).willReturn(Donors.withoutMember());
        given(donorRepository.findNextNumber()).willReturn(DonorConstants.NUMBER);

        // WHEN
        created = service.create(author);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Donors.withoutMember());
    }

}
