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

package com.bernardomg.association.test.payment.integration.service;

import java.util.Iterator;

import org.apache.commons.collections4.IterableUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.payment.model.DtoPayment;
import com.bernardomg.association.payment.model.Payment;
import com.bernardomg.association.payment.model.PaymentType;
import com.bernardomg.association.payment.service.DefaultPaymentService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default payment service - get all")
@Sql({ "/db/queries/payment/multiple.sql" })
public class ITDefaultPaymentServiceGetAll {

    @Autowired
    private DefaultPaymentService service;

    public ITDefaultPaymentServiceGetAll() {
        super();
    }

    @Test
    @DisplayName("Returns all the entities")
    public void testGetAll_Count() {
        final Iterable<Payment> result;
        final Payment           sample;

        sample = new DtoPayment();

        result = service.getAll(sample);

        Assertions.assertEquals(5, IterableUtils.size(result));
    }

    @Test
    @DisplayName("Returns all the entities data")
    public void testGetAll_Data() {
        final Iterator<? extends Payment> result;
        Payment                           data;
        final Payment                     sample;

        sample = new DtoPayment();

        result = service.getAll(sample)
            .iterator();

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Payment 1", data.getDescription());
        Assertions.assertEquals(1, data.getQuantity());
        Assertions.assertEquals(PaymentType.INCOME, data.getType());
        Assertions.assertEquals(2, data.getDay());
        Assertions.assertEquals(3, data.getMonth());
        Assertions.assertEquals(2020, data.getYear());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Payment 2", data.getDescription());
        Assertions.assertEquals(1, data.getQuantity());
        Assertions.assertEquals(PaymentType.INCOME, data.getType());
        Assertions.assertEquals(2, data.getDay());
        Assertions.assertEquals(3, data.getMonth());
        Assertions.assertEquals(2020, data.getYear());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Payment 3", data.getDescription());
        Assertions.assertEquals(1, data.getQuantity());
        Assertions.assertEquals(PaymentType.INCOME, data.getType());
        Assertions.assertEquals(2, data.getDay());
        Assertions.assertEquals(3, data.getMonth());
        Assertions.assertEquals(2020, data.getYear());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Payment 4", data.getDescription());
        Assertions.assertEquals(1, data.getQuantity());
        Assertions.assertEquals(PaymentType.INCOME, data.getType());
        Assertions.assertEquals(2, data.getDay());
        Assertions.assertEquals(3, data.getMonth());
        Assertions.assertEquals(2020, data.getYear());

        data = result.next();
        Assertions.assertNotNull(data.getId());
        Assertions.assertEquals("Payment 5", data.getDescription());
        Assertions.assertEquals(1, data.getQuantity());
        Assertions.assertEquals(PaymentType.INCOME, data.getType());
        Assertions.assertEquals(2, data.getDay());
        Assertions.assertEquals(3, data.getMonth());
        Assertions.assertEquals(2020, data.getYear());
    }

}
