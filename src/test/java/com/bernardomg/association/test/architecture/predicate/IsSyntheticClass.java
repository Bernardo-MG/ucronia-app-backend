
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;

/**
 * Checks if a class is synthetic. Useful to avoid generated code.
 */
public final class IsSyntheticClass extends DescribedPredicate<JavaClass> {

    public IsSyntheticClass() {
        super("synthetic class");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.getModifiers()
            .contains(JavaModifier.SYNTHETIC);
    }

}
