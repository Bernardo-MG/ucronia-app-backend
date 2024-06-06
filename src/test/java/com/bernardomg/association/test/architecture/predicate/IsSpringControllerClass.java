
package com.bernardomg.association.test.architecture.predicate;

import org.springframework.stereotype.Controller;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is a Spring controller class. This is done by checking the class annotations.
 */
public final class IsSpringControllerClass extends DescribedPredicate<JavaClass> {

    public IsSpringControllerClass() {
        super("Spring controller classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isMetaAnnotatedWith(Controller.class);
    }

}
