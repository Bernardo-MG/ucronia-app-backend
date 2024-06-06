
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is in a service package, which means inside package named "service".
 */
public final class IsInServicePackage extends DescribedPredicate<JavaClass> {

    private static final String    PACKAGE                 = ".service";

    /**
     * TODO: this is meaningless here
     */
    private final IsSyntheticClass syntheticClassPredicate = new IsSyntheticClass();

    public IsInServicePackage() {
        super("service classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return (javaClass.getPackageName()
            .endsWith(PACKAGE)) && (!syntheticClassPredicate.test(javaClass));
    }

}
