
package com.bernardomg.asset.test.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.bernardomg.asset.test.TestConfiguration;

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
