
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.web.bind.annotation.RestController;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class IsSpringControllerClass extends DescribedPredicate<JavaClass> {

    public IsSpringControllerClass() {
        super("controller classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAnnotatedWith(RestController.class);
    }

}
