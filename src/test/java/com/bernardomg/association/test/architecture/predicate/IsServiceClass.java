
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class IsServiceClass extends DescribedPredicate<JavaClass> {

    private static final String    PACKAGE                 = ".service";

    private final IsSyntheticClass syntheticClassPredicate = new IsSyntheticClass();

    public IsServiceClass() {
        super("service classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return (javaClass.getPackageName()
            .endsWith(PACKAGE)) && (!syntheticClassPredicate.test(javaClass));
    }

}
