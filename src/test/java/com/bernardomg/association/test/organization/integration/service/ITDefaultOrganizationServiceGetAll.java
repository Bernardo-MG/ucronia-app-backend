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

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.organization.model.Organization;
import com.bernardomg.association.organization.service.DefaultOrganizationService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default organization service - get all")
@Sql({ "/db/queries/organization/multiple.sql" })
public class ITDefaultOrganizationServiceGetAll {

    @Autowired
    private DefaultOrganizationService service;

    public ITDefaultOrganizationServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<? extends Organization> result;

        result = service.getAll();

        Assertions.assertEquals(5, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data")
    public void testGetAll_Data() {
        final Iterator<? extends Organization> result;
        Organization                           data;

        result = service.getAll()
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Org 1", data.getName());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Org 2", data.getName());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Org 3", data.getName());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Org 4", data.getName());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Org 5", data.getName());
    }

}
