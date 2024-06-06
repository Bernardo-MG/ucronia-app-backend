
package com.bernardomg.association.test.architecture.predicate;

import com.bernardomg.validation.Validator;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is a validator.
 */
public final class IsValidatorClass extends DescribedPredicate<JavaClass> {

    public IsValidatorClass() {
        super("validator classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.isAssignableTo(Validator.class);
    }

}
