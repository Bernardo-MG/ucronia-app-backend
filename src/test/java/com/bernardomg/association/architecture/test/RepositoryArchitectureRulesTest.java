
package com.bernardomg.association.architecture.test;

import static com.bernardomg.association.architecture.config.SpringRepositoryClassPredicate.areSpringRepositoryClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryArchitectureRulesTest {

    @ArchTest
    static final ArchRule spring_repositories_should_be_in_repository_package = classes()
        .that(areSpringRepositoryClasses())
        .should()
        .resideInAPackage("..adapter.inbound.jpa.repository..");

    @ArchTest
    static final ArchRule spring_repositories_should_be_suffixed              = classes()
        .that(areSpringRepositoryClasses())
        .should()
        .haveSimpleNameEndingWith("SpringRepository");

}
