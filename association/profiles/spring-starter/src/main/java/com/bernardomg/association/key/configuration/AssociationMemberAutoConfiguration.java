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

package com.bernardomg.association.key.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.key.adapter.inbound.jpa.repository.JpaKeyRepository;
import com.bernardomg.association.key.adapter.inbound.jpa.repository.KeySpringRepository;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.usecase.service.DefaultKeyService;
import com.bernardomg.association.key.usecase.service.KeyService;

@Configuration
@ComponentScan({ "com.bernardomg.association.key.adapter.outbound.rest.controller",
        "com.bernardomg.association.key.adapter.inbound.jpa" })
public class AssociationMemberAutoConfiguration {

    @Bean("keyRepository")
    public KeyRepository getKeyRepository(final KeySpringRepository keySpringRepository) {
        return new JpaKeyRepository(keySpringRepository);
    }

    @Bean("keyService")
    public KeyService getKeyService(final KeyRepository keyRepository) {
        return new DefaultKeyService(keyRepository);
    }

}
