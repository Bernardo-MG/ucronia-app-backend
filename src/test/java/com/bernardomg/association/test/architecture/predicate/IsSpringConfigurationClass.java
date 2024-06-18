
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is a Spring configuration class. This is done by checking the class annotations, and ignoring
 * Spring Boot application classes.
 */
public final class IsSpringConfigurationClass extends DescribedPredicate<JavaClass> {

    public IsSpringConfigurationClass() {
        super("Spring configuration classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return (javaClass.isMetaAnnotatedWith(Configuration.class)
                || javaClass.isMetaAnnotatedWith(AutoConfiguration.class))
                && (!javaClass.isMetaAnnotatedWith(SpringBootApplication.class));
    }

}
