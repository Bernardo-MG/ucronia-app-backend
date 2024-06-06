
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class SpringControllerClassPredicate extends DescribedPredicate<JavaClass> {

    public SpringControllerClassPredicate() {
        super("controller classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAnnotatedWith(RestController.class);
    }

}
