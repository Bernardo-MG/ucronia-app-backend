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

package com.bernardomg.association.inventory.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.inventory.adapter.inbound.jpa.repository.JpaDonorRepository;
import com.bernardomg.association.inventory.domain.repository.DonorRepository;
import com.bernardomg.association.inventory.security.register.InventoryPermissionRegister;
import com.bernardomg.association.inventory.usecase.service.DefaultDonorService;
import com.bernardomg.association.inventory.usecase.service.DonorService;
import com.bernardomg.association.person.adapter.inbound.jpa.repository.PersonSpringRepository;
import com.bernardomg.security.permission.initializer.usecase.PermissionRegister;

/**
 * Transaction configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class InventoryConfiguration {

    public InventoryConfiguration() {
        super();
    }

    @Bean("donorRepository")
    public DonorRepository getDonorRepository(final PersonSpringRepository personSpringRepository) {
        return new JpaDonorRepository(personSpringRepository);
    }

    @Bean("donorService")
    public DonorService getDonorService(final DonorRepository donorRepository) {
        return new DefaultDonorService(donorRepository);
    }

    @Bean("inventoryPermissionRegister")
    public PermissionRegister getInventoryPermissionRegister() {
        return new InventoryPermissionRegister();
    }

}
