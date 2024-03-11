
package com.bernardomg.association.architecture.test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;

import java.io.Serializable;

import com.bernardomg.association.architecture.predicate.Predicates;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@AnalyzeClasses(packages = "com.bernardomg.association", importOptions = ImportOption.DoNotIncludeTests.class)
public class RepositoryArchitectureRulesTest {

    @ArchTest
    static final ArchRule jpa_entities_should_be_annotated                    = classes()
        .that(Predicates.areJpaEntitiesClasses())
        .should()
        .beAnnotatedWith(Entity.class)
        .andShould()
        .beAnnotatedWith(Table.class);

    @ArchTest
    static final ArchRule jpa_entities_should_be_in_model_package             = classes()
        .that(Predicates.areJpaEntitiesClasses())
        .should()
        .resideInAPackage("..adapter.inbound.jpa.model..");

    @ArchTest
    static final ArchRule jpa_entities_should_be_serializable                 = classes()
        .that(Predicates.areJpaEntitiesClasses())
        .should()
        .beAssignableTo(Serializable.class);

    @ArchTest
    static final ArchRule jpa_entities_should_be_suffixed                     = classes()
        .that(Predicates.areJpaEntitiesClasses())
        .should()
        .haveSimpleNameEndingWith("Entity");

    @ArchTest
    static final ArchRule jpa_entity_fields_should_be_annotated               = fields().that()
        .areDeclaredInClassesThat(Predicates.areJpaEntitiesClasses())
        .and()
        .areNotStatic()
        .should()
        .beAnnotatedWith(Column.class)
        .orShould()
        .beAnnotatedWith(OneToMany.class)
        .orShould()
        .beAnnotatedWith(ManyToOne.class)
        .orShould()
        .beAnnotatedWith(ManyToMany.class)
        .orShould()
        .beAnnotatedWith(OneToOne.class);

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
