
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;

public final class ServiceClassPredicate extends DescribedPredicate<JavaClass> {

    private static final String           PACKAGE                 = ".service";

    private final SyntheticClassPredicate syntheticClassPredicate = new SyntheticClassPredicate();

    public ServiceClassPredicate() {
        super("service classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return (javaClass.getPackageName()
            .endsWith(PACKAGE)) && (!syntheticClassPredicate.test(javaClass));
    }

}
