
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Configuration;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class SpringConfigurationClassPredicate extends DescribedPredicate<JavaClass> {

    public SpringConfigurationClassPredicate() {
        super("configuration classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAnnotatedWith(Configuration.class) || javaClass.isAnnotatedWith(AutoConfiguration.class);
    }

}
