
package com.bernardomg.association.test.architecture.predicate;

public class Predicates {

    public static final IsSpringCachedMethod areCachedMethod() {
        return new IsSpringCachedMethod();
    }

    public static final IsSpringCacheAnnotation areCachingAnnotation() {
        return new IsSpringCacheAnnotation();
    }

    public static final IsSpringConfigurationClass areConfigurationClasses() {
        return new IsSpringConfigurationClass();
    }

    public static final IsSpringControllerClass areControllerClasses() {
        return new IsSpringControllerClass();
    }

    public static final IsJpaAnnotation areJpaAnnotation() {
        return new IsJpaAnnotation();
    }

    public static final IsJpaAnnotatedClass areJpaEntitiesClasses() {
        return new IsJpaAnnotatedClass();
    }

    public static final IsRepositoryNotSpringClass areRepositoryClasses() {
        return new IsRepositoryNotSpringClass();
    }

    public static final IsInServicePackage areServiceClasses() {
        return new IsInServicePackage();
    }

    public static final IsSpringRepositoryClass areSpringRepositoryClasses() {
        return new IsSpringRepositoryClass();
    }

    public static final IsValidatorClass areValidatorClasses() {
        return new IsValidatorClass();
    }

}
