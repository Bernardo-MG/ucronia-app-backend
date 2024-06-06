
package com.bernardomg.association.test.architecture.predicate;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;

public final class SyntheticClassPredicate extends DescribedPredicate<JavaClass> {

    public SyntheticClassPredicate() {
        super("synthetic class");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        return javaClass.getModifiers()
            .contains(JavaModifier.SYNTHETIC);
    }

}
