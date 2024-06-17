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

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.test.config.factory.Donors;
import com.bernardomg.association.inventory.usecase.service.DefaultDonorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DonorService - get all")
class TestDonorServiceGetAll {

    @Mock
    private DonorRepository          donorRepository;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    @InjectMocks
    private DefaultDonorService      service;

    public TestDonorServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("When there is data it is returned")
    void testGetAll() {
        final Iterable<Donor> donors;
        final Pageable        pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        given(donorRepository.findAll(pageable)).willReturn(List.of(Donors.valid()));

        // WHEN
        donors = service.getAll(pageable);

        // THEN
        Assertions.assertThat(donors)
            .as("donors")
            .containsExactly(Donors.valid());
    }

    @Test
    @DisplayName("When there is no data nothing is returned")
    void testGetAll_NoData() {
        final Iterable<Donor> donors;
        final Pageable        pageable;

        // GIVEN
        pageable = Pageable.unpaged();

        given(donorRepository.findAll(pageable)).willReturn(List.of());

        // WHEN
        donors = service.getAll(pageable);

        // THEN
        Assertions.assertThat(donors)
            .as("donors")
            .isEmpty();
    }

    @Test
    @DisplayName("When sorting ascending by full name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Asc_FullName() {
        final Pageable    pageable;
        final Page<Donor> readDonors;

        // GIVEN
        readDonors = new PageImpl<>(List.of(Donors.valid()));
        given(donorRepository.findAll(pageableCaptor.capture())).willReturn(readDonors);

        pageable = PageRequest.of(0, 1, Sort.by("fullName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("firstName"), Order.asc("lastName"));
    }

    @Test
    @DisplayName("When sorting descending by full name, and applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Paged_Desc_FullName() {
        final Pageable    pageable;
        final Page<Donor> readDonors;

        // GIVEN
        readDonors = new PageImpl<>(List.of(Donors.valid()));
        given(donorRepository.findAll(pageableCaptor.capture())).willReturn(readDonors);

        pageable = PageRequest.of(0, 1, Sort.by(Direction.DESC, "fullName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.desc("firstName"), Order.desc("lastName"));
    }

    @Test
    @DisplayName("When sorting ascending by full name, and not applying pagination, it is corrected to the valid fields")
    void testGetAll_Sort_Unpaged_Asc_FullName() {
        final Pageable    pageable;
        final Page<Donor> readDonors;

        // GIVEN
        readDonors = new PageImpl<>(List.of(Donors.valid()));
        given(donorRepository.findAll(pageableCaptor.capture())).willReturn(readDonors);

        pageable = Pageable.unpaged(Sort.by("fullName"));

        // WHEN
        service.getAll(pageable);

        // THEN
        pageableCaptor.getValue()
            .getSort()
            .toList();
        Assertions.assertThat(pageableCaptor.getValue())
            .as("sort")
            .extracting(Pageable::getSort)
            .extracting(Sort::toList)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(Order.asc("firstName"), Order.asc("lastName"));
    }

}
