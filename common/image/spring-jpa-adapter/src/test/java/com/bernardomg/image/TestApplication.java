/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.bernardomg.image.test.configuration.TestConfiguration;

@SpringBootApplication
@Import({ TestConfiguration.class })
public class TestApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    public TestApplication() {
        super();
    }
}
