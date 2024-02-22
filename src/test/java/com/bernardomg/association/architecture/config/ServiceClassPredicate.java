
package com.bernardomg.association.architecture.config;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;

public final class ServiceClassPredicate extends DescribedPredicate<JavaClass> {

    private static final String PACKAGE = "service";

    public static final ServiceClassPredicate areServiceClasses() {
        return new ServiceClassPredicate();
    }

    private static boolean isSynthetic(final JavaClass javaClass) {
        return javaClass.getModifiers()
            .contains(JavaModifier.SYNTHETIC);
    }

    private ServiceClassPredicate() {
        super("are service classes");
    }

    @Override
    public final boolean test(final JavaClass javaClass) {
        final boolean service;

        if (!isSynthetic(javaClass) && !javaClass.getFullName()
            .endsWith("package-info.java")) {
            service = javaClass.getPackageName()
                .endsWith(PACKAGE)
                    && javaClass.getSimpleName()
                        .endsWith("Service");
        } else {
            service = false;
        }

        return service;
    }

}
