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

package com.bernardomg.association.library.gamesystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.JpaGameSystemRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.gamesystem.usecase.service.DefaultGameSystemService;
import com.bernardomg.association.library.gamesystem.usecase.service.GameSystemService;

/**
 * Library configuration.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Configuration
public class LibraryGameSystemConfiguration {

    public LibraryGameSystemConfiguration() {
        super();
    }

    @Bean("gameSystemRepository")
    public GameSystemRepository getGameSystemRepository(final GameSystemSpringRepository gameSystemRepo) {
        return new JpaGameSystemRepository(gameSystemRepo);
    }

    @Bean("gameSystemService")
    public GameSystemService getGameSystemService(final GameSystemRepository gameSystemRepo) {
        return new DefaultGameSystemService(gameSystemRepo);
    }

}
