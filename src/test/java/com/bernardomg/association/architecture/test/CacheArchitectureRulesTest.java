
package com.bernardomg.association.architecture.test;

import static com.bernardomg.association.architecture.config.ControllerClassPredicate.areControllerClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class CacheArchitectureRulesTest {

    @ArchTest
    static final ArchRule cache_configuration_should_be_outbound = classes().that()
        .haveSimpleNameEndingWith("Caches")
        .should()
        .resideInAPackage("..adapter.outbound.cache..");

    @ArchTest
    static final ArchRule controllers_methods_should_be_cached   = methods().that()
        .areDeclaredInClassesThat(areControllerClasses())
        .and()
        .arePublic()
        .should()
        .beAnnotatedWith(Caching.class)
        .orShould()
        .beAnnotatedWith(Cacheable.class)
        .orShould()
        .beAnnotatedWith(CacheEvict.class);

    @ArchTest
    static final ArchRule services_should_not_be_cached          = methods().that()
        .areDeclaredInClassesThat(areControllerClasses())
        .should()
        .notBeAnnotatedWith(Caching.class)
        .orShould()
        .notBeAnnotatedWith(Cacheable.class)
        .orShould()
        .notBeAnnotatedWith(CacheEvict.class);

}
