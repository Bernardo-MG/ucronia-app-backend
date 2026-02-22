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

package com.bernardomg.association.member.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.model.MemberSummary;
import com.bernardomg.association.member.domain.repository.MemberSummaryRepository;
import com.bernardomg.association.member.test.configuration.factory.MemberSummaries;
import com.bernardomg.association.member.usecase.service.DefaultMemberSummaryService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberSummaryService - get summary")
class TestMemberSummaryServiceGetSummary {

    @Mock
    private MemberSummaryRepository     memberSummaryRepository;

    @InjectMocks
    private DefaultMemberSummaryService service;

    @Test
    @DisplayName("It returns the summary")
    void testGetSummary_NoData() {
        final MemberSummary summary;
        final MemberSummary existing;

        // GIVEN
        existing = MemberSummaries.valid();
        given(memberSummaryRepository.findCurrent()).willReturn(existing);

        // WHEN
        summary = service.getSummary();

        // THEN
        Assertions.assertThat(summary)
            .isEqualTo(existing);
    }

}
