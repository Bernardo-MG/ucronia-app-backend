/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.association.test.organization.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.organization.model.DtoOrganization;
import com.bernardomg.association.organization.model.Organization;
import com.bernardomg.association.organization.model.PersistentOrganization;
import com.bernardomg.association.organization.repository.OrganizationRepository;
import com.bernardomg.association.organization.service.DefaultOrganizationService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default organization service - update")
@Sql({ "/db/queries/organization/single.sql" })
public class ITDefaultOrganizationServiceUpdate {

    @Autowired
    private OrganizationRepository     repository;

    @Autowired
    private DefaultOrganizationService service;

    public ITDefaultOrganizationServiceUpdate() {
        super();

        // TODO: Check invalid ids
    }

    @Test
    @DisplayName("Adds no entity when updating")
    public void testUpdate_AddsNoEntity() {
        final DtoOrganization member;

        member = new DtoOrganization();
        member.setName("Org 123");

        service.update(1L, member);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final DtoOrganization        member;
        final PersistentOrganization entity;

        member = new DtoOrganization();
        member.setName("Org 123");

        service.update(1L, member);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Org 123", entity.getName());
    }

    @Test
    @DisplayName("Returns the previous data")
    public void testUpdate_ReturnedData() {
        final Organization    result;
        final DtoOrganization member;

        member = new DtoOrganization();
        member.setName("Org 123");

        result = service.update(1L, member);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Org 123", result.getName());
    }

}
