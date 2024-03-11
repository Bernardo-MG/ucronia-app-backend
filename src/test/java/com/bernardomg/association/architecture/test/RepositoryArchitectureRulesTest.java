
package com.bernardomg.association.architecture.test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.bernardomg.association.architecture.predicate.Predicates;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryArchitectureRulesTest {

    @ArchTest
    static final ArchRule jpa_entities_should_be_in_model_package             = classes()
        .that(Predicates.areJpaEntitiesClasses())
        .should()
        .resideInAPackage("..adapter.inbound.jpa.model..");

    @ArchTest
    static final ArchRule jpa_entities_should_be_suffixed                     = classes()
        .that(Predicates.areJpaEntitiesClasses())
        .should()
        .haveSimpleNameEndingWith("Entity");

    @ArchTest
    static final ArchRule spring_repositories_should_be_in_repository_package = classes()
        .that(Predicates.areSpringRepositoryClasses())
        .should()
        .resideInAPackage("..adapter.inbound.jpa.repository..");

    @ArchTest
    static final ArchRule spring_repositories_should_be_suffixed              = classes()
        .that(Predicates.areSpringRepositoryClasses())
        .should()
        .haveSimpleNameEndingWith("SpringRepository");

}
