
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

/**
 * Checks if a class is in a service package, which means inside package named "service".
 */
public final class IsInServicePackage extends DescribedPredicate<JavaClass> {

    /**
     * TODO: careful when checking by package
     */
    private static final String    INNER_PACKAGE           = ".service.";

    /**
     * TODO: careful when checking by package
     */
    private static final String    TAIL_PACKAGE            = ".service";

    /**
     * TODO: this is meaningless here
     */
    private final IsSyntheticClass syntheticClassPredicate = new IsSyntheticClass();

    public IsInServicePackage() {
        super("class in service package");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        final String packageName = javaClass.getPackageName();
        return (packageName.contains(INNER_PACKAGE) || packageName.endsWith(TAIL_PACKAGE))
                && !syntheticClassPredicate.test(javaClass);
    }

}
