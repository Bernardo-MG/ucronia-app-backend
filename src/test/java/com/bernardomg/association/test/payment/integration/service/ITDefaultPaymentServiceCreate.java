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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.payment.model.DtoPayment;
import com.bernardomg.association.payment.model.Payment;
import com.bernardomg.association.payment.model.PaymentType;
import com.bernardomg.association.payment.model.PersistentPayment;
import com.bernardomg.association.payment.repository.PaymentRepository;
import com.bernardomg.association.payment.service.DefaultPaymentService;
import com.bernardomg.association.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Default payment service - create")
public class ITDefaultPaymentServiceCreate {

    @Autowired
    private PaymentRepository     repository;

    @Autowired
    private DefaultPaymentService service;

    public ITDefaultPaymentServiceCreate() {
        super();
    }

    @Test
    @DisplayName("Adds an entity when creating")
    public void testCreate_AddsEntity() {
        final DtoPayment payment;

        payment = new DtoPayment();
        payment.setDescription("Payment");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(3);
        payment.setYear(4);

        service.create(payment);

        Assertions.assertEquals(1L, repository.count());
    }

    @Test
    @DisplayName("Persists the data")
    public void testCreate_PersistedData() {
        final DtoPayment        payment;
        final PersistentPayment entity;

        payment = new DtoPayment();
        payment.setDescription("Payment");
        payment.setType(PaymentType.INCOME);
        payment.setQuantity(1l);
        payment.setDay(2);
        payment.setMonth(3);
        payment.setYear(4);

        service.create(payment);
        entity = repository.findAll()
            .iterator()
            .next();

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals("Payment", entity.getDescription());
    }

    @Test
    @DisplayName("Returns the created data")
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

        result = service.create(payment);

        Assertions.assertNotNull(result.getId());
        Assertions.assertEquals("Payment", result.getDescription());
    }

}
