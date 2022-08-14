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

package com.bernardomg.association.test.balance.integration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.bernardomg.association.balance.model.DtoPayment;
import com.bernardomg.association.balance.model.Payment;
import com.bernardomg.association.balance.model.PaymentType;
import com.bernardomg.association.balance.model.PersistentPayment;
import com.bernardomg.association.balance.repository.PaymentRepository;
import com.bernardomg.association.balance.service.DefaultPaymentService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default payment service - update")
@Sql({ "/db/queries/payment/single.sql" })
public class ITDefaultPaymentServiceUpdate {

    @Autowired
    private PaymentRepository     repository;

    @Autowired
    private DefaultPaymentService service;

    public ITDefaultPaymentServiceUpdate() {
        super();

        // TODO: Check invalid ids
    }

    @Test
    @DisplayName("Returns the previous data")
    public void testCreate_ReturnedData() {
        final Payment    result;
        final DtoPayment payment;

        payment = new DtoPayment();
        payment.setDescription("Payment");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(3);
        payment.setYear(4);

        result = service.update(1L, payment);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Payment", result.getDescription());
    }

    @Test
    @DisplayName("Adds no entity when updating")
    public void testUpdate_AddsNoEntity() {
        final DtoPayment payment;

        payment = new DtoPayment();
        payment.setDescription("Payment 123");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(3);
        payment.setYear(4);

        service.update(1L, payment);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Updates persisted data")
    public void testUpdate_PersistedData() {
        final DtoPayment        payment;
        final PersistentPayment entity;

        payment = new DtoPayment();
        payment.setDescription("Payment 123");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(3);
        payment.setYear(4);

        service.update(1L, payment);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Payment 123", entity.getDescription());
    }

}
